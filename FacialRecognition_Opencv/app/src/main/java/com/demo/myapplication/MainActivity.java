package com.demo.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");

    }

   

    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;

    // Used in Camera selection from menu (when implemented)
    private boolean              mIsJavaCamera = true;
    private MenuItem mItemSwitchCamera = null;

    // These variables are used (at the moment) to fix camera orientation from 270degree to 0degree
    Mat mRgba;
    Mat mRgbaF;
    Mat mRgbaT;
    Mat mGray;
    CascadeClassifier face_cascade;
    int screenWidth,screenHeight;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    load_cascade();
                    load_cascade1();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_camera);

        // Example of a call to a native method
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(validate(0L,0L));



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       screenHeight= displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;


        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.show_camera_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
    }





    @Override
    public void onCameraViewStarted(int width, int height) {

        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mRgbaF = new Mat(height, width, CvType.CV_8UC4);
        mRgbaT = new Mat(width, width, CvType.CV_8UC4);

        mGray = new Mat(width, width, CvType.CV_8UC1);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        // TODO Auto-generated method stub
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();
        // Rotate mRgba 90 degrees
        Core.transpose(mRgba, mRgbaT);
        Imgproc.resize(mRgbaT, mRgbaF, mRgbaF.size(), 0,0, 0);
        Core.flip(mRgbaF, mRgba, 1 );
//
//        Mat gray=inputFrame.gray();
//        Imgproc.cvtColor(mRgba, gray, Imgproc.COLOR_BGR2GRAY);


     //   faceDetection(mRgba.getNativeObjAddr());
         faceDetection(mRgba.getNativeObjAddr(),mGray.getNativeObjAddr());





     /*  MatOfRect faces = new MatOfRect();



       Mat low_res = new Mat(screenWidth, screenHeight, CvType.CV_8UC1);

        Imgproc.equalizeHist( low_res, low_res );
        face_cascade.detectMultiScale(low_res, faces);

        System.out.println("FACES SIZE ************ "+faces.size());

        for(Rect rect:faces.toArray())
        {
            Point center= new Point(4*rect.x + 4*rect.width*0.5, 4*rect.y + 4*rect.height*0.5 );
            Imgproc.ellipse( mRgba, new Point(center.x,center.y), new Size( rect.width*2, rect.height*2), 0, 0, 360, new Scalar( 0, 0, 160 ), 20, 8, 0 );
        }

        */
        return mRgba; // This function must return

//        Mat edges = inputFrame.gray();
//        detectEdges(edges.getNativeObjAddr());
//
//        return edges;

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    private void load_cascade(){
        try {
            // LOAD FROM ASSET
            InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt);

            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            face_cascade = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if(face_cascade.empty())
            {
                Log.v("MyActivity","--(!)Error loading A\n");
                return;
            }
            else
            {
                Log.v("MyActivity",
                        "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

                Log.v("MyActivity",
                        "Classifier Path " + mCascadeFile.getAbsolutePath());
                Log.v("MyActivity",
                        "" +
                                "***** Path " + mCascadeFile.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("MyActivity", "Failed to load cascade. Exception thrown: " + e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void load_cascade1(){
        try {
            // LOAD FROM ASSET
            InputStream is = getResources().openRawResource(R.raw.haarcascade_eye_tree_eyeglasses);

            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_eye_tree_eyeglasses.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            face_cascade = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if(face_cascade.empty())
            {
                Log.v("MyActivity","--(!)Error loading A\n");
                return;
            }
            else
            {
                Log.v("MyActivity",
                        "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

                Log.v("MyActivity",
                        "Classifier Path " + mCascadeFile.getAbsolutePath());
                Log.v("MyActivity",
                        "" +
                                "***** Path " + mCascadeFile.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("MyActivity", "Failed to load cascade. Exception thrown: " + e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String validate(long matAddrGr,long matAddRgba);
    public native void detectEdges(long matAddrGr);
    public native void faceDetection(long matAddrRgba,long matGray);
}
