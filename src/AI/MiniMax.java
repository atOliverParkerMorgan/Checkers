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
        //System.out.println("Before start: ");
        //game.getBoard().printOut();
        Game simulatingGame;
        Move bestMove = null;
        double currentValue;

        //int numMoves = 100 / game.getCurrentPlayer().getAllLegalMoves().size();

        // these values have to be changed during the first miniMax loop
        double highestSeenValue = Double.MIN_VALUE;

        double lowestSeenValues = Double.MAX_VALUE;


        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;


        for (Move move : game.getCurrentPlayer().getAllLegalMoves()) {
            System.out.println("OG Move xFrom: "+ move.xFrom+" Move yFrom: "+move.yFrom+" Move xTo: "+move.xTo+"Move yTo: "+move.yTo);
            simulatingGame = Game.copy(game);
            simulatingGame = Main.getGameAfterMove(move,simulatingGame);

            currentValue = game.getCurrentPlayer().isWhite() ?
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

        }
        //System.out.println("After start: ");
        //game.getBoard().printOut();
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
        //System.out.println("BEFORE WHITE: ");
        //original.getBoard().printOut();
        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame = Main.getGameAfterMove(move, game);
           // System.out.println("AFTER WHITE: ");
           // newGame.getBoard().printOut();
            final double currentValue;

            if(newGame.getCurrentPlayer().isWhite()) {
                currentValue = max(newGame, depth - 1, alpha, beta);
               // System.out.println("AGAIN");
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
       // System.out.println("BEFORE BLACK: ");
       // original.getBoard().printOut();
        for (final Move move : original.getCurrentPlayer().getAllLegalMoves()) {
            Game newGame =  Main.getGameAfterMove(move, game);
            //System.out.println("AFTER BLACK: ");
           // newGame.getBoard().printOut();
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
