package codepath.android.com.instagram.feed;

public class FeedItemModel {

    private String userName;
    private String comment1;
    private String comment2;
    private String imageUrl;

    public FeedItemModel(String userName, String imageUrl, String comment1, String comment2) {
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.comment1 = comment1;
        this.comment2 = comment2;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment1() {
        return comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment) {
        comment2 = comment;
    }
}
