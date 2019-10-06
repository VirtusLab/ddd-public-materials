---
@title[Cancel]
## What next?
</br>
@fa[question-circle fa-7x]

---
## Undecided leavers
![](time-based-domain-events/kafka/.assets/diagrams/undecided_leavers.png)

---
## YAP
### YetAnotherProcessor
#### Canceller

```kotlin

   private fun deleteSchedule(command: Cancel): (String) -> Unit = { currentEffectiveDate ->
        logger.info { "Cancelling event ${command.event}" }
        effectiveEvents.delete(command.scheduleKeyOf(currentEffectiveDate))
        effectiveDates.delete(command.eventKey())

    }
```
---
## Final Topology
@img[bg-white span-45](time-based-domain-events/kafka/.assets/img/final_topology.png)
