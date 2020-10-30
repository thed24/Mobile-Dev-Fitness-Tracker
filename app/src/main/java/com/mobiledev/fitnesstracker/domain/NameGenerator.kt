package com.mobiledev.fitnesstracker.domain

import com.mobiledev.fitnesstracker.persistence.ExerciseType

class NameGenerator {

    fun createName(item: ExerciseItem) : String {
        var name = createPace(item.pace, item.ExerciseType)
        name += createDistance(item.distance)
        return "\"$name\""
        }

    private fun createPace(pace: Float, exerciseType: ExerciseType): String {
        return if (exerciseType == ExerciseType.RUNNING)
            when {
                pace > 0 && pace < 6 -> "A slow-paced run "
                pace > 6 && pace < 10 -> "A medium-paced run "
                else -> "A fast-paced run "
            } else
            when {
                pace > 0 && pace < 2 -> "A slow-paced walk "
                pace > 2 && pace < 4 -> "A medium-paced walk "
                else -> "A fast-paced walk "
            }
    }

    private fun createDistance(distance: Float): String {
        return when {
                distance > 0 && distance < 5 -> "for a short distance"
                distance > 5 && distance < 20 -> "for a long distance"
                else -> "for a very long distance"
            }
    }
}