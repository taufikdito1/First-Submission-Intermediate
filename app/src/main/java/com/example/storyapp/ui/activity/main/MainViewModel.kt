package com.example.storyapp.ui.activity.main

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.data.model.story.StoriesResponse
import com.example.storyapp.data.model.story.Story
import com.example.storyapp.data.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainVIewModel : ViewModel() {

    private val _listStory = MutableLiveData<ArrayList<Story>>()
    val listStory: LiveData<ArrayList<Story>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getAllStories(auth: String) {
        _isLoading.value = true
        ApiConfig().getApiService().getListStory("Bearer $auth")
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listStory.postValue(response.body()?.listStory)
                        Log.d(TAG, response.body()?.listStory.toString())
                    }
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, t.message.toString())
                }
            })
    }

    companion object{
        const val TAG = "tag"
    }
}