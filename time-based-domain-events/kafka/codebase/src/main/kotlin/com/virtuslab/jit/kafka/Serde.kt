package com.virtuslab.jit.kafka

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.virtuslab.jit.EffectiveEvent
import com.virtuslab.jit.TimeBasedEvent
import mu.KotlinLogging
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer

private val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule()).enableDefaultTyping()
private val logger = KotlinLogging.logger {}


object TimeBasedEventSerializer : Serializer<TimeBasedEvent> {
    override fun serialize(topic: String, data: TimeBasedEvent): ByteArray = objectMapper.writeValueAsBytes(data)

    override fun close() = logger.info { "Closing TimeBasedEventSerializer" }

    override fun configure(configs: MutableMap<String, *>, isKey: Boolean) {}
}

object TimeBasedEventDeserializer : Deserializer<TimeBasedEvent> {
    override fun deserialize(topic: String, data: ByteArray): TimeBasedEvent = objectMapper.readValue(data)

    override fun close() = logger.info { "Closing TimeBasedEventDeserializer" }

    override fun configure(configs: MutableMap<String, *>, isKey: Boolean) {}
}

object EffectiveEventSerde : Serde<EffectiveEvent> {
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}

    override fun deserializer(): Deserializer<EffectiveEvent> = EffectiveEventDeserializer

    override fun close() {}

    override fun serializer(): Serializer<EffectiveEvent> = EffectiveEventSerializer
}

object EffectiveEventSerializer : Serializer<EffectiveEvent> {
    override fun serialize(topic: String, data: EffectiveEvent): ByteArray = objectMapper.writeValueAsBytes(data)

    override fun close() = logger.info { "Closing TimeBasedEventSerializer" }

    override fun configure(configs: MutableMap<String, *>, isKey: Boolean) {}
}

object EffectiveEventDeserializer : Deserializer<EffectiveEvent> {
    override fun deserialize(topic: String, data: ByteArray): EffectiveEvent = objectMapper.readValue(data)

    override fun close() = logger.info { "Closing TimeBasedEventDeserializer" }

    override fun configure(configs: MutableMap<String, *>, isKey: Boolean) {}
}