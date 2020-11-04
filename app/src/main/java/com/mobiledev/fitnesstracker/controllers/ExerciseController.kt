package com.mobiledev.fitnesstracker.controllers

import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.domain.ExerciseItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExerciseController @Inject constructor(var exerciseItemDao: ExerciseItemDao) :
    BaseController<ExerciseItem> {

    override fun updateEntry(item: ExerciseItem) {
        runBlocking {
            launch(Dispatchers.Default) {
                exerciseItemDao.update(item)
            }
        }
    }

    override fun addEntry(item: ExerciseItem) {
        runBlocking {
            launch(Dispatchers.Default) {
                exerciseItemDao.insert(item)
            }
        }
    }

    override fun getEntry(itemId: Int): ExerciseItem {
        return runBlocking {
            withContext(Dispatchers.Default) {
                getEntryAsync(itemId)
            }
        }
    }

    override fun getAllEntries(): List<ExerciseItem> {
        return runBlocking {
            withContext(Dispatchers.Default) {
                getAllEntriesAsync()
            }
        }
    }

    override fun removeEntry(item: ExerciseItem) {
        runBlocking {
            launch(Dispatchers.Default) {
                exerciseItemDao.delete(item)
            }
        }
    }

    private suspend fun getEntryAsync(itemId: Int): ExerciseItem {
        return exerciseItemDao.getById(itemId)
    }

    private suspend fun getAllEntriesAsync(): List<ExerciseItem> {
        return exerciseItemDao.getAll()
    }
}
