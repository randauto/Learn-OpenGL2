package com.g3.learningopengl.wallpaper.render;

import android.opengl.GLSurfaceView;

import com.g3.learningopengl.lesson3.LessonThreeRenderer;
import com.g3.learningopengl.wallpaper.OpenGLES2WallpaperService;

public class LessonThreeWallpaperService extends OpenGLES2WallpaperService {
    @Override
    public GLSurfaceView.Renderer getNewRenderer() {
        return new LessonThreeRenderer();
    }
}
