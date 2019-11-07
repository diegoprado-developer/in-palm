package com.diegoprado.inpalm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.diegoprado.inpalm.helper.Base64Custom
import com.diegoprado.inpalm.helper.DateUtil
import com.diegoprado.inpalm.model.Movimentacao
import com.diegoprado.inpalm.model.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ReceitaActivity : AppCompatActivity() {

    lateinit var campoData: TextInputEditText
    lateinit var campoCategoria: TextInputEditText
    lateinit var campoDescricao: TextInputEditText

    lateinit var campoValor: EditText

    lateinit var btnAdd: FloatingActionButton

    var movimentacao: Movimentacao? = null

    private var firebaseRef: DatabaseReference = ConfiguracaoFirebase.getFirebaseDatabase!!
    private var firebaseAuth: FirebaseAuth = ConfiguracaoFirebase.firebasebaseAutenticacao!!

    private var receitaTotal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)

        supportActionBar?.title ="InPalm"

        campoData = findViewById(R.id.edtDataReceita)
        campoCategoria = findViewById(R.id.edtCategoriaReceita)
        campoDescricao = findViewById(R.id.edtDescricaoReceita)

        campoValor = findViewById(R.id.edtValorReceita)

        btnAdd = findViewById(R.id.addFlotingReceita)

        // preencha o campo com a data atual

        campoData.setText(DateUtil().dataAtual)

        getReceitaTotal()

        btnAdd.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(validarCamposReceita()){

                    salvarReceita()
                }
            }
        })

    }

    fun salvarReceita(){

        movimentacao = Movimentacao()

        val data = campoData.text.toString()
        val valorRecuperado = java.lang.Double.parseDouble(campoValor.text.toString())

        movimentacao!!.valor = valorRecuperado
        movimentacao!!.categoria = campoCategoria.text.toString()
        movimentacao!!.descricao = campoDescricao.text.toString()
        movimentacao!!.data = data
        movimentacao!!.tipo = "r"

        var receitaAtualizada: Double = receitaTotal + valorRecuperado

        atualizarReceita(receitaAtualizada)


        movimentacao!!.salvarMovimentacao(data)

        finish()

    }

    fun validarCamposReceita(): Boolean{

        var textoValor: String = campoValor.text.toString()
        var textoData: String = campoData.text.toString()
        var textoCategoria: String = campoCategoria.text.toString()
        var textoDescricao: String = campoDescricao.text.toString()

        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){

                        return true

                    }else{
                        Toast.makeText(this@ReceitaActivity, "descricão não preenchido", Toast.LENGTH_LONG).show()
                        return false
                    }

                }else{
                    Toast.makeText(this@ReceitaActivity, "categoria não preenchida", Toast.LENGTH_LONG).show()
                    return false
                }

            }else{
                Toast.makeText(this@ReceitaActivity, "data não preenchida", Toast.LENGTH_LONG).show()
                return false
            }

        }else{
            Toast.makeText(this@ReceitaActivity, "valor não preenchido", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun getReceitaTotal(){

        var emailUsuario = firebaseAuth.currentUser!!.email
        var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())
        var usuarioRef: DatabaseReference = firebaseRef.child("usuarios").child(idUsuario)

        usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario: Usuario = dataSnapshot.getValue(Usuario::class.java!!)!!
                receitaTotal = usuario.receitaTotal!!
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun atualizarReceita(receita: Double){

        var emailUsuario = firebaseAuth.currentUser!!.email
        var idUsuario = Base64Custom.codificarBase64(emailUsuario.toString())
        var usuarioRef: DatabaseReference = firebaseRef.child("usuarios").child(idUsuario)

        usuarioRef.child("receitaTotal").setValue(receita)

    }
}
