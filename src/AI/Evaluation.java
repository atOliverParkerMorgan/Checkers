package AI;

import Checkers.Game;
import Checkers.Piece;

class Evaluation {
    private final static int PieceBias = 10;
    private final static int QueenBias = 40;


    static int Score(Game game){
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
