## Case 1
### _Discount Eligibility_
<br/>
@quote[After 6 months of service, **Discount Becomes Effective**](Domain Expert)

Note:
Hmmm...6 months of service, indeed it's some indication of important period of time.

---
@title[Time]
@snap[midpoint span-100]
@fa[clock fa-7x]
@snapend

Note:
How we can model such time-based requirement?

---
@title[Time-based domain events]
@quote[After 6 months of service, **Discount Become Effective**](Domain Expert)
@snap[midpoint]
![](time-based-domain-events/modelling/.assets/diagrams/discount_became_effective.png)
@snapend


Note:
Maybe it's just a domain-event. Sounds legit, doesn't it?

---
@title[Time-based domain events]
@quote[expiring time frame will generally have a descriptive name]

@quote[Therefore, you have a name for that particular time-based Domain Event.](V. Vernon)

@snap[midpoint span-100]
<br/>
<br/>
<br/>
How this expiring time frame is called?


@snapend

Note:
Studying red-book we can find something interesting in that matter.
But the question remains.

---
@title[Discount Eligibility]
### Discount Eligibility Date
@quote[When **Eligibility Date Is Reached**, then **Discount Becomes Effective**](Domain Expert)

![](time-based-domain-events/modelling/.assets/diagrams/discount_eligibility_date.png)

Note:
Distilling more, and more knowledge we finally got to another descriptive concept, namely, DiscountEligibilityDate.
