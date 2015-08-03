package codepath.android.com.instagram.feed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import codepath.android.com.instagram.R;

public class AddCommentActivity extends Activity {

    EditText addCommentView;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_comment);
        position = getIntent().getIntExtra("position", -1);
        setLayout();
    }


    private void setLayout() {
        addCommentView = (EditText) findViewById(R.id.add_comment_edit_text);
        findViewById(R.id.send_comment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("new_comment", addCommentView.getText().toString());
                returnIntent.putExtra("position", position);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        findViewById(R.id.cancel_comment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCommentActivity.this.finish();
            }
        });
        
    }
}
