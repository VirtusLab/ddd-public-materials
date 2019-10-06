package com.virtuslab.jit.kafka

import com.virtuslab.jit.EffectiveEvent
import mu.KotlinLogging
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.processor.AbstractProcessor
import org.apache.kafka.streams.processor.ProcessorContext
import org.apache.kafka.streams.processor.PunctuationType
import java.time.Duration

class EffectiveEventsForwarder(private val scheduleDuration: Duration) : AbstractProcessor<String, Any>() {

    private val logger = KotlinLogging.logger {}
    private lateinit var stores: Stores

    override fun init(context: ProcessorContext) {
        super.init(context)
        stores = Stores(context)
        context.schedule(scheduleDuration, PunctuationType.WALL_CLOCK_TIME) { forwardAndCommit() }
    }

    private fun forwardAndCommit() =
        forwardEffectiveEvents().also { context().commit() }

    private fun forwardEffectiveEvents() =
        stores.onEffectiveEvents { iterator ->
            iterator.forEach { forward(it) }
        }

    private fun forward(keyValue: KeyValue<String, EffectiveEvent>) = keyValue.run {
        logger.info { "Forwarding event $value" }
        context().forward(value.id, value)
    }

    override fun process(key: String, any: Any) {}

    companion object {
        const val NAME = "EffectiveEventsForwarder"
    }
}