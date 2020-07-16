import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo.ui.SubscriberViewModel
import com.example.roomdemo.repository.SubscriberRepository
import com.example.roomdemo.utils.CoroutineContextProvider

import java.lang.IllegalArgumentException

class SubscriberViewModelFactory(private val repository: SubscriberRepository,val contextProvider: CoroutineContextProvider):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(repository, contextProvider) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}