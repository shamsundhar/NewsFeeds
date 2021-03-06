package com.app.newsfeed.newbulletin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class NewsBulletinViewModel(private val newsBulletinRepository: NewsBulletinRepository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _articles: MutableLiveData<List<Article>> = MutableLiveData()

    val articles: LiveData<List<Article>>
        get() = _articles

    fun getNewsFeed(countryCode: String = "in") {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val response = newsBulletinRepository.getNewsFeed(countryCode, "entertainment")
                withContext(Dispatchers.Main) {
                    _articles.value = response.articles
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}