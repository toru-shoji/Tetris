package com.toro_studio.Tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.toro_studio.Tetris.models.Constants;
import com.toro_studio.Tetris.presenter.IGamePresetner;
import com.toro_studio.Tetris.presenter.TetrisPresenter;
import com.toro_studio.Tetris.views.IGameViews;

import java.util.Timer;
import java.util.TimerTask;

import static com.toro_studio.Tetris.models.Constants.DEBUG;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Log.d(TAG, "onCreate(" + savedInstanceState + ")");
        }
        setContentView(new GameSurfaceView(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DEBUG) {
            Log.d(TAG, "onStart()");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) {
            Log.d(TAG, "onResume()");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(DEBUG) {
            Log.d(TAG, "onPause()");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(DEBUG) {
            Log.d(TAG, "onStop()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(DEBUG) {
            Log.d(TAG, "onDestroy()");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(DEBUG) {
            Log.d(TAG, "onRestart()");
        }
    }

    public static class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable,
            View.OnTouchListener, IGameViews {

        private static final String TAG = "GameSurfaceView";

        private IGamePresetner presenter;

        private int fps = Constants.GAME_FPS;

        private boolean mIsAttached;
        private SurfaceHolder mHolder;
        private Thread mThread;
        private long mTime1;
        private long mTime2;

        public GameSurfaceView(Context context) {
            super(context);
            if(DEBUG) {
                Log.d(TAG, "MainSurfaceView(" + context + ")");
            }
            initGameSurfaceView();
        }

        public GameSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
            if(DEBUG) {
                Log.d(TAG, "MainSurfaceView(" + context + ", " + attrs + ")");
            }
            initGameSurfaceView();
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if(DEBUG) {
                Log.d(TAG, "surfaceCreated(" + surfaceHolder + ")");
            }
            setFocusable(true);
            requestFocus();
            presenter.initGameScreenInfo();
            mThread = new Thread(this);
            mThread.start();
            timerStart();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            if(DEBUG) {
                Log.d(TAG, "surfaceChanged(" + surfaceHolder + ", " + i + ", " + i1 + ", " + i2 + ")");
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if(DEBUG) {
                Log.d(TAG, "surfaceDestroyed(" + surfaceHolder + ")");
            }
            mThread = null;
            mIsAttached = false;
        }

        @Override
        public void run() {
            if(DEBUG) {
                Log.d(TAG, "run()");
            }
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
            if(DEBUG) {
                Log.d(TAG, "onTouch(" + v + ", " + event + ")");
            }
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                presenter.touchControllerOrButton(x, y);
            }
            return false;
        }

        private void initGameSurfaceView() {
            if(DEBUG) {
                Log.d(TAG, "initGameSurfaceView()");
            }
            setFocusable(true);
            setZOrderOnTop(true);
            mIsAttached = true;
            mHolder = getHolder();
            mHolder.addCallback(this);
            setOnTouchListener(this);
            presenter = new TetrisPresenter(this);
        }

        private void timerStart() {
            if(DEBUG) {
                Log.d(TAG, "timerStart()");
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    presenter.fulfillRoleGame();
                }
            }, 1000, 1000);
        }

    }

}