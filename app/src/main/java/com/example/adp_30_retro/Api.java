package com.example.adp_30_retro;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Api {

    // اینجا در اصل مانند یک روت عمل میکند
    @GET("posts")
    // اینجا همان درخواست
    // get
    // استفاده شده است
    Call<List<Post>> getPosts(
//            @Query("userId") Integer userId,
//            @Query("userId") Integer userId2,
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );
    // اینجا همان کوئری استرینگ
    // استفاده شده است

    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    // ما از یک مدل استفاده میکنیم که در آنها از
    // getter && setter
    // استفاده شده است
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    // اینجا یک پارامتر پاس میدهیم
    // و در آنطرف آدرس میدهیم
    @GET
    Call<List<Comment>> getComments(@Url String url);

    // در اینجا بادی همه مقادیر را دارد و فرستاد
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    // در اینجا بوسیله فیلد میتوان مقادیر را معین کرد و فرستاد
    @FormUrlEncoded
    // کاراکتر های مخصوص روی یو آر ال انکد میشود
    @POST
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("text") String text
    );
    // روش فوق بصورت کویری استرینگ است

    // یک روش دیگر
    // شبیه به روش فرم دیتا در جاوااسکریپت
    @FormUrlEncoded
    @POST
    Call<POST> createPost(@FieldMap Map<String, String> fields);


    // در اینجا هم ای داده شده
    // و هم از بادی استفاده شده
    // توجه کنید که حاشیه نویسی ها در جاوا خیلی شبیه به دکوریتور ها در
    // تایپ اسکریپت میباشد
    @Headers({"Static-Header1: 123", "Static-Header2: 456"})
    // بوسیله حاشیه نویسیمیتوان یک هدر ست کرد و تعیین کرد
    @PUT("posts/{id}")
    Call<Post> putPost(@Header("Dynamic-Header") String header,
                       @Path("id") int id,
                       @Body Post post);

    @PATCH("post/{id}")
    // در این قسمت ارایه ای از هدر ها را مپ کردیم بطور مثال
    Call<Post> patchPost(@HeaderMap Map<String, String> headers,
                         @Path("id") int id,
                         @Body Post post);

    @DELETE("post/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
