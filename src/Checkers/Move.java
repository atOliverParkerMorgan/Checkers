package Checkers;

public final class Move {
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
    public Move(int xFrom, int yFrom){
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = -1;
        this.yTo = -1;
    }
  // boolean isDouble(){

  //     return Math.abs(xTo - xFrom)==2 && Math.abs(yTo - yFrom)==2;
  // }

    void setFromPosition(Move move){
        this.xFrom = move.xFrom;
        this.yFrom = move.yFrom;
    }
}
