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

public class LoginActivity extends AppCompatActivity {
    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Remove a barra de status que contém a hora, data, notificações
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);


        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                vaidarLogin();

                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                    }
                    else{
                        campoSenha.setError("Preencha o campo com sua senha!");
                    }
                }
                else{
                    campoEmail.setError("Preencha o campo com seu Email!");
                }
            }
        });
    }

    public  void vaidarLogin(){

    autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Toast.makeText(LoginActivity.this,
                        "Login efetuado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(LoginActivity.this,
                        "Erro no l,ogin!",
                        Toast.LENGTH_SHORT).show();
            }
                /*/String excecao = "";
                try{
                    throw task.getException();
                }

                catch (FirebaseAuthWeakPasswordException e)
                {
                    //excecao = "Digite uma senha mais forte!";
                    campoSenha.setError("Digite uma senha válida!");
                }
                catch (FirebaseAuthUserCollisionException e)
                {
                    //excecao = "Esse email já existe em nossa base de dados!";
                    campoEmail.setError("Esse e-mail já está registrado!");
                }
                catch (FirebaseAuthInvalidCredentialsException e){
                    //excecao = "Digite um e-mail válido!";
                    campoEmail.setError("Diga um e-mail válido!");
                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this,"Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_LONG).show();

                    //excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                    e.printStackTrace();
                }*/
        }
    });

    }




}