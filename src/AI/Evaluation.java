package AI;

import Checkers.Game;
import Checkers.Piece;

class Evaluation {
    private final static int PieceBias = 10;
    private final static int QueenBias = 40;
    private final static int EndOfBoardPosition = 2;


    static int Score(Game game){
        int whiteScore = 0;
        int blackScore = 0;
        for (Piece piece: game.getWhitePlayer().getAllPieces()) {
            if(piece.isQueen()){
                whiteScore+=QueenBias;
            }else {
                whiteScore+=PieceBias;
            }
            if(piece.getX()==0||piece.getX()==7){
                whiteScore+=EndOfBoardPosition;
            }

        }

        for (Piece piece: game.getBlackPlayer().getAllPieces()) {
            if(piece.isQueen()){
                blackScore+=QueenBias;
            }else {
                blackScore+=PieceBias;
            }
            if(piece.getX()==0||piece.getX()==7){
                blackScore+=EndOfBoardPosition;
            }

        }


        return whiteScore - blackScore;

    }

}
