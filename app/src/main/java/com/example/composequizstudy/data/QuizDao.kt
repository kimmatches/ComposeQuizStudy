package com.example.composequizstudy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(ListConverters::class)
interface QuizDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quiz: Quiz)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quizzes: List<Quiz>)

    @Query("SELECT EXISTS(SELECT 1 FROM quizTable WHERE id = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("SELECT COUNT(id) FROM quizTable")
    suspend fun countQuizzes(): Int

    @Query("SELECT * FROM quizTable")
    fun getQuizzes(): Flow<List<Quiz>>

    @Query("SELECT * FROM quizTable WHERE category = :category")
    suspend fun getQuizzesByCategory(category: String):  Quiz





    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(quiz: Quiz)

    @Query("DELETE FROM QUIZTABLE")
    suspend fun deleteAll()
    @Delete
    suspend fun deleteQuiz(quiz: Quiz)

}