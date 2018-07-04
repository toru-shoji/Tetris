package com.toro_studio.Tetris.tools;

import android.graphics.Paint;

public class ChainPaint extends Paint {

    private ChainPaint() {}

    public static ChainPaint newInstance() {
        return new ChainPaint();
    }

    public ChainPaint addAlias(boolean bool) {
        setAntiAlias(bool);
        return this;
    }

    public ChainPaint addColor(int color) {
        setColor(color);
        return this;
    }

    public ChainPaint addStyle(Paint.Style style) {
        setStyle(style);
        return this;
    }

    public ChainPaint addStrokeWidth(int width) {
        setStrokeWidth(width);
        return this;
    }

}
