package com.example.roomdemo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.platform.app.InstrumentationRegistry

import androidx.test.runner.AndroidJUnit4
import com.example.roomdemo.database.Subscriber
import com.example.roomdemo.database.SubscriberDAO
import com.example.roomdemo.database.SubscriberDataBase
import com.example.roomdemo.repository.SubscriberRepository
import com.example.roomdemo.ui.SubscriberViewModel
import com.example.roomdemo.utils.Event
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class SubscriberViewModelTest {

    private lateinit var repositoryMock: SubscriberRepository
    private lateinit var subscriberViewModel: SubscriberViewModel


    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    private val testContextProvider = TestContextProvider()

    @Before
    fun setup(){


        val subscriberDAO = SubscriberDataBase.getInstance(InstrumentationRegistry.getInstrumentation().targetContext).subscriberDAO
        repositoryMock = SubscriberRepository(subscriberDAO)

        subscriberViewModel = SubscriberViewModel(repositoryMock, testContextProvider)
    }



    @Test
    fun testInsert() = runBlockingTest {
        val subscriber = Subscriber(1,"test", "test")
        subscriberViewModel.insert(subscriber)
        testContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        val event = subscriberViewModel.statusMessage.value

        assertEquals("Inserted Successfully",event?.peekContent() )
    }
    @After
    fun tearDown() {

    }

}