package com.toro_studio.Tetris.models;

public enum TetriminoPiece {

    TetrimonoI(new TetriminoI()),
    TetriminoO(new TetriminoO()),
    TetriminoS(new TetriminoS()),
    TetriminoZ(new TetriminoZ()),
    TetriminoJ(new TetriminoJ()),
    TetriminoL(new TetriminoL()),
    TetriminoT(new TetriminoT());

    public Tetrimino tetrimino;

    TetriminoPiece(Tetrimino tetrimino) {
        this.tetrimino = tetrimino;
    }

}
