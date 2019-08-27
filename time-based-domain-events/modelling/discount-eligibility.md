## Case 1
### _Discount Eligibility_
<br/>
@quote[After 6 months of service, **Discount Become Effective**](Domain Expert)

---
@title[Time]
@snap[midpoint span-100]
@fa[clock fa-7x]
@snapend

---
## Time-based domain events
### A niche
<br/>
@quote[Schedulers and Timers are also very interesting types of events, but they are a often times an implementation-level consideration.](K. Webber)

---
## Time-based events
### A niche
<br/>
@quote[...expiring time frame will generally have a descriptive name that will become part of the Ubiquitous Language...](V. Vernon)

---
## Time-based domain events
### A niche
<br/>
@quote[Therefore, you have a name for that particular time-based Domain Event.](V. Vernon)
  

---
@title[Time-based domain events]
@quote[After 6 months of service, **Discount Become Effective**](Domain Expert)
@snap[midpoint]
![](time-based-domain-events/modelling/.assets/diagrams/discount_became_effective.png)
@snapend

---
@title[Time-based domain events]
@quote[**expiring time frame will generally have a descriptive name**] 

@snap[midpoint span-100]
@fa[question fa-5x]

How this expiring time frame is called?

@snapend

---
@title[Discount Eligibility]
### Discount Eligibility Date
@quote[When **Eligibility Date Is Reached**, then **Discount Become Effective**](Domain Expert)

![](time-based-domain-events/modelling/.assets/diagrams/discount_eligibility_date.png)

---
@title[Discount Eligibility]
@quote[...then **Discount Become Effective**](Domain Expert)
@snap[midpoint span-100]

@fa[question fa-5x]

What does it mean?
@snapend

---
@title[Discount Eligibility]
@quote[When **Discount Became Effective**, then **Discount Account is created** and then **associated to Employee**](Domain Expert)


![](time-based-domain-events/modelling/.assets/diagrams/discount_account_association.png)

---
@title[Discount Eligibility]
@quote[However, to create **Discount Account**, an external system needs to be called](Domain Expert)
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
![](time-based-domain-events/modelling/.assets/diagrams/external_system.png)


---
### What if??
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
- What events should be produced by the Aggregate?
- How to implement _event scheduling_? Is it appropriate?
@olend

---
## First model
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/discount_eligibility_first_model.png)


---
## Model summary
@ol
- @color[green](Scheduled Commands)
- @color[green](Identified expiring time frame)
- @color[green](Identified an Aggregate)
- @color[green](EligibilityDate invariants in Discount Aggregate)
- @color[red](State management in ProcessManager)
- @color[red](External dependencies - retries)
- @color[red](Deviated from Domain Expert's model)
- @color[red](No DiscountBecameEffective event at all)
@olend
