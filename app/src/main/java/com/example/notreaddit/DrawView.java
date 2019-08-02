package com.example.notreaddit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    Bitmap background;
    Article article;
    HashMap<String,Point> nodes;
    boolean drawPoints;
    private Draw delegate;
    private Thread thread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (thread == null)
        {
            Log.d("DrawView", "DrawThread is null");
            return;
        }
        while (true)
        {
            try
            {
                Log.d("DrawView", "Request last frame");
                thread.join(5000);
                break;
            } catch (Exception e)
            {
                Log.e("DrawView", "Could not join with draw thread");
            }
        }
        thread = null;
    }

    public interface Draw{
        Canvas drawThis(Canvas canvas);
    }

    public DrawView(Context context,Draw draw){
        super(context);
        delegate = draw;
        Log.d("DrawView","My constructor called");
        getHolder().addCallback(this);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true); //necessary
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    public DrawView(Context context) {
        super(context);
        Log.d("DrawView","Default constructor called");
        getHolder().addCallback(this);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true); //necessary
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }
    public DrawView(Context context, AttributeSet attrs) {
        super(context,attrs);
        Log.d("DrawView","Default constructor called 2");
        getHolder().addCallback(this);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true); //necessary
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void run() {
        Log.d("DrawView","Running");
        if(delegate != null) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            getHolder().unlockCanvasAndPost(delegate.drawThis(canvas));
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }
}