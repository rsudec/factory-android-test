package com.rsudec.factory_android_test.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rsudec.factory_android_test.R;
import com.rsudec.factory_android_test.model.Article;
import com.rsudec.factory_android_test.model.ResponseAPI;
import com.rsudec.factory_android_test.model.api.NewsAPI;
import com.rsudec.factory_android_test.adapters.ArticleListAdapter;
import com.rsudec.factory_android_test.view.MainPage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleListFragment extends Fragment {



    private RecyclerView recyclerView;

    private ProgressBar progressBar;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainPage.progressBar.setVisibility(View.VISIBLE);
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        getData(view);

        return view;
    }

    private void setData(List<Article> articleList, View view){

        recyclerView = (RecyclerView) view.findViewById(R.id.article_list);

        ArticleListAdapter adapter = new ArticleListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setArticleList(articleList);

    }

    private void getData(View view){

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        File httpCache = new File(getContext().getCacheDir(), "response");
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
                    MainPage.progressBar.setVisibility(View.GONE);
                    setData(response.body().getArticleList(), view);
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    MainPage.progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(view.getContext()).setTitle("Greška"). setMessage("Ups, došlo je do greške").setPositiveButton("U redu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            call.cancel();
                        }
                    }).show();
                }

            });

        } catch (Exception e) {
            MainPage.progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
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


}
