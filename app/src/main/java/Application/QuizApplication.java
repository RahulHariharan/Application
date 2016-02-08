package Application;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by rahul on 27-01-2016.
 */

public class QuizApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this);
    }
}
