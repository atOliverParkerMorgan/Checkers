package AI;

import Checkers.Game;
import Checkers.Move;

import java.util.List;

public class GameAndFollowUpMove{
    Game game;
    List<Move> move;
    public GameAndFollowUpMove(Game game, List<Move> move){
        this.game = game;
        this.move = move;
    }
}