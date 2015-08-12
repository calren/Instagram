package codepath.android.com.instagram;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import codepath.android.com.instagram.feed.AddCommentActivity;
import codepath.android.com.instagram.feed.FeedItemModel;
import codepath.android.com.instagram.feed.FeedRecyclerViewAdapter;
import codepath.android.com.instagram.profile.UserImagesRecyclerViewAdapter;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.yalantis.cameramodule.activity.CameraActivity;

public class HomeActivity extends Activity {

    View tabBar;
    ViewFlipper layoutViewFlipper;
    private final static int FEED_PAGE = 0;
    private final static int PROFILE_PAGE = 1;
    NetworkController networkController = new NetworkController();
    FeedRecyclerViewAdapter feedRecyclerViewAdapter;
    UserImagesRecyclerViewAdapter userImagesRecyclerViewAdapter;
    List<FeedItemModel> feedItemModels = new ArrayList<>();
    List<String> imageUrls = new ArrayList<>();
    RecyclerView feedRecyclerView;
    RecyclerView userImagesRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutViewFlipper = (ViewFlipper) findViewById(R.id.layout_view_flipper);
        setUpBottomTabBar();
        setUpFeedActivity();
        setUpProfileActivity();
        layoutViewFlipper.setDisplayedChild(FEED_PAGE);
    }

    private void setUpBottomTabBar() {
        tabBar = findViewById(R.id.bottom_tab_bar);
        findViewById(R.id.home_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutViewFlipper.setDisplayedChild(FEED_PAGE);
            }
        });
        findViewById(R.id.profile_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutViewFlipper.setDisplayedChild(PROFILE_PAGE);
            }
        });
        findViewById(R.id.camera_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
    }

    private void launchCamera() {

//        Intent intent = new Intent(this, CameraActivity.class);
//        intent.putExtra(CameraActivity.PATH, Environment.getExternalStorageDirectory().getPath());
//        intent.putExtra(CameraActivity.OPEN_PHOTO_PREVIEW, true);
//        startActivity(intent);

        // launch custom camera
        // Intent myIntent = new Intent(HomeActivity.this, CustomCameraActivity.class);
        // this.startActivity(myIntent);

        // Launch default camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void setUpFeedActivity() {
        feedRecyclerView = (RecyclerView) findViewById(R.id.feed_recycler_view);
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(this, feedItemModels);
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.feed_refresh_container));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkController.fetchPopularPhotos(HomeActivity.this);
            }
        });
        networkController.fetchFeedPhotos(this);
    }

    private void setUpProfileActivity() {
        userImagesRecyclerView = (RecyclerView) findViewById(R.id.user_images_recycler_view);
        userImagesRecyclerViewAdapter = new UserImagesRecyclerViewAdapter(imageUrls, this);
        userImagesRecyclerView.setAdapter(userImagesRecyclerViewAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        userImagesRecyclerView.setLayoutManager(manager);
        networkController.fetchPhotos(this);
    }

    /*
     * called when pull to refresh, reset feed with new api response
     */
    public void updateFeed(ArrayList<FeedItemModel> feedItems) {
        feedItemModels.clear();
        feedItemModels.addAll(feedItems);
        feedRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void updateUserImages(ArrayList<String> imageUrls) {
        this.imageUrls.clear();
        this.imageUrls.addAll(imageUrls);
        userImagesRecyclerViewAdapter.notifyDataSetChanged();
    }

    /*
     * Adds a new comment to a feed model based on result from AddCommentActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && data.getIntExtra(AddCommentActivity.INTENT_CODE, 0) != 0) {
            // feedItemModels.get(data.getIntExtra(AddCommentActivity.POSITION_INTENT_KEY, -1))
            // .setComment2(data.getStringExtra(AddCommentActivity.COMMENT_INTENT_KEY));
            // feedRecyclerViewAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            // Convert it to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();

            // Create the ParseFile
            final ParseFile file = new ParseFile("androidbegin.png", image);
            // Upload the image into Parse Cloud
            file.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        System.out.println("file : " + file.getUrl());
                        FeedItemModel newFeedItem = new FeedItemModel();
                        newFeedItem.put("imageUrl", file.getUrl());
                        newFeedItem.put("username", "calren");
                        newFeedItem.put("likesCount", 1235);
                        newFeedItem.put("profileImageUrl", file.getUrl());
                        newFeedItem.saveInBackground();
                    } else {
                    }
                }
            });
        }
    }
}
