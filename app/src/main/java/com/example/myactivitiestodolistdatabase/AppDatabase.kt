package com.example.myactivitiestodolistdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{

//            val tempInstance = INSTANCE
//
//            if(tempInstance !=null){
//                return tempInstance
//            }

            return INSTANCE?: synchronized(this){

                val instance = Room.databaseBuilder(

                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_Database"

                ).build()

                INSTANCE = instance
                instance

            }

        }

    }
}