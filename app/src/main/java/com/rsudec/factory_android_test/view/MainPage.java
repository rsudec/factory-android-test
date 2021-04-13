package com.rsudec.factory_android_test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ProgressBar;


import com.rsudec.factory_android_test.R;
import com.rsudec.factory_android_test.helper.ConnectionHelper;
import com.rsudec.factory_android_test.model.Article;

import com.rsudec.factory_android_test.fragments.ArticleListFragment;

import java.util.List;

public class MainPage extends AppCompatActivity {
     public boolean isConnected = false;
     private List<Article> articleList;
     public static ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        initializeFragments();

        ConnectionHelper.getInstance().registerNetworkCallback(getApplicationContext());





    }

    private void initializeFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ArticleListFragment articleListFragment = new ArticleListFragment();

        fragmentTransaction.replace(R.id.fragment_main, articleListFragment);
        fragmentTransaction.commit();
    }




}