package com.example.composequizstudy.screens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composequizstudy.data.Quiz
import com.example.composequizstudy.data.QuizDataSource
import com.example.composequizstudy.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(private val repository: QuizRepository) : ViewModel() {
    private var _quizList = MutableStateFlow<List<Quiz>>(emptyList())
    val quizList = _quizList.asStateFlow()

    init {
        checkAndInitializeData()
    }
    private fun checkAndInitializeData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkAndLoadQuizzes()  // 데이터베이스 확인 및 필요한 경우 데이터 로드
            updateQuizList()
        }
    }

    private fun updateQuizList() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllQuizzes().distinctUntilChanged()
                .collect { listOfQuiz ->
                    if (listOfQuiz.isEmpty()) {
                        Log.d("empty", ": 엠티 리스트")
                    } else {
                        _quizList.value = listOfQuiz
                    }
                }
        }
    }
    fun addQuiz(quiz: Quiz) =viewModelScope.launch { repository.addQuiz(quiz) }
    fun updateQuiz(quiz: Quiz) = viewModelScope.launch {
        repository.updateQuiz(quiz)
    }
    fun removeQuiz(quiz: Quiz) = viewModelScope.launch { repository.updateQuiz(quiz) }
    fun deleteAllQuizzes() = viewModelScope.launch {
        repository.deleteAllQuizzes()
    }

}