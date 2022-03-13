package com.example.adp_30_retro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().serializeNulls().create();

        // این کلا برای شما لاگ میاندازد
        // در خواست های http را
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("interceptor-Header", "xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        // اینجا برای اتصال استفاده شده است
        // همچنین از دیزاین پترن
        // بیلدر استفاده شده است
        // و متد چینینگ
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // اینجا یک اتصال ساخته شده است
        api = retrofit.create(Api.class);
//        getPosts();
//        getComments();
//        createPost();
//        updatePost();
        deletePost();
    }

    private void getComments() {
        // اینجا براساس
        // api
        // گرفته شده است
//        Call<List<Comment>> call = api.getComments(3);

        // اینجا براساس
        // url
        // بجای اینکه یو آر ال مورد نظر را
        // در روت بنویسیم
        // با گذاشتن پارامتری در آنجا
        // اینجا یوار ال را وارد میکنیم
        Call<List<Comment>> call = api.getComments("posts/{id}/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code :" + response.code());
                    return;
                }

                List<Comment> comments = response.body();
                for(Comment comment: comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        // اینجا در خواست انجام میشود
        // و اگر انجام شد مقادیر را بر میگرداند
        // و اگر نشد پیغام خطا میدهد
//        Call<List<Post>> call = api.getPosts(1, 4, "id", "desc");
        // اینجا بر اساس ای دی دیسندینگ میکند
//        Call<List<Post>> call = api.getPosts(1, 4, null, null);
        // و همچنین دو کاربر را برمیگرداند
        // آی دی دو کاربر را ارسال میکنید و براساس
        // آن پست ها را دریافت میکنید
        Call<List<Post>> call = api.getPosts(new Integer[]{2, 3, 6}, null, null);
        // میتواند آرایه باشد

//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("userId", "1");
//        parameters.put("_sort", "id");
//        parameters.put("_order", "desc");
//        Call<List<Post>> call = api.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code :" + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for(Post post: posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        int userId = 23;
        String title = "new title";
        String text = "new text";

        // اینجا براساس چیزی که در مدل هست
        // داده ها ست میشوند
        // سپس به متد مورد نظر که در
        // api
        // نوشتیم
        // دسترسی پیدا میکنیم
        // و ارسال میکنیم
        Post post = new Post(userId, title, text);

//        Map<String, String> fields = new HashMap<>();
//        fields.put("userId", "25");
//        fields.put("title", "new Title");
//        fields.put("text", "new Text");

        //        Call<Post> call = api.createPost(post);
        //        Call<Post> call = api.createPost(fields);
        Call<Post> call = api.createPost(23, "New Title", "New Text");
        // اکنون منتظر جواب هستیم
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code :" + response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        int userId = 23;
        String title = "new title";
        String text = "new text";

        Post post = new Post(userId, title, text);

        // اینجا میگوید به این آدرس
        // داده ها را با همین آی دی بفرست
        // بصورت
        // put
//        Call<Post> call = api.putPost("abc", 5, post);
        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "def");
        headers.put("Map-Header2", "ghi");
        Call<Post> call = api.patchPost(headers, 5, post);

        // اینجا میگوید به این آدرس
        // داده ها را با همین آی دی بفرست
        // بصورت
        // patch
//        Call<Post> call = api.patchPost(5, post);
        // میتوان برای کنترل کندده درخواست ها
        // کلاس مجزایی نوشت و اینجا صدا زد
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code :" + response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = api.deletePost(3);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code :" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}