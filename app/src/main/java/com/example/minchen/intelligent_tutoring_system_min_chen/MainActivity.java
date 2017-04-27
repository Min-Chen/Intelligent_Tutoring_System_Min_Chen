package com.example.minchen.intelligent_tutoring_system_min_chen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.minchen.intelligent_tutoring_system_min_chen.helper.HTTPRequestHelper;
import com.example.minchen.intelligent_tutoring_system_min_chen.helper.MathUtil;
import com.example.minchen.intelligent_tutoring_system_min_chen.model.FrownContainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CameraDetector.CameraEventListener, CameraDetector.ImageListener {
    private static final int maxProcessingRate = 10 ;
    private static final int slidingWindowSizeInDataPoints = 15;
    private static final int slidingWindowSizeInSeconds = 15;
    private static final float threshold = (float) 60.0;

    private FrownContainer frownContainer;
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
        frownContainer = new FrownContainer();
        heartbeatToPushFacialExpression();

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

        frownContainer.addFrown(frown, timeStamp);

        //Print joy number
        System.out.println("Frown:" + frown);

        //User reached the threshold with your last 15 captured emotions.
        printIfReachThreshold();

        //User reached the threshold during the last 15 seconds
        printIfReachThreshold(frown);

    }

    private void printIfReachThreshold(float timeStamp) {
        if ((timeStamp - frownContainer.getFirstTimeStampInFrownList()) >= slidingWindowSizeInSeconds) {

            float currAverage = MathUtil.ave(frownContainer.getFrownNumList());

            if (currAverage >= threshold) {
                System.out.println("You have reached the threshold during the last 15 seconds.");
            }

            frownContainer.removeElement(0);
        }
    }

    private boolean printIfReachThreshold() {
        boolean isReachThreshold = false;
        if (frownContainer.getSize() == slidingWindowSizeInDataPoints) {

            float currAverage = MathUtil.ave(this.frownContainer.getFrownNumList());

            if (currAverage >= threshold) {
                isReachThreshold = true;
                System.out.println("You have reached the threshold with your last 15 captured emotions.");
            }
            frownContainer.removeElement(0);
        }
        return isReachThreshold;
    }

    public void heartbeatToPushFacialExpression() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    String urlString = new StringBuffer()
                            .append("http://")
//                            .append("192.168.1.255").append(":")
                            .append("10.2.23.39").append(":")
                            .append(3050)
                            .append("/api/heartbeat")
                            .append("?frown=")
                            .append(printIfReachThreshold())
                            .toString();
                    System.out.println("url string to send to sentiment analysis server is: " + urlString);
                    String result = null;
                    try {
                        URL url = new URL(urlString);
                        result = HTTPRequestHelper.getHTML(url);
                        System.out.println("blah");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
