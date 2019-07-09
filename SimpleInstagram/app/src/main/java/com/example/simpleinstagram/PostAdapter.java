package com.example.simpleinstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleinstagram.models.Post;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    private final int REQUEST_CODE = 20;
    Context context;

    // pass in the Posts array in the constructor
    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    // invoked to create a new row (inflates layout and caches references into ViewHolder)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder holder = new ViewHolder(tweetView);

        return holder;
    }

    // bind the values of tweet based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data according to position
        Post post = mPosts.get(position);

        // populate the views according to this data - username, body, and image via Glide
        holder.topUsername.setText(post.getUser().getUsername());
        holder.bottomUsername.setText(post.getUser().getUsername());
        holder.bodyText.setText(post.getDescription());
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    // allows us to see the total amount of tweets
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // create ViewHolder class that will contain our recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView topUsername;
        public TextView bottomUsername;
        public ImageView ivHeartImage;
        public ImageView ivCommentImage;
        public ImageView ivDmImage;
        public ImageView ivArchiveImage;
        public TextView bodyText;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.profilePerson);
            topUsername = (TextView) itemView.findViewById(R.id.topUsername);
            bottomUsername = (TextView) itemView.findViewById(R.id.bottomUsername);
            ivHeartImage = (ImageView) itemView.findViewById(R.id.heartImage);
            ivCommentImage = (ImageView) itemView.findViewById(R.id.commentImage);
            ivDmImage = (ImageView) itemView.findViewById(R.id.dmImage);
            ivArchiveImage = (ImageView) itemView.findViewById(R.id.archiveImage);
            bodyText = (TextView) itemView.findViewById(R.id.bodyText);
        }
    }
}
