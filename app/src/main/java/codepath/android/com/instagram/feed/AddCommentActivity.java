package codepath.android.com.instagram.feed;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import codepath.android.com.instagram.R;

public class AddCommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_comment);
    }
}
