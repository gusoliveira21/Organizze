package com.githubgusoliveira21.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.githubgusoliveira21.organizze.R;
import com.githubgusoliveira21.organizze.config.ConfiguracaoFirebase;
import com.githubgusoliveira21.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome,campoEmail, campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Remove a barra de status que contém a hora, data, notificações
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoSenha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrarUsuario(usuario.getNome(), usuario.getSenha());
                        }else{
                            campoSenha.setError("Preencha o campo com sua senha!");

                            /*Toast.makeText(CadastroActivity.this,
                                    "Preencha a Senha!",
                                    Toast.LENGTH_SHORT).show();*/}

                    }else{
                        campoEmail.setError("Preencha o campo com seu Email!");
                        /*Toast.makeText(CadastroActivity.this,
                                "Preencha o Email!",
                                Toast.LENGTH_SHORT).show();*/
                    }
                }else{
                    campoNome.setError("Preencha o campo com seu Nome!");
                    /*Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();*/
                }
            }
        });
    }



    public void cadastrarUsuario(String usuario, String senha){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario,senha)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,
                            "Cadastro efetuado com sucesso!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CadastroActivity.this,
                            "Erro ao efetuar cadastro!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        })
        ;
    }


}