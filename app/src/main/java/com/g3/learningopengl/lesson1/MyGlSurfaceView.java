package com.g3.learningopengl.lesson1;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class MyGlSurfaceView extends GLSurfaceView {
    public MyGlSurfaceView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TTTT", "x = " + event.getX() + ", y = " + event.getY());
        return true;
    }
}
