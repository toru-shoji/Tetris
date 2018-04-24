package com.toro_studio.Tetris.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.toro_studio.Tetris.GameActivity;
import com.toro_studio.Tetris.models.Constants;
import com.toro_studio.Tetris.models.Tetrimino;
import com.toro_studio.Tetris.models.TetriminoPiece;
import com.toro_studio.Tetris.models.TetrisModels;
import com.toro_studio.Tetris.utils.PaintCreator;
import com.toro_studio.Tetris.views.IGameViews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.toro_studio.Tetris.models.Constants.DEBUG;

public class TetrisPresenter implements IGamePresetner{

    private static final String TAG = "TetrisPresenter";

    private IGameViews views;
    private TetrisModels models;

    public TetrisPresenter(IGameViews views) {
        this.views = views;
        models = new TetrisModels();
        initModels(((GameActivity.GameSurfaceView)views).getContext());
    }

    @Override
    public final void drawGameScreen(Canvas canvas) {
        if(DEBUG) {
            Log.d(TAG, "drawGameScreen(" + canvas + ")");
        }
        int columns = models.getColumns();
        int itemSize = models.getItemSize();
        Paint paint = models.getPaint();

        for(int count = 5 * columns; count < models.getScreenRectList().size(); count++) {
            if(0 != models.getBlockArea()[count]) {
                canvas.drawRect(models.getScreenRectList().get(count), models.getBlockPaint());
            } else {
                canvas.drawRect(models.getScreenRectList().get(count), paint);
            }
        }
        for(int count = 0; count < models.getStoredPieceLocationArray().size(); count++) {
            if(0 != models.getStoredPieceLocationArray().get(count)) {
                canvas.drawRect(models.getScreenRectList().get(count), models.getStoredPaint());
            }
        }
        canvas.drawRect(models.getControllerRectLeftRight(), paint);
        canvas.drawRect(models.getControllerRectTopBottom(), paint);
        canvas.drawCircle(models.getButton1().left + itemSize,
                models.getButton1().top + itemSize, itemSize, paint);
        canvas.drawCircle(models.getButton2().left + itemSize,
                models.getButton2().top + itemSize, itemSize, paint);
        if(null != models.getMovePieceRect()) {
            for(Rect r : models.getMovePieceRect()) {
                canvas.drawRect(r, models.getBlockPaint());
            }
        }
        if(null != models.getGohstRectMap()) {
            for(Point keyPoint : models.getGohstRectMap().keySet()) {
                canvas.drawRect(models.getGohstRectMap().get(keyPoint), models.getGohstPaint());
            }
        }
    }

    @Override
    public final void touchControllerOrButton(int x, int y) {
        if(DEBUG) {
            Log.d(TAG, "touchControllerOrButton(" + x + ", " + y + ")");
        }
        int itemSize = models.getItemSize();
        int columns = models.getColumns();
        int center = models.getTetrimiCenter();
        Tetrimino moveTetrimino = models.getMoveTetrimino();
        Rect leftRight = models.getControllerRectLeftRight();
        List<Integer> locationArray = models.getStoredPieceLocationArray();
        if(leftRight.contains(x, y)) {
            int direct = moveTetrimino.getDirect();
            List<Point> pointList = moveTetrimino.getPointList(direct);
            if(leftRight.left < x && x < leftRight.left + (itemSize * 2)) {
                if(checkLeftMove(locationArray, pointList, center)) {
                    models.setTetrimiCenter(center - 1);
                    displayGohstTetrimino();
                    downTetrimino();
                }
            }
            if(leftRight.right - (itemSize * 2) < x && x < leftRight.right) {
                if(checkRightMove(locationArray, pointList, center)) {
                    models.setTetrimiCenter(center + 1);
                    displayGohstTetrimino();
                    downTetrimino();
                }
            }
            return;
        }
        Rect topBottom = models.getControllerRectTopBottom();
        if(models.getControllerRectTopBottom().contains(x, y)) {
            if(topBottom.top < y && y < topBottom.top + itemSize * 2) {
                displayGohstTetrimino();
                turnTetrimino();
            }
            if(topBottom.bottom - (itemSize * 2) < y && y < topBottom.bottom) {
                models.setTetrimiCenter(center + columns);
                displayGohstTetrimino();
                downTetrimino();
            }
            return;
        }
        if(models.getButton1().contains(x, y)) {
            if(null != models.getMovePieceRect() & null != moveTetrimino) {
                fall2BottomTetrimino();
            }
            return;
        }
        if(models.getButton2().contains(x, y)) {
            turnTetrimino();
            return;
        }
    }

    @Override
    public final void fulfillRoleGame() {
        if(DEBUG) {
            Log.d(TAG, "fulfillRoleGame()");
        }
        models.setTetrimiCenter(models.getTetrimiCenter() + models.getColumns());
        downTetrimino();
        displayGohstTetrimino();
    }

    @Override
    public final void initGameScreenInfo() {
        if(DEBUG) {
            Log.d(TAG, "initGameScreenInfo()");
        }
        ArrayList<Rect> tmpRectArray = new ArrayList<>();
        int screenWidth = models.getScreenWidth();
        int columns = models.getColumns();
        int rows = models.getRows();
        int itemSize = models.getItemSize();

        int gameWidthSize = itemSize * columns;
        int startX = Math.abs((screenWidth - gameWidthSize) / 2);
        int startY = 0;
        for(int count1 = 0; count1 < rows; count1++) {
            for(int count2 = 0; count2 < columns; count2++) {
                Rect rect = new Rect(startX, startY, startX + itemSize, startY + itemSize);
                tmpRectArray.add(rect);
                startX += itemSize;
            }
            startX = Math.abs((screenWidth - gameWidthSize) / 2);
            startY += itemSize;
        }
        models.setScreenRectList(tmpRectArray);
    }

    synchronized private void createTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "createTetrimino()");
        }
        models.setMoveTetrimino(null);
        models.setMovePieceRect(null);
        models.setTetrimiCenter(-1);
        Random random = new Random();
        int tetriminoValue = random.nextInt(7);
        int direction = random.nextInt(4);
        models.setMoveTetrimino(TetriminoPiece.values()[tetriminoValue].tetrimino);
        models.setTetrimiCenter(78);
        List<Rect> tmpRectList = locateTetrimino(
                direction,
                models.getScreenRectList(),
                models.getStoredPieceLocationArray(),
                models.getColumns(),
                models.getMoveTetrimino(),
                models.getTetrimiCenter());
        if(null != tmpRectList) {
            models.setMovePieceRect(tmpRectList);
        }
    }

    private void downTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "downTetrimino()");
        }
        if(null == models.getMoveTetrimino()) {
            createTetrimino();
        }
        int direct = models.getMoveTetrimino().getDirect();
        List<Rect> tmpRectList = locateTetrimino(
                direct,
                models.getScreenRectList(),
                models.getStoredPieceLocationArray(),
                models.getColumns(),
                models.getMoveTetrimino(),
                models.getTetrimiCenter());
        if(null != tmpRectList) {
            models.setMovePieceRect(tmpRectList);
        }
        List<Rect> movePieceRect = models.getMovePieceRect();
        if(null == tmpRectList & null != movePieceRect) {
            judgeTetrimino();
        }
        if(null == tmpRectList & null == movePieceRect) {
            createTetrimino();
        }
    }

    private void fall2BottomTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "fall2BottomTetrimino()");
        }
        int weight = getWeightTetrimino();
        if(-1 == weight) {
            return;
        }
        int center = models.getTetrimiCenter();
        int columns = models.getColumns();
        center += weight * columns;
        models.setTetrimiCenter(center);
        downTetrimino();
    }

    private void turnTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "turnTetrimino()");
        }
        int columns = models.getColumns();
        int direct = models.getMoveTetrimino().getDirect();
        if(-1 == direct) {
            return;
        }
        direct++;
        if(4 <=direct) {
            direct = 0;
        }
        List<Point> pointList = models.getMoveTetrimino().getPointList(direct);
        int center = models.getTetrimiCenter();
        if(!((columns / 2) < (center % columns))) {
            if(!checkLeftTurn(pointList, center)) {
                return;
            }
        }
        if(!((center % columns) < (columns / 2))) {
            if(!checkRightTurn(pointList, center)) {
                return;
            }
        }
        List<Rect> tmpRectList = locateTetrimino(direct, models.getScreenRectList(),
                models.getStoredPieceLocationArray(), columns, models.getMoveTetrimino(), center);
        if(null != tmpRectList) {
            models.setMovePieceRect(tmpRectList);
        }
    }

    private void judgeTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "judgeTetrimino()");
        }
        int columns = models.getColumns();
        List<Integer> storedPieceLocationArray = models.getStoredPieceLocationArray();
        List<Integer> tmpList = new ArrayList<>();
        for(int count1 = 0; count1 < storedPieceLocationArray.size(); count1+= columns) {
            boolean flag = true;
            for(int count2 = 0; count2 < columns; count2++) {
                if(0 == storedPieceLocationArray.get(count1 + count2)) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                tmpList.add((count1 / columns));
            }
        }
        if(0 != tmpList.size()) {
            eraseRow(storedPieceLocationArray, columns, tmpList);
        }
        if(null != models.getMovePieceRect()) {
            createTetrimino();
        }
    }

    private void storeTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "storeTetrimino()");
        }
        int columns = models.getColumns();
        int rows = models.getRows();
        int tetriminoCenter = models.getTetrimiCenter();
        tetriminoCenter -= columns;
        List<Integer> absLocList =
                getTetriminoAbsLocate(models.getMoveTetrimino(), columns, tetriminoCenter);
        List<Integer> storedPieceLocationArray = models.getStoredPieceLocationArray();
        if(null == absLocList || 0 == absLocList.size()) {
            return;
        }
        boolean chkFlag = false;
        for(Integer i : absLocList) {
            if(i < 0 | columns * rows <= i) {
                chkFlag = true;
                break;
            }
        }
        if(chkFlag) {
            return;
        }
        for(Integer i : absLocList) {
            storedPieceLocationArray.set(i, 1);
        }
        models.setStoredPieceLocationArray(storedPieceLocationArray);
        models.setTetrimiCenter(0);
        models.setMoveTetrimino(null);
        models.setGohstRectMap(null);
    }

    private void displayGohstTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "displayGohstTetrimino()");
        }
        int weight = getWeightTetrimino();
        if(-1 == weight) {
            return;
        }
        int columns = models.getColumns();
        int tmpCenter = models.getTetrimiCenter() + ((weight -1) * columns);
        Map<Point, Rect> bottomDummyRect = locateGohstTetrimino(
                models.getScreenRectList(),
                models.getStoredPieceLocationArray(),
                columns,
                models.getMoveTetrimino(),
                tmpCenter);
        if(null == bottomDummyRect) {
            return;
        }
        models.setGohstRectMap(bottomDummyRect);
    }

    private int getWeightTetrimino() {
        if(DEBUG) {
            Log.d(TAG, "getWeightTetrimino()");
        }
        if(null == models.getMoveTetrimino() | null == models.getMovePieceRect()) {
            return -1;
        }
        int direct = models.getMoveTetrimino().getDirect();
        List<Point> pointList = models.getMoveTetrimino().getPointList(direct);
        int weight = getBottomTetrimino(
                models.getScreenRectList(),
                models.getStoredPieceLocationArray(),
                pointList,
                models.getColumns(),
                models.getTetrimiCenter());
        return weight;
    }

    private List<Integer> initStoredPieceLocationArray() {
        if(DEBUG) {
            Log.d(TAG, "initStoredPieceLocationArray()");
        }
        int columns = models.getColumns();
        int rows = models.getRows();
        List<Integer> list = new ArrayList<>();
        for(int count = 0; count < columns * rows; count++) {
            list.add(count, 0);
        }
        return list;
    }

    private List<Integer> getTetriminoAbsLocate(Tetrimino tetrimino, int column, int center) {
        if(DEBUG) {
            Log.d(TAG, "getTetriminoAbsLocate(" + tetrimino + ", " + column + ", " + center + ")");
        }
        if(null == tetrimino) {
            return null;
        }
        int direct = tetrimino.getDirect();
        List<Point> pointList = tetrimino.getPointList(direct);
        List<Integer> tmpList = new ArrayList<>();
        for(Point point : pointList) {
            tmpList.add(getLocNo(point, column, center));
        }
        return tmpList;
    }

    private Map<Point, Rect> locateGohstTetrimino(List<Rect> screenRectList,
                                                 List<Integer> storedPieceLocationArray,
                                                 int column, Tetrimino tetrimino, int center) {
        if(DEBUG) {
            Log.d(TAG, "locateGohsttetrimino(" + screenRectList + ", " +
                    storedPieceLocationArray + ", " + column + ", " + tetrimino + ", " + center + ")");
        }
        List<Point> tmpPointList = tetrimino.getPointList(tetrimino.getDirect());
        Map<Point, Rect> tmpRectMap = calcRect2PointMap(screenRectList, tmpPointList, column, center);
        return tmpRectMap;
    }

    private List<Rect> locateTetrimino(int direct, List<Rect> screenRectList,
                                      List<Integer> storedPieceLocationArray, int column, Tetrimino tetrimino, int center) {
        if(DEBUG) {
            Log.d(TAG, "locateTetrimino(" + direct + ", " + screenRectList + ", " +
                    column + ", " + ", " + ", " + tetrimino + ", " + center + ")");
        }
        List<Point> tmpPointList = tetrimino.getPointList(direct);
        List<Rect> tmpRectList = calcRect2Point(screenRectList, tmpPointList, column, center);
        if(!checkVerticalMove(screenRectList, storedPieceLocationArray, tmpRectList, tmpPointList, column, center)) {
            storeTetrimino();
            return null;
        }
        tetrimino.setDirect(direct);
        return tmpRectList;
    }

    private void eraseRow(List<Integer> storedPieceLocationList, int columnCount, List<Integer> rowCount) {
        if(DEBUG) {
            Log.d(TAG, "eraseRow(" + storedPieceLocationList + ", " + columnCount + ", " + rowCount + ")");
        }
        for(Integer i : rowCount) {
            for(int count = 0; count < columnCount; count++) {
                storedPieceLocationList.remove(((i * columnCount) + count));
                storedPieceLocationList.add(0, 0);
            }
        }
    }

    private boolean checkLeftTurn(List<Point> pointList, int center) {
        if(DEBUG) {
            Log.d(TAG, "checkLeftTurn(" + pointList + ", " + center + ")");
        }
        for(Point point : pointList) {
            if(11 <= ((center + point.x) % 12)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRightTurn(List<Point> pointList, int center) {
        if(DEBUG) {
            Log.d(TAG, "checkRightTurn(" + pointList + ", " + center + ")");
        }
        for(Point point : pointList) {
            if(((center + point.x) % 12) <= 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLeftMove(List<Integer> storedPieceLocationArray, List<Point> pointList, int center) {
        if(DEBUG) {
            Log.d(TAG, "checkLeftMove(" + storedPieceLocationArray + ", " + pointList + ", " + center + ")");
        }
        for(Point point : pointList) {
            if(0 == ((center + point.x) % 12)) {
                return false;
            }
        }
        Collections.sort(pointList, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return point.x - t1.x;
            }
        });
        if(0 != storedPieceLocationArray.get(center + pointList.get(0).x -1)) {
            return false;
        }
        return true;
    }

    private boolean checkRightMove(List<Integer> storedPieceLocationArray, List<Point> pointList, int center) {
        if(DEBUG) {
            Log.d(TAG, "checkRightMove(" + storedPieceLocationArray + ", " + pointList + ", " + center + ")x");
        }
        for(Point point : pointList) {
            if(11 == (center + point.x) % 12) {
                return false;
            }
        }
        Collections.sort(pointList, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return t1.x - point.x;
            }
        });
        if(0 != storedPieceLocationArray.get(center + pointList.get(0).x + 1)) {
            return false;
        }
        return true;
    }

    private boolean checkVerticalMove(List<Rect> screenRectList, List<Integer> storedPieceLocationArray,
                                      List<Rect> tmpRectList, List<Point> pointList, int column,
                                      int center) {
        if(DEBUG) {
            Log.d(TAG, "checkVerticalMove(" + screenRectList + ", " +
                    storedPieceLocationArray + ", " + tmpRectList + ", " + pointList + ", " +
                    column + ", " + center + ")");
        }
        if(null == tmpRectList) {
            return false;
        }
        if(null == pointList) {
            return false;
        }
        for(Point point : pointList) {
            if(screenRectList.size() -1 < center + (12 * point.y)) {
                return false;
            }
        }
        Collections.sort(pointList, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return t1.y - point.y;
            }
        });
        for(Point point : pointList) {
            if(0 != storedPieceLocationArray.get(center + point.x + (point.y * column))) {
                return false;
            }
        }
        return true;
    }

    private int getBottomTetrimino(List<Rect> screenRectList, List<Integer> storedListLocationArray,
                                  List<Point> pointList, int column, int center) {
        if(DEBUG) {
            Log.d(TAG, "getBottomTetrimino(" + screenRectList + ", " +
                    storedListLocationArray + ", " + pointList + ", " + column + ", " + center + ")");
        }
        Collections.sort(pointList, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return point.x - t1.x;
            }
        });
        List<Integer> weightList = new ArrayList<>();
        for(Point p : pointList) {
            int total = 0;
            for(int count = center + p.x + (p.y * column); count < screenRectList.size(); count += column) {
                if(0 != storedListLocationArray.get(count)) {
                    break;
                }
                total++;
            }
            weightList.add(total);
        }
        Collections.sort(weightList, new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return integer - t1;
            }
        });
        return weightList.get(0);
    }

    private Map<Point, Rect> calcRect2PointMap(List<Rect> screenRectList, List<Point> pointList,
                                               int column, int center) {
        if(DEBUG) {
            Log.d(TAG, "calcRect2PointMap(" + screenRectList + ", " + pointList + ", " +
                    column + ", " + center + ")");
        }
        Map<Point, Rect> tmpMap = new HashMap<>();
        for(Point point : pointList) {
            int locNo = getLocNo(point, column, center);
            if(0 <= locNo & locNo < screenRectList.size()) {
                tmpMap.put(point, screenRectList.get(locNo));
            }
        }
        return tmpMap;
    }

    private List<Rect> calcRect2Point(List<Rect> screenRectList, List<Point> pointList, int column,
                                      int center) {
        if(DEBUG) {
            Log.d(TAG, "calcrect2Point(" + screenRectList + ", " + pointList + ", " +
                    column + ", " + center + ")");
        }
        if(null == pointList) {
            return null;
        }
        List<Rect> tmpList = new ArrayList<>();
        int locNo = -1;
        for(Point point : pointList) {
            locNo = getLocNo(point,  column, center);
            if(0 <= locNo & locNo < screenRectList.size()) {
                tmpList.add(screenRectList.get(locNo));
            }
        }
        return tmpList;
    }

    private int getLocNo(Point point, int column, int center) {
        if(DEBUG) {
            Log.d(TAG, "getLocNo(" + point + ", " + column + ", " + center + ")");
        }
        int locNo = -1;
        if(-2 == point.y) {
            locNo = center - (column * 2) + point.x;
        }
        if(-1 == point.y) {
            locNo = center - column + point.x;
        }
        if(0 == point.y) {
            locNo = center + point.x;
        }
        if(1 == point.y) {
            locNo = center + column + point.x;
        }
        if(2 == point.y) {
            locNo = center + (column * 2) + point.x;
        }
        return locNo;
    }

    private void initModels(Context context) {
        if(DEBUG) {
            Log.d(TAG, "initModels(" + context + ")");
        }

        int columns = Constants.GAME_COLUMNS;
        int rows = Constants.GAME_ROWS;
        Resources resources = context.getResources();
        int statusBarHeight = calculateStatusBarHeight(context);
        int screenTop = 0;
        int screenHeight = resources.getDisplayMetrics().heightPixels - statusBarHeight - screenTop;
        int gameScreenHeight = (int)(screenHeight * Constants.SCREEN_HEIGHT_RATIO);

        int itemSize = (int)((float)gameScreenHeight / (float)rows);
        if(0 == itemSize % 2) {
            itemSize += 1;
        }
        screenTop = itemSize * 6;

        models.setStoredPieceLocationArray(initStoredPieceLocationArray());
        models.setGohstRectMap(new HashMap<Point, Rect>());
        models.setBlockArea(new int[columns * rows]);
        models.setColumns(columns);
        models.setRows(rows);
        models.setScreenWidth(resources.getDisplayMetrics().widthPixels);
        models.setStatusBarHeight(statusBarHeight);
        models.setGameScreenHeight(gameScreenHeight);
        models.setItemSize(itemSize);
        models.setScreenTop(screenTop);
        models.setScreenHeight(screenHeight);
        models.setStoredPieceLocationArray(initStoredPieceLocationArray());
        models.setButton1(createButton1Rect());
        models.setButton2(createButton2Rect());
        models.setControllerRectTopBottom(createTopBottomControllerRect());
        models.setControllerRectLeftRight(createLeftRightControllerRect());
        models.setPaint(PaintCreator.getInstance().getGridPaint());
        models.setBlockPaint(PaintCreator.getInstance().getBlockPaint());
        models.setStoredPaint(PaintCreator.getInstance().getStorePaint());
        models.setGohstPaint(PaintCreator.getInstance().getGohstPaint());
    }

    private int calculateStatusBarHeight(Context context) {
        if(DEBUG) {
            Log.d(TAG, "calculateStatusBarHeight(" + context + ")");
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if(0 < resourceId) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private Rect createButton1Rect() {
        if(DEBUG) {
            Log.d(TAG, "createButton1Rect()");
        }
        int itemSize = models.getItemSize();
        int button1Top = models.getGameScreenHeight() + models.getStatusBarHeight() + (itemSize * 3);
        int button1Left = (models.getScreenWidth() / 4) * 3;
        return new Rect(button1Left, button1Top, button1Left + (itemSize * 2),
                button1Top + (itemSize * 2));
    }

    private Rect createButton2Rect() {
        if(DEBUG) {
            Log.d(TAG, "createButton2Rect()");
        }
        int itemSize = models.getItemSize();
        int button2Top = models.getGameScreenHeight() + models.getStatusBarHeight() + (itemSize * 3);
        int button2Left = (models.getScreenWidth() / 4) * 3 - (itemSize * 3);
        return new Rect(button2Left, button2Top, button2Left + (itemSize * 2),
                button2Top + (itemSize * 2));
    }

    private Rect createLeftRightControllerRect() {
        if(DEBUG) {
            Log.d(TAG, "createLeftRightControllerRect()");
        }
        int itemSize = models.getItemSize();
        int controllerVLeft = models.getScreenWidth() / 4;
        int controllerVTop = models.getGameScreenHeight() + models.getStatusBarHeight() + itemSize;
        int controllerHLeft = controllerVLeft - (itemSize * 2);
        int controllerHTop = controllerVTop + (itemSize * 2);
        int controllerHRight = controllerHLeft + (itemSize * 6);
        int controllerHBottom = controllerHTop + (itemSize * 2);
        return new Rect(controllerHLeft, controllerHTop, controllerHRight, controllerHBottom);
    }

    private Rect createTopBottomControllerRect() {
        if(DEBUG) {
            Log.d(TAG, "createTopBottomControllerRect()");
        }
        int itemSize = models.getItemSize();
        int controllerVLeft = models.getScreenWidth() / 4;
        int controllerVTop = models.getGameScreenHeight() + models.getStatusBarHeight() + itemSize;
        int controllerVRight = controllerVLeft + (itemSize * 2);
        int controllerVBottom = controllerVTop + (itemSize * 6);
        return new Rect(controllerVLeft, controllerVTop, controllerVRight, controllerVBottom);
    }

}
