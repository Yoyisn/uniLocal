package co.edu.eam.unilocal.model

import java.time.LocalTime

data class Schedule (
    val day: DayOfWeek,
    val open: LocalTime,
    val close: LocalTime
)