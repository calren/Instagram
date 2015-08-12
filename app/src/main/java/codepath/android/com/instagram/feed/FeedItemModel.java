package codepath.android.com.instagram.feed;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

@ParseClassName("FeedItem")
public class FeedItemModel extends ParseObject{

    public String userName;
    public String comment1;
    public String comment2;
    public String imageUrl;
    public String profileImageUrl;
    public int likesCount;

    public FeedItemModel() {

    }

    public FeedItemModel(String userName, String profileImageUrl, String imageUrl, int likesCount, String comment1,
            String comment2) {
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.profileImageUrl = profileImageUrl;
        this.likesCount = likesCount;
        this.comment1 = comment1;
        this.comment2 = comment2;
    }

    public String getProfileImageUrl() {
        return getString("profileImageUrl");
    }

    public String getImageUrl() {
        return getString("imageUrl");
    }

    public String getUserName() {
        return getString("username");
    }

    public String getComment1() {
        return getString("comment1");
    }

    public int getLikesCount() {
        return getInt("likesCount");
    }

    public String getComment2() {
        return getString("comment2");
    }
}
