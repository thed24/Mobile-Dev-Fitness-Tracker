package com.mobiledev.fitnesstracker.domain

import androidx.room.*
import androidx.room.Dao

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(obj: T)

    @Delete
    fun delete(obj: T)

    @Update
    fun update(obj: T)
}