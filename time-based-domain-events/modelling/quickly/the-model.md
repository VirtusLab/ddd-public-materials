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

Note:

Of course, it's worth asking a question whether it's a valid approach to have discount as an aggregate before reaching eligibility.
It has this benefit of encapsulating rules regarding eligibility date calculation.

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/5.png)
@snapend

Note:
which is put in the DiscountCreate event.

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/6.png)
@snapend

Note:

Here fun part - process manager. The approach is more about scheduling actions.
As a trigger we have the initial eligibility date, but also it takes care of potential failures with re-scheduling necessary actions.

---
## The model

@snap[west]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount-eligibility-fragments/7.png)
@snapend

Note:
Having invariants inside Discount aggregate we can ensure that association can only happen if the eligibility date is reached.

---
## The model
@snap[west snap-100]
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/discount_eligibility_first_model.png)
@snapend

Note:

Finally, we can say that Discount is Associated or maybe rather we should say that Discount Become Effective?

I'll leave you with that questions and jump to yet another, slightly different case.

I hope you're still with me.