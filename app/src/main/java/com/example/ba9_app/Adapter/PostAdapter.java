package com.example.ba9_app.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ba9_app.Model.Post;
import com.example.ba9_app.R;

import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> mList;
    private Activity context;

    public PostAdapter(Activity context, List<Post> mList) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.each_post, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Post post = mList.get(position);
        holder.setPostPic(post.getImage());
        holder.setPostDate(post.getDate());
        holder.setPostCaption(post.getCaption());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postPic;
        TextView postDate, postCaption;
        View mView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPostPic(String urlPost) {
            postPic = mView.findViewById(R.id.post_tv);
            Glide.with(context).load(urlPost).into(postPic);
        }

        public void setPostDate(String date) {
            postDate = mView.findViewById(R.id.date_tv);
            postDate.setText(date);
        }

        public void setPostCaption(String caption) {
            postCaption = mView.findViewById(R.id.caption_tv);
            postCaption.setText(caption);
        }
    }
}
