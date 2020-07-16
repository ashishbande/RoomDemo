package com.example.roomdemo.ui

import SubscriberViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.R
import com.example.roomdemo.database.Subscriber
import com.example.roomdemo.database.SubscriberDataBase
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.repository.SubscriberRepository
import com.example.roomdemo.utils.CoroutineContextProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    private var contextProvider = CoroutineContextProvider()

    private lateinit var adaptor : SubscriberAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        val dao = SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository, contextProvider)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.subscriberViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        displaySubscriberList()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscriber.observe(this, Observer {
              adaptor.setSubscriberData(it)
              adaptor.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView(){
       binding.subscriberView.layoutManager = LinearLayoutManager(this)
        adaptor =  SubscriberAdaptor { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        binding.subscriberView.adapter = adaptor
       }

    private fun listItemClicked(subscriber: Subscriber){
       subscriberViewModel.initUpdateDelete(subscriber)
    }
}
