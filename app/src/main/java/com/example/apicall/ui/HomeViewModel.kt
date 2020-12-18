package com.example.apicall.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicall.data.entity.Post
import com.example.apicall.data.repository.PostRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    private val postRepo : PostRepository
) : ViewModel() {
    val responseLiveData : MutableLiveData<List<Post>>  = MutableLiveData()

    fun getPost(){
        viewModelScope.launch {
             postRepo.getPost()
                .catch { e->
                    Log.i("main", "getPost: ${e.message}")
                }
                 .collect{ response->
                     Log.i("TAGGO", "getPost: ${response.get(0).title}")
                     responseLiveData.value = response
                 }
        }
    }
}