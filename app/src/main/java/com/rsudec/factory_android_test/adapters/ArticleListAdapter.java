package com.rsudec.factory_android_test.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rsudec.factory_android_test.R;
import com.rsudec.factory_android_test.model.Article;
import com.rsudec.factory_android_test.view.ArticleDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ArticleListAdapter  extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private Context context;



    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    private List<Article> articleList;


    public ArticleListAdapter(Context context ){
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        articleList.get(position).getUrlToImage();
        Glide.with(context).asBitmap().load(articleList.get(position).getUrlToImage()).apply(new RequestOptions().override(120,120)).into(holder.img);
        holder.title.setText(articleList.get(position).getTitle());
        holder.relativeLayout.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putParcelableArrayList("articleList", (ArrayList) articleList);
            Intent intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra("article",articleList.get(position));
            intent.putExtra("articleListBundle", b);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView img;
        private RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.article_item_image);
            title  = itemView.findViewById(R.id.article_item_title);
            relativeLayout = itemView.findViewById(R.id.article_item_layout);
        }
    }
}
