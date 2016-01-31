package com.nicodelee.view;


import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 翻转动画
 *
 * @author Sodino E-mail:sodinoopen@hotmail.com
 * @version Time��2012-6-27 ����07:32:00
 */
public class RotateAnimation extends Animation {
    /**
     *
     */
    public static final boolean DEBUG = false;
    /**
     *
     */
    public static final boolean ROTATE_DECREASE = true;
    /**
     *
     */
    public static final boolean ROTATE_INCREASE = false;
    /**
     *
     */
    public static final float DEPTH_Z = 310.0f;
    /**
     *
     */
    public static final long DURATION = 800l;
    /**
     *
     */
    private boolean type;
    private float centerX;
    private float centerY;
    private Camera camera;
    /**
     *
     */
    private InterpolatedTimeListener listener;

    public RotateAnimation(float cX, float cY, boolean type) {
//        Log.d("ANDROID_LAB", "here:RotateAnimation");
        centerX = cX;
        centerY = cY;
        this.type = type;
        setDuration(DURATION);
//        Log.d("ANDROID_LAB", "here:RotateAnimation after");
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        //
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    public void setInterpolatedTimeListener(InterpolatedTimeListener listener) {
        this.listener = listener;
    }

    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        // interpolatedTime:[0.0f,10.f]
        if (listener != null) {
            Log.d("ANDROID_LAB", "here:");
            listener.interpolatedTime(interpolatedTime);
            if (interpolatedTime==1){
                listener=null;
            }
        }
        float from = 0.0f, to = 0.0f;
        if (type == ROTATE_DECREASE) {
            from = 0.0f;
            to = 180.0f;
        } else if (type == ROTATE_INCREASE) {
            from = 360.0f;
            to = 180.0f;
        }
        float degree = from + (to - from) * interpolatedTime;
        boolean overHalf = (interpolatedTime > 0.5f);
        if (overHalf) {
            //
            degree = degree - 180;
        }
        // float depth = 0.0f;
        float depth = (0.5f - Math.abs(interpolatedTime - 0.5f)) * DEPTH_Z;
        final Matrix matrix = transformation.getMatrix();
        camera.save();
        camera.translate(0.0f, 0.0f, depth);
        camera.rotateY(degree);
        camera.getMatrix(matrix);
        camera.restore();
        if (DEBUG) {
            if (overHalf) {
                matrix.preTranslate(-centerX * 2, -centerY);
                matrix.postTranslate(centerX * 2, centerY);
            }
        } else {
            //
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    /**
     *
     */
    public interface InterpolatedTimeListener {
        public void interpolatedTime(float interpolatedTime);
    }
}