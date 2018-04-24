package com.toro_studio.Tetris.models;

import android.graphics.Point;

import java.util.List;

public interface Tetrimino {
    List<Point> getPointList(int direct);
    int getDirect();
    void setDirect(int direct);
}
