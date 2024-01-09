package ru.mishenko.maksin.sysquestions.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val text: String,
    val answers: List<String>,
    val trueAnswersIndex: List<Int>
)
