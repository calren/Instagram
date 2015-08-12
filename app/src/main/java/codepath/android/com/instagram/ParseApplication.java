package codepath.android.com.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "8IEANGLn2BWgAGW645V8gZdDdqo1G1VHZXWZAsfo", "kRmWjXEIp4T1kRVs3cHXdAZBp1T1ReUmYyQHApGy");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }

}
