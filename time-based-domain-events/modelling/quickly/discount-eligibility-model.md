---
@title[Discount Eligibility]
@quote[...then **Discount Becomes Effective**](Domain Expert)
@snap[midpoint span-100]

@fa[question fa-5x]

What does it mean?
@snapend

Note:

Nevertheless, it wasn't enough to understand what we should do, so the questions arose - What does it mean that Discount Become Effective?

---
@title[Discount Eligibility]
@quote[When **Discount Became Effective**, then **Discount Account is created** and then **associated to Employee**](Domain Expert)

</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount_account_association.png)

Note:
Here we have some answer. It looks like it's not only about the fact that DiscountBecomeEffective but
rather when and what should happen next.

We need create an account and assigned it. Only then Employee can spend Discount.

What more?

---
@title[Discount Eligibility]
@quote[However, to create **Discount Account**, an external system needs to be called](Domain Expert)
@quote[Only when **Discount Account is created** then **Discount** can be associated](Domain Expert)

@snap[south]
@fa[frown fa-5x]
@snapend

Note:
And here comes dragons - external dependencies
Even worse - such dependencies may indicate wrong boundaries of responsibilities.

Of course, being involved into engineering means asking a lot of questions like this ->

---
### What if??
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/external_system_crash.png)

Note:

What if external dependencies is unavailable?
We asked such question, and the response brought some interesting insights.
---
@title[ReSchedule]
@quote[It's ok to re-schedule this event to next day](Domain Expert)

@snap[midpoint]
@fa[flushed fa-5x]
But events are facts in the past? How to schedule them?
@snapend

Note:

Are we still care about this precise point in time named EligibilityDate?

This and other concerns came into our mind.

With all of it in mind, we proposed some model.