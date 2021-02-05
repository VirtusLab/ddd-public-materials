## Case 3
### _End of Financial Year_
@snap[midpoint span-100]
@ol
- Context-wide events
- Fun-out effect
@olend
@snapend
  
---
## Context-wide events

@quote[When Financial Year Ends, all Effective Discounts need to be refreshed](Domain Expert)
![](time-based-domain-events/modelling/.assets/diagrams/context_wide.png)

Note:

Once again, we deal with some precisely defined point in time, some expiring time frame over the year.

---
## Fan-out effect
<br/>
<br/>
<br/>
@snap[south span-65]
![](time-based-domain-events/modelling/.assets/diagrams/fan_out.png)
@snapend

Note:

What we can observe as well is that context-wide event causes a lot of local reactions of individual entities/

It's known as a fun-out effect.

With a large number of entities it may be a technical challenge.

---
## Unit of time
<br/>
<br/>
<br/>
What <b>End of Financial Year</b> does really mean?

Note:

So digging into unit of time in that context is very crucial.

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
<br/>

Note:

If we ask question like:

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

Note:

but asking about consequences may lead to interesting insights.

Like here:

---
## Drivers

@snap[midpoint span-100]
@quote[Whenever Financial Year Ends, all Discount needs to be refreshed to 8am]
@snapend

Note:

With such stated requirement we can even build some quality metrics to drive our design.

---
## Scheduling?

@snap[midpoint span-65]
![](time-based-domain-events/modelling/.assets/diagrams/schedulling.png)
@snapend

Note:

Coming back to the model. should we use invented earlier event scheduler for the purpose of refreshing?

Rather not.

---
## Act on the current state
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/financial_year_end_model.png)

Note:

Instead, let's stick to scheduled commands and act upon current state of Discount keeping invariants where they should belong to.