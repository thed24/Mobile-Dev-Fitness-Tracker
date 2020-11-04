package com.mobiledev.fitnesstracker.domain

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ExerciseItemDao : BaseDao<ExerciseItem> {
    @Query("SELECT * FROM ExerciseItem")
    override fun getAll(): List<ExerciseItem>

    @Query("SELECT * FROM ExerciseItem WHERE id LIKE :id")
    override fun getById(id: Int): ExerciseItem
}
