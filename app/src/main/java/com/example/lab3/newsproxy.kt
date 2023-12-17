package com.example.lab3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class NewsApiProxy(val apiKey: String) {

    suspend fun getNews(q: String): List<NewPoint> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://newsdata.io/api/1/news?apikey=$apiKey&q=$q")
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body()?.string()
        val newsData = mutableListOf<NewPoint>()

        val jsonArray = JSONObject(responseBody.toString())
        val news = jsonArray.getJSONArray("results")

        for (i in 0 until news.length()) {
            val jsonObject = news.getJSONObject(i)
            val title = jsonObject.getString("title")
            val description = jsonObject.getString("content")
            val newsItem = NewPoint(title, description)
            newsData.add(newsItem)
        }

        newsData
    }

}