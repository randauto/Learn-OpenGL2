package com.g3.practiceopengl;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.g3.learningopengl.databinding.ActivityCameraSurfaceViewShowBinding;

public class CameraSurfaceViewShowActivity extends AppCompatActivity implements SurfaceHolder.Callback2 {
    private ActivityCameraSurfaceViewShowBinding mBinding;

    SurfaceView mSurfaceView;

    public SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCameraSurfaceViewShowBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mSurfaceView = mBinding.mSurface;

        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        initView();
    }

    private void initView() {
        mBinding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("rotationY", 0.0f, 360.0f, 0.0f);
                PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f, 1.0f);
                PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f, 1.0f);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mSurfaceView, valuesHolder, valuesHolder1, valuesHolder3);
                objectAnimator.setDuration(5000).start();
            }
        });
    }

    @Override
    public void surfaceRedrawNeeded(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mParameters = mCamera.getParameters();
                    mParameters.setPictureFormat(PixelFormat.JPEG);
                    mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    mCamera.setParameters(mParameters);
                    mCamera.startPreview();
                    mCamera.cancelAutoFocus();
                }
            }
        });
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}