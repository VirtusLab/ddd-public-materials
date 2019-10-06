package com.virtuslab.jit.kafka

import com.virtuslab.jit.*
import com.virtuslab.jit.kafka.Command.*
import com.virtuslab.jit.kafka.EventTranslator.translate
import mu.KotlinLogging
import org.apache.kafka.streams.processor.AbstractProcessor
import org.apache.kafka.streams.processor.To

class Translator : AbstractProcessor<String, TimeBasedEvent>() {

    private val logger = KotlinLogging.logger {}

    override fun process(key: String, event: TimeBasedEvent) =
        when (event) {
            is EmployeeMarkedAsLeaver -> schedule(translate(event))
            is EmployeePostponedLeaving -> reschedule(translate(event))
            is EmployeeCancelledLeaving -> cancel(translate(event))
        }

    private fun schedule(event: EffectiveEvent) {
        logger.info { "Forwarding $event to Scheduler" }
        context().forward(event.eventKey(), Schedule(event), To.child(Scheduler.NAME))
    }

    private fun reschedule(event: EffectiveEvent) {
        logger.info { "Forwarding $event to Rescheduler" }
        context().forward(event.eventKey(), Reschedule(event), To.child(Rescheduler.NAME))
    }

    private fun cancel(event: EffectiveEvent) {
        logger.info { "Forwarding $event to Canceller" }
        context().forward(event.eventKey(), Cancel(event), To.child(Canceller.NAME))
    }

    companion object {
        const val NAME = "Translator"
    }
}