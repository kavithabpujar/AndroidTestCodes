package com.services.myapplication;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;

import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class TestJobService extends JobService {
    static int counter=0;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "LocationService";

    private final static long UPDATE_INTERVAL = 4 * 1000;  /* 4 secs */
    private final static long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    public boolean onStartJob(JobParameters params) {
        Util.schedulerJob(getApplicationContext()); // reschedule the job
        //   Toast.makeText(this, "Bg Service", Toast.LENGTH_SHORT).show();
        Log.i("in JOB SERVICE ", "in  ++++  " + (counter++));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return true;
    }

    private void getLocation() {

        // ---------------------------------- LocationRequest ------------------------------------
        // Create the location request to start receiving updates
               LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(UPDATE_INTERVAL);
        mLocationRequestHighAccuracy.setFastestInterval(FASTEST_INTERVAL);


        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocation: stopping the location service.");
            stopSelf();
            return;
        }
        Log.d(TAG, "getLocation: getting location information.");
        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy,locationCallback,
                Looper.myLooper()); // Looper.myLooper tells this to repeat forever until thread is destroyed
    }

    LocationCallback locationCallback= new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            Log.d(TAG, "onLocationResult: got location result.");

            Location location = locationResult.getLastLocation();

            if (location != null) {
                System.out.println(  "***********"+ location.getLatitude()+" , "+ location.getLongitude());
                Toast.makeText(TestJobService.this, location.getLatitude()+" , "+ location.getLongitude(), Toast.LENGTH_SHORT).show();

                UserLocation userLocation=new UserLocation();
                userLocation.setL(location.getLatitude()+","+location.getLongitude());
                userLocation.setT(""+System.currentTimeMillis());
                List<UserLocation> userLocList=new ArrayList<>();
                userLocList.add(userLocation);

                User user=new User();
                user.setLocs(userLocList);
                new RetrofitProvider().setLocationData(user);

                //                            User user = ((UserClient)(getApplicationContext())).getUser();
                //    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
//                            UserLocation userLocation = new UserLocation(user, geoPoint, null);
//                            saveUserLocation(userLocation);

                Toast.makeText(getApplicationContext(),location.getLatitude()+" , "+ location.getLongitude(),Toast.LENGTH_SHORT).show();
            }
        }
    };

}