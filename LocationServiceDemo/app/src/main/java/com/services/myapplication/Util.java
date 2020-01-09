package com.services.myapplication;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Util {

    static int counter=0;
    static JobScheduler jobScheduler = null;

    // schedule the start of the service every 10 - 30 seconds
    public static void schedulerJob(Context context) {
       // ComponentName serviceComponent = new ComponentName(context,TestJobService.class);
        ComponentName serviceComponent = new ComponentName(context,LocationService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0,serviceComponent);
//        builder.setMinimumLatency(1*1000);    // wait at least
//        builder.setOverrideDeadline(3*1000);  //delay time
        builder.setMinimumLatency(5*1000);    // wait at least
        builder.setOverrideDeadline(15*1000);  //delay time
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);  // require unmetered network
        builder.setRequiresCharging(false);  // we don't care if the device is charging or not
        builder.setRequiresDeviceIdle(true); // device should be idle
        System.out.println("(scheduler Job ************ "+(counter++));
        Log.i("in  UTIL ", "in  ++++  " + (counter++));

        boolean isRunning=false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("LocationService".equals(service.service.getClassName())) {
                    isRunning=true;
                    break;
                }
            }

            if(!isRunning) {
                Toast.makeText(context,"Service Stpped , so start again",Toast.LENGTH_SHORT).show();
                System.out.println("Service Stpped , so start again ******");
                jobScheduler = context.getSystemService(JobScheduler.class);
                jobScheduler.schedule(builder.build());
            }

        }




    }


    public static void stopJobScheduler(Context context){
        try {
            if (jobScheduler != null) {
                jobScheduler.cancelAll();

            }

            jobScheduler = context.getSystemService(JobScheduler.class);
            jobScheduler.cancelAll();

         }catch (Exception e){
            e.printStackTrace();
        }
    }



}
