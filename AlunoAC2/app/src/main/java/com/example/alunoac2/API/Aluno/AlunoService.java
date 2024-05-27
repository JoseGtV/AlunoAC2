package com.example.alunoac2.API.Aluno;

import android.widget.EditText;

import com.example.alunoac2.Models.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface AlunoService {

    @GET("aluno")
    Call<List<Aluno>> getAlunos();

    @POST("aluno")
    Call<Aluno> postAluno(@Body Aluno aluno);
    @DELETE("aluno/{id}")
    Call<Void> deleteAluno(@Path("id") int id);

    @GET("aluno/{id}")
    Call<Aluno> getAlunoById (@Path("id") int idAluno);






}
