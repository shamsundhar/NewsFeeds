package com.app.newsfeed.newsarticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.newsfeed.newbulletin.Article

class NewsArticleViewModelFactory(val article: Article) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsArticleViewModel(article) as T
    }
}