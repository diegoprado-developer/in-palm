package com.diegoprado.inpalm.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.diegoprado.inpalm.helper.Base64Custom
import com.diegoprado.inpalm.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener

import kotlinx.android.synthetic.main.activity_principal.*
import java.text.DecimalFormat

class PrincipalActivity : AppCompatActivity() {

    var calendarView: MaterialCalendarView? = null

    var textoSaudacao: TextView? = null
    var textoSaldo: TextView? = null

    var authFirebase: FirebaseAuth? = ConfiguracaoFirebase.firebasebaseAutenticacao!!
    var firebaseRef: DatabaseReference = ConfiguracaoFirebase.getFirebaseDatabase!!

    var despesaTotal = 0.0
    var receitaTotal = 0.0
    var resumoUsuario = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

        textoSaldo = findViewById(R.id.txtSaldo)
        textoSaudacao = findViewById(R.id.txtSaudacao)
        calendarView = findViewById(R.id.calendarView)

        configuraCalendarView()
        recuperarResumo()

//        menu_despesa.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    fun recuperarResumo(){

        var emailUsuario = authFirebase?.currentUser!!.email
        var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())
        var usuarioRef: DatabaseReference = firebaseRef.child("usuarios").child(idUsuario)

        usuarioRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario: Usuario = dataSnapshot.getValue(Usuario::class.java!!)!!

                despesaTotal = usuario.despesaTotal!!
                receitaTotal = usuario.receitaTotal!!

                resumoUsuario = receitaTotal - despesaTotal

                var decimalFormat: DecimalFormat = DecimalFormat("0.##")
                var resultFormatado = decimalFormat.format(resumoUsuario)

                textoSaudacao?.text = "Olá, " + usuario.nome
                textoSaldo?.text = "R$ " + resultFormatado
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_principal, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            R.id.menuSair -> {

                authFirebase?.signOut()

                startActivity(Intent(this, MainActivity::class.java))

            }
        }

        return super.onOptionsItemSelected(item)
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
