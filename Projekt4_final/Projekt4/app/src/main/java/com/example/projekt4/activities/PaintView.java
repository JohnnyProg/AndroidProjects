package com.example.projekt4.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PaintView extends View {
    private ViewGroup.LayoutParams params = null;

    public Path path = new Path();
    public Paint paintBrush = new Paint();
    private ArrayList<Path> pathList= new ArrayList<Path>();
    private ArrayList<Integer> colorList = new ArrayList<Integer>();

    private ArrayList<Pair<Float, Float>> circleList =new ArrayList<Pair<Float, Float>>();
    private ArrayList<Integer> colorCircleList = new ArrayList<Integer>();

    int currentColor;
    private Canvas currCanvas;

//    private Paint paintBrush

    public PaintView(Context context) {

        super(context);
        init();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        paintBrush.setColor(Color.BLACK);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeWidth(8f);

//        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void changeColor(int c) {
        colorList.add(currentColor);
        currentColor = c;
        pathList.add(path);
        path = new Path();
    }

    public void clearCanva() {
        Log.e("dasdas", "czyszczenie canvy");
        pathList= new ArrayList<Path>();
        colorList = new ArrayList<Integer>();

        circleList =new ArrayList<Pair<Float, Float>>();
        colorCircleList = new ArrayList<Integer>();
        path = new Path();
        postInvalidate();
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(
                this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.d("jkashdj", "down");
                path.moveTo(x, y);
                circleList.add(new Pair<>(x, y));
                colorCircleList.add(currentColor);
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d("jkashdj", x + " " + y);
                path.lineTo(x, y);
                //pathList.add(path);
                //colorList.add(currentColor);
                break;
            case MotionEvent.ACTION_UP:
                circleList.add(new Pair<>(x, y));
                colorCircleList.add(currentColor);
                break;
            default:
                return false;
        }
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.e("kajwhdkjw", "" + pathList.size() + " / " + colorList.size());
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setColor(currentColor);
        canvas.drawPath(path, paintBrush);
        for (int i = 0; i < pathList.size(); i++) {
            //canvas.drawPath(pathList.get(0), paintBrush);
//            paintBrush.setStyle(Paint.Style.STROKE);
            paintBrush.setColor(colorList.get(i));
            canvas.drawPath(pathList.get(i), paintBrush);
//            Log.e("kajwhdkjw", i + " : " + pathList.get(i) + " / " + colorList.get(i));
            invalidate();
        }

        for (int i = 0; i < circleList.size(); i++) {
            paintBrush.setStyle(Paint.Style.FILL);
            paintBrush.setColor(colorCircleList.get(i));
            Pair<Float, Float> cords = circleList.get(i);
            canvas.drawCircle(cords.first, cords.second, 20, paintBrush);
        }
        currCanvas = canvas;
//        invalidate();
    }
}
