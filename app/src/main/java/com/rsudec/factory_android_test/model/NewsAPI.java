package com.rsudec.factory_android_test.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

        @GET("articles?source=bbc-news&sortBy=top&apiKey=6946d0c07a1c4555a4186bfcade76398")
        Call<ResponseAPI> articles();

}
