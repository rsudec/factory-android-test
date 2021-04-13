package com.rsudec.factory_android_test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAPI {
    @SerializedName("status")
    String status;
    @SerializedName("source")
    String source;
    @SerializedName("sortBy")
    String sortBy;
    @SerializedName("articles")
    List<Article> articleList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
