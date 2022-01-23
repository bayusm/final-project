package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.ActivityExtraParameters;
import com.example.finalproject.PostDetailActivity;
import com.example.finalproject.R;
import com.example.finalproject.post.data.Post;
import com.example.finalproject.post.data.PostCategory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ListPostAdapter extends RecyclerView.Adapter<ListPostAdapter.ViewHolder> {

    private final List<Post> listPost;

    public ListPostAdapter(List<Post> listPost) {
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listPost.get(position);

        holder.viewParent.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra(ActivityExtraParameters.POST_DETAIL_ID, post.getId());
            context.startActivity(intent);
        });

        holder.tvUsername.setText(post.getUsernamePublisher());
        holder.tvMainCategory.setText(post.getMainCategory().name);
        holder.tvTitle.setText(post.getTitle());
        holder.tvCreatedAt.setText(post.getCreatedAt());
        String commentAmount = Integer.toString(post.getCommentAmount());
        holder.tvComments.setText(commentAmount);
        String likeAmount = Integer.toString(post.getLikeAmount());
        holder.tvLikes.setText(likeAmount);

        PostCategory[] tags = post.getSubCategories();
        for (int i = 0; i < 7; i++) {
            if (i < tags.length) {
                holder.tvTags[i].setVisibility(View.VISIBLE);
                holder.tvTags[i].setText(tags[i].name);
            } else {
                holder.tvTags[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        int postId;

        View viewParent;
        TextView tvUsername, tvMainCategory, tvTitle, tvCreatedAt, tvLikes, tvComments;
        TextView[] tvTags = new TextView[7];

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            itemView.setOnClickListener(v -> {
//                Context context = itemView.getContext();
//                Intent intent = new Intent(context, PostDetailActivity.class);
//                intent.putExtra(ActivityExtraParameters.POST_DETAIL_ID, postId);
//                context.startActivity(intent);
//            });

            viewParent = itemView;
            tvUsername = itemView.findViewById(R.id.tv_username_publisher);
            tvMainCategory = itemView.findViewById(R.id.tv_main_category_tag);
            tvTitle = itemView.findViewById(R.id.tv_post_title);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
            tvLikes = itemView.findViewById(R.id.tv_post_like_amount);
            tvComments = itemView.findViewById(R.id.tv_post_comment_amount);

            tvTags[0] = itemView.findViewById(R.id.tv_sub_category_tag_1);
            tvTags[1] = itemView.findViewById(R.id.tv_sub_category_tag_2);
            tvTags[2] = itemView.findViewById(R.id.tv_sub_category_tag_3);
            tvTags[3] = itemView.findViewById(R.id.tv_sub_category_tag_4);
            tvTags[4] = itemView.findViewById(R.id.tv_sub_category_tag_5);
            tvTags[5] = itemView.findViewById(R.id.tv_sub_category_tag_6);
            tvTags[6] = itemView.findViewById(R.id.tv_sub_category_tag_7);
        }
    }
}
