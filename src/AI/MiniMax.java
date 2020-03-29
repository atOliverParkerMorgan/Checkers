package AI;

import Checkers.Game;
import Checkers.Main;
import Checkers.Move;
import Checkers.Player;

// this code is inspired by MY chessAI engine (https://github.com/atOliverParkerMorgan/Chess_AI/blob/master/src/Game/AI/MiniMax.java)
// (which I created )
// why reinvent the wheel, when I've already put in the effort to build my own MiniMax algorithm

public final class MiniMax {
    private int searchDepth;

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
        Player player =game.getCurrentPlayer();

        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;
        System.out.println("Before: "+game.getCurrentPlayer().isWhite());

        for (Move move : player.getAllLegalMoves()) {
            simulatingGame = Game.copy(game);
            currentValue = player.isWhite() ?
                    max(simulatingGame, this.searchDepth, alpha, beta) :
                    min(simulatingGame, this.searchDepth, alpha, beta);

            // white is max black is min
            if(currentValue>highestSeenValue && player.isWhite()){
                highestSeenValue = currentValue;
                bestMove = move;


            }else if(currentValue<lowestSeenValues && !player.isWhite()){
                lowestSeenValues = currentValue;
                bestMove = move;

            }

        }
        System.out.println("Start: "+player.isWhite());
        System.out.println("After: "+game.getCurrentPlayer().isWhite());
        System.out.println("BestMove: "+lowestSeenValues);
        return bestMove;
    }

    private double max(Game game, final int depth, double alpha, double beta) {
        if (depth == 0 || game.isGameHasEnded()) {
            return Evaluation.Score(game);
        }
        if(!game.getCurrentPlayer().isWhite()){
            System.out.println("Error White");
            game.setCurrentPlayer(game.getWhitePlayer());
        }
        double highestSeenValue = Double.MIN_VALUE;
        final Game original = Game.copy(game);
        original.setCurrentPlayer(original.getWhitePlayer());

        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame = Main.getGameAfterMove(move, game);
            final double currentValue;

            if(newGame.getCurrentPlayer().isWhite()) {
                currentValue = max(newGame, depth - 1, alpha, beta);
                System.out.println("AGAIN");
            }else {

                currentValue = min(newGame, depth - 1, alpha, beta);
            }


            highestSeenValue = Math.max(currentValue, highestSeenValue);
            alpha = Math.max(alpha, highestSeenValue);

            if (beta <= alpha) {
                break;
            }

        }


        return highestSeenValue;
    }

    private double min(Game game, final int depth, double alpha, double beta) {
        if (depth == 0 || game.isGameHasEnded()) {
            return Evaluation.Score(game);
        }
        if(game.getCurrentPlayer().isWhite()){
            System.out.println("Error Black");
            game.setCurrentPlayer(game.getBlackPlayer());
        }

        double lowestSeenValue = Double.MAX_VALUE;
        final Game original = Game.copy(game);
        //System.out.println("Should be Black: "+original.getCurrentPlayer().isWhite());
        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame =  Main.getGameAfterMove(move, game);
            final double currentValue;
            if(newGame.getCurrentPlayer().isWhite()) {
                currentValue = max(newGame, depth - 1, alpha, beta);
            }else {
                System.out.println("AGAIN");
                currentValue = min(newGame, depth - 1, alpha, beta);
            }


            lowestSeenValue = Math.min(currentValue, lowestSeenValue);
            beta = Math.min(beta, lowestSeenValue);

            if (beta <= alpha) {
                break;
            }

        }
        return lowestSeenValue;
    }


}
