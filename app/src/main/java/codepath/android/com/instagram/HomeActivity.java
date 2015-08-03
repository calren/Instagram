package codepath.android.com.instagram;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
    RecyclerView feedRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layoutViewFlipper = (ViewFlipper) findViewById(R.id.layout_view_flipper);
        setUpBottomTabBar();
        setUpFeedActivity();
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
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
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
        networkController.fetchPopularPhotos(HomeActivity.this);
    }

    public void updateFeed(ArrayList<FeedItemModel> feedItems) {
        feedItemModels.clear();
        feedItemModels.addAll(feedItems);
        feedRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
