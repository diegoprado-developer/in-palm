package com.diegoprado.inpalm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.diegoprado.inpalm.R

import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

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

}
