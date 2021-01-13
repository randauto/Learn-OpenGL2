package com.g3.learningopengl.wallpaper;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

/**
 * Created by tuanlq on 1/13/21
 */
public class WallpaperGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "WallpaperGLSurfaceView";

    public WallpaperGLSurfaceView(Context context) {
        super(context);
    }

    @Override
    public SurfaceHolder getHolder() {
        return super.getHolder();
    }

    public void onDestroy() {
        super.onDetachedFromWindow();
    }
}
