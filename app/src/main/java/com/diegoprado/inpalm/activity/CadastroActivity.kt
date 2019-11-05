package com.diegoprado.inpalm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.diegoprado.inpalm.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.lang.Exception

class CadastroActivity : AppCompatActivity() {


    private lateinit var autenticacao: FirebaseAuth
    var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)


        btnCadastrar.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var txtNome = edtNome.text.toString()
                var txtEmail = edtEmail.text.toString()
                var txtSenha = edtSenha.text.toString()


                if( !txtNome.isEmpty() ){
                    if( !txtEmail.isEmpty() ){
                        if( !txtSenha.isEmpty() ){

                            usuario = Usuario()
                            usuario!!.nome = txtNome
                            usuario!!.email = txtEmail
                            usuario!!.senha = txtSenha

                            cadastrarUsuario()

                        }else{
                            Toast.makeText(this@CadastroActivity, "Campo senha em branco", Toast.LENGTH_LONG).show()
                        }

                    }else{
                        Toast.makeText(this@CadastroActivity, "Campo email em branco", Toast.LENGTH_LONG).show()
                    }

                }else {

                    Toast.makeText(this@CadastroActivity, "Campo nome em branco", Toast.LENGTH_LONG).show()
                }
            }
        }
        )
    }

    fun cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.firebasebaseAutenticacao!!
        autenticacao.createUserWithEmailAndPassword(usuario!!.email.toString(), usuario!!.senha.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@CadastroActivity, "Usuario cadastrado com sucesso", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    var excecao: String = ""

                    try {
                        throw task.exception!!
                    }catch (e: FirebaseAuthWeakPasswordException){
                        excecao = "Digite uma senha mais forte"
                    }catch (e: FirebaseAuthInvalidCredentialsException){
                        excecao = "Digite um e-mail valido"
                    }catch (e: FirebaseAuthUserCollisionException){
                        excecao = "Conta já cadastradada"
                    }catch (e: Exception){
                        excecao = "Erro ao cadastrar usuario: " + e.message
                        e.printStackTrace()
                    }

                    Toast.makeText(this@CadastroActivity, excecao, Toast.LENGTH_LONG).show()
                }
            }

    }
}
