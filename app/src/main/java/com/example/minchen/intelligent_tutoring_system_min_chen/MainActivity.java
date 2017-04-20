package com.example.minchen.intelligent_tutoring_system_min_chen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.minchen.intelligent_tutoring_system_min_chen.helper.MathUtil;
import com.example.minchen.intelligent_tutoring_system_min_chen.model.JoyContainer;
import com.example.minchen.intelligent_tutoring_system_min_chen.sentiment_analysis.SentimentAnalysis;
//import com.example.minchen.intelligent_tutoring_system_min_chen.sentiment_analysis.SentimentAnalysis;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements CameraDetector.CameraEventListener, CameraDetector.ImageListener {
    private static final int maxProcessingRate = 10 ;
    private static final int slidingWindowSizeInDataPoints = 15;
    private static final int slidingWindowSizeInSeconds = 15;
    private static final float threshold = (float) 60.0;

    private JoyContainer joyContainer;
    private SurfaceView cameraDetectorSurfaceView;
    private CameraDetector cameraDetector;

    public static final String tempdirectory = System.getProperty("java.io.tmpdir");
    public static final String pathSep = System.getProperty("path.separator");
    public static final String CLASSPATH = System.getProperty("java.class.path");
    // let the ClassLoader be only one -- global variable.
    public static ClassLoader parentLoader;
    public static ClassLoader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraDetectorSurfaceView = (SurfaceView)findViewById(R.id.cameraDetectorSurfaceView);
        cameraDetector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraDetectorSurfaceView) ;
        joyContainer = new JoyContainer();

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
        float engagement = face.emotions.getEngagement();
        float valance = face.emotions.getValence();

        float frown = face.expressions.getBrowFurrow();
        float attention = face.expressions.getAttention();

        joyContainer.addJoy(joy, timeStamp);

        //Print joy number
        System.out.println("Joy:" + joy);

        //User reached the threshold with your last 15 captured emotions.
        printIfReachThreshold();

        //User reached the threshold during the last 15 seconds
        printIfReachThreshold(joy);

    }

    private void printIfReachThreshold(float timeStamp) {
        if ((timeStamp - joyContainer.getFirstTimeStampInJoyList()) >= slidingWindowSizeInSeconds) {

            float currAverage = MathUtil.ave(joyContainer.getJoyNumList());

            if (currAverage >= threshold) {
                System.out.println("You have reached the threshold during the last 15 seconds.");
            }

            joyContainer.removeElement(0);
        }
    }

    private void printIfReachThreshold() {
        if (joyContainer.getSize() == slidingWindowSizeInDataPoints) {

            float currAverage = MathUtil.ave(this.joyContainer.getJoyNumList());

            if (currAverage >= threshold) {
                System.out.println("You have reached the threshold with your last 15 captured emotions.");
            }

            joyContainer.removeElement(0);
        }
    }

    public static void main(String[] args) throws IOException {
        exec(new Scanner(System.in));
    }

    public static void exec(Scanner sc) throws IOException {
        parentLoader = ClassLoader.getSystemClassLoader();
        URL tmpURL = new File(tempdirectory).toURI().toURL();
        loader = new URLClassLoader(new URL[] { tmpURL }, parentLoader);

        while (true) {
            System.out.print("> ");
            while(sc.hasNextLine()) {
                String input = sc.nextLine();
                input = input.trim();
                if (input.length() == 0) {
                    continue;
                }
                if (input.equals("quit")) {
                    break;
                }
                else {
                    SentimentAnalysis.getSentimentScore(input);
                    continue;
                }
            }
        }
    }

//    public static void main(String[] args) {
//        SentimentAnalysis.getSentimentScore("This is a test!");
//
//    }
}
