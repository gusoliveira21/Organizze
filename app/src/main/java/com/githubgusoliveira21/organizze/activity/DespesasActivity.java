package com.githubgusoliveira21.organizze.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.githubgusoliveira21.organizze.R;
import com.githubgusoliveira21.organizze.config.ConfiguracaoFirebase;
import com.githubgusoliveira21.organizze.helper.Base64Custom;
import com.githubgusoliveira21.organizze.helper.DateCustom;
import com.githubgusoliveira21.organizze.model.Movimentacao;
import com.githubgusoliveira21.organizze.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoDescricao, campoCategoria;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private Double despesaTotal;
    private Double despesaAtualizada;
    private String data;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        campoData = findViewById(R.id.editDataDespesa);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao= findViewById(R.id.editDescricao);
        campoValor= findViewById(R.id.campoValor);

        //Preenche o campo data com a data atual
        campoData.setText(DateCustom.dataAtual());

        Toast.makeText(this, "Depois: " + campoData.getText().toString(), Toast.LENGTH_SHORT).show();

        recuperarDespesaTotal();

    };

    public void salvarDespesa(View view){
        if(validarCamposDespesa()){

            Toast.makeText(this, "Salvar: "+campoData.getText().toString(), Toast.LENGTH_SHORT).show();

            data = campoData.getText().toString();

            Toast.makeText(this, "Salvar: "+campoData.getText().toString(), Toast.LENGTH_SHORT).show();

            double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            //movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
            movimentacao = new Movimentacao();
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");

            despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesa( despesaAtualizada );

            movimentacao.salvar(data);
            finish();
            Toast.makeText(this, "Despesa Inserida!", Toast.LENGTH_SHORT).show();
        }
    };

    public boolean validarCamposDespesa(){
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if (!textoValor.isEmpty()){
            if(!textoCategoria.isEmpty()){
               if(!textoDescricao.isEmpty()){
                   if(!textoData.isEmpty()){
                        return true;
                    }else{
                       msgErro();
                       campoData.setError("Diga uma data");
                       return false;}
                }else{
                   msgErro();
                   campoDescricao.setError("Diga uma descrição");
                   return false;}
            }else{
                msgErro();
                campoCategoria.setError("Diga uma categoria\nex:Água, Luz, Telefone, Internet");
                return false;}
        }else{
            msgErro();
            campoValor.setError("Diga um valor");
            return false;}
    };

    public void recuperarDespesaTotal(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void msgErro(){
        Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_LONG).show();
    }

    public void atualizarDespesa(Double despesa){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);
    }

}