package com.example.minchen.moving_average_min_chen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CameraDetector.CameraEventListener, CameraDetector.ImageListener {
    SurfaceView cameraDetectorSurfaceView;
    CameraDetector cameraDetector;
    int maxProcessingRate = 10 ;
    private List<Float> joyList = new ArrayList<>();
    private List<Joy> joysList = new ArrayList<>();

    private int numDataPointsInList = 15;
    private final float threshold = (float) 70.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraDetectorSurfaceView = (SurfaceView)
                findViewById(R.id.cameraDetectorSurfaceView);

        cameraDetector = new CameraDetector(this ,
                CameraDetector.CameraType.CAMERA_FRONT, cameraDetectorSurfaceView) ;

        cameraDetector.setMaxProcessRate(maxProcessingRate) ;

        cameraDetector.setImageListener(this);
        cameraDetector.setOnCameraEventListener(this);

        cameraDetector.setDetectAllEmotions(true);

        cameraDetector.start();
    }

    @Override
    public void onCameraSizeSelected(int cameraHeight, int cameraWidth, Frame.ROTATE rotate) {
        ViewGroup.LayoutParams params = cameraDetectorSurfaceView.getLayoutParams();
        params.height = cameraHeight;
        params.width = cameraWidth;

        cameraDetectorSurfaceView.setLayoutParams(params);
    }

    @Override
    /*
    * Process image results from Affectiva SDK
    * */
    public void onImageResults(List<Face> faces, Frame frame, float timeStamp) {
        if (faces == null) {
            return; // frame was not processed
        }
        if (faces.size() == 0) {
            return; // no face found
        }
        Face face = faces.get(0);
        float joy = face.emotions.getJoy();

//        float anger = face.emotions.getAnger();
//        float surprise = face.emotions.getSurprise();

        System.out.println("Joy:" + joy);
//        System.out.println("Anger: " + anger);
//        System.out.println("Surprise: " + surprise);
//        printIfReachThreshold(joy);
        System.out.println("timestamp: " + timeStamp);

        printIfReachThreshold(timeStamp, joy);

    }

    private void printIfReachThreshold(float timeStamp, float joy) {
        float currAverage = 0;
        joysList.add(new Joy(joy, timeStamp));
        if ((timeStamp - joysList.get(0).getTimeStamp()) >= 15) {
            currAverage = sum(getJoynessList())/joysList.size(); // get all joys value from the list
            System.out.println("current average joyness is: " + currAverage);
            if (currAverage >= threshold) {
                System.out.println("You have reached the threshold.");
            }
            joysList.remove(0);
        }
    }

    private void printIfReachThreshold(float joy) {
        float currAverage = 0;
        joyList.add(joy);
        if (joyList.size() == (this.numDataPointsInList+1)) {
            joyList.remove(0);
            currAverage = sum(this.joyList)/this.numDataPointsInList;
            if (currAverage >= threshold) {
                System.out.println("You have reached the threshold.");
            }
        }
    }

    private List<Float> getJoynessList() {
        List<Float> list = new ArrayList<>();
        for (Joy joy : joysList) {
            list.add(joy.getJoyness());
        }
        return list;
    }

    private float sum(List<Float> list) {
        float sum = 0;
        for (Float i : list) {
            sum += i;
        }
        return sum;
    }
}
