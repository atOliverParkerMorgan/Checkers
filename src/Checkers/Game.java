package Checkers;

import cz.gyarab.util.light.*;

import java.awt.*;
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
        whitePlayer.setOpponent(blackPlayer);
        blackPlayer.setOpponent(whitePlayer);
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
    public Game copy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Game) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeGameToCopy(Game game){
        this.currentPlayer = game.currentPlayer;
        this.blackPlayer = game.blackPlayer;
        this.whitePlayer = game.whitePlayer;
        this.board = game.board;
        this.gameHasEnded = game.gameHasEnded;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
