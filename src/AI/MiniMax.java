package AI;

import Checkers.Game;
import Checkers.Main;
import Checkers.Move;

// this code is inspired by MY chessAI engine (https://github.com/atOliverParkerMorgan/Chess_AI/blob/master/src/Game/AI/MiniMax.java)
// (which I created )
// why reinvent the wheel, when I've already put in the effort to build my own MiniMax algorithm

public final class MiniMax {
    private int searchDepth;

    public MiniMax(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public Move getBestMove(final Game game, boolean UI) {
        Game simulatingGame;
        Move bestMove = null;
        double currentValue;

        int numMoves = 100 / game.getCurrentPlayer().getAllLegalMoves().size();
        int all = 0;
        // these values have to be changed during the first miniMax loop
        double highestSeenValue = Integer.MIN_VALUE;

        double lowestSeenValues = Integer.MAX_VALUE;


        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;


        for (Move move : game.getCurrentPlayer().getAllLegalMoves()) {

            simulatingGame = Main.getGameAfterMove(move,game);

            currentValue = simulatingGame.getCurrentPlayer().isWhite() ?
                    max(simulatingGame, this.searchDepth, alpha, beta) :
                    min(simulatingGame, this.searchDepth, alpha, beta);

            // white is max black is min
            if(currentValue>highestSeenValue &&  game.getCurrentPlayer().isWhite()){
                highestSeenValue = currentValue;
                bestMove = move;


            }else if(currentValue<lowestSeenValues && ! game.getCurrentPlayer().isWhite()){
                lowestSeenValues = currentValue;
                bestMove = move;

            }
            if(UI) {
                all += numMoves;
                System.out.println("Process: " + all + " %");
            }

        }

        return bestMove;
    }

    private double max(Game game, final int depth, double alpha, double beta) {
        if (depth == 0 || game.isGameHasEnded()) {
            return Evaluation.Score(game);
        }
        double highestSeenValue = Double.MIN_VALUE;
        final Game original = Game.copy(game);
        original.setCurrentPlayer(original.getWhitePlayer());

        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame = Main.getGameAfterMove(move, game);

            final double currentValue;

            // jumps in a row
            if(newGame.getCurrentPlayer().isWhite()) {
                currentValue = max(newGame, depth - 1, alpha, beta);
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

        double lowestSeenValue = Double.MAX_VALUE;
        final Game original = Game.copy(game);
        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame =  Main.getGameAfterMove(move, game);

            final double currentValue;

            // jumps in a row
            if(newGame.getCurrentPlayer().isWhite()) {
                currentValue = max(newGame, depth - 1, alpha, beta);
            }else {
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
