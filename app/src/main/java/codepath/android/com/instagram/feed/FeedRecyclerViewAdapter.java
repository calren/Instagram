package codepath.android.com.instagram.feed;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import codepath.android.com.instagram.R;

import com.squareup.picasso.Picasso;

public class FeedRecyclerViewAdapter
        extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder> {

    private List<FeedItemModel> feedItems;
    private Context context;

    public FeedRecyclerViewAdapter(Context context, List<FeedItemModel> feedItems) {
        this.feedItems = feedItems;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView image;
        public TextView comment1;
        public TextView comment2;
        public View addComment;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.feed_item_user_name);
            image = (ImageView) itemView.findViewById(R.id.feed_item_image);
            comment1 = (TextView) itemView.findViewById(R.id.feed_item_comment1);
            comment2 = (TextView) itemView.findViewById(R.id.feed_item_comment2);
            addComment = itemView.findViewById(R.id.add_comment);
        }
    }

    @Override
    public FeedRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(context).inflate(R.layout.feed_item_view, parent, false);
        return new FeedRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedRecyclerViewAdapter.ViewHolder holder, int position) {
        FeedItemModel feedItemModel = feedItems.get(position);
        holder.userName.setText(feedItemModel.getUserName());
        Picasso.with(context).load(feedItemModel.getImageUrl()).into(holder.image);
        holder.comment1.setText(feedItemModel.getComment1());
        holder.comment2.setText(feedItemModel.getComment2());
        holder.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddCommentActivity.class);
                view.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }
}
