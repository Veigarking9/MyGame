package com.example.mygame;

public class Position {
    private int PosX;
    private int PosY;

    public int getPosX() {
        return PosX;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }

    public Position(int Wide, int High) {
        PosX = Wide;
        PosY = High;
    }
}
