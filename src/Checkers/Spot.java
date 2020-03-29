package Checkers;

import cz.gyarab.util.light.LightColor;

import java.io.Serializable;

class Spot implements Serializable {
    private final int x;
    private final int y;
    Piece piece;

    Spot(int x, int y){
        this.x = x;
        this.y = y;
        this.piece = null;
    }


    Piece getPiece() {
        return piece;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
