package com.app.newsfeed.newbulletin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class NewsBulletinViewModel(private val newsBulletinRepository: NewsBulletinRepository) :
    ViewModel() {

    private var _articles: MutableLiveData<List<Article>> = MutableLiveData()

    val articlesData: LiveData<List<Article>>
        get() = _articles

    /**
     * Makes a network call to get the Current news articles for the given country
     * subscribe to [articlesData] LiveData to get notified for [NewsFeed.articles] response update
     *
     * @param countryCode refer [Country] for current supported Countries
     *
     */
    fun getNewsFeed(countryCode: String = Country.UNITED_STATES.countryCode) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = newsBulletinRepository.getNewsFeed(countryCode, "entertainment")
                withContext(Dispatchers.Main) {
                    _articles.value = response.articles
                }
            }
        }
    }
}