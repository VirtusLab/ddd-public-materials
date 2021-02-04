## Case 2
### _Leavers_
@snap[midpoint span-100]
@ol
- Just in time events
- Event Scheduler!
@olend
@snapend


Note:

We can think that leaving a company is a final stage of employee workcycle.
Reality will verify it very shortly.

Here we are about to discuss a phenomena of Just in time events, and what is more Event Scheduler

---
## Leavers 
@quote[Whenever **Employee leaves**, an **Effective Discount** should be **disassociated**](Domain Expert)
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/leavers.png)

Note:
Sounds simple.
A from left to right flow. Quite easy we thought.

---
## Contexts dependency
</br>
</br>
</br>
![](time-based-domain-events/modelling/.assets/diagrams/contexts.png)

Note:
However, I've already mentioned a dependency between those 2 contexts

---
## Events for future
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/events_for_future.png)

Note:
And our lovely friends doesn't speak our language. 
They are more about collecting facts around Employee decisions and produces EmployeeMarkedAsLeaver with leaving date included.

Interestingly, this date can be in the past or in the future. 

What to do then?

---
## ACL
Anti-corruption layer
<br/>
<br/>
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/acl.png)

Note:

Thankfully, we know the pattern of translating one model into another using ACL.
It looked like a matching case for this pattern.

How to achieve it?

---
## Event Scheduler
<br/>
![](time-based-domain-events/modelling/.assets/diagrams/marked_as_leaver.png)

Note:

Here comes an almighty weirdo called event scheduler.
Disclaimer, the technical details are out of the scope for this short talk, but I'm happy to share possible techniques how to achieve it.

It picks up EmployeeMarkedAsLeaver event, puts it somewhere and waits until leaving date is reached.
On leaving date, it sends this even straight to Benefits context.

Seems to be a working scenario. 