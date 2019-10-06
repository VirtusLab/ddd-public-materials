package com.virtuslab.jit.kafka

import com.virtuslab.jit.EffectiveEvent
import mu.KotlinLogging
import org.apache.kafka.streams.processor.AbstractProcessor
import org.apache.kafka.streams.processor.ProcessorContext

class Cleaner : AbstractProcessor<String, EffectiveEvent>() {
    private val logger = KotlinLogging.logger {}
    private lateinit var stores: Stores

    override fun init(context: ProcessorContext) {
        super.init(context)
        stores = Stores(context)
    }

    override fun process(key: String, event: EffectiveEvent) = clean(event)

    private fun clean(event: EffectiveEvent): Unit = stores.run {
        logger.info { "Cleaning stores for effective event $event" }
        effectiveEvents.delete(event.effectiveDateKey())
        //key=employeeId:eventType -> value=2019-10-10
        effectiveDates.delete(event.eventKey())
    }

    companion object {
        const val NAME = "Cleaner"
    }

}