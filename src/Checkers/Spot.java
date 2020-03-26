package Checkers;

import cz.gyarab.util.light.LightColor;

import java.io.Serializable;

public class Spot implements Serializable {
    final int x;
    final int y;
    Piece piece;

    Spot(int x, int y){
        this.x = x;
        this.y = y;
        this.piece = null;
    }
    Spot(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    LightColor getPieceColor(){
        if(piece==null){
            return null;
        }else {
            return piece.getColor();
        }

    }

    Piece getPiece() {
        return piece;
    }
}
