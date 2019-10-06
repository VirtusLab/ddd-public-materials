package com.virtuslab.jit.kafka

import com.virtuslab.jit.kafka.Command.Cancel
import mu.KotlinLogging
import org.apache.kafka.streams.processor.AbstractProcessor
import org.apache.kafka.streams.processor.ProcessorContext

class Canceller : AbstractProcessor<String, Cancel>() {

    private val logger = KotlinLogging.logger {}
    private lateinit var stores: Stores

    override fun init(context: ProcessorContext) {
        super.init(context)
        stores = Stores(context)
    }

    override fun process(key: String, command: Cancel) = cancel(command)

    private fun cancel(command: Cancel) = stores.onEffectiveDateFor(command.eventKey(), deleteSchedule(command))

    private fun deleteSchedule(command: Cancel): (String) -> Unit = { currentEffectiveDate ->
        logger.info { "Cancelling event ${command.event}" }
        stores.run {
            effectiveEvents.delete(command.scheduleKeyOf(currentEffectiveDate))
            effectiveDates.delete(command.eventKey())
        }
    }

    companion object {
        const val NAME = "Canceller"
    }

}