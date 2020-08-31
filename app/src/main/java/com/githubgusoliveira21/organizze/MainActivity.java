package com.githubgusoliveira21.organizze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.githubgusoliveira21.organizze.activity.CadastroActivity;
import com.githubgusoliveira21.organizze.activity.LoginActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove a barra de status que contém a hora, data, notificações
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_main);
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        //Primeira tela do slide
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorIntro_1)
                .fragment(R.layout.intro_1)
                .build());
        //Segunda tela do slide
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorIntro_2)
                .fragment(R.layout.intro_2)
                .build());
        //terceira tela do slide
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorIntro_3)
                .fragment(R.layout.intro_3)
                .build());
        //Quarta tela do slide
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorIntro_4)
                .fragment(R.layout.intro_4)
                .build());
        //Quinta tela do slide: Cadastro
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorIntro_5)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());


    }

public void btEntrar(View view){
    startActivity(new Intent(this, LoginActivity.class));
}

public void btCadastrar(View view) {
    startActivity(new Intent(this, CadastroActivity.class));
}



}