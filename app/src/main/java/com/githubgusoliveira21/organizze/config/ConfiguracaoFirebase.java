package com.githubgusoliveira21.organizze.config;
import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;

    //Retorna a instancia do firebase
    public static FirebaseAuth getFirebaseAutenticacao(){
        if ( autenticacao == null)
            autenticacao = FirebaseAuth.getInstance();
        return autenticacao;
    }



}
