package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import common.QuizAuthenticator;

public class QuizAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private QuizAuthenticator mAuthenticator;

    public QuizAuthenticatorService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("sdf","Sdfsdf23e4324");
        mAuthenticator = new QuizAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mAuthenticator.getIBinder();
    }
}
