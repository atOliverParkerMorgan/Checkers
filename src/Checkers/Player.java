package Checkers;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private boolean isWhite;
    private Color color;
    private final int numberOfPieces;
    private Player Opponent;
    private List<Piece> allPieces;
     List<Move> allLegalMoves;

    Player(boolean isWhite){
        this.Opponent = null;
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
                for (int column = row % 2; column < game.matrix.getWidth(); column += 2) {
                    allPieces.add(new Piece(column, row));
                }
            }
        }else {
            for (int row = game.matrix.getHeight() - 1; row >= game.matrix.getHeight() - numberOfPieces / 4; row--) {
                for (int column = row % 2; column < game.matrix.getWidth(); column += 2) {
                    allPieces.add(new Piece(column, row));
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
    void removePieceFromOpponent(int x, int y){
        for (int i = 0; i < Opponent.allPieces.size(); i++) {
            if(Opponent.allPieces.get(i).getX()==x && Opponent.allPieces.get(i).getY()==y){
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

    void setOpponent(Player opponent) {
        Opponent = opponent;
    }

    Player getOpponent(){
        return Opponent;
    }

    public List<Move> getAllLegalMoves() {
        return allLegalMoves;
    }

    //public void setAllLegalMoves(List<Move> allLegalMoves) {
    //    this.allLegalMoves = allLegalMoves;
    //}
}
