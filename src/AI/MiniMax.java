package AI;

import Checkers.Game;
import Checkers.Main;
import Checkers.Move;
import Checkers.Player;
import cz.gyarab.util.light.LightColor;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// this code is inspired by MY chessAI engine (https://github.com/atOliverParkerMorgan/Chess_AI/blob/master/src/Game/AI/MiniMax.java)
// (which I created )
// why reinvent the wheel, when I've already put in the effort to build my own MiniMax algorithm

public final class MiniMax {
    private int searchDepth;
    private List<Move> moveAddedAfterJumpedOver;
    private List<List<Move>> allMovesJumpedOver;

    public MiniMax(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public Move getBestMove(final Game game) {
        Game simulatingGame;
        Move bestMove = null;
        double currentValue;

        //int numMoves = 100 / game.getCurrentPlayer().getAllLegalMoves().size();

        // these values have to be changed during the first miniMax loop
        double highestSeenValue = Double.MIN_VALUE;

        double lowestSeenValues = Double.MAX_VALUE;
        Player player = game.getCurrentPlayer();

        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;

        for (Move move : player.getAllLegalMoves()) {
            simulatingGame = game.copy();
            currentValue = player.isWhite() ?
                    max(simulatingGame, this.searchDepth, alpha, beta, !move.hasTaken) :
                    min(simulatingGame, this.searchDepth, alpha, beta, !move.hasTaken);

            // white is max black is min
            if(currentValue>highestSeenValue && player.isWhite()){
                highestSeenValue = currentValue;
                bestMove = move;

            }else if(currentValue<lowestSeenValues && !player.isWhite()){
                lowestSeenValues = currentValue;
                bestMove = move;
            }

        }

        return bestMove;
    }

    private double max(Game game, final int depth, double alpha, double beta, boolean switchMinMax) {
        if (depth == 0 || game.isGameHasEnded()) {
            return Evaluation.Score(game);
        }
        double highestSeenValue = Double.MIN_VALUE;
        Game original = game.copy();
        System.out.println(original.getCurrentPlayer().isWhite());
        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            System.out.println("Move: "+ move);
            GameAndFollowUpMove gameAndFollowUpMove = Main.getGameAfterMove(move);
            Game newGame = gameAndFollowUpMove.game;
            final double currentValue;
            if(gameAndFollowUpMove.move!=null){
                newGame.getCurrentPlayer().setAllLegalMoves(gameAndFollowUpMove.move);
                Main.getLegalMoves(newGame.getCurrentPlayer().isWhite(),true, game);
                currentValue = max(newGame, depth - 1, alpha, beta, !move.hasTaken);
            }
           else {
                newGame.setCurrentPlayer(newGame.getBlackPlayer());
                newGame.getCurrentPlayer().setAllLegalMoves(Main.getLegalMoves(newGame.getCurrentPlayer().isWhite(), false, game));
                currentValue = min(newGame, depth - 1, alpha, beta, !move.hasTaken);

            }
            highestSeenValue = Math.max(currentValue, highestSeenValue);
            alpha = Math.max(alpha, highestSeenValue);

            if (beta <= alpha) {
                break;
            }

        }


        return highestSeenValue;
    }

    private double min(Game game, final int depth, double alpha, double beta, boolean switchMinMax) {
        if (depth == 0 || game.isGameHasEnded()) {
            return Evaluation.Score(game);
        }

        double lowestSeenValue = Double.MAX_VALUE;
        Game original = game.copy();

        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            GameAndFollowUpMove gameAndFollowUpMove = Main.getGameAfterMove(move);
            Game newGame = gameAndFollowUpMove.game;
            final double currentValue;
            if(gameAndFollowUpMove.move!=null){
                newGame.getCurrentPlayer().setAllLegalMoves(gameAndFollowUpMove.move);
                Main.getLegalMoves(newGame.getCurrentPlayer().isWhite(),true, game);
                currentValue = min(newGame, depth - 1, alpha, beta, !move.hasTaken);
            }
            else {
                newGame.setCurrentPlayer(newGame.getWhitePlayer());
                newGame.getCurrentPlayer().setAllLegalMoves(Main.getLegalMoves(newGame.getCurrentPlayer().isWhite(),false, game));
                System.out.println(newGame.getCurrentPlayer().getAllLegalMoves());
                currentValue = max(newGame, depth - 1, alpha, beta, !move.hasTaken);
            }

            lowestSeenValue = Math.min(currentValue, lowestSeenValue);
            beta = Math.min(beta, lowestSeenValue);
            if (beta <= alpha) {
                break;
            }

        }
        return lowestSeenValue;
    }



//private List<Game> getAllEndingMoves(Game game, List<Move> followUpMoves){
//   returnEndOfMoveTree(game, followUpMoves);
//   List<Game> filteredGames = new ArrayList<>();

//    for (List<Move> moveList : allMovesJumpedOver) {
//        if(moveList!=null){
//            filteredGames.add(gameTreeSaver.savedGame);
//        }
//    }

//    return filteredGames;

//}

//private Move returnEndOfMoveTree(Game game, List<Move> followUpMove){
//    for (Move move:followUpMove) {

//        Game simulatingGame = game.copy();
//        List<Move> newFollowUpMoves = Main.move(move, simulatingGame);
//        if (newFollowUpMoves != null) {
//            returnEndOfMoveTree(simulatingGame, newFollowUpMoves);
//        } else {
//            allMovesJumpedOver.add(moveAddedAfterJumpedOver);
//            moveAddedAfterJumpedOver = new ArrayList<>();
//            return move;
//        }
//    }


//    return null;


//}


}
