package com.example.adp_30_retro;

import com.google.gson.annotations.SerializedName;

// در این جا شما میتوانید به چند روش داد ها را بگیرید
// یا بصورت
// access property => getter && setter
// و یا بصورت استفاده از یک متد سازنده
public class Post {
    private int userId;
    private Integer id;
    private String title;

    @SerializedName("body")
    private String text;

    // متد سازنده
    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
