package com.virtuslab.jit.kafka

import com.virtuslab.jit.*

object EventTranslator {

    fun translate(timeBasedEvent: TimeBasedEvent) : EffectiveEvent = when(timeBasedEvent) {
        is EmployeeMarkedAsLeaver -> EmployeeLeft(timeBasedEvent.employeeId, timeBasedEvent.leavingDate)
        is EmployeePostponedLeaving -> EmployeeLeft(timeBasedEvent.employeeId, timeBasedEvent.leavingDate)
        is EmployeeCancelledLeaving -> EmployeeLeft(timeBasedEvent.employeeId, timeBasedEvent.leavingDate)
    }
}