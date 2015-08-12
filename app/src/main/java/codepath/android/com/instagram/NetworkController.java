package codepath.android.com.instagram;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import codepath.android.com.instagram.feed.FeedItemModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public final class NetworkController {
    private AsyncHttpClient client = new AsyncHttpClient();;
    public static final String CLIENT_ID = "0dc5d7db20c747fab6f288c437055c40";

    public void fetchPhotos(HomeActivity activity) {
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        client.get(popularUrl, getPhotosHandler(activity));
    }

    private JsonHttpResponseHandler getPhotosHandler(final HomeActivity activity) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray photosJSON = response.getJSONArray("data");

                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        JSONObject user = photoJSON.getJSONObject("user");
                        JSONObject image =
                                photoJSON.getJSONObject("images").getJSONObject(
                                        "standard_resolution");

                        imageUrls.add(image.getString("url"));

                        activity.updateUserImages(imageUrls);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void fetchFeedPhotos(final HomeActivity activity) {
        final ArrayList<FeedItemModel> feedItemModels = new ArrayList<>();

        // Define the class we would like to query
        ParseQuery<FeedItemModel> query = ParseQuery.getQuery(FeedItemModel.class);
        // Define our query conditions
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<FeedItemModel>() {
            public void done(List<FeedItemModel> itemList, ParseException e) {
                if (e == null) {
                    for (FeedItemModel feedItem : itemList) {
                        System.out.println("feed" + feedItem.getImageUrl());
                        feedItemModels.add(new FeedItemModel(feedItem.getUserName(), feedItem
                                .getProfileImageUrl(), feedItem.getImageUrl(), feedItem
                                .getLikesCount(), feedItem.getComment1(), feedItem.getComment2()));

                    }
                    activity.updateFeed(feedItemModels);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void fetchPopularPhotos(HomeActivity activity) {
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        client.get(popularUrl, getPopularResponseHandler(activity));
    }

    private JsonHttpResponseHandler getPopularResponseHandler(final HomeActivity activity) {
        final ArrayList<FeedItemModel> feedItemModels = new ArrayList<>();
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray photosJSON = response.getJSONArray("data");

                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        JSONObject user = photoJSON.getJSONObject("user");
                        JSONObject image =
                                photoJSON.getJSONObject("images").getJSONObject(
                                        "standard_resolution");

                        JSONObject firstCommentJson =
                                photoJSON.getJSONObject("comments").getJSONArray("data")
                                        .getJSONObject(0);
                        JSONObject secondCommentJson =
                                photoJSON.getJSONObject("comments").getJSONArray("data")
                                        .getJSONObject(1);


                        feedItemModels.add(new FeedItemModel(user.getString("username"), user
                                .getString("profile_picture"), image.getString("url"), photoJSON
                                .getJSONObject("likes").getInt("count"), firstCommentJson
                                .getJSONObject("from").getString("username")
                                + " "
                                + firstCommentJson.getString("text"), secondCommentJson
                                .getJSONObject("from").getString("username")
                                + " "
                                + secondCommentJson.getString("text")));

                        activity.updateFeed(feedItemModels);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
