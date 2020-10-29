package com.mobiledev.fitnesstracker.controllers

import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.domain.ExerciseItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ExerciseController @Inject constructor (var exerciseItemDao: ExerciseItemDao) : BaseController<ExerciseItem> {

    override fun updateEntry(item: ExerciseItem) {
        runBlocking {
            val job = launch(Dispatchers.Default) {
                exerciseItemDao.update(item)
            }
        }
    }

    override fun addEntry(item: ExerciseItem) {
        runBlocking {
            val job = launch(Dispatchers.Default) {
                exerciseItemDao.insert(item)
            }
        }
    }

    override fun getEntry(itemId: Int): ExerciseItem {
        lateinit var item: ExerciseItem
        runBlocking {
            val job = launch(Dispatchers.Default) {
                item = exerciseItemDao.getById(itemId)
            }
        }
        return item
    }

    override fun getAllEntries(): List<ExerciseItem> {
        var items = emptyList<ExerciseItem>()
        runBlocking {
            val job = launch(Dispatchers.Default) {
                items = exerciseItemDao.getAll()
            }
        }
        return items
    }

    override fun removeEntry(item: ExerciseItem) {
        runBlocking {
            val job = launch(Dispatchers.Default) {
                exerciseItemDao.delete(item)
            }
        }
    }
}