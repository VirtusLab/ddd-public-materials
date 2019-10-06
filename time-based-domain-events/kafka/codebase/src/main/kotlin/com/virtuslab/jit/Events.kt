package com.virtuslab.jit

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDate

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = EmployeeMarkedAsLeaver::class, name = "EmployeeMarkedAsLeaver"),
    JsonSubTypes.Type(value = EmployeePostponedLeaving::class, name = "EmployeePostponedLeaving"),
    JsonSubTypes.Type(value = EmployeeCancelledLeaving::class, name = "EmployeeCancelledLeaving")
)
sealed class TimeBasedEvent(val id: String, val effectiveDate: LocalDate)

data class EmployeeMarkedAsLeaver(
    val employeeId: String,
    val leavingDate: LocalDate
) : TimeBasedEvent(employeeId, leavingDate)

data class EmployeePostponedLeaving (
    val employeeId: String,
    val leavingDate: LocalDate
) : TimeBasedEvent(employeeId, leavingDate)


data class EmployeeCancelledLeaving (
    val employeeId: String,
    val leavingDate: LocalDate
) : TimeBasedEvent(employeeId, leavingDate)


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = EmployeeLeft::class, name = "EmployeeLeft")
)
sealed class EffectiveEvent(val id: String, private val effectiveDate: LocalDate) {

    fun eventKey() = "$id:${this::class.simpleName}"

    fun effectiveDateKey(date: String = effectiveDate.isoString()) = "$date:${eventKey()}"

    fun effectiveDate(): String = effectiveDate.isoString()
}

data class EmployeeLeft(
    val employeeId: String,
    val leavingDate: LocalDate
) : EffectiveEvent(employeeId, leavingDate)