package com.virtuslab.jit.kafka

import com.virtuslab.jit.EffectiveEvent
import com.virtuslab.jit.Time
import com.virtuslab.jit.isoString
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.processor.ProcessorContext
import org.apache.kafka.streams.state.KeyValueIterator
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.streams.state.StoreBuilder
import org.apache.kafka.streams.state.Stores


const val EFFECTIVE_EVENTS_STORE = "effective-events-store"
const val EFFECTIVE_DATES_STORE = "effective-dates-store"

fun effectiveEventsEventStore(): StoreBuilder<KeyValueStore<String, EffectiveEvent>> =
    Stores.keyValueStoreBuilder(
        Stores.persistentKeyValueStore(EFFECTIVE_EVENTS_STORE),
        Serdes.String(),
        EffectiveEventSerde
    ).withLoggingEnabled(
        mutableMapOf()
    )

fun effectiveDatesStore(): StoreBuilder<KeyValueStore<String, String>> =
    Stores.keyValueStoreBuilder(
        Stores.persistentKeyValueStore(EFFECTIVE_DATES_STORE),
        Serdes.String(),
        Serdes.String()
    ).withLoggingEnabled(
        mutableMapOf()
    )

class Stores(context: ProcessorContext) {
    val effectiveEvents: KeyValueStore<String, EffectiveEvent> = context.keyValueStore(EFFECTIVE_EVENTS_STORE)
    val effectiveDates: KeyValueStore<String, String> = context.keyValueStore(EFFECTIVE_DATES_STORE)


    private fun <K, V> ProcessorContext.keyValueStore(name: String) = getStateStore(name) as KeyValueStore<K, V>

    fun onEffectiveDateFor(
        eventKey: String,
        onExistingEffectiveDate: (String) -> Unit
    ): Unit = effectiveDates.get(eventKey)?.let { onExistingEffectiveDate(it) } ?: { }()


    fun onEffectiveEvents(iterateOver: (KeyValueIterator<String, EffectiveEvent>) -> Unit) =
        effectiveEvents.range("0", Time.tomorrow().isoString()).use { iterateOver(it) }
}