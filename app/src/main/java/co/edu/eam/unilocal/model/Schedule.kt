package co.edu.eam.unilocal.model

import android.os.Build
import androidx.annotation.RequiresApi
//import java.time.LocalTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
data class Schedule(
    val day: DayOfWeek = DayOfWeek.MONDAY,
    val open: Date = Date(),
    val close: Date = Date()
) {
    fun toDisplayString(): String {
        return " ${day.name} $open - $close "
    }
}