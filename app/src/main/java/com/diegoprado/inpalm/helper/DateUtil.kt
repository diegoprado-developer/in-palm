package com.diegoprado.inpalm.helper

import java.text.SimpleDateFormat

class DateUtil {

    val dataAtual: String?
        get() {


            var date: Long = System.currentTimeMillis()

            val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

            var dataString: String = simpleDateFormat.format(date)

            return dataString
        }

//    fun dataAtual(): String {
//
//        var date: Long = System.currentTimeMillis()
//
//        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
//
//        var dataString: String = simpleDateFormat.format(date)
//
//        return dataString
//    }
}