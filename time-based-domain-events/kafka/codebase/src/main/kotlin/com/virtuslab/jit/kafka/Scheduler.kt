package com.virtuslab.jit.kafka

import com.virtuslab.jit.kafka.Command.Schedule
import mu.KotlinLogging
import org.apache.kafka.streams.processor.AbstractProcessor
import org.apache.kafka.streams.processor.ProcessorContext

class Scheduler : AbstractProcessor<String, Schedule>() {

    private val logger = KotlinLogging.logger {}
    private lateinit var stores: Stores

    override fun init(context: ProcessorContext) {
        super.init(context)
        stores = Stores(context)
    }

    override fun process(key: String, command: Schedule) = schedule(command)

    private fun schedule(command: Schedule) = command.run {
        logger.info { "Scheduling $event" }
        stores.run {
            effectiveEvents.put(scheduleKey(), event)
            effectiveDates.put(eventKey(), effectiveDate())
        }
    }

    companion object {
        const val NAME = "Scheduler"
    }

}