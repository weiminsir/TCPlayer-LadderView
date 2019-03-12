package com.wick.player

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.ParseException
import java.text.SimpleDateFormat


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun dateToStamp() {

        val a=getTimestampByTime("2019-02-27 16:40:38") - System.currentTimeMillis()

        println(a)

        val time = a/1000

        val hh = time / 3600
        val mm = (time - 3600 * hh) / 60
        val ss = (time - 3600 * hh - 60 * mm)
        val sb = StringBuilder()
        if (mm < 10) {
            sb.append("0" + mm + "分")
        } else {
            sb.append(mm.toString().plus("分"))
        }

        if (ss < 10) {
            sb.append("0" + ss + "秒")
        } else {
            sb.append(ss.toString() + "秒");
        }

        println(sb)


    }

    fun getCurDate(): String {
        val currentTimeMillis = System.currentTimeMillis()
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(currentTimeMillis)
    }

    fun getTimestampByTime(str: String): Long {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        try {
            val date = dateFormat.parse(str)
            return date.time

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

}
