package com.diegoprado.inpalm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.diegoprado.inpalm.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener

import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {

    var calendarView: MaterialCalendarView? = null

    var textoSaudacao: TextView? = null
    var textoSaldo: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

        textoSaldo = findViewById(R.id.txtSaldo)
        textoSaudacao = findViewById(R.id.txtSaudacao)
        calendarView = findViewById(R.id.calendarView)

        configuraCalendarView()

//        menu_despesa.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }


    fun adicionarReceita(view: View) {
        startActivity(Intent(this, ReceitaActivity::class.java))
    }

    fun adicionarDespesa(view: View){
        startActivity(Intent(this, DespesaActivity::class.java))
    }

    fun configuraCalendarView(){

        val meses = arrayOf<CharSequence>(
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
        )

        calendarView?.setTitleMonths(meses)

        calendarView?.setOnMonthChangedListener(object : OnMonthChangedListener {
            override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {

            }
        })
    }

}
