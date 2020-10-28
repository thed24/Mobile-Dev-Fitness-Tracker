package com.mobiledev.fitnesstracker.controllers

import com.mobiledev.fitnesstracker.domain.ExerciseItem

class ExerciseController(
    private var exerciseListItems: MutableList<ExerciseItem>,
) {
    fun updateEntry(newItem: ExerciseItem, oldItemId: Int) {
        val oldItem = exerciseListItems.find { exercise ->
            exercise.id == oldItemId
        }
        if (oldItem != null) {
            exerciseListItems[exerciseListItems.indexOf(oldItem)] = newItem
        }
    }

    fun addEntry(newItem: ExerciseItem) {
        newItem.id = exerciseListItems.size
        exerciseListItems.add(newItem)
    }

    fun getEntry(itemId: Int): ExerciseItem {
        return exerciseListItems[itemId]
    }

    fun removeEntry(item: ExerciseItem) {
        exerciseListItems.remove(item)
    }
}