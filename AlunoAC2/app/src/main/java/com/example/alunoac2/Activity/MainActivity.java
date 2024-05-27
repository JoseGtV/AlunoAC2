package com.example.alunoac2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alunoac2.API.Aluno.AlunoService;
import com.example.alunoac2.API.Aluno.ApiAluno;
import com.example.alunoac2.API.CEP.ApiCep;
import com.example.alunoac2.API.CEP.CepService;
import com.example.alunoac2.Models.Aluno;
import com.example.alunoac2.Models.ViaCepResponse;
import com.example.alunoac2.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText ra;
    EditText nome;
    EditText cepEditText;
    EditText logradouroEditText;
    EditText complementoEditText;
    EditText bairroEditText;
    EditText ufEditText;
    EditText localidadeEditText;
    Button loginButton, btnVisualizar;

    AlunoService alunoService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        alunoService = ApiAluno.getAlunoService();

        ra = (EditText) findViewById(R.id.ra);
        nome = (EditText) findViewById(R.id.nome);
        loginButton = (Button) findViewById(R.id.loginButton);
        btnVisualizar = (Button) findViewById(R.id.btnVisualizar);
        cepEditText = findViewById(R.id.cep);
        logradouroEditText = findViewById(R.id.logradouro);
        complementoEditText = findViewById(R.id.complemento);
        bairroEditText = findViewById(R.id.bairro);
        ufEditText = findViewById(R.id.uf);
        localidadeEditText = findViewById(R.id.localidade);

        cepEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String cep = cepEditText.getText().toString();
                if (!cep.isEmpty()) {
                    fetchAddress(cep);
                }
            }
        });

        /**
         *

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = new Aluno();
                aluno.setRa(Integer.parseInt(ra.getText().toString()));
                aluno.setNome(nome.getText().toString());
                aluno.setCep(cepEditText.getText().toString());
                aluno.setLogradouro(logradouroEditText.getText().toString());
                aluno.setComplemento(complementoEditText.getText().toString());
                aluno.setBairro(bairroEditText.getText().toString());
                aluno.setUf(ufEditText.getText().toString());
                aluno.setLocalidade(localidadeEditText.getText().toString());

                inserirAluno(aluno);


            }
        });
         */
        loginButton.setOnClickListener(v -> {
            String raText = ra.getText().toString();
            if (!raText.isEmpty()) {
                int raValue = Integer.parseInt(raText);
                verificarRA(raValue);
            }
        });

        btnVisualizar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaAlunoActivity.class);
            startActivity(intent);
        });
    }

    //cria aluno
    private Aluno criarAluno(){
        Aluno aluno = new Aluno();
        aluno.setRa(Integer.parseInt(ra.getText().toString()));
        aluno.setNome(nome.getText().toString());
        aluno.setCep(cepEditText.getText().toString());
        aluno.setLogradouro(logradouroEditText.getText().toString());
        aluno.setComplemento(complementoEditText.getText().toString());
        aluno.setBairro(bairroEditText.getText().toString());
        aluno.setUf(ufEditText.getText().toString());
        aluno.setLocalidade(localidadeEditText.getText().toString());
        return aluno;
    }
    
    

    //insere um novo aluno
    private void inserirAluno(Aluno aluno) {
        Call<Aluno> call = alunoService.postAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Aluno createdPost = response.body();
                    Toast.makeText(MainActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    // Tratar erro, se necessário
                    Log.e("Inserir", "Erro ao criar" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                // Tratar o erro de requisição, se necessário
                Log.e("Inserir", "Erro ao criar" + t.getMessage());
            }
        });
    }

    //verifica o ra do aluno
    private void verificarRA(int ra) {
        Log.d("verificarRA", "Verificando RA: " + ra);
        Call<Aluno> call = alunoService.getAlunoById(ra);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                Log.d("verificarRA", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Aluno alunoExistente = response.body();
                    Log.d("verificarRA", "Aluno encontrado: " + alunoExistente.getNome());
                    Toast.makeText(MainActivity.this, "Aluno com RA já cadastrado", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("verificarRA", "Aluno não encontrado, criando novo aluno");
                    Aluno novoAluno = criarAluno();
                    inserirAluno(novoAluno);
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e("VerificarRA", "Erro ao verificar RA: " + t.getMessage());
            }
        });
    }


    private void fetchAddress(String cep) {
        CepService cepService = ApiCep.getCepService();
        Call<ViaCepResponse> call = cepService.getEndereco(cep);

        call.enqueue(new Callback<ViaCepResponse>() {
            @Override
            public void onResponse(Call<ViaCepResponse> call, Response<ViaCepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViaCepResponse address = response.body();
                    logradouroEditText.setText(address.getLogradouro());
                    complementoEditText.setText(address.getComplemento());
                    bairroEditText.setText(address.getBairro());
                    ufEditText.setText(address.getUf());
                    localidadeEditText.setText(address.getLocalidade());
                    Toast.makeText(MainActivity.this, "Endereço encontrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViaCepResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        ra.setText("");
        nome.setText("");
        cepEditText.setText("");
        logradouroEditText.setText("");
        complementoEditText.setText("");
        bairroEditText.setText("");
        ufEditText.setText("");
        localidadeEditText.setText("");
    }


}