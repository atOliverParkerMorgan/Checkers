package Checkers;

import cz.gyarab.util.light.LightColor;

import java.io.Serializable;

class Board implements Serializable {
    Spot[][] board;
    private int width;
    private int height;
    Board(int width, int height){
        this.width = width;
        this.height = height;

        this.board = new Spot[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.board[i][j] = new Spot(i,j);
            }
        }
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }
    LightColor getColor(int x, int y){
        return getSpot(x,y).getPieceColor();
    }
    private Spot getSpot(int x, int y){
        return board[x][y];
    }
    boolean isOn(int x, int y){
        return getSpot(x, y).getPiece()!=null;
    }
    boolean isOff(int x, int y){
        return getSpot(x, y).getPiece()==null;
    }
    void addPieceToSpot(int x, int y, Piece piece){
        board[x][y].piece = piece;
    }

    void nullOutPieces(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.board[i][j].piece = null;
            }
        }


    }




}
