package com.example.alunoac2.API.CEP;

import com.example.alunoac2.Models.ViaCepResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {

    //consulta o cep
    @GET("{cep}/json/")
    Call<ViaCepResponse> getEndereco(@Path("cep") String cep);
}
