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
import com.githubgusoliveira21.organizze.helper.Base64Custom;
import com.githubgusoliveira21.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {


    private EditText campoNome,campoEmail, campoSenha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //getSupportActionBar().setTitle("Cadastro");

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
                            cadastrarUsuario();
                        }
                        else{
                            campoSenha.setError("Preencha o campo com sua senha!");
                        }
                    }
                    else{
                        campoEmail.setError("Preencha o campo com seu Email!");
                    }
                }
                else{
                    campoNome.setError("Preencha o campo com seu Nome!");
                }
            }
        });
    }



    public void cadastrarUsuario()
    {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setIdUsuario(idUsuario);
                            usuario.salvar();

                            finish();

                        }else{
                            //String excecao = "";
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
                                Toast.makeText(CadastroActivity.this,"Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_LONG).show();

                                //excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            //Como não to usando a variavel excessao, não preciso usar essa linha
                            //Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();

                        }





                    }
                })
        ;
    }


}