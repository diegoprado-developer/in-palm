package com.diegoprado.inpalm.model

import com.diegoprado.inpalm.config.ConfiguracaoFirebase
import com.diegoprado.inpalm.helper.Base64Custom
import com.diegoprado.inpalm.helper.DateUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class Movimentacao {

    var data: String? = null
    var categoria: String? = null
    var descricao: String? = null
    var tipo: String? = null
    var valor: Double? = null

    fun salvarMovimentacao(data: String){

        var autenticacao: FirebaseAuth = ConfiguracaoFirebase.firebasebaseAutenticacao!!
        var idUsuario = Base64Custom.codificarBase64(autenticacao.currentUser?.email.toString())
        var firebaseDatabase: DatabaseReference = ConfiguracaoFirebase.getFirebaseDatabase!!
        var mesAno = DateUtil().mesAnoDataEscolhida(data)

        firebaseDatabase.child("movimentacao")
            .child(idUsuario)
            .child(mesAno)
            .push()
            .setValue(this)
    }
}