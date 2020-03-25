package AI;

import Checkers.Game;
import Checkers.Piece;

public class Evaluation {

    public static int Score(Game game){
        int numberOfWhitePieces = game.getWhitePlayer().getAllPieces().size();
        int numberOfBlackPieces = game.getBlackPlayer().getAllPieces().size();
        return numberOfWhitePieces - numberOfBlackPieces;

    }

}
