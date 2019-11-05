package com.diegoprado.inpalm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.helper.DateUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class DespesaActivity : AppCompatActivity() {

    lateinit var campoData: TextInputEditText
    lateinit var campoCategoria: TextInputEditText
    lateinit var campoDescricao: TextInputEditText

    lateinit var campoValor: EditText

    lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)

        campoData = findViewById(R.id.edtDataDespesa)
        campoCategoria = findViewById(R.id.edtCategoriaDespesa)
        campoDescricao = findViewById(R.id.edtDescricaoDespesa)

        campoValor = findViewById(R.id.edtValorDespesa)

        btnAdd = findViewById(R.id.addFlotingDespesa)

        // preencha o campo com a data atual

        campoData.setText(DateUtil().dataAtual)

    }
}
