package com.services.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActivityManager;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    AppCompatButton stopScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLastKnownLocation();

        stopScheduler=findViewById(R.id.stopScheduler);;
        stopScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.stopJobScheduler(MainActivity.this);
                Intent serviceIntent = new Intent(MainActivity.this, LocationService.class);
                stopService(serviceIntent);
                stopService(new Intent(LocationService.LOCATION_SERVICE));
                finish();



            }
        });


   }

    public void getLastKnownLocation()
    {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location=task.getResult();
                    //  GeoPoint geoPoint = new GeoPoint(location.getLatitude(),location.getLongitude());
                }
            }
        });
        Util.schedulerJob(getApplicationContext());
        startLocationService();
    }


    private void startLocationService(){
     //   if(!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(this, LocationService.class);
//        this.startService(serviceIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

                MainActivity.this.startForegroundService(serviceIntent);
            }else{
                startService(serviceIntent);
            }
        }
  //  }

}
