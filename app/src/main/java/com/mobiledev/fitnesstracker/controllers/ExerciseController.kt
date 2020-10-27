package com.mobiledev.fitnesstracker.controllers

import com.mobiledev.fitnesstracker.ExerciseItem

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
        val item = newItem
        item.id = exerciseListItems.size
        exerciseListItems.add(item)
    }

    fun getEntry(itemId: Int): ExerciseItem {
        return exerciseListItems[itemId]
    }

    fun removeEntry(item: ExerciseItem) {
        exerciseListItems.remove(item)
    }
}