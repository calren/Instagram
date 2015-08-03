package codepath.android.com.instagram;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewFlipper;

import codepath.android.com.instagram.feed.FeedItemModel;
import codepath.android.com.instagram.feed.FeedRecyclerViewAdapter;

public class HomeActivity extends Activity {

    View tabBar;
    ViewFlipper layoutViewFlipper;
    private final static int FEED_PAGE = 0;
    private final static int PROFILE_PAGE = 1;
    NetworkController networkController = new NetworkController();
    FeedRecyclerViewAdapter feedRecyclerViewAdapter;
    List<FeedItemModel> feedItemModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layoutViewFlipper = (ViewFlipper) findViewById(R.id.layout_view_flipper);
        setUpBottomTabBar();
        setUpFeedActivity();
        showFeedActivity();
    }

    private void setUpBottomTabBar() {
        tabBar = findViewById(R.id.bottom_tab_bar);
        findViewById(R.id.home_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFeedActivity();
            }
        });
        findViewById(R.id.profile_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileActivity();
            }
        });
        findViewById(R.id.camera_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
    }

    private void showProfileActivity() {
        layoutViewFlipper.setDisplayedChild(PROFILE_PAGE);
    }

    private void showFeedActivity() {
        layoutViewFlipper.setDisplayedChild(FEED_PAGE);
        networkController.fetchPopularPhotos(this);
    }

    private void setUpFeedActivity() {
        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.feed_recycler_view);
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(this, feedItemModels);
        rvUsers.setAdapter(feedRecyclerViewAdapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    private void launchCamera() {
        // TODO maybe launch custom camera
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }

    public void updateFeed(ArrayList<FeedItemModel> feedItems) {
        feedItemModels.addAll(feedItems);
        feedRecyclerViewAdapter.notifyDataSetChanged();
    }
}
