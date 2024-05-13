package com.example.composequizstudy.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.composequizstudy.data.QuizDao
import com.example.composequizstudy.data.QuizDatabase
import com.example.composequizstudy.repository.QuizRepository
//import com.example.composequizstudy.repository.QuizRepositoryIn
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Singleton
    @Provides
    fun provideQuizDao(quizDatabase: QuizDatabase) : QuizDao
        = quizDatabase.quizDao()

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context = application


    @Singleton
    @Provides
    fun provideQuizRepository(context: Context,quizDao: QuizDao): QuizRepository = QuizRepository(context, quizDao)

//    @Singleton
//    @Provides
//    fun provideQuizRepository(
//        quizDao: QuizDao
//    ): QuizRepositoryIn {
//        return QuizRepository(quizDao)
//    }

    @Singleton
    @Provides
    fun provideQuizDatabase(@ApplicationContext context: Context): QuizDatabase {
        return Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            "quiz_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}