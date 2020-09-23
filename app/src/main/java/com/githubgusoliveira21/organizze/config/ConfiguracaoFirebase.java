package com.githubgusoliveira21.organizze.config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase ;

    //Retorna a instancia do firebase database
    public static DatabaseReference getFirebaseDatabase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }


    //Retorna a instancia do firebase auth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if ( autenticacao == null)
            autenticacao = FirebaseAuth.getInstance();
        return autenticacao;
    }



}
