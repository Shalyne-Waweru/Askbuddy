package com.kevine.askbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPostsActivity extends BaseActivity {
    RecyclerView recyclerView;
    PostListResponse postListResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);
        recyclerView = findViewById(R.id.posts_recyclerView);
        //fetchPost();
    }

    /*private void fetchPost() {
        showProgress();

        (Api.getClient().fetchPostByUserID(session.getUserId())).enqueue(new Callback<PostListResponse>() {
            @Override
            public void onResponse(Call<PostListResponse> call, Response<PostListResponse> response) {
                Log.debug("fetchResp: ");
                dismissProgress();
                postListResponseData = response.body();
                setDataInRecyclerView();
            }

            @Override
            public void onFailure(Call<PostListResponse> call, Throwable t) {
                //if an error occurs in the network transaction
                Toast.makeText(AllPostsActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                dismissProgress();

            }
        });
    }*/

    private void setDataInRecyclerView() {
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllPostsActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of PostsAdapter to send the reference and data to Adapter
        PostsAdapter postsAdapter = new PostsAdapter(AllPostsActivity.this, (List<PostListResponse>) postListResponseData);
        // set the Adapter to RecyclerView
        recyclerView.setAdapter(postsAdapter);
    }
}
