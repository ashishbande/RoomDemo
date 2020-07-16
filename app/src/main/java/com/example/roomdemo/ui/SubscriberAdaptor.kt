package com.example.roomdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.R
import com.example.roomdemo.database.Subscriber
import com.example.roomdemo.databinding.ListItemBinding

class SubscriberAdaptor(
    private val clickListener : (Subscriber) -> Unit): RecyclerView.Adapter<SubscriberAdaptor.SubscriberViewHolder>() {

    private val list = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.list_item, parent, false)
        return SubscriberViewHolder(
            binding
        )
    }

    fun setSubscriberData(subscribers: List<Subscriber>){
        list.clear()
        list.addAll(subscribers)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
          holder.bind(list[position], clickListener)
    }

    class SubscriberViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
            binding.nameTextView.text = subscriber.name
            binding.emailText.text = subscriber.email

            binding.listItemLayout.setOnClickListener{
                clickListener(subscriber)
            }
        }
    }
}

