---
@title[Discount Eligibility]
@quote[...then **Discount Becomes Effective**](Domain Expert)
@snap[midpoint span-100]

@fa[question fa-5x]

What does it mean?
@snapend

---
@title[Discount Eligibility]
@quote[When **Discount Became Effective**, then **Discount Account is created** and then **associated to Employee**](Domain Expert)

</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount_account_association.png)

---
@title[Discount Eligibility]
@quote[However, to create **Discount Account**, an external system needs to be called](Domain Expert)
</br>
@fa[meh fa-5x]

---
@title[Discount Eligibility]
@quote[However, to create **Discount Account**, an external system needs to be called](Domain Expert)
@quote[Only when **Discount Account is created** then **Discount** can be associated](Domain Expert)

@snap[south]
@fa[frown fa-5x]
@snapend

---
@title[Discount Eligibility]
<br/>
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/external_system.png)


---
### What if??
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/external_system_crash.png)

---
@title[ReSchedule]
@quote[It's ok to re-schedule this event to next day](Domain Expert)

@snap[midpoint]
@fa[flushed fa-5x]
But events are facts in the past? How to schedule them?
@snapend

---
@title[Retries]
![](time-based-domain-events/modelling/.assets/diagrams/external_system_retry.png)


---
## Concerns
@ol
- Is **DiscountBecameEffective** event really relevant?
- Should Time-based domain events be part of event stream?
- Scheduled Commands vs Scheduled Events?
- What is an **Aggregate**?
@olend

---
## Aggregate
@snap[midpoint]
![](time-based-domain-events/modelling/.assets/diagrams/discount_aggregate.png)

@snapend
