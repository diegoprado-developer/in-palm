package com.diegoprado.inpalm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.diegoprado.inpalm.R
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide

class MainActivity : IntroActivity() {

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

    fun btnEntrar(view: View){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun newCadastro(view: View){
        startActivity(Intent(this, CadastroActivity::class.java))
    }
}
