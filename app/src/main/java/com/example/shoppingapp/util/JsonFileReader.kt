package com.example.shoppingapp.util

import android.content.Context
import com.example.e_com.data.Category
import com.example.shoppingapp.data.CategoriesResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class JsonFileReader {

    suspend fun readAndParseJson(context: Context, resourceId: Int): List<Category> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                val reader = BufferedReader(InputStreamReader(inputStream))

                val jsonString = reader.use { it.readText() }

                val gson = Gson()
                val categories = gson.fromJson(jsonString, CategoriesResponse::class.java).categories

                categories ?: emptyList()
            } catch (e: IOException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}