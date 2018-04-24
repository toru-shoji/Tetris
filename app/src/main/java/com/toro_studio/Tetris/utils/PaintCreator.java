package com.toro_studio.Tetris.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import static com.toro_studio.Tetris.models.Constants.DEBUG;

public class PaintCreator {

    private static final String TAG = "PaintCreator";

    private PaintCreator() {};

    private static final class PaintCreatorHolder {
        private static final PaintCreator instance = new PaintCreator();
    }

    public static PaintCreator getInstance() {
        return PaintCreatorHolder.instance;
    }

    public Paint getGridPaint() {
        if(DEBUG) {
            Log.d(TAG, "getGridPaint()");
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }

    public Paint getBlockPaint() {
        if(DEBUG) {
            Log.d(TAG, "getBlockPaint()");
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#757575"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    public Paint getStorePaint() {
        if(DEBUG) {
            Log.d(TAG, "getStorePaint()");
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#90FF0000"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    public Paint getGohstPaint() {
        if(DEBUG) {
            Log.d(TAG, "getGohstPaint()");
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#A00000FF"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

}
