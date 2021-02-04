## Case 3
### _End of Financial Year_
@snap[midpoint span-100]
@ol
- Context-wide events
- Fun-out effect
@olend
@snap[end]

---
## End of Financial Year
</br>
@quote[When Financial Year Ends, all Effective Discounts need to be refreshed](Domain Expert)

@fa[calendar-day fa-5x]
---
## Context-wide events
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/context_wide.png)

---
## Fan-out effect
<br/>
<br/>
<br/>
@snap[south span-65]
![](time-based-domain-events/modelling/.assets/diagrams/fan_out.png)
@snapend

---
## Unit of time
<br/>
<br/>
<br/>
What <b>End of Financial Year</b> does really mean?


---
@snap[north]
## Immediacy
@snapend

@snap[midpoint fragment]
@quote[How quickly all Discounts need to be rerfreshed?]
@snapend

@snap[south fragment]
@quote[Immediately, all at once!](Domain Expert)
@snapend

---
@snap[north]
## Eventuality
@snapend

@snap[midpoint fragment span-100]
@quote[What are consequences if all Discount are refreshed till 7am?]
@snapend

@snap[south fragment span-100]
@quote[Hmm...not big, shops open at 8am to spend Discount](Domain Expert)
@snapend

---
## Drivers

@quote[Whenever Financial Year Ends, all Discount needs to be refreshed to 8am]


@snap[midpoint text-left text-05 text-italic  span-100 text-bold fragment]
</br>
</br>
</br>
refreshStart=00:00

totalRefreshingTime < refreshStart + 8h

totalRefreshingTime = (singleDiscountRefreshTime * numberOfDiscounts) / concurrencyLevel

@snapend

---
## Scheduling?

@snap[midpoint span-65]
![](time-based-domain-events/modelling/.assets/diagrams/schedulling.png)
@snapend

---
## Act on the current state
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/financial_year_end_model.png)