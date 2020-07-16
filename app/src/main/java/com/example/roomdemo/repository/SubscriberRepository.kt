package com.example.roomdemo.repository

import com.example.roomdemo.database.Subscriber
import com.example.roomdemo.database.SubscriberDAO

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {
    val subscribers = subscriberDAO.getAll()

    suspend fun insert(subscriber: Subscriber): Long{
        return subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int{
        return subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int{
        return subscriberDAO.deleteAll()
    }
}