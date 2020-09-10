package com.kevine.askbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hendraanggrian.appcompat.widget.SocialTextView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    Context context;
    List<PostListResponse> postListResponseData;

    public PostsAdapter(Context context, List<PostListResponse> postListResponseData) {
        this.context = context;
        this.postListResponseData = postListResponseData;
    }

    @NonNull
    @Override
    public PostsAdapter.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item, null);
        PostsViewHolder postsViewHolder = new PostsViewHolder(view);
        return postsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.PostsViewHolder holder, int position) {
        //get post by position
        PostListResponse postDetails = postListResponseData.get(position);
        //setting data to our views

        holder.p_topic.setText(postDetails.getTopic());
        holder.p_description.setText(postDetails.getDescription());

    }

    @Override
    public int getItemCount() {
        return postListResponseData.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {
        SocialTextView p_topic, p_description;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            p_topic = (SocialTextView) itemView.findViewById(R.id.p_topic);
            p_description = (SocialTextView) itemView.findViewById(R.id.p_description);
        }
    }
}
