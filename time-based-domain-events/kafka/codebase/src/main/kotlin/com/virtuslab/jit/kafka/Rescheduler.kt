package com.virtuslab.jit.kafka

import com.virtuslab.jit.kafka.Command.Reschedule
import mu.KotlinLogging
import org.apache.kafka.streams.processor.AbstractProcessor
import org.apache.kafka.streams.processor.ProcessorContext

class Rescheduler : AbstractProcessor<String, Reschedule>() {

    private val logger = KotlinLogging.logger {}
    private lateinit var stores: Stores

    override fun init(context: ProcessorContext) {
        super.init(context)
        stores = Stores(context)
    }

    override fun process(key: String, command: Reschedule): Unit = reschedule(command)

    private fun reschedule(command: Reschedule) : Unit =
        stores.onEffectiveDateFor(command.eventKey(), storeNewSchedule(command))

    private fun storeNewSchedule(command: Reschedule): (String) -> Unit = { currentEffectiveDate ->
        logger.info { "Rescheduling event ${command.event}" }
        stores.run {
            effectiveEvents.delete(command.scheduleKeyOf(currentEffectiveDate))
            effectiveEvents.put(command.scheduleKey(), command.event)
            effectiveDates.put(command.eventKey(), command.effectiveDate())
        }
    }

    companion object {
        const val NAME = "Rescheduler"
    }
}