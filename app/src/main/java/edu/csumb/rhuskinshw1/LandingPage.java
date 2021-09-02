package edu.csumb.rhuskinshw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LandingPage extends AppCompatActivity {
    private TextView textViewResult;

    public static final String ACTIVITY_LABEL = "LANDING_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        textViewResult = findViewById(R.id.text_view_result);

        //refactor this in a seperate file in order to add both users and
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);

        Call<List<Post>> call = jsonApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post: posts) {
                    String content = "";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "ID : " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Text" + post.getText() + "\n\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    public static Intent getIntent(Context context, String toastValue) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(LandingPage.ACTIVITY_LABEL, toastValue);
        return intent;
    }
}