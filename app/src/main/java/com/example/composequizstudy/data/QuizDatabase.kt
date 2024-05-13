package com.example.composequizstudy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Quiz::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class QuizDatabase: RoomDatabase() {
    abstract fun quizDao() : QuizDao

}