---
@title[Reschedule]
## Are we done?
</br>
@fa[question-circle fa-7x]

---
## Postponing Leavers
</br>
![](time-based-domain-events/kafka/.assets/diagrams/postponing_leavers.png)

---
### The idea
</br>
@ol
1. Extend **Translator** to forward **Reschedule** command
1. Add new **StateStore** to store **EventKey** to **EffectiveDate** mapping
1. Extend **Scheduler** top put **EventKey** to **EffectiveDate** mapping
1. Add new **Processor** to update **EffectiveDate**
1. Extend **Cleaner** with new **StateStore**
@olend

---
## Reschedule Topology
@img[bg-white span-45](time-based-domain-events/kafka/.assets/img/reschedule_topology.png)


---
@title[Rescheduler]
@snap[west snap-100]

```kotlin
class Rescheduler : AbstractProcessor<String, Reschedule>() {
    (...)
  
    override fun process(key: String, command: Reschedule) = reschedule(command)

    private fun reschedule(command: Reschedule) =
        onEffectiveDateFor(command.eventKey(), storeNewSchedule(command))
        
    private fun onEffectiveDateFor(
        eventKey: String,
        onExistingEffectiveDate: (String) -> Unit
        //key=employeeId:eventType -> value=2019-10-10
    ) = effectiveDates.get(eventKey)?.let { onExistingEffectiveDate(it) }

    private fun storeNewSchedule(command: Reschedule): (String) -> Unit = 
    { currentEffectiveDate ->
        logger.info { "Rescheduling event ${command.event}" }
        effectiveEvents.delete(command.scheduleKeyOf(currentEffectiveDate))
        effectiveEvents.put(command.scheduleKey(), command.event)
        //key=employeeId:eventType -> value=2019-10-10
        effectiveDates.put(command.eventKey(), command.effectiveDate())
    }

    (...)
}
```
@[6-7]
@[9-12]
@[12-13]
@[15-21]
@[17]
@[18]
@[19-20]