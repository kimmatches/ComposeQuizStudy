package com.example.composequizstudy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.composequizstudy.data.ListConverters
import com.example.composequizstudy.data.Quiz
import com.example.composequizstudy.data.QuizDataSource
import com.example.composequizstudy.data.QuizDatabase
import com.example.composequizstudy.screens.QuizViewModel
import com.example.composequizstudy.ui.theme.ComposeQuizStudyTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val mainViewModel : QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuizStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val mainViewModel: QuizViewModel by viewModels()
                    QuizApp2(mainViewModel)
//                    QuizApp2()
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizApp(
    quizViewModel: QuizViewModel
){
    val quizzes by quizViewModel.quizList.collectAsState()
    val pagerState = rememberPagerState(pageCount = {quizzes.size})
    LaunchedEffect(quizzes) {
        Log.d("QuizApp", "Current quiz list size: ${quizzes.size}")
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            QuizScreen(quiz = quizzes[page])
            Button(onClick = { /*TODO*/ }) {
                Text(text = "dldd")
            }
        }

    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizApp2(
    quizViewModel: QuizViewModel
) {
    val quizzes by quizViewModel.quizList.collectAsState()

    // rememberPagerState를 초기화
    val pagerState = rememberPagerState(pageCount = {quizzes.size})

    // 퀴즈 목록의 변화를 로그로 기록
    LaunchedEffect(quizzes) {
        Log.d("QuizApp", "Current quiz list size: ${quizzes.size}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            QuizScreen(quiz = quizzes[page])
        }

        // 버튼 클릭 시 새 퀴즈 추가
        Button(onClick = { quizViewModel.addQuiz(Quiz(category = "Science", quiz = "What is the boiling point of water?", answer = "100°C")) }) {
            Text(text = "Add Quiz")
        }
    }
}

@Composable
fun QuizScreen(
    quiz: Quiz
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = quiz.category)

            Text(text = quiz.quiz)

            Text(text = quiz.answer)


        }

    }
}
