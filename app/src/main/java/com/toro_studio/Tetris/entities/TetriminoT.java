package com.toro_studio.Tetris.entities;

import android.graphics.Point;

import com.toro_studio.Tetris.constraints.ITetrimino;

import java.util.ArrayList;
import java.util.List;

public class TetriminoT implements ITetrimino {

    private final List<List<Point>> pointList;
    private int direct;

    public TetriminoT() {

        List<Point> tmpList1 = new ArrayList<>();
        tmpList1.add(new Point(-1, 0));
        tmpList1.add(new Point(0, 0));
        tmpList1.add(new Point(1, 0));
        tmpList1.add(new Point(0, -1));

        List<Point> tmpList2 = new ArrayList<>();
        tmpList2.add(new Point(0, -1));
        tmpList2.add(new Point(0, 0));
        tmpList2.add(new Point(0, 1));
        tmpList2.add(new Point(-1, 0));

        List<Point> tmpList3 = new ArrayList<>();
        tmpList3.add(new Point(-1, 0));
        tmpList3.add(new Point(0, 0));
        tmpList3.add(new Point(1, 0));
        tmpList3.add(new Point(0, 1));

        List<Point> tmpList4 = new ArrayList<>();
        tmpList4.add(new Point(0, -1));
        tmpList4.add(new Point(0, 0));
        tmpList4.add(new Point(0, 1));
        tmpList4.add(new Point(1, 0));

        pointList = new ArrayList<>();
        pointList.add(tmpList1);
        pointList.add(tmpList2);
        pointList.add(tmpList3);
        pointList.add(tmpList4);
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
        return "TetriminoT{" +
                "pointList=" + pointList +
                ", direct=" + direct +
                '}';
    }

}