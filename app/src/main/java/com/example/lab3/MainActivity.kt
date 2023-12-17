package com.example.lab3

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val API_KEY = "pub_3491826262a726cf9bf20e46f5ca3db19b98b"


class MainActivity : Activity() {
    private val newsProxy = NewsApiProxy(API_KEY)


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val searchField = findViewById<EditText>(R.id.searchEditText)
        searchButton.setOnClickListener {
            val query = searchField.text.toString().trim()

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val result = getRequests(query)
                    val itemList = findViewById<RecyclerView>(R.id.items_list)
                    val newsAdapter = newsAdapter()
                    itemList.layoutManager = LinearLayoutManager(this@MainActivity)
                    itemList.adapter = newsAdapter
                    newsAdapter.clear()
                    newsAdapter.addNewsPoints(result)
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private suspend fun getRequests(query: String): List<NewPoint> {
        return newsProxy.getNews(query)
    }

}
