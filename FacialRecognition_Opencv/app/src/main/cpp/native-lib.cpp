#include <jni.h>
#include <string>
#include<opencv2/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>

#include <opencv2/objdetect.hpp>
#include "opencv2/imgproc.hpp"
#include <iostream>

using namespace std;
using namespace cv;

CascadeClassifier faceCascade;
CascadeClassifier eyesCascade;

extern "C" JNIEXPORT jstring JNICALL
Java_com_demo_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_demo_myapplication_MainActivity_validate(
        JNIEnv *env,
        jobject ,
        jlong addGray,
        jlong addRgba
        ) {
    cv::Rect();
    cv::Mat();
    std::string hello2 = "Hello from openCV";
    return env->NewStringUTF(hello2.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_demo_myapplication_MainActivity_detectEdges(
        JNIEnv *env,
        jobject /* this */,
        jlong gray) {
    cv::Mat& edges = *(cv::Mat *) gray;
    cv::Canny(edges, edges, 50.0, 250.0,3,false);

//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
}


int toGray(cv::Mat& img,cv::Mat& gray){
    cv::cvtColor(img,gray,cv::COLOR_RGBA2GRAY);
    if(gray.rows == img.rows && gray.cols==img.cols)
    {
        return 1;
    }else{
        return 0;
    }
}

void detect(cv::Mat& frame)
{

//    Mat frame;
//    cvtColor(colorframe, frame, COLOR_BGR2GRAY);
//
//    equalizeHist(frame, frame);

//    cv::Mat frame;
//    cv::cvtColor(colorframe,frame,cv::COLOR_RGBA2GRAY);

    std::string faceCascadePath = "/data/user/0/com.demo.myapplication/app_cascade/haarcascade_frontalface_alt.xml";
    faceCascade.load( faceCascadePath );

    std::string eyesCascadePath = "/data/user/0/com.demo.myapplication/app_cascade/haarcascade_eye_tree_eyeglasses.xml";
    eyesCascade.load( eyesCascadePath );

    if(!faceCascade.load(faceCascadePath)){
        printf("************* ERROR LOADING FACES");
        return;
    }else{
        printf("************* SUCCESS LOADING FACES");
    }


    if(!eyesCascade.load(eyesCascadePath)){
        printf("************* ERROR LOADING EYES");
        return;
    }else{
        printf("************* SUCCESS LOADING EYES");
    }


    std::vector<Rect> faces;
    faceCascade.detectMultiScale(frame, faces,1.1,2,0|cv::CASCADE_SCALE_IMAGE,Size(30,30));


    std::vector<Rect> eyes;
    eyesCascade.detectMultiScale(frame, eyes);


    for ( size_t i = 0; i < faces.size(); i++ )
    {
        int x1 = faces[i].x;
        int y1 = faces[i].y;
        int x2 = faces[i].x + faces[i].width;
        int y2 = faces[i].y + faces[i].height;

        Point center(faces[i].x+faces[i].width*0.5,faces[i].y+faces[i].height*0.5);
        ellipse(frame,center,Size(faces[i].width*0.5,faces[i].height*0.5),0,0,360,Scalar(0,0,160),10,8,0);

        Mat faceROI=frame(faces[i]);
      /* eyesCascade.detectMultiScale(faceROI, eyes);
        for ( size_t i = 0; i < eyes.size(); i++ )
        {
            int x1 = eyes[i].x;
            int y1 = eyes[i].y;
            int x2 = eyes[i].x + eyes[i].width;
            int y2 = eyes[i].y + eyes[i].height;

            Point center(eyes[i].x+eyes[i].width*0.5,eyes[i].y+eyes[i].height*0.5);
            ellipse(frame,center,Size(eyes[i].width*0.5,eyes[i].height*0.5),0,0,360,Scalar(0,0,160),10,8,0);
           }*/



    }



    }

 /*   for ( size_t i = 0; i < eyes.size(); i++ )
    {
        int x1 = eyes[i].x;
        int y1 = eyes[i].y;
        int x2 = eyes[i].x + eyes[i].width;
        int y2 = eyes[i].y + eyes[i].height;

        Point center(eyes[i].x+eyes[i].width*0.5,eyes[i].y+eyes[i].height*0.5);
        ellipse(frame,center,Size(eyes[i].width*0.5,eyes[i].height*0.5),0,0,360,Scalar(0,0,160),10,8,0);
    }*/


extern "C" JNIEXPORT void JNICALL
Java_com_demo_myapplication_MainActivity_faceDetection(
        JNIEnv *env,
        jobject /* this */,
        jlong addRgba,jlong addrGray) {
    cv::Mat& frame=*(cv::Mat*)addRgba;
    printf("************* SUCCESS  CALLING DETECT FACES");

  //  cv::Mat& mGray=*(cv::Mat*)addrGray;


  //cv::cvtColor(frame,mGray,cv::COLOR_RGBA2GRAY);

//    int conv;
//    jint retVal;
//    conv=toGray(frame,mGray);


    detect(frame);




//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
}


