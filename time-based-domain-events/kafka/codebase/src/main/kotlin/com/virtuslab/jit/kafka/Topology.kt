package com.virtuslab.jit.kafka

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.processor.ProcessorSupplier
import java.time.Duration

const val EMPLOYEE_EVENTS_TOPIC = "employee-events"
const val EFFECTIVE_EVENTS_TOPIC = "effective-events"

private const val EMPLOYEE_EVENTS = "EmployeeEvents"
private const val EFFECTIVE_EVENTS = "EffectiveEvents"
private const val EFFECTIVE_EVENTS_TO_CLEAN = "EffectiveEventsToClean"

fun topology(scheduleInterval: Duration): Topology {

    return Topology()
        .addSource(
            Topology.AutoOffsetReset.EARLIEST, EMPLOYEE_EVENTS, Serdes.String().deserializer(),
            TimeBasedEventDeserializer, EMPLOYEE_EVENTS_TOPIC
        )
        .addProcessor(
            Translator.NAME,
            ProcessorSupplier { Translator() },
            EMPLOYEE_EVENTS
        )
        .addProcessor(
            Scheduler.NAME,
            ProcessorSupplier { Scheduler() },
            Translator.NAME
        )
        .addProcessor(
            Rescheduler.NAME,
            ProcessorSupplier { Rescheduler() },
            Translator.NAME
        )
        .addProcessor(
            Canceller.NAME,
            ProcessorSupplier { Canceller() },
            Translator.NAME
        )
        .addProcessor(
            EffectiveEventsForwarder.NAME,
            ProcessorSupplier { EffectiveEventsForwarder(scheduleInterval) },
            Scheduler.NAME,
            Rescheduler.NAME,
            Canceller.NAME
        )
        .addStateStore(
            effectiveEventsEventStore(),
            Scheduler.NAME,
            Rescheduler.NAME,
            Canceller.NAME,
            EffectiveEventsForwarder.NAME
        )
        .addStateStore(
            effectiveDatesStore(),
            Scheduler.NAME,
            Rescheduler.NAME,
            Canceller.NAME,
            EffectiveEventsForwarder.NAME
        )
        .addSink(
            EFFECTIVE_EVENTS,
            EFFECTIVE_EVENTS_TOPIC,
            Serdes.String().serializer(),
            EffectiveEventSerializer,
            EffectiveEventsForwarder.NAME
        )
        .addSource(
            Topology.AutoOffsetReset.EARLIEST, EFFECTIVE_EVENTS_TO_CLEAN, Serdes.String().deserializer(),
            EffectiveEventDeserializer, EFFECTIVE_EVENTS_TOPIC
        )
        .addProcessor(
            Cleaner.NAME,
            ProcessorSupplier { Cleaner() },
            EFFECTIVE_EVENTS_TO_CLEAN
        )
        .connectProcessorAndStateStores(Cleaner.NAME, EFFECTIVE_EVENTS_STORE)
        .connectProcessorAndStateStores(Cleaner.NAME, EFFECTIVE_DATES_STORE)

}
