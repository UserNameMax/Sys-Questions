package ru.mishenko.maksin.sysquestions.repository

import ru.mishenko.maksin.sysquestions.model.Question

interface QuestionRepository {
    fun getQuestions(): List<Question>
}