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


    fun mesAnoDataEscolhida(data: String): String {
            var retornoData= data?.split("/").toTypedArray()
            var dia = retornoData[0]
            var mes = retornoData[1]
            var ano = retornoData[2]

            val mesAno = mes + ano

            return mesAno
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