package com.diegoprado.inpalm.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfiguracaoFirebase {

    companion object {

        private var autenticacao: FirebaseAuth? = null
        private var firebaseReference: DatabaseReference? = null

        //retorna instancia firebase database
        val getFirebaseDatabase: DatabaseReference?
            get() {

                if (firebaseReference == null) {
                    firebaseReference = FirebaseDatabase.getInstance().reference
                }

                return firebaseReference
            }

        //retorna instancia firebase auth
        val firebasebaseAutenticacao: FirebaseAuth?
            get() {

                if (autenticacao == null) {
                    autenticacao = FirebaseAuth.getInstance()
                }

                return this!!.autenticacao
            }
    }
}