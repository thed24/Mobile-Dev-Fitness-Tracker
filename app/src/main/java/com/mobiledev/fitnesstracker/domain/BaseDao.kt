package com.mobiledev.fitnesstracker.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = IGNORE)
    fun insert(obj: T): Long

    @Delete
    fun delete(obj: T)

    @Update
    fun update(obj: T)
}
