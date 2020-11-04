package com.mobiledev.fitnesstracker.persistence

import androidx.room.TypeConverter

class ExerciseTypeConverter {

    private val runningId = 1

    @TypeConverter
    fun fromExerciseType(value: ExerciseType): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toExerciseType(value: Int): ExerciseType {
        return when (value) {
            runningId -> ExerciseType.RUNNING
            else -> ExerciseType.WALKING
        }
    }
}

enum class ExerciseType {
    WALKING, RUNNING
}
