package com.kevine.askbuddy.post.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kevine.askbuddy.R;
import com.kevine.askbuddy.post.model.PostModel;

import java.util.List;

/*import butterknife.BindView;
import butterknife.ButterKnife;*/

public class ApiPostAdapter extends RecyclerView.Adapter<ApiPostAdapter.ViewHolder> {

    private List<PostModel> postModelList;
    private Context context;

    public ApiPostAdapter(List<PostModel> postModelList, Context context) {
        this.postModelList = postModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApiPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiPostAdapter.ViewHolder holder, int position) {
        //get post by position
        PostModel postDetails = postModelList.get(position);
        //setting data to our views

        /*holder.tvPostTitle.setText(postDetails.getTopic());
        holder.tvPostDesc.setText(postDetails.getDescription());
        holder.tvPostLikes.setText(postDetails.getLikes());*/

    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //declare variables
        /*@BindView(R.id.tvPostTitle)
        TextView tvPostTitle;*/

        /*@BindView(R.id.tvPostDesc)
        TextView tvPostDesc;*/

        /*@BindView(R.id.tvPostLikes)
        TextView tvPostLikes;*/

        /*@BindView(R.id.ivLike)
        ImageView ivLike;*/

        /*@BindView(R.id.ivUnlike)
        ImageView ivUnlike;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initalizing variables
            /*ButterKnife.bind(this,itemView);*/
        }
    }
}
