package com.mobiledev.fitnesstracker.domain

import androidx.room.*
import androidx.room.Dao
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface BaseDao<T> {
    @Insert(onConflict = IGNORE)
    fun insert(obj: T): Long

    @Delete
    fun delete(obj: T)

    @Update
    fun update(obj: T)
}