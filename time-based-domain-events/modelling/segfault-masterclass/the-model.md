## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/1.png)
@snapend

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/2.png)
@snapend

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/3.png)
@snapend

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/4.png)
@snapend

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/5.png)
@snapend

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/6.png)
@snapend


---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/7.png)
@snapend

---
## The model
@snap[west snap-100]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount_eligibility_first_model.png)
@snapend


---
## Testability

@snap[west span-25]
@box[bg-green](Given </br></br>)
@box[bg-blue](When </br></br>)
@box[bg-gold](Then </br></br>)
@snapend

@snap[midpoint]
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/discount_eligibility_test.png)
@snapend

---
## Model summary
### @color[green](Pros)
@ol
- @color[green](Scheduled Commands)
- @color[green](Identified expiring time frame)
- @color[green](Identified an Aggregate)
- @color[green](EligibilityDate invariants in Discount Aggregate)
- @color[green](Model is testable)
@olend

---
## Model summary
### @color[red](Cons)
@ol
- @color[red](State and time management in ProcessManager)
- @color[red](External dependencies - retries)
- @color[red](Deviated from Domain Expert's model)
- @color[red](No DiscountBecameEffective event at all)
@olend
