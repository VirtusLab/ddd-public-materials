## Passage of Time Event
</br>
@quote[Replace cron jobs and scheduled commands, with an agnostic event to indicate the passage of time.](M.Verraes)

---
## Passage of Time Event
</br>
@snap[midpoint]
![](time-based-domain-events/modelling/.assets/diagrams/passage-of-time/passage_of_time.png)
@snapend

---
## Next model

@snap[midpoint span-70]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/passage-of-time/discount_eligibility_model.png)
@snapend

---
## Testability

@snap[west span-25]
@box[bg-green](Given </br></br>)
@box[bg-blue](When </br></br>)
@box[bg-gold](Then </br></br>)
@snapend

@snap[midpoint]
![](time-based-domain-events/modelling/.assets/diagrams/passage-of-time/passage_of_time_test.png)
@snapend

---
## Next model summary
### @color[green](Pros)
@ol
- @color[green](Usage of DiscountBecameEffective event)
- @color[green](Discount not created until associated)
- @color[green](No scheduled commands)
- @color[green](High level tests)
@olend


---
## Next model summary
### @color[red](Cons)
@ol
- @color[red](Huge technical complexity)
- @color[red](Additional state to manage - DiscountEligibility)
- @color[red](Not obvious retry)
- @color[red](Not intuitive design to follow)
@olend

---
## Complexity
</br>
@quote[Complexity in software is the result of inherent domain complexity - essential - mixing with technical complexity - accidental] (S.Millet)

