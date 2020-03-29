package Checkers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    Player whitePlayer;
    Player blackPlayer;
    Player currentPlayer;
    boolean followUpMove;
    List<Move> UI_moves;
    Move lastMove;
    private boolean gameHasEnded;
    Board board;


    Game(){
        this.board = new Board(8,8);
        this.gameHasEnded = false;
        this.lastMove = null;
        this.UI_moves = new ArrayList<>();
        this.followUpMove = false;
        this.whitePlayer = new Player(true);
        this.blackPlayer = new Player(false);
        whitePlayer.initPieces(this);
        blackPlayer.initPieces(this);

        this.currentPlayer = whitePlayer;

    }



    public boolean isGameHasEnded() {
        return gameHasEnded;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    // !!! This is not my code, copied from the internet !!!
    public static Game copy(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Game) obj;
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
