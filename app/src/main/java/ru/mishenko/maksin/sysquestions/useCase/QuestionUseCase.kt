package ru.mishenko.maksin.sysquestions.useCase

import ru.mishenko.maksin.sysquestions.repository.QuestionRepository
import java.util.GregorianCalendar
import kotlin.random.Random

class QuestionUseCase(private val questionRepository: QuestionRepository) {
    private val allEvents = questionRepository.getQuestions()

    fun getAllEvents() = getRandomEvents(allEvents.size)

    fun getRandomEvents(n: Int) =
        with(allEvents.toMutableList()) {
            val random = Random(GregorianCalendar().timeInMillis)
            List(n) { get(random.nextInt(size)).apply { remove(this) } }
        }
}