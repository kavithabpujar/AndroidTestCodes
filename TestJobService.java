package com.services.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

public class TestJobService extends JobService {
    static int counter=0;

    @Override
    public boolean onStartJob(JobParameters params) {
        Util.schedulerJob(getApplicationContext()); // reschedule the job
        Toast.makeText(this, "Bg Service", Toast.LENGTH_SHORT).show();
        Log.i("in JOB SERVICE ", "in  ++++  " + (counter++));

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}