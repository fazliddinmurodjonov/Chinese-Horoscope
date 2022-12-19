package com.programmsoft.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.programmsoft.room.entity.ZodiacBase

@Dao
interface ZodiacBaseDao {
    @Insert
    fun insert(zodiacBase: ZodiacBase)

    @Update
    fun update(zodiacBase: ZodiacBase)

    @Query("select *from ZodiacBase where id = :id")
    fun getById(id: Int): ZodiacBase

    @Query("select *from ZodiacBase")
    fun getAll(): List<ZodiacBase>
}