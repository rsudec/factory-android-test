package com.rsudec.factory_android_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.rsudec.factory_android_test.helper.ConnectionHelper;
import com.rsudec.factory_android_test.model.Article;

import com.rsudec.factory_android_test.model.NewsAPI;
import com.rsudec.factory_android_test.model.ResponseAPI;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
     public boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectionHelper.getInstance().registerNetworkCallback(getApplicationContext());



        Button refresh = findViewById(R.id.button_id);
        refresh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getData();
            }
        });

    }






    private static final Interceptor InterceptorOnline = new Interceptor() {
        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            int maxAge = 300; // 5 minuta

            return originalResponse.newBuilder()

                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };
    /*private static final Interceptor InterceptorOffline = new Interceptor() {
        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            int maxStale = 60*60*24*1; // 5 minuta

            return originalResponse.newBuilder()

                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    };*/
    private void getData(){

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        File httpCache = new File(getApplicationContext().getCacheDir(), "response");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCache, cacheSize);


        OkHttpClient client = new OkHttpClient
                .Builder()

                .addNetworkInterceptor(InterceptorOnline)
                .cache(cache)
                .build();

        Call<ResponseAPI> request = new Retrofit
                .Builder()
                .baseUrl("https://newsapi.org/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(NewsAPI.class)
                .articles();

        try {
            request.enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {

                    Log.d("Req", "Response");
                    for(Article a : response.body().getArticleList()){
                        Log.d("Article", a.getTitle());
                    }
                    Log.d("Time", formatter.format(new Date()));
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Log.d("Req", t.getMessage());
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}