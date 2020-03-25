import javafx.scene.effect.Light;

class Piece {
    private int x;
    private int y;
    private Color color;
    private boolean isQueen;

    Piece(int x, int y, boolean isWhite){
        this.isQueen = false;
        this.x = x;
        this.y = y;
        this.color = isWhite ? Color.WHITE: Color.BLACK;
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

    public boolean isQueen() {
        return isQueen;
    }
}
