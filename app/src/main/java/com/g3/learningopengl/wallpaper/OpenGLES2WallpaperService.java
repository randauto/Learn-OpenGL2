package com.g3.learningopengl.wallpaper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.view.SurfaceHolder;
import android.widget.Toast;

/**
 * Created by tuanlq on 1/13/21
 */
public abstract class OpenGLES2WallpaperService extends GLWallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new OpenGLES2Engine();
    }

    class OpenGLES2Engine extends GLWallpaperService.MyGLEngine {
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            boolean supportsEs2 =
                    configurationInfo.reqGlEsVersion >= 0x20000
                            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                            && (Build.FINGERPRINT.startsWith("generic")
                            || Build.FINGERPRINT.startsWith("unknown")
                            || Build.MODEL.contains("google_sdk")
                            || Build.MODEL.contains("Emulator")
                            || Build.MODEL.contains("Android SDK built for x86")));
            if (supportsEs2) {
                setEGLContextClientVersion(2);
                setPreserveEGLContextOnPause(true);
                setRenderer(getNewRenderer());
            } else {
                Toast.makeText(getBaseContext(), "Not support OPENGL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    abstract GLSurfaceView.Renderer getNewRenderer();
}
