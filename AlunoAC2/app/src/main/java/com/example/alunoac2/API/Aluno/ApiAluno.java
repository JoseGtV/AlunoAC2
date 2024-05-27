package com.example.alunoac2.API.Aluno;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAluno {

    private static final String BASE_URL = "https://6653ab0a1c6af63f46754ca5.mockapi.io/";

    private static Retrofit retrofit = null;

    public static Retrofit getAluno(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
    public static AlunoService getAlunoService(){
        return getAluno().create(AlunoService.class);
    }
}
