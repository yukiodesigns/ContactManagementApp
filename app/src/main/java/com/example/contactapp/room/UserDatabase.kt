package com.example.contactapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room

@Database(entities = [User::class], version = 1)
abstract class UserDatabase {
    abstract val userDAO: UserDAO

    //Singleton
    companion object{

        @Volatile
        private var INSTANCE: UserDatabase ?= null
        fun getInstance(context: Context) : UserDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    //create db object
                    instance = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_db").build()

                }
                return instance
            }

        }


    }

}