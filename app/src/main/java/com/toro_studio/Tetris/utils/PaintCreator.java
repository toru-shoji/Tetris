package com.toro_studio.Tetris.utils;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintCreator {

    private PaintCreator() {};

    private static final class PaintCreatorHolder {
        private static final PaintCreator instance = new PaintCreator();
    }

    public static PaintCreator getInstance() {
        return PaintCreatorHolder.instance;
    }

    public Paint getGridPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }

    public Paint getBlockPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#757575"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    public Paint getStorePaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#90FF0000"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

    public Paint getGohstPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#A00000FF"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }

}
