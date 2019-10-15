---
@title[Kafka]
## Events streaming 
<br/>
![](time-based-domain-events/kafka/.assets/diagrams/colleague_benefits_kafka.png)

---
@title[Drivers]
## Drivers
</br>
@ol
1. Precision of **days**
1. 450k employees
1. ~9k leavers per month
1. Isolation of processing
1. Usage of existing infrastructure -> Kafka
1. Fault-tolerance 
1. Duplication avoidance
@olend

---
## Kafka Streams
@img[span-60](time-based-domain-events/kafka/.assets/img/kafka-topology.jpg)

@snap[south span-100]
@size[0.4em](https://docs.confluent.io)
@snapend

---
## Kafka Streams DSL
</br>

```java
KTable<EmployeeId, Employee> employee = new KStreamBuilder()
    .stream("employee-events")
    .groupBy((employeeId, event) -> event.employeeId(), ...)
    .aggregate(..., "employee_store");
```

---
## How to postpone event publishing?
</br>
![](time-based-domain-events/kafka/.assets/diagrams/postponing_events.png)

---
## Event Scheduler
</br>
![](time-based-domain-events/kafka/.assets/diagrams/schedulling.png)

---
## PAPI
Kafka Streams Processor API

```java
public interface Processor<K, V> {
     /**
     (...)
     * ProcessorContext#schedule(Duration, PunctuationType, Punctuator) schedule
     * Punctuator#punctuate(long) called periodically
     * and to access attached StateStore.
     (...)
     */
    void init(ProcessorContext context);
    
    void process(K key, V value);
    
    void close();
}
```
@[4-5](Schedule!)

---
## Processor Context

```java
public interface ProcessorContext {
    (...)
    Cancellable schedule(final Duration interval,
                     final PunctuationType type,
                     final Punctuator callback) throws IllegalArgumentException;
    
    <K, V> void forward(final K key, final V value);
    (...)
}
```

--- 
## Processor Time
</br>
@ol
- STREAM_TIME
- WALL_CLOCK_TIME
@olend

---
## Where put not effective events?

@fa[question-circle fa-7x]

---
## State Stores
@img[span-60](time-based-domain-events/kafka/.assets/img/state_stores.jpg)


@snap[south span-100]
@size[0.4em](https://docs.confluent.io)
@snapend

---
### Is it feasible?
</br>
![](time-based-domain-events/kafka/.assets/diagrams/schedulling.png)

---
### The idea
</br>
@ol
1. Build Kafka Stream App
1. Use **StateStore** to store not effective events
1. Schedule **Punctuation** to poll for effective events from **StateStore**
1. Forward all effective events
@olend

