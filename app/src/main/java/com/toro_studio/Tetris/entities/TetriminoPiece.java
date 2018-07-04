package com.toro_studio.Tetris.entities;

import com.toro_studio.Tetris.constraints.ITetrimino;

public enum TetriminoPiece {

    TetrimonoI(new TetriminoI()),
    TetriminoO(new TetriminoO()),
    TetriminoS(new TetriminoS()),
    TetriminoZ(new TetriminoZ()),
    TetriminoJ(new TetriminoJ()),
    TetriminoL(new TetriminoL()),
    TetriminoT(new TetriminoT());

    public ITetrimino tetrimino;

    TetriminoPiece(ITetrimino tetrimino) {
        this.tetrimino = tetrimino;
    }

}
