package Checkers;

public class Piece {
    private int x;
    private int y;

    private boolean isQueen;

    Piece(int x, int y){
        this.isQueen = false;
        this.x = x;
        this.y = y;
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
}
