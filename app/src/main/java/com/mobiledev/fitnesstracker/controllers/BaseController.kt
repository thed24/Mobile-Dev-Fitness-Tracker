package com.mobiledev.fitnesstracker.controllers

interface BaseController<T> {
    fun updateEntry(item: T)

    fun addEntry(item: T)

    fun getEntry(itemId: Int): T

    fun getAllEntries(): List<T>

    fun removeEntry(item: T)
}
