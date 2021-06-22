package com.example.test_bitnet

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    //Дефолтные тесты нужно было удалить
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun cast_current_date() {
        val cal = Calendar.getInstance()
        val dateTime = cal.time
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val formatted = dateFormat.format(dateTime)
        assertEquals("2021-06", formatted)
    }
}