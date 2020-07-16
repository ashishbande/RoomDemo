package com.example.roomdemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDataBase : RoomDatabase(){
    abstract val subscriberDAO: SubscriberDAO

    companion object{
        @Volatile
        private var INSTANCE: SubscriberDataBase? = null
        fun getInstance(context: Context):SubscriberDataBase{
             var instance : SubscriberDataBase? = INSTANCE
             if (instance == null){
                 instance = Room.databaseBuilder(context.applicationContext, SubscriberDataBase::class.java, "SubscriberDB").build()
             }
           return instance
        }
    }
}