package com.virtuslab.jit

import java.time.LocalDate
import java.time.format.DateTimeFormatter

typealias Today = () -> LocalDate

object Time {

    private val todayAsNow: Today = { LocalDate.now() }
    private var currentToday: Today = todayAsNow

    fun setToday(today: Today) {
        currentToday = today
    }

    fun restoreDefault() {
        setToday(todayAsNow)
    }

    fun tomorrow(): LocalDate = currentToday().plusDays(1)

    fun today() : LocalDate = currentToday()
}

fun LocalDate.isoString(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE)