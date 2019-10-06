package com.virtuslab.jit.kafka

import com.virtuslab.jit.EffectiveEvent

sealed class Command {
    abstract val event: EffectiveEvent

    data class Schedule(override val event: EffectiveEvent) : Command()

    data class Reschedule(override val event: EffectiveEvent) : Command()

    data class Cancel(override val event: EffectiveEvent) : Command()

    fun scheduleKey(): String = event.effectiveDateKey()

    fun eventKey(): String = event.eventKey()

    fun effectiveDate(): String = event.effectiveDate()

    fun scheduleKeyOf(effectiveDate: String) = event.effectiveDateKey(effectiveDate)

}