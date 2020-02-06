package com.demo.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class MyResultReciever extends ResultReceiver {


    private Receiver mReceiver;

    public MyResultReciever (Handler handle)
    {
        super(handle);
    }

    public void setmReceiver(Receiver r)
    {
        mReceiver=r;



    }

    protected void onReceiveResult(int resultCode, Bundle resultData)
    {
        if(mReceiver != null)
        {
            mReceiver.onReceiveResult(resultCode,resultData);
        }
    }

    public interface Receiver{
        void onReceiveResult(int resultCode,Bundle resultData);
    }

}
