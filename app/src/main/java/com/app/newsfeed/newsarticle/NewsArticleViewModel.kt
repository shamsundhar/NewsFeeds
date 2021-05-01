package com.app.newsfeed.newsarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.newsfeed.newbulletin.Article

class NewsArticleViewModel(mArticle: Article) : ViewModel() {


    private var _article = MutableLiveData<Article>()

    val article: LiveData<Article>
        get() = _article

    init {
        _article.postValue(mArticle)
        println("Posting done")
    }

}