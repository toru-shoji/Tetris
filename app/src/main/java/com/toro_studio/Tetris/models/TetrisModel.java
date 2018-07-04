package com.toro_studio.Tetris.models;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.toro_studio.Tetris.constraints.ITetrimino;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TetrisModel {

    private int columns;
    private int rows;

    private int screenWidth;
    private int screenHeight;
    private int statusBarHeight;
    private int gameScreenHeight;
    private int screenTop;
    private int itemSize;

    private Rect controllerRectLeftRight;
    private Rect controllerRectTopBottom;
    private Rect button1;
    private Rect button2;

    private List<Integer> storedPieceLocationArray;
    private List<Rect> movePieceRect;
    private Map<Point, Rect> gohstRectMap;
    private List<Rect> screenRectList;

    private ITetrimino moveTetrimino;
    private int tetrimiCenter;
    private int[] blockArea;

    private Paint paint;
    private Paint blockPaint;
    private Paint storedPaint;
    private Paint gohstPaint;

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }

    public int getGameScreenHeight() {
        return gameScreenHeight;
    }

    public void setGameScreenHeight(int gameScreenHeight) {
        this.gameScreenHeight = gameScreenHeight;
    }

    public int getScreenTop() {
        return screenTop;
    }

    public void setScreenTop(int screenTop) {
        this.screenTop = screenTop;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public Rect getControllerRectLeftRight() {
        return controllerRectLeftRight;
    }

    public void setControllerRectLeftRight(Rect controllerRectLeftRight) {
        this.controllerRectLeftRight = controllerRectLeftRight;
    }

    public Rect getControllerRectTopBottom() {
        return controllerRectTopBottom;
    }

    public void setControllerRectTopBottom(Rect controllerRectTopBottom) {
        this.controllerRectTopBottom = controllerRectTopBottom;
    }

    public Rect getButton1() {
        return button1;
    }

    public void setButton1(Rect button1) {
        this.button1 = button1;
    }

    public Rect getButton2() {
        return button2;
    }

    public void setButton2(Rect button2) {
        this.button2 = button2;
    }

    public List<Integer> getStoredPieceLocationArray() {
        return storedPieceLocationArray;
    }

    public void setStoredPieceLocationArray(List<Integer> storedPieceLocationArray) {
        this.storedPieceLocationArray = storedPieceLocationArray;
    }

    public List<Rect> getMovePieceRect() {
        return movePieceRect;
    }

    public void setMovePieceRect(List<Rect> movePieceRect) {
        this.movePieceRect = movePieceRect;
    }

    public Map<Point, Rect> getGohstRectMap() {
        return gohstRectMap;
    }

    public void setGohstRectMap(Map<Point, Rect> gohstRectMap) {
        this.gohstRectMap = gohstRectMap;
    }

    public List<Rect> getScreenRectList() {
        return screenRectList;
    }

    public void setScreenRectList(List<Rect> screenRectList) {
        this.screenRectList = screenRectList;
    }

    public ITetrimino getMoveTetrimino() {
        return moveTetrimino;
    }

    public void setMoveTetrimino(ITetrimino moveTetrimino) {
        this.moveTetrimino = moveTetrimino;
    }

    public int getTetrimiCenter() {
        return tetrimiCenter;
    }

    public void setTetrimiCenter(int tetrimiCenter) {
        this.tetrimiCenter = tetrimiCenter;
    }

    public int[] getBlockArea() {
        return blockArea;
    }

    public void setBlockArea(int[] blockArea) {
        this.blockArea = blockArea;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getBlockPaint() {
        return blockPaint;
    }

    public void setBlockPaint(Paint blockPaint) {
        this.blockPaint = blockPaint;
    }

    public Paint getStoredPaint() {
        return storedPaint;
    }

    public void setStoredPaint(Paint storedPaint) {
        this.storedPaint = storedPaint;
    }

    public Paint getGohstPaint() {
        return gohstPaint;
    }

    public void setGohstPaint(Paint gohstPaint) {
        this.gohstPaint = gohstPaint;
    }

    @Override
    public String toString() {
        return "TetrisModels{" +
                "columns=" + columns +
                ", rows=" + rows +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                ", statusBarHeight=" + statusBarHeight +
                ", gameScreenHeight=" + gameScreenHeight +
                ", screenTop=" + screenTop +
                ", itemSize=" + itemSize +
                ", controllerRectLeftRight=" + controllerRectLeftRight +
                ", controllerRectTopBottom=" + controllerRectTopBottom +
                ", button1=" + button1 +
                ", button2=" + button2 +
                ", storedPieceLocationArray=" + storedPieceLocationArray +
                ", movePieceRect=" + movePieceRect +
                ", gohstRectMap=" + gohstRectMap +
                ", screenRectList=" + screenRectList +
                ", moveTetrimino=" + moveTetrimino +
                ", tetrimiCenter=" + tetrimiCenter +
                ", blockArea=" + Arrays.toString(blockArea) +
                ", paint=" + paint +
                ", blockPaint=" + blockPaint +
                ", storedPaint=" + storedPaint +
                ", gohstPaint=" + gohstPaint +
                '}';
    }
}
