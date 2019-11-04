package com.diegoprado.inpalm.config

import com.google.firebase.auth.FirebaseAuth

class ConfiguracaoFirebase {

    companion object {

        private var autenticacao: FirebaseAuth? = null


        val firebasebaseAutenticacao: FirebaseAuth?
            get() {

                if (autenticacao == null) {
                    autenticacao = FirebaseAuth.getInstance()
                }

                return this!!.autenticacao
            }
    }
}