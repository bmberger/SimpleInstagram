package com.example.simpleinstagram;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simpleinstagram.models.Post;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        if (post.getUser().getParseFile("profilepic") != null) {
            Glide.with(context).load(post.getUser().getParseFile("profilepic").getUrl()).apply(RequestOptions.circleCropTransform()).into(holder.ivProfileImage);
        }
        Glide.with(context).load(post.getImage().getUrl()).into(holder.postImage);
        holder.relTimeAgo.setText(getRelativeTimeAgo(post.getDate()));
    }

    // relative timestamp on each tweet
    public String getRelativeTimeAgo(String rawJsonDate) {
        String instaFormat = "EEE MMM dd hh:mm:ss zzz yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(instaFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //reformat string and turn "minutes ago" into "m" and "seconds ago" to "s"
        relativeDate = relativeDate.replaceAll(" second.* ago", "s");
        relativeDate = relativeDate.replaceAll(" minute.* ago", "m");
        relativeDate = relativeDate.replaceAll(" hour.* ago", "h");
        relativeDate = relativeDate.replaceAll(" day.* ago", "d");
        relativeDate = relativeDate.replaceAll(" week.* ago", "w");
        relativeDate = relativeDate.replaceAll(" month.* ago", "mo");

        return relativeDate;
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
        public ImageView postImage;
        public TextView relTimeAgo;

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
            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            relTimeAgo = (TextView) itemView.findViewById(R.id.relTimeAgo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = mPosts.get(position);

                    Intent detailIntent = new Intent(v.getContext(), DetailsActivity.class);
                    detailIntent.putExtra("post", post);
                    v.getContext().startActivity(detailIntent);
                }
            });

            ivHeartImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = mPosts.get(position);

                    Intent detailIntent = new Intent(v.getContext(), DetailsActivity.class);
                    detailIntent.putExtra("post", post);
                    v.getContext().startActivity(detailIntent);
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = mPosts.get(position);
                    ParseUser user = post.getUser();

                    Intent profileIntent = new Intent(v.getContext(), ProfileActivity.class);
                    profileIntent.putExtra("user", user);
                    v.getContext().startActivity(profileIntent);
                }
            });

            topUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = mPosts.get(position);
                    ParseUser user = post.getUser();

                    Intent profileIntent = new Intent(v.getContext(), ProfileActivity.class);
                    profileIntent.putExtra("user", user);
                    v.getContext().startActivity(profileIntent);
                }
            });

            bottomUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = mPosts.get(position);
                    ParseUser user = post.getUser();

                    Intent profileIntent = new Intent(v.getContext(), ProfileActivity.class);
                    profileIntent.putExtra("user", user);
                    v.getContext().startActivity(profileIntent);
                }
            });
        }
    }
}
