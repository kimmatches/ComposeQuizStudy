package com.example.composequizstudy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "quizTable")
@TypeConverters(ListConverters::class)
data class Quiz(
    val category: String,
    var quiz: String,
    var answer: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "id = $id, category = $category, quiz = $quiz, answer = $answer"
    }
}

class QuizDataSource {
    fun loadNotes(): List<Quiz>{
        return listOf(
            Quiz("데이터베이스","문제", "답" ),
            Quiz("데이터베이스","문제", "답" ),
            Quiz("데이터베이스","문제", "답" ),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
            Quiz("데이터베이스","문제", "답"),
        )
    }
}