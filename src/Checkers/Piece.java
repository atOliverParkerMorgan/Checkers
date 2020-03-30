package Checkers;

import java.io.Serializable;

public class Piece implements Serializable {
    private int x;
    private int y;
    private boolean isWhite;

    private boolean isQueen;

    Piece(int x, int y, boolean isWhite){
        this.isQueen = false;
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    }

    public int getX() {
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

    public boolean isQueen() {
        return isQueen;
    }

    boolean isWhite() {
        return isWhite;
    }



}
