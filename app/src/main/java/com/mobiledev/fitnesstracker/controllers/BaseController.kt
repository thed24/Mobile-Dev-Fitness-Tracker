package com.mobiledev.fitnesstracker.controllers

import com.mobiledev.fitnesstracker.domain.ExerciseItem

interface BaseController<T> {
    fun updateEntry(item: T)

    fun addEntry(item: T)

    fun getEntry(itemId: Int): T

    fun getAllEntries(): List<T>

    fun removeEntry(item: T)
}
