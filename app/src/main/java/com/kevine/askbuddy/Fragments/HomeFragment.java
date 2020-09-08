package com.kevine.askbuddy.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kevine.askbuddy.Adapter.PostAdapter;
import com.kevine.askbuddy.BaseFragment;
import com.kevine.askbuddy.Constants;
import com.kevine.askbuddy.Log;
import com.kevine.askbuddy.MainActivity;
import com.kevine.askbuddy.Model.Post;
import com.kevine.askbuddy.R;
import com.kevine.askbuddy.network.ApiClientString;
import com.kevine.askbuddy.network.ApiInterface;
import com.kevine.askbuddy.post.adapter.ApiPostAdapter;
import com.kevine.askbuddy.post.model.PostModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    //private RecyclerView recyclerViewPosts;

    private RecyclerView rvPost;
    public   List<PostModel> postModelList;
    private ApiPostAdapter postAdapter;

    //private PostAdapter postAdapter;
    private List<Post> postList;

    private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        rvPost = view.findViewById(R.id.recycler_view_posts);
        rvPost.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        rvPost.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new ApiPostAdapter(postModelList, getContext());
        rvPost.setAdapter(postAdapter);

        followingList = new ArrayList<>();
        fetchPost();
        //checkFollowingUsers();

        return view;
    }

    private void checkFollowingUsers() {

        FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                followingList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                //readPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readPosts() {

        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    for (String id : followingList) {
                        if (post.getPublisher().equals(id)){
                            postList.add(post);
                        }
                    }
                }
                //postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    private void fetchPost() {
        showProgress();

        ApiInterface apiInterface = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.fetchPostByUserID("2");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismissProgress();
                try {
                    JSONObject obj = new JSONObject(response.body());

                    String respdesc = obj.optString(Constants.KEY_RESPDESC);
                    String respcode = obj.optString(Constants.KEY_RESPCODE);

                    JSONArray jsonArray = obj.getJSONArray("details");

                    if (respcode.equals("01")){

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject postObj = jsonArray.optJSONObject(i);
                            PostModel postModel = new PostModel(
                                    postObj.optString("p_description"),
                                    postObj.optString("u_id"),
                                    postObj.optString("p_topic"),
                                    postObj.optString("p_likes")
                            );
                           // postModelList.add(postModel);
                            Log.debug("jsonArray",postModel.getU_id());
                        }
                        postAdapter.notifyDataSetChanged();
                    }


                }catch (JSONException e){
                    dismissProgress();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismissProgress();

            }
        });
    }
}
