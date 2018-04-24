package com.toro_studio.Tetris.models;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class TetriminoI implements Tetrimino {

    private final List<List<Point>> pointList;
    private int direct;

    public TetriminoI() {

        List<Point> pointList1 = new ArrayList<>();
        pointList1.add(new Point(-1, 0));
        pointList1.add(new Point(0, 0));
        pointList1.add(new Point(1, 0));
        pointList1.add(new Point(2, 0));

        List<Point> pointList2 = new ArrayList<>();
        pointList2.add(new Point(0, -1));
        pointList2.add(new Point(0, 0));
        pointList2.add(new Point(0, 1));
        pointList2.add(new Point(0, 2));

        List<Point> pointList3 = new ArrayList<>();
        pointList3.add(new Point(-1, 0));
        pointList3.add(new Point(0, 0));
        pointList3.add(new Point(1, 0));
        pointList3.add(new Point(2, 0));

        List<Point> pointList4 = new ArrayList<>();
        pointList4.add(new Point(0, -1));
        pointList4.add(new Point(0, 0));
        pointList4.add(new Point(0, 1));
        pointList4.add(new Point(0, 2));

        pointList = new ArrayList<>();
        pointList.add(pointList1);
        pointList.add(pointList2);
        pointList.add(pointList3);
        pointList.add(pointList4);
    }

    @Override
    public List<Point> getPointList(int direct) {
        return pointList.get(direct);
    }

    @Override
    public int getDirect() {
        return direct;
    }

    @Override
    public void setDirect(int direct) {
        this.direct = direct;
    }

    @Override
    public String toString() {
        return "TetriminoI{" +
                "pointList=" + pointList +
                ", direct=" + direct +
                '}';
    }

}