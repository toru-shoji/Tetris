package com.toro_studio.Tetris.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.toro_studio.Tetris.entities.Constants;
import com.toro_studio.Tetris.constraints.IGamePresetner;
import com.toro_studio.Tetris.presenters.TetrisPresenter;
import com.toro_studio.Tetris.constraints.IGameViews;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurfaceView(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public static class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable,
            View.OnTouchListener, IGameViews {

        private IGamePresetner presenter;

        private int fps = Constants.GAME_FPS;

        private boolean mIsAttached;
        private SurfaceHolder mHolder;
        private Thread mThread;
        private long mTime1;
        private long mTime2;

        public GameSurfaceView(Context context) {
            super(context);
            initGameSurfaceView();
        }

        public GameSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initGameSurfaceView();
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            setFocusable(true);
            requestFocus();
            presenter.initGameScreenInfo();
            mThread = new Thread(this);
            mThread.start();
            timerStart();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mThread = null;
            mIsAttached = false;
        }

        @Override
        public void run() {
            while(mIsAttached) {
                mTime1 = System.currentTimeMillis();
                Canvas canvas = mHolder.lockCanvas();
                if(null == canvas) {
                    return;
                }
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                presenter.drawGameScreen(canvas);
                mHolder.unlockCanvasAndPost(canvas);
                mTime2 = System.currentTimeMillis();
                if(mTime2 - mTime1 < fps){
                    try {
                        Thread.sleep(fps - (mTime2 - mTime1));
                    } catch (InterruptedException e) {}
                }
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                presenter.touchControllerOrButton(x, y);
            }
            return false;
        }

        private void initGameSurfaceView() {
            setFocusable(true);
            setZOrderOnTop(true);
            mIsAttached = true;
            mHolder = getHolder();
            mHolder.addCallback(this);
            setOnTouchListener(this);
            presenter = new TetrisPresenter(this);
        }

        private void timerStart() {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    presenter.fulfillRoleGame();
                }
            }, 1000, 1000);
        }

    }

}