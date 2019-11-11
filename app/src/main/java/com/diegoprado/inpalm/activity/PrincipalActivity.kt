package com.diegoprado.inpalm.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.adapter.AdapterMovimentacao
import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.diegoprado.inpalm.helper.Base64Custom
import com.diegoprado.inpalm.model.Movimentacao
import com.diegoprado.inpalm.model.Usuario
import com.google.android.material.behavior.SwipeDismissBehavior
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
import java.util.*

class PrincipalActivity : AppCompatActivity() {

    var calendarView: MaterialCalendarView? = null

    var textoSaudacao: TextView? = null
    var textoSaldo: TextView? = null

    var recycleView: RecyclerView? = null
    var adapterMovimentacao: AdapterMovimentacao? = null
    private var listMovimentacoes: ArrayList<Movimentacao> = ArrayList()
    private lateinit var movimentacao: Movimentacao
    private var movimentacoesRef: DatabaseReference? = null
    private var mesAnoSelecionado: String? = null

    var authFirebase: FirebaseAuth? = ConfiguracaoFirebase.firebasebaseAutenticacao!!
    var firebaseRef: DatabaseReference = ConfiguracaoFirebase.getFirebaseDatabase!!
    private var usuarioRef: DatabaseReference? = ConfiguracaoFirebase.getFirebaseDatabase!!
    private lateinit var valueEventListenerUsuario: ValueEventListener
    private lateinit var valueEventListenerMovimentacoes: ValueEventListener

    var despesaTotal = 0.0
    var receitaTotal = 0.0
    var resumoUsuario = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        toolbar.title = "InPalm"
        setSupportActionBar(toolbar)

        textoSaldo = findViewById(R.id.txtSaldo)
        textoSaudacao = findViewById(R.id.txtSaudacao)
        calendarView = findViewById(R.id.calendarView)
        recycleView = findViewById(R.id.recycleMovimentos)

        configuraCalendarView()
        swipe()

        //configurando adapter
        adapterMovimentacao = AdapterMovimentacao(listMovimentacoes, this)

        //configurando recycler view
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycleView!!.layoutManager = layoutManager
        recycleView!!.setHasFixedSize(true)
        recycleView!!.adapter = adapterMovimentacao

//        menu_despesa.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    fun swipe(){
        val itemTouch = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                val dragFralgs =
                    ItemTouchHelper.ACTION_STATE_IDLE //como nao vamos usar, deixamos indisponivel

                val swipFlags = ItemTouchHelper.START or ItemTouchHelper.END

                return ItemTouchHelper.Callback.makeMovementFlags(dragFralgs, swipFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                excluirMovimentacao(viewHolder)
            }
        }

        ItemTouchHelper(itemTouch).attachToRecyclerView(recycleView)

    }

    fun excluirMovimentacao(viewHolder: RecyclerView.ViewHolder){

        var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@PrincipalActivity)

        alertDialog.setTitle("Excluir movimentação da conta")
        alertDialog.setMessage("Você tem certeza que deseja realmente excluir essa movimentação da sua conta?")
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("Confirmar", DialogInterface.OnClickListener { dialog, which ->
            val position = viewHolder.adapterPosition
            movimentacao = listMovimentacoes.get(position)

            var emailUsuario = authFirebase?.currentUser!!.email
            var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())

            var mesAno = mesAnoSelecionado.toString()

            movimentacoesRef = firebaseRef!!.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)

            movimentacoesRef!!.child(movimentacao.key!!).removeValue()

            adapterMovimentacao!!.notifyItemRemoved(position)

            atualizarSaldo()

        })

        alertDialog.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this@PrincipalActivity, "Cancelado", Toast.LENGTH_LONG).show()

            adapterMovimentacao!!.notifyDataSetChanged()
        })

        val dialog: AlertDialog = alertDialog.create()
        dialog.show()

    }

    fun atualizarSaldo(){

        var emailUsuario = authFirebase?.currentUser!!.email
        var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario)

        if(movimentacao.tipo.equals("r")){
            receitaTotal = receitaTotal - movimentacao.valor!!
            usuarioRef!!.child("receitaTotal").setValue(receitaTotal)
        }

        if(movimentacao.tipo.equals("d")){
            despesaTotal = despesaTotal - movimentacao.valor!!
            usuarioRef!!.child("despesaTotal").setValue(despesaTotal)
        }
    }

    fun recuperarMovimentacoes(){

        var emailUsuario = authFirebase?.currentUser!!.email
        var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())

        var mesAno = mesAnoSelecionado.toString()

        movimentacoesRef = firebaseRef!!.child("movimentacao")
            .child(idUsuario)
            .child(mesAno)

        valueEventListenerMovimentacoes = movimentacoesRef!!.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listMovimentacoes.clear()

                for(dados: DataSnapshot in dataSnapshot.children){
                    var movimentacao: Movimentacao = dados.getValue(Movimentacao::class.java)!!
                    movimentacao.key = dados.key
                    listMovimentacoes.add(movimentacao)
                }

                adapterMovimentacao?.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

        })
    }

    fun recuperarResumo(){

        var emailUsuario = authFirebase?.currentUser!!.email
        var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario)

        Log.i("onStop", "evento listenerUsuario foi adicionado")
        valueEventListenerUsuario = usuarioRef!!.addValueEventListener(object: ValueEventListener {
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

        var dataAtual: CalendarDay = calendarView!!.currentDate

        var mesSelecionado: String = String.format("%02d", dataAtual.month)
        mesAnoSelecionado = mesSelecionado + "" + dataAtual.year.toString()


        calendarView?.setOnMonthChangedListener(object : OnMonthChangedListener {
            override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
                var mesSelecionado: String = String.format("%02d", date!!.month)
                mesAnoSelecionado = mesSelecionado + "" + date?.year.toString()

                movimentacoesRef!!.removeEventListener(valueEventListenerMovimentacoes)
                recuperarMovimentacoes()

            }
        })
    }

    override fun onStart() {
        super.onStart()

        recuperarResumo()
        recuperarMovimentacoes()
    }

    override fun onStop() {
        super.onStop()
        Log.i("onStop", "evento listenerUsuario foi removido")
        usuarioRef!!.removeEventListener(valueEventListenerUsuario)
        movimentacoesRef!!.removeEventListener(valueEventListenerMovimentacoes)
    }

}
