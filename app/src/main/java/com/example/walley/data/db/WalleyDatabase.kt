package com.example.walley.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhotoEntity::class], exportSchema = false, version = 1)
abstract class WalleyDatabase : RoomDatabase(){

    abstract fun getPhotosDao() : PhotosDao

    companion object{

        private var instance : WalleyDatabase? = null

        fun initDatabase(context: Context) : WalleyDatabase {
            return instance ?: synchronized(WalleyDatabase::class){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalleyDatabase::class.java,
                    "walley_database"
                ).build()
                instance!!
            }
        }
    }
}