package com.mobiledev.fitnesstracker.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobiledev.fitnesstracker.persistence.ExerciseType
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDateTime


@Parcelize
@Entity
data class ExerciseItem(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "distance") var distance: Float,
    @ColumnInfo(name = "time_spent") var timeSpent: Float,
    @ColumnInfo(name = "exercise_type") var exerciseType: ExerciseType,
    @ColumnInfo(name = "pace") var pace: Float,
    @ColumnInfo(name = "time_stamp") val timeStamp: String? = getDateTime()
) : Parcelable

private fun getDateTime(): String? {
    var dateTime = LocalDateTime.now()
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val dayNumberSuffix: String = getDayNumberSuffix(dateTime.dayOfMonth)
    val formatter = SimpleDateFormat("EEEE', ' dd'$dayNumberSuffix of' MMMM yyyy', ' hh:mm a")
    return formatter.format(parser.parse(dateTime.toString()))
}

private fun getDayNumberSuffix(day: Int): String {
    val thDayRange = 11..13

    return if (day in thDayRange) {
        "th"
    } else when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}
