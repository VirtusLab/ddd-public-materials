## Worth noting
### Passage of Time Event
</br>
@quote[Replace cron jobs and scheduled commands, with an agnostic event to indicate the passage of time.](M.Verraes)

</br>
@snap[south]
![](time-based-domain-events/modelling/.assets/diagrams/passage-of-time/passage_of_time.png)
@snapend


Note:

In the context of time events, Mathias proposed Passage of Time pattern.
I definitely find it useful and even tried to model Discount Eligibility in that in mind.
Happy to share outcomes later.

---
## Worth noting
### Events for future/past

@snap[west span-30 text-06 fragment]
![](time-based-domain-events/modelling/.assets/diagrams/retroactive_event.png)
@snapend


@snap[east span-30 text-06 fragment]
@box[rounded bg-orange](Bi-temporality #As At vs As Of)
@snapend


Note:

Also, when speaking about different timelines there is a pattern of Retroactive event with two types of dates.

It leads to brain-breaking analysis of bi-temporal perspective.