package com.diegoprado.inpalm;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;

public class slideTeste extends IntroActivity {

    private static FirebaseAuth autenticacao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);
    }


    public void getFirebasebaseAutenticacao() {

        autenticacao.createUserWithEmailAndPassword("email", "senha")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(slideTeste.this, "cadastrado com sucesso", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(slideTeste.this, "erro ao cadastrar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
