package com.toro_studio.Tetris.tools;

import android.graphics.Rect;
import android.graphics.RectF;

public class ChainRectF extends RectF {

    private ChainRectF() {}

    public static ChainRectF newInstance() {
        return new ChainRectF();
    }

    public ChainRectF left(int leftValue) {
        set(leftValue, top, right, bottom);
        return this;
    }

    public ChainRectF top(int topValue) {
        set(left, topValue, right, bottom);
        return this;
    }

    public ChainRectF right(int rightValue) {
        set(left, top, rightValue, bottom);
        return this;
    }

    public ChainRectF bottom(int bottomValue) {
        set(left, top, right, bottomValue);
        return this;
    }

    public Rect asRect() {
        return new Rect((int)left, (int)top, (int)right, (int)bottom);
    }

}
