package com.example.alunoac2.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alunoac2.API.Aluno.AlunoService;
import com.example.alunoac2.API.Aluno.ApiAluno;
import com.example.alunoac2.Adapter.AlunoAdapter;
import com.example.alunoac2.Models.Aluno;
import com.example.alunoac2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AlunoAdapter alunoAdapter;
    AlunoService alunoService;
    Button btnExcluir;
    TextView txtRA;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.lista_alunos);

        recyclerView = findViewById(R.id.recyclerView);



        if (recyclerView == null) {
            Log.e("ListaAlunoActivity", "RecyclerView não foi encontrado!");
            Toast.makeText(this, "Erro ao carregar a lista de alunos", Toast.LENGTH_SHORT).show();
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        alunoService = ApiAluno.getAlunoService();
        fetchAlunos();



    }

    private void fetchAlunos() {
        Call<List<Aluno>> call = alunoService.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Aluno> listaDeAlunos = response.body();
                    alunoAdapter = new AlunoAdapter(listaDeAlunos, ListaAlunoActivity.this);
                    recyclerView.setAdapter(alunoAdapter);
                } else {
                    Toast.makeText(ListaAlunoActivity.this, "Falha ao obter alunos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListaAlunoActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ListaAlunoActivity", "Erro ao buscar alunos", t);
            }
        });
    }
}

