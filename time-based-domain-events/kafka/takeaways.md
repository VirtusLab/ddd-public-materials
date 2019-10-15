---
@title[Achievements]
## Achievements
@ol
- @color[green](Goal achieved on Kafka infrastructure)
- @color[green](Composable approach to Topologies)
- @color[green](Testability)
- @color[green](Out of the box scalability based on Kafka partitioning)  
- @color[green](Fault tolerance thanks to StateStores and Topic replication)
- @color[green](Isolation of processing per partition)
@olend

---
## Considerations
@ol
- @color[red](Usage of low-level PAPI)
- @color[red](2 state-stores shared between Processors)
- @color[red](Range query based on some internals)
- @color[red](No TTL for persistent StateStores)
- @color[red](Increased memory footprint and disk space because of RocksDB)
- @color[red](WALL_CLOCK_TIME best effort based on polling)
@olend

---
## Takeaways
@ol
- READ DOCUMENTATION!
- Adjust default configuration
- Measure performance and resource usage
- Abstract time
- Is it really suitable solution for scheduling?
@olend

---
## Alternatives
@ol
- Kafka **Suppress** operator
    - for windowed operations
    - based on STREAM_TIME
    - emitting final results as a main purpose

@olend

---
## Alternatives

### Redis?

@quote[Basically expired events are generated when the Redis server deletes the key and not when the time to live theoretically reaches the value of zero.](http://redis.io/topics/notifications)

---
## Alternatives
</br>
#### Polling on some persistent datastore
@ol
- how to scale it? 
@olend
@snap[south snap-100]
![](time-based-domain-events/kafka/.assets/diagrams/database.png)
@snapend
---
## References
@snap[west snap-100]
@ul
- [Codebase](https://github.com/VirtusLab/ddd-public-materials/tree/master/time-based-domain-events/kafka/codebase)
- [Kafka Querable State Stores](https://cwiki.apache.org/confluence/display/KAFKA/KIP-67%3A+Queryable+state+for+Kafka+Streams)
- [Retroactive event](https://martinfowler.com/eaaDev/RetroactiveEvent.html)
- [Retroactive event sourcing](https://www.infoq.com/news/2018/02/retroactive-future-event-sourced/)
- [Beyond the Kafka DSL](https://www.slideshare.net/ConfluentInc/beyond-the-dsl-unlocking-the-power-of-kafka-streams-with-the-processor-api)
- [Kafka Internal Data Management](https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Internal+Data+Management)
- [Reactive event sourcing](https://blog.redelastic.com/corporate-arts-crafts-modelling-reactive-systems-with-event-storming-73c6236f5dd7)
- [Kafka topology viz](https://zz85.github.io/kafka-streams-viz/)
- [Kafka Processor API](https://kafka.apache.org/documentation/streams/developer-guide/processor-api.html)
@ulend
@snapend
