package com.example.shoppingapp.data.loca

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        var INSTANCE: ProductDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ProductDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    ProductDatabase::class.java,
                    "product.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as ProductDatabase
        }
    }

}