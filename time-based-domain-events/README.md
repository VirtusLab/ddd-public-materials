# Time-based domain events

This project explores some solutions and modelling approach to provide support for concept related to **time based domain events**.

## Theoretical background

A **domain event** is a business fact that happened in the past. 

There are various sources of **domain events** like **Aggregates** or **Polices**.

Also, time may be a crucial within specific **Bounded Context**
If in our domain an expiration of form of time is an important concept it may be modelled as a **time-based domain event**. 

For example, **FinancialYearEnded** which is context wide event. 

Moreover, events like **StudentBecameAnAdult** are purely aggregate specific but still trigger by time.
 
 
## Resources

1. [Modelling](https://github.com/VirtusLab/ddd-public-materials/tree/master/time-based-domain-events/modelling)
1. [Just in time events on Kafka](https://github.com/VirtusLab/ddd-public-materials/tree/master/time-based-domain-events/kafka)
