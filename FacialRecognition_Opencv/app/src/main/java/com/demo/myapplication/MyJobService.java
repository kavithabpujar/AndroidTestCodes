package com.demo.myapplication;

import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

public class MyJobService extends JobIntentService {

    private ResultReceiver mResultRecever;

    private static final int DOWNLOAD_ID=1689;
    private static final int SHOW_RESULT=123;

    public static final String ACTION_DOWNLOAD="action.DOWNLOAD_DATA";
    public static String RECEIVER_DATA="RECEIVER_DATA";


    public static void enqueueWork(Context context, ResultReceiver resultReceiver)
    {
        Intent intent=new Intent(context,JobService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(RECEIVER_DATA,resultReceiver);
        enqueueWork(context,JobService.class,DOWNLOAD_ID,intent);

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
                 if(intent.getAction() != null)
                 {
                     switch (intent.getAction())
                     {
                         case ACTION_DOWNLOAD:
                             mResultRecever= intent.getParcelableExtra(RECEIVER_DATA);
                 for(int i=0;i<10;i++)
                 {
                     Bundle bundle=new Bundle();
                     bundle.putString("data",String.format("10"));
                     mResultRecever.send(SHOW_RESULT,bundle);
                 }

                 break;
         }
    }

    }
}
