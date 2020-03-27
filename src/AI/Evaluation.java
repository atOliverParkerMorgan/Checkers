package AI;

import Checkers.Game;
import Checkers.Piece;

public class Evaluation {
    public final static int PieceBias = 10;
    public final static int QueenBias = 40;


    public static int Score(Game game){
        int numberOfWhitePieces = 0;
        int numberOfBlackPieces = 0;
        for (Piece piece: game.getWhitePlayer().getAllPieces()) {
            if(piece.isQueen()){
                numberOfWhitePieces+=QueenBias;
            }else {
                numberOfWhitePieces+=PieceBias;
            }
        }

        for (Piece piece: game.getBlackPlayer().getAllPieces()) {
            if(piece.isQueen()){
                numberOfBlackPieces+=QueenBias;
            }else {
                numberOfBlackPieces+=PieceBias;
            }
        }


        return numberOfWhitePieces - numberOfBlackPieces;

    }

}
