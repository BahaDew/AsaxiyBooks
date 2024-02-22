package com.sudo_pacman.asaxiybooks.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sudo_pacman.asaxiybooks.data.dao.BookDao
import com.sudo_pacman.asaxiybooks.data.entity.EntityBookData


@Database(entities = [EntityBookData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBookDao(): BookDao
}


