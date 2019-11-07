package com.diegoprado.inpalm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.diegoprado.inpalm.model.Usuario
import com.google.firebase.auth.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {


    private lateinit var autenticacao: FirebaseAuth
    var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var txtEmail = edtEmail.text.toString()
                var txtSenha = edtSenha.text.toString()


                if( !txtEmail.isEmpty() ){
                    if( !txtSenha.isEmpty() ){

                        usuario = Usuario()
                        usuario!!.email = txtEmail
                        usuario!!.senha = txtSenha

                        validarLogin()

                    }else{
                        Toast.makeText(this@LoginActivity, "Preencha a senha", Toast.LENGTH_LONG).show()
                    }

                }else{
                    Toast.makeText(this@LoginActivity, "Preencha o e-mail", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun validarLogin(){
        autenticacao = ConfiguracaoFirebase.firebasebaseAutenticacao!!

        autenticacao.signInWithEmailAndPassword(
            usuario!!.email.toString(),
            usuario!!.senha.toString()
        ).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){

                abrirPrincipal()

            }else{

                var excecao: String = ""

                try {
                    throw task.exception!!
                }catch (e: FirebaseAuthInvalidUserException){
                    excecao = "Usuario não cadastrado"
                }catch (e: FirebaseAuthInvalidCredentialsException){
                    excecao = "E-mail ou senha incorreto"
                }catch (e: Exception){
                    excecao = "Erro ao cadastrar usuario: " + e.message
                    e.printStackTrace()
                }

                Toast.makeText(this@LoginActivity,  excecao, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun abrirPrincipal(){
        startActivity(Intent(this, PrincipalActivity::class.java))
        finish()
    }
}
