package com.diegoprado.inpalm.model

import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude

class Usuario {

    @Exclude
    var idUsuario: String? = null
    @Exclude
    var senha: String? = null

    var nome: String? = null
    var email: String? = null
    var receitaTotal: Double? = 0.00
    var despesaTotal: Double? = 0.00

    fun salvarUser(){
        var firebase: DatabaseReference = ConfiguracaoFirebase.getFirebaseDatabase!!

        firebase.child("usuarios")
            .child(this.idUsuario!!)
            .setValue(this)
    }

}