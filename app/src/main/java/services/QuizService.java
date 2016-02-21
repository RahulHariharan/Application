package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import adapters.QuizSyncAdapter;

public class QuizService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static QuizSyncAdapter sQuizSyncAdapter = null;

    public QuizService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (sSyncAdapterLock) {
            if (sQuizSyncAdapter == null) {
                sQuizSyncAdapter = new QuizSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sQuizSyncAdapter.getSyncAdapterBinder();
    }
}
