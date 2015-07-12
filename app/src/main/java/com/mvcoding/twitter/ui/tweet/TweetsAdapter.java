package com.mvcoding.twitter.ui.tweet;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.Status;

class TweetsAdapter extends BaseAdapter<Status, TweetsAdapter.ViewHolder> {
    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false));
    }

    @Override protected void onBindViewHolder(ViewHolder holder, int position, Status item) {
        final long tweetTime = item.getCreatedAt().getTime();
        final CharSequence time = DateUtils.getRelativeTimeSpanString(tweetTime, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);

        Glide.with(holder.photoImageView.getContext())
                .load(item.getUser().getOriginalProfileImageURL())
                .centerCrop()
                .into(holder.photoImageView);
        holder.nameTextView.setText(item.getUser().getName());
        holder.usernameTextView.setText("@" + item.getUser().getScreenName());
        holder.timeTextView.setText(time);
        holder.messageTextView.setText(item.getText());
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.photoImageView) ImageView photoImageView;
        @Bind(R.id.nameTextView) TextView nameTextView;
        @Bind(R.id.usernameTextView) TextView usernameTextView;
        @Bind(R.id.timeTextView) TextView timeTextView;
        @Bind(R.id.messageTextView) TextView messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
