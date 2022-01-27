package com.example.mygame;

import android.graphics.Color;

import com.google.type.PostalAddressOrBuilder;

public class Snake {
    private int color;
    private Position position;
    private int tamaño = 1;

    public Snake(Position pos) {
        color = android.graphics.Color.argb(255,255,0,255);
        this.position = pos;
    }

    public int getColor() {
        return color;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    public Position getPosition() {
        return position;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        tamaño = tamaño;
    }
}
