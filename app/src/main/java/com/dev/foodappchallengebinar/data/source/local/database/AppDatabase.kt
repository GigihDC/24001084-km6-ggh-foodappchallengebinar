package com.dev.foodappchallengebinar.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.foodappchallengebinar.data.source.local.database.dao.CartDao
import com.dev.foodappchallengebinar.data.source.local.database.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DB_NAME = "FoodApp.db"

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration().build()
        }
    }
}
