package Checkers;

import cz.gyarab.util.light.LightColor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private boolean isWhite;
    private Color color;
    private final int numberOfPieces;
    private List<Piece> allPieces;
     List<Move> allLegalMoves;

    Player(boolean isWhite){
        this.allLegalMoves = new ArrayList<>();
        this.isWhite = isWhite;
        if(isWhite){
            this.color = Color.WHITE;
        }else {
            this.color = Color.BLACK;
        }

        // player pieces init
        this.numberOfPieces = 12;
        this.allPieces = new ArrayList<>();


    }
    void initPieces(Game game){
        if(isWhite){
            for (int row = 0; row < this.numberOfPieces/4; row++) {
                for (int column = row % 2; column < game.board.getWidth(); column += 2) {
                    Piece piece = new Piece(column, row, true);
                    allPieces.add(piece);
                    game.board.addPieceToSpot(column,row,piece);
                }
            }
        }else {
            for (int row = game.board.getHeight() - 1; row >= game.board.getHeight() - numberOfPieces / 4; row--) {
                for (int column = row % 2; column < game.board.getWidth(); column += 2) {
                    Piece piece = new Piece(column, row, false);
                    allPieces.add(piece);
                    game.board.addPieceToSpot(column,row,piece);
                }
            }
        }
    }

    public boolean isWhite() {
        return isWhite;
    }

    public List<Piece> getAllPieces() {
        return allPieces;
    }

    Color getColor() {
        return color;
    }
    Piece getPiece(int x, int y){
        for (Piece piece:allPieces) {
            if(piece.getX()==x&&piece.getY()==y){
                return piece;
            }
        }
        return null;
    }
    List<Move> getLegalMovesOfPiece(Piece piece){
        List<Move> allLegalMovesOfPiece = new ArrayList<>();
        for (Move move: allLegalMoves) {
            if(move.xFrom==piece.getX()&&move.yFrom==piece.getY()){
                allLegalMovesOfPiece.add(move);
            }
        }
        return allLegalMovesOfPiece;
    }
    static void removePieceFromOpponent(int x, int y, Game game){
            Player Opponent = Player.getOpponent(game);

            for (int i = 0; i < Opponent.allPieces.size();
            i++){
                if (Opponent.allPieces.get(i).getX() == x && Opponent.allPieces.get(i).getY() == y) {
                    Opponent.allPieces.remove(Opponent.allPieces.get(i));
                    return;
                }
            }

        System.out.println("X: "+x+" \nY: "+y+"\n Error not Found");

    }
    void setQueen(int x, int y){
        for (Piece piece:allPieces) {
            if(piece.getX()==x&&piece.getY()==y){
               piece.setQueen();
            }
        }
    }


    public List<Move> getAllLegalMoves() {
        return allLegalMoves;
    }

    public void setAllLegalMoves(List<Move> allLegalMoves) {
        this.allLegalMoves = allLegalMoves;
    }


    static Player getOpponent(Game game){
        if(game.currentPlayer.isWhite){
           return game.blackPlayer;
        }else {
           return game.whitePlayer;

        }
    }

}
