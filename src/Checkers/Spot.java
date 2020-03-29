package Checkers;

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

    int getY() {
        return y;
    }

    int getX() {
        return x;
    }
}
