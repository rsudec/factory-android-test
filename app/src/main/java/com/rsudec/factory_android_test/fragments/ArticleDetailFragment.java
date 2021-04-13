package com.rsudec.factory_android_test.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rsudec.factory_android_test.R;
import com.rsudec.factory_android_test.model.Article;

public class ArticleDetailFragment extends Fragment {
    private Article selectedArticle;
    private ImageView img;
    private TextView title;
    private TextView description;
    private void initializeData(View v){
        img = v.findViewById(R.id.article_detail_image);
        title = v.findViewById(R.id.article_detail_title);
        description = v.findViewById(R.id.article_detail_description);
        
        Glide.with(v.getContext()).asBitmap().load(selectedArticle.getUrlToImage()).apply(new RequestOptions().override(v.getWidth(), 200)).into(img);


        title.setText(selectedArticle.getTitle());
        description.setText(selectedArticle.getDescription());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        Bundle bundle = getArguments();
        if(bundle != null){
            selectedArticle = bundle.getParcelable("article");
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        initializeData(view);

        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }
}
