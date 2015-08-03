package codepath.android.com.instagram;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import codepath.android.com.instagram.feed.FeedItemModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public final class NetworkController {
    private AsyncHttpClient client = new AsyncHttpClient();;
    public static final String CLIENT_ID = "0dc5d7db20c747fab6f288c437055c40";

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

                        feedItemModels.add(new FeedItemModel(user.getString("username"), image
                                .getString("url"), photoJSON.getJSONObject("comments")
                                .getJSONArray("data").get(0).toString(), photoJSON
                                .getJSONObject("comments").getJSONArray("data").get(1).toString()));

                        activity.updateFeed(feedItemModels);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
