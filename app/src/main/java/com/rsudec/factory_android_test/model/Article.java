package com.rsudec.factory_android_test.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("author")
    String author;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("url")
    String url;
    @SerializedName("urlToImage")
    String urlToImage;
    @SerializedName("publishedAt")
    String publishedAt;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
