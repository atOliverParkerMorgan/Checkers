package Checkers;

import cz.gyarab.util.light.LightColor;

import java.io.Serializable;

public class Piece implements Serializable {
    private int x;
    private int y;
    private LightColor color;

    private boolean isQueen;

    Piece(int x, int y, LightColor color){
        this.isQueen = false;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
    void setXY(Move move){
        x = move.xTo;
        y = move.yTo;
    }

    void setQueen(){
        this.isQueen = true;
    }

    boolean isQueen() {
        return isQueen;
    }

    LightColor getColor() {
        return color;
    }



}
