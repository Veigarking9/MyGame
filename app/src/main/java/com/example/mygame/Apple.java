package com.example.mygame;

import android.graphics.Color;

public class Apple {
    private int color;
    private Position Position;

    public Apple(Position pos){
        color = Color.argb(255,255,0,0);
        this.Position = pos;
    }

    public int getAplColor() {
        return color;
    }

    public void setAplPosition(Position pos) {
        this.Position = pos;
    }

    public Position getAplPosition() {
        return Position;
    }

}
