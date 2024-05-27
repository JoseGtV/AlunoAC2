package com.example.alunoac2.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alunoac2.API.Aluno.AlunoService;
import com.example.alunoac2.API.Aluno.ApiAluno;
import com.example.alunoac2.Models.Aluno;
import com.example.alunoac2.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunos;
    private Context context;
    private AlunoService alunoService;

    public AlunoAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
        this.alunoService = ApiAluno.getAlunoService();
    }


    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_usuario, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.txtRA.setText("RA: " + aluno.getRa());
        holder.txtNome.setText("Nome: " + aluno.getNome());
        holder.txtCep.setText("CEP: " + aluno.getCep());
        holder.txtLogradouro.setText("Logradouro: " + aluno.getLogradouro());
        holder.txtComplemento.setText("Complemento: " + aluno.getComplemento());
        holder.txtBairro.setText("Bairro: " + aluno.getBairro());
        holder.txtUF.setText("UF: " + aluno.getUf());
        holder.txtLocalidade.setText("Localidade: " + aluno.getLocalidade());

        holder.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Aluno alunoToDelete = alunos.get(adapterPosition);
                    deleteAluno(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }



    public class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView txtRA, txtNome, txtCep, txtLogradouro, txtComplemento, txtBairro, txtUF, txtLocalidade;
        Button btnExcluir;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRA = itemView.findViewById(R.id.txtRA);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtCep = itemView.findViewById(R.id.txtCep);
            txtLogradouro = itemView.findViewById(R.id.txtLogradouro);
            txtComplemento = itemView.findViewById(R.id.txtComplemento);
            txtBairro = itemView.findViewById(R.id.txtBairro);
            txtUF = itemView.findViewById(R.id.txtUF);
            txtLocalidade = itemView.findViewById(R.id.txtLocalidade);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }

    private void deleteAluno(int position) {
        int id = alunos.get(position).getId();
        Log.d("deleteAluno", "Tentando deletar aluno com ID: " + id);
        Call<Void> call = alunoService.deleteAluno(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Aluno deletado com sucesso", Toast.LENGTH_SHORT).show();
                    alunos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, alunos.size());
                } else {
                    Log.e("deleteAluno", "Falha ao deletar aluno: " + response.code() + " - " + response.message());
                    Toast.makeText(context, "Falha ao deletar aluno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AlunoAdapter", "Erro ao deletar aluno", t);
            }
        });
    }


}

