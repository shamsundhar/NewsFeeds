package com.app.newsfeed.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("Error"))
                } catch (e: JSONException) {
                }
            }
            throw IOException(message.toString())
        }
    }
}