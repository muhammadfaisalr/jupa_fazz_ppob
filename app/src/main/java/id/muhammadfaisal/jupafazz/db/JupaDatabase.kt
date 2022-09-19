package id.muhammadfaisal.jupafazz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.muhammadfaisal.jupafazz.db.dao.ProductDao
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class JupaDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
}