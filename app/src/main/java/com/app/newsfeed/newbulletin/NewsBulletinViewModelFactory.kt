package com.app.newsfeed.newbulletin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsBulletinViewModelFactory(private val repository: NewsBulletinRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsBulletinViewModel(repository) as T
    }
}