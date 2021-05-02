package com.app.newsfeed.newbulletin

import com.app.newsfeed.network.SafeApiRequest
import com.app.newsfeed.network.ServiceApiInterface

class NewsBulletinRepository(private val api: ServiceApiInterface) : SafeApiRequest() {

    suspend fun getNewsFeed(country: String, category: String): NewsFeed {
        return apiRequest { api.getNewsFeed(country, category) }
    }
}