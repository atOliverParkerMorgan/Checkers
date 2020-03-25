package AI;

import Checkers.Game;
import Checkers.Main;
import Checkers.Move;
import Checkers.Player;

import java.util.List;

public final class MiniMax {
    private int searchDepth;

    public MiniMax(int searchDepth){
        this.searchDepth = searchDepth;
    }

    public Move getBestMove(final Game game){
        Game simulatingGame = game.copy();

        int numMoves = 100 / game.getCurrentPlayer().getAllLegalMoves().size();

        // these values have to be change during the first miniMax loop
        double highestSeenValue = Integer.MIN_VALUE;
        double lowestSeenValues = Integer.MAX_VALUE;

        Player player = game.getCurrentPlayer();

        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;

        for(final Move move : player.getAllLegalMoves()) {

            // move logic
            List<Move> followUpMoves = Main.move(move);
            if(followUpMoves!=null){

            }else {
                Main.endTurn();
            }


        }

        return new Move(0,0);
    }

}
