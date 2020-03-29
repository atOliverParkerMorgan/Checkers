package Checkers;

import cz.gyarab.util.light.LightColor;

import java.io.*;

public class Board implements Serializable {
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
        Piece piece = getSpot(x,y).getPiece();
        if(piece==null){
            return null;
        }else {
            if(piece.isWhite()){
                return LightColor.CHESSBOARD_WHITE;
            }else {
                return LightColor.CHESSBOARD_BLACK;
            }
        }
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

    public void printOut() {
        StringBuilder charBoard = new StringBuilder();

        for (int i = 7; i >= 0; i--) {
            charBoard.append(i+1).append(" ");
            for (int j = 0; j < 8; j++) {

                if (board[j][i].piece != null) {
                    if (board[j][i].piece.isWhite()){
                        char piece = board[j][i].piece.isQueen() ? '\u26C3' : '\u26C2';
                        charBoard.append(piece);
                    } else {
                        char piece = board[j][i].piece.isQueen() ? '\u26C1' : '\u26C0';
                        charBoard.append(piece);
                    }
                } else {
                    if ((board[j][i].getX() + board[j][i].getY() )% 2 == 0){
                        charBoard.append('\u2B1C');
                    } else {
                        charBoard.append('\u2B1B');
                    }



                }
                charBoard.append(' ');

            }

            charBoard.append('\n');

        }


        System.out.println("-----------------------");
        System.out.println(charBoard.toString());
    }

    public Spot[][] getBoard() {
        return board;
    }
}
