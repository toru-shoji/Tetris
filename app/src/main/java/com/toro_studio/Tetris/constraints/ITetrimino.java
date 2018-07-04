package com.toro_studio.Tetris.constraints;

import android.graphics.Point;

import java.util.List;

public interface ITetrimino {
    List<Point> getPointList(int direct);
    int getDirect();
    void setDirect(int direct);
}
