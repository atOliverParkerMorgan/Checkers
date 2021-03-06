package Checkers;

import java.io.Serializable;

public final class Move implements Serializable {
    int xFrom;
    int yFrom;
    int xTo;
    int yTo;
    boolean hasTaken;

    Move(int xFrom, int yFrom, int xTo, int yTo, boolean hasTaken){
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
        this.hasTaken = hasTaken;
    }
    Move(int xFrom, int yFrom){
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = -1;
        this.yTo = -1;
    }

    void setFromPosition(Move move){
        this.xFrom = move.xFrom;
        this.yFrom = move.yFrom;
    }
}
