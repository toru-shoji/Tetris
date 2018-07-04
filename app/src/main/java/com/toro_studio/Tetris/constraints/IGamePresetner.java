package com.toro_studio.Tetris.constraints;

import android.graphics.Canvas;

public interface IGamePresetner {
    void initGameScreenInfo();
    void drawGameScreen(Canvas canvas);
    void touchControllerOrButton(int x, int y);
    void fulfillRoleGame();
}
