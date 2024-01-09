package ru.mishenko.maksin.sysquestions.repository

import android.content.Context
import kotlinx.serialization.json.Json
import ru.mishenko.maksin.sysquestions.R
import ru.mishenko.maksin.sysquestions.model.Question
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class ResourceQuestionRepository(private val context: Context) : QuestionRepository {
    override fun getQuestions(): List<Question> {
        return Json.decodeFromString(context.getRawFile(R.raw.questions))
    }

    private fun Context.getRawFile(id: Int): String {
        val inputStream = resources.openRawResource(id)
        val writer = StringWriter()
        with(BufferedReader(InputStreamReader(inputStream, "UTF-8"))) {
            var buffer = CharArray(1024)
            var n = 0
            while (read(buffer).run {
                    n = this
                    this != -1
                }) {
                writer.write(buffer, 0, n);
            }
        }
        return writer.toString()
    }
}