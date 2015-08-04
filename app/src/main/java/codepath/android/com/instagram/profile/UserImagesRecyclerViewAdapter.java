package codepath.android.com.instagram.profile;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import codepath.android.com.instagram.R;

import com.squareup.picasso.Picasso;

public class UserImagesRecyclerViewAdapter
        extends RecyclerView.Adapter<UserImagesRecyclerViewAdapter.ViewHolder> {

    private List<String> imageUrls;
    private Context context;

    public UserImagesRecyclerViewAdapter(List<String> imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_item);
        }
    }

    @Override
    public UserImagesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        View itemView =
                LayoutInflater.from(context).inflate(R.layout.personal_image_item, parent, false);
        return new UserImagesRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserImagesRecyclerViewAdapter.ViewHolder holder, final int position) {

        Picasso.with(context).load(imageUrls.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }
}
