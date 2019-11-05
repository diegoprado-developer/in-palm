package com.diegoprado.inpalm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide

class MainActivity : IntroActivity() {

    private lateinit var autenticacao: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.slider_1)
            .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slider_2)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slider_3)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slider_4)
                .build()
        )

        addSlide(
            FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slider_intro_cadastro)
                .canGoForward(false)
                .build()
        )

    }

    override fun onStart() {
        super.onStart()
        verificarUsuarioLogado()
    }

    fun btnEntrar(view: View){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun newCadastro(view: View){
        startActivity(Intent(this, CadastroActivity::class.java))
    }

    fun verificarUsuarioLogado(){

        autenticacao = ConfiguracaoFirebase.firebasebaseAutenticacao!!
//        autenticacao.signOut()
        if(autenticacao.currentUser != null){
           abrirPrincipal()
        }
    }

    fun abrirPrincipal(){
        startActivity(Intent(this, PrincipalActivity::class.java))
        finish()
    }
}
