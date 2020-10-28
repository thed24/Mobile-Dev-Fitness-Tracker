package com.mobiledev.fitnesstracker.persistence

import androidx.room.TypeConverter

class ExerciseTypeConverter{

    @TypeConverter
    fun fromExerciseType(value: ExerciseType): Int{
        return value.ordinal
    }

    @TypeConverter
    fun toExerciseType(value: Int): ExerciseType {
        return when(value){
            1 -> ExerciseType.RUNNING
            else -> ExerciseType.WALKING
        }
    }
}

enum class ExerciseType {
    WALKING, RUNNING
}