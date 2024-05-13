package com.example.composequizstudy.repository

import android.content.Context
import com.example.composequizstudy.data.Quiz
import com.example.composequizstudy.data.QuizDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.inject.Inject

interface QuizRepositoryIn {
    fun getQuizList(): List<Quiz>
    fun getAllQuizzes(): Flow<List<Quiz>>
    suspend fun addQuiz(quiz: Quiz)
    suspend fun updateQuiz(quiz: Quiz)
    suspend fun removeQuiz(quiz: Quiz)
    suspend fun deleteAllQuizzes()
    suspend fun getQuizzesByCategory(category: String): Quiz
}

//class QuizRepository @Inject constructor(
//    @ApplicationContext private val context: Context,
//    private val quizDao: QuizDao
//) {
//    fun getQuizList(): List<Quiz> {
//        val inputStream = context.assets.open("quizzes.json")
//        val inputSystemReader = InputStreamReader(inputStream)
//        val jsonString = inputSystemReader.readText()
//        val type = object : TypeToken<List<Quiz>>() {}.type
//
//        return GsonBuilder().create().fromJson(jsonString, type)
//    }
//    suspend fun addQuiz(quiz: Quiz) = quizDao.insert(quiz)
//
//    suspend fun updateQuiz(quiz: Quiz) = quizDao.update(quiz)
//
//    suspend fun deleteQuiz(quiz: Quiz) = quizDao.deleteQuiz(quiz)
//
//    suspend fun deleteAllQuiz(quiz: Quiz) = quizDao.deleteAll()
//
//    suspend fun getAllQuizzes(): Flow<List<Quiz>> =
//        quizDao.getQuizzes().flowOn(Dispatchers.IO).conflate()
//
//    suspend fun getQuizzesByCategory(category: String): Quiz =
//        quizDao.getQuizzesByCategory(category)
//}

//
//interface QuizRepositoryIn {
//    fun getAllQuizzes(): Flow<List<Quiz>>
//    suspend fun addQuiz(quiz: Quiz)
//    suspend fun updateQuiz(quiz: Quiz)
//    suspend fun removeQuiz(quiz: Quiz)
//    suspend fun deleteAllQuizzes()
//    suspend fun getQuizzesByCategory(category: String): Quiz
//}
//
//class QuizRepository @Inject constructor(
//    @ApplicationContext private val context: Context,
//    private val quizDao: QuizDao
//) : QuizRepositoryIn {
//    override fun getQuizList(): List<Quiz>{
//        val inputStream = context.assets.open("quizzes.json")
//        val inputSystemReader = InputStreamReader(inputStream)
//        val jsonString = inputSystemReader.readText()
//        val type = object : TypeToken<List<Quiz>>() { }.type
//
//        return GsonBuilder().create().fromJson(jsonString, type)
//    }
//    val quizzes: Flow<List<Quiz>> = quizDao.getQuizzes().flowOn(Dispatchers.IO).conflate()
//    override fun getAllQuizzes(): Flow<List<Quiz>> {
//        return quizDao.getQuizzes()
//    }
//
//    override suspend fun addQuiz(quiz: Quiz) {
//        return  quizDao.insert(quiz)
//    }
//
//    override suspend fun updateQuiz(quiz: Quiz) {
//        return  quizDao.update(quiz)
//    }
//
//    override suspend fun removeQuiz(quiz: Quiz) {
//        return quizDao.deleteQuiz(quiz)
//    }
//
//    override suspend fun deleteAllQuizzes() {
//        return quizDao.deleteAll()
//    }
//
//    override suspend fun getQuizzesByCategory(category: String): Quiz {
//        return  quizDao.getQuizzesByCategory(category)
//    }
//
//}


class QuizRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val quizDao: QuizDao
) : QuizRepositoryIn {

    private var isDataLoaded = false

    override fun getQuizList(): List<Quiz> {
        val inputStream = context.assets.open("quizzes.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Quiz>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    suspend fun insertQuizzesIntoDatabase() {
        if (!isDataLoaded) {
            val quizzes = getQuizList()
            quizzes.forEach { quiz ->
                if (!quizDao.exists(quiz.id)) {
                    quizDao.insert(quiz)
                }
            }
            isDataLoaded = true  // 데이터 로드 완료 후 플래그 설정
        }
    }
    suspend fun checkAndLoadQuizzes() {
        val quizCount = quizDao.countQuizzes()  // 데이터베이스에서 퀴즈 수를 세는 쿼리
        if (quizCount == 0) {  // 퀴즈가 데이터베이스에 없는 경우
            insertQuizzesIntoDatabase()
        }
    }


    val quizzes: Flow<List<Quiz>> = quizDao.getQuizzes().flowOn(Dispatchers.IO).conflate()

    override fun getAllQuizzes(): Flow<List<Quiz>> = quizDao.getQuizzes()

    override suspend fun addQuiz(quiz: Quiz) {
        quizDao.insert(quiz)
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        quizDao.update(quiz)
    }

    override suspend fun removeQuiz(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }

    override suspend fun deleteAllQuizzes() {
        quizDao.deleteAll()
    }

    override suspend fun getQuizzesByCategory(category: String): Quiz {
        return  quizDao.getQuizzesByCategory(category)
    }
}