package services;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;

public class QuizJobService extends JobService {
    public QuizJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }


}
