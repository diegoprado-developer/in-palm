package com.diegoprado.inpalm;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;

public class slideTeste extends IntroActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);
    }
}
