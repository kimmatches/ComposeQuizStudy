package com.example.composequizstudy.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

@ProvidedTypeConverter
class ListConverters {
    private val gson = GsonBuilder().create()
    @TypeConverter
    fun listToJson(value: List<String?>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return gson.fromJson(value, Array<String>::class.java)?.toList()
    }
}