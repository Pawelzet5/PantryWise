package com.example.pantrywise.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pantrywise.model.db.converters.DatabaseConverters
import com.example.pantrywise.model.db.dao.ProductDao
import com.example.pantrywise.model.db.entities.DbProduct

@Database(entities = [DbProduct::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class PantryWiseDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
}