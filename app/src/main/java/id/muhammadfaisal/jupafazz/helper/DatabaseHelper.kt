package id.muhammadfaisal.jupafazz.helper

import android.content.Context
import androidx.room.Room
import id.muhammadfaisal.jupafazz.db.JupaDatabase
import id.muhammadfaisal.jupafazz.db.dao.ProductDao

class DatabaseHelper {
    companion object {
        private fun connect(context: Context): JupaDatabase {
            return Room.databaseBuilder(context, JupaDatabase::class.java, "jupa_db")
                .allowMainThreadQueries().build()
        }

        fun productDao(context: Context) : ProductDao {
            return connect(context).productDao()
        }

    }
}