package AI;

import Checkers.Game;
import Checkers.Main;
import Checkers.Move;
import Checkers.Player;

import java.util.List;

public final class MiniMax {
    private int searchDepth;

    public MiniMax(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public Move getBestMove(final Game game) {
        Game simulatingGame;
        Move bestMove = null;
        double currentValue;

        int numMoves = 100 / game.getCurrentPlayer().getAllLegalMoves().size();

        // these values have to be changed during the first miniMax loop
        double highestSeenValue = Integer.MIN_VALUE;
        double lowestSeenValues = Integer.MAX_VALUE;

        Player player = game.getCurrentPlayer();

        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;

        for (final Move move : player.getAllLegalMoves()) {
            simulatingGame = game.copy();
            currentValue = simulatingGame.getCurrentPlayer().isWhite()?
                    max(simulatingGame,this.searchDepth, alpha, beta):
                    min(simulatingGame,this.searchDepth,alpha,beta);

            // white is max black is min
            if(currentValue>highestSeenValue && simulatingGame.getCurrentPlayer().isWhite()){
                highestSeenValue = currentValue;
                bestMove = move;

            }else if(currentValue<lowestSeenValues && !simulatingGame.getCurrentPlayer().isWhite()){
                lowestSeenValues = currentValue;
                bestMove = move;
            }



        }

        return bestMove;
    }

    private double max(Game game, final int depth, double alpha, double beta) {
        if (depth == 0) {
            return Evaluation.Score(game);
        }
        double highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();

        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {

            Game newGame = Main.getGameAfterMove(move);
            // this.timeMove+= System.currentTimeMillis() - starTime;

            final double currentValue = min(newGame, depth - 1, alpha, beta);

            highestSeenValue = Math.max(currentValue, highestSeenValue);
            alpha = Math.max(alpha, highestSeenValue);

            if (beta <= alpha) {
                break;
            }

        }


        return highestSeenValue;
    }

    private double min(Game game, final int depth, double alpha, double beta) {
        if (depth == 0) {
            return Evaluation.Score(game);
        }

        double lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame = Main.getGameAfterMove(move);
            // this.timeMove+= System.currentTimeMillis() - starTime;

            final double currentValue = max(newGame, depth - 1, alpha, beta);

            lowestSeenValue = Math.min(currentValue, lowestSeenValue);
            beta = Math.min(beta, lowestSeenValue);
            if (beta <= alpha) {
                break;
            }

        }
        return lowestSeenValue;
    }
}
