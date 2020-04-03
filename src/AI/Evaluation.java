package AI;

import Checkers.Game;
import Checkers.Piece;

class Evaluation{
    private final static int PieceBias = 10;
    private final static int QueenBias = 40;
    private final static int EndOfBoardPosition = 2;
    private final static int EndGameScenario = 99999;


    static int Score(Game game, int depth){
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
            if(game.isGameHasEnded() && game.getCurrentPlayer().isWhite()){
                whiteScore+=EndGameScenario+10000*depth;
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
            if(game.isGameHasEnded() && !game.getCurrentPlayer().isWhite()){
                blackScore+=EndGameScenario+100000*depth;
            }

        }


        return whiteScore - blackScore;

    }

}
