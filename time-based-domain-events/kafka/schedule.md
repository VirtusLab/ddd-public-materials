---
@title[Schedule]
## Schedule Topology
@img[bg-white span-30](time-based-domain-events/kafka/.assets/img/schedule_topology.png)

---
@title[Translator]
@snap[west snap-100]
```kotlin
    class Translator : AbstractProcessor<String, TimeBasedEvent>() {
    
        private val logger = KotlinLogging.logger {}
    
        override fun process(key: String, event: TimeBasedEvent) =
            when (event) {
                is EmployeeMarkedAsLeaver -> schedule(translate(event))
            }
            
        private fun translate(event: TimeBasedEvent) : EffectiveEvent = 
            when(event) {
                is EmployeeMarkedAsLeaver -> EmployeeLeft(event.employeeId, event.leavingDate)
            }
    
        private fun schedule(event: EffectiveEvent) {
            logger.info { "Forwarding $event to Scheduler" }
            context()
                .forward(
                    event.id, 
                    Schedule(event), 
                    To.child(Scheduler.NAME)
                 )
        }
    }
```
@[5-8]
@[10-13]
@[15-23]
@snapend

---
## Scheduler
@snap[west snap-100]
```kotlin
class Scheduler : AbstractProcessor<String, Schedule>() {
    (...)
    
    override fun process(key: String, command: Schedule) = schedule(command)

    private fun schedule(command: Schedule) = command.run {
        logger.info { "Scheduling $event" }
        // effective-date:employeeId:event-type 
        // i.e 2019-10-10:543678:EmployeeLeft
        effectiveEvents.put(scheduleKey(), event)   
        }

    (...)
}
```
@[4-5]
@[8-10]
@snapend
---
@snap[north snap-100]
#### EffectiveEventsForwarder
@snapend

@snap[west span-100]
```kotlin
class EffectiveEventsForwarder(private val duration: Duration) : AbstractProcessor<String, Command>() {

    override fun init(context: ProcessorContext) {
        context.schedule(
            duration, 
            PunctuationType.WALL_CLOCK_TIME
        ) { 
            forwardAndCommit() 
        }
    }

    private fun forwardAndCommit() =
        forwardEffectiveEvents().also { context().commit() }

    private fun forwardEffectiveEvents() =
        onEffectiveEvents { iterator ->
            iterator.forEach { forward(it) } 
        }
    
   private fun effectiveEvents(iterator: (KeyValueIterator<String, EffectiveEvent>) -> Unit) =
        //key:2019-10-10:543678:EmployeeLeft -> value:EffectiveEvent
        effectiveEvents 
            // from["0"] to["2019-10-11"]
            .range("0", Time.tomorrow().isoString())  
                .use { iterator(it) }
        
    private fun forward(keyValue: KeyValue<String, EffectiveEvent>) = 
        keyValue.run {  
                logger.info { "Forwarding event $value" }
            context().forward(value.id, value)
        }
}

```
@[4-9]
@[15-18]
@[20-22]
@[23-24]
@[26-29]
@snapend

---
## Scenarios

```kotlin
            scenario("should forward past dated event") {
                val pastDatedEvent = pastDatedEvent()
                val expectedEvent = translate(pastDatedEvent)

                publishEvent(pastDatedEvent)

                shouldForwardEvent(expectedEvent)
                shouldNotForwardNextEvent()
            }

            scenario("should forward future dated event on effective date") {
                val futureDatedEvent = futureDatedEvent()
                val expectedEvent = translate(futureDatedEvent)

                publishEvent(futureDatedEvent)
                shouldNotForwardNextEvent()

                Time.setToday { futureDatedEvent.effectiveDate }
                shouldForwardEvent(expectedEvent)

                shouldNotForwardNextEvent()
            }
```
@[18](Mocked Time)

---?code=time-based-domain-events/kafka/codebase/src/main/kotlin/com/virtuslab/jit/Time.kt&lang=kotlin 
@snap[north-east snap-100]
#### Time
@snapend

---
@snap[midpoint snap-100]
## Does it work?
@snapend

---
## Yes but

```kotlin
   private fun effectiveEvents(iterator: (KeyValueIterator<String, EffectiveEvent>) -> Unit) =
        //key:2019-10-10:543678:EmployeeLeft -> value:EffectiveEvent
        effectiveEvents 
            // from["0"] to["2019-10-11"]
            .range("0", Time.tomorrow().isoString())  
            .use { iterator(it) }

```
@[4-5]
---
## Cleaner
@snap[west snap-100]
```kotlin
class Cleaner : AbstractProcessor<String, EffectiveEvent>() {
    (...)
    override fun process(key: String, event: EffectiveEvent) = clean(event)

    private fun clean(event: EffectiveEvent): Unit = stores.run {
        logger.info { "Cleaning stores for effective event $event" }
        effectiveEvents.delete(event.effectiveDateKey())
    }
    
    (...)
}
```
@[5-8]
@snapend
---
### Schedule topology with Cleaner
@img[bg-white span-50](time-based-domain-events/kafka/.assets/img/scheduler_with_cleaner_topology.png)