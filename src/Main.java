import cz.gyarab.util.light.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private  static Game game;
    private Main() {
        game = new Game();

        boardColors();
        updateBoard();
        getLegalMoves(game.currentPlayer.isWhite());


        game.matrix.getInteractiveLightPanel().setUserSelectionMode(
                InteractiveLightPanel.UserSelectionMode.PRESS);
        game.matrix.getInteractiveLightPanel().setSelectionVisible(true);
        game.matrix.getInteractiveLightPanel().addSelectListener(event -> {
            if(!game.isGameHasEnded()) {
                updateBoard();
                boardColors();


                if (event.getCol() < 0) {
                    return;
                }
                if (event.getRow() < 0) {
                    return;
                }
                LightColor lc = game.matrix.getColor(event.getCol(), event.getRow());


                if (lc == null) {

                    for (int i = 0; i < game.UI_moves.size(); i++) {

                        if (game.UI_moves.get(i).yTo == event.getRow() && game.UI_moves.get(i).xTo == event.getCol()) {
                            game.UI_moves.get(i).setFromPosition(game.lastMove);
                            game.currentPlayer.allLegalMoves = move(game.UI_moves.get(i));
                            if (null != game.currentPlayer.allLegalMoves) {
                                if (game.currentPlayer.allLegalMoves.size() != 0) {
                                    updateBoard();
                                    showHint(event.getRow(), event.getCol(), true);
                                } else {
                                    endTurn();
                                }
                            } else {
                                endTurn();
                            }
                        }
                    }
                } else {
                    showHint(event.getRow(), event.getCol(), false);
                }
            }
        });

    }

    private static void switchPlayers() {
        game.currentPlayer = game.currentPlayer.isWhite()? game.blackPlayer : game.whitePlayer;
        if(game.currentPlayer.isWhite()) {
            game.matrix.setBackground(LightColor.WHITE);
        }else{
            game.matrix.setBackground(LightColor.BLACK);
        }
    }

     private static void endTurn(){
        updateBoard();
        switchPlayers();
        getLegalMoves(game.currentPlayer.isWhite());

        if(checkIfPlayerHasLost(game.currentPlayer)){
           if(game.currentPlayer.isWhite()){
               System.out.println("BLACK HAS WON");
           }else {
               System.out.println("WHITE HAS WON");
           }

        }

        game.UI_moves = new ArrayList<>();
        game.followUpMove = false;
        checkIfQueen();
    }

    private static void checkIfQueen(){
        for (int i = 0; i < game.matrix.getWidth(); i++) {
            if(game.matrix.isOn(i,game.matrix.getHeight()-1)){
                if(game.matrix.getColor(i,game.matrix.getHeight()-1)==Color.WHITE.getColor()) {
                    game.whitePlayer.setQueen(i,game.matrix.getHeight()-1);
                    break;
                }
            }if(game.matrix.isOn(i,0)){
                if(game.matrix.getColor(i,0)==Color.BLACK.getColor()) {
                    game.blackPlayer.setQueen(i, 0);
                    break;
                }
            }
        }

    }

    private static void showHint(int eventRow, int eventCol, boolean followUp){
        if(!followUp) {
            game.UI_moves = new ArrayList<>();
        }

        for (Move move : game.currentPlayer.allLegalMoves) {
            if (move.yFrom == eventRow && move.xFrom == eventCol) {
                game.UI_moves.add(move);
                game.matrix.setBackground(move.xTo, move.yTo, LightColor.CYAN);
            }
        }
        game.lastMove = new Move(eventCol, eventRow);
    }



    private static void getLegalMoves(boolean isWhite){
        Player player;
        int yDir;
        if(isWhite){
           player = game.whitePlayer;
           yDir = 1;
        }else {
           player = game.blackPlayer;
           yDir = -1;
        }

        player.allLegalMoves = new ArrayList<>();

        for (Piece piece: player.getAllPieces()) {

            // calculating queen move
            int loopOver = 1;
            if(piece.isQueen()){
                loopOver = 2;
            }

            for (int i = 0; i < loopOver; i++) {

                // move logic
                if (piece.getX() + 1 <= 7 && yDir + piece.getY() <= 7 && yDir + piece.getY() >= 0) {
                    if (game.matrix.isOff(piece.getX() + 1, piece.getY() + yDir)) {
                        player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() + 1, piece.getY() + yDir));
                    } else {
                        if (piece.getX() + 2 <= 7 && 2 * yDir + piece.getY() <= 7 && 2 * yDir + piece.getY() >= 0) {
                            if (game.matrix.getColor(piece.getX() + 1, piece.getY() + yDir).equals(player.getOpponent().getColor().getColor()) &&
                                    game.matrix.isOff(piece.getX() + 2, piece.getY() + 2 * yDir)) {
                                player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() + 2, piece.getY() + 2 * yDir));

                            }
                        }
                    }
                }

                if (piece.getX() - 1 >= 0 && yDir + piece.getY() <= 7 && yDir + piece.getY() >= 0) {
                    if (game.matrix.isOff(piece.getX() - 1, piece.getY() + yDir)) {
                        player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() - 1, piece.getY() + yDir));
                    } else {
                        if (piece.getX() - 2 >= 0 && 2 * yDir + piece.getY() <= 7 && 2 * yDir + piece.getY() >= 0) {
                            if (game.matrix.getColor(piece.getX() - 1, piece.getY() + yDir).equals(player.getOpponent().getColor().getColor()) &&
                                    game.matrix.isOff(piece.getX() - 2, piece.getY() + 2 * yDir)) {
                                player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() - 2, piece.getY() + 2 * yDir));

                            }
                        }
                    }
                }
                // adding possible moves behind the queen
                if (loopOver == 2) {
                    yDir *= -1;
                }
            }
            if(isWhite){
                yDir = 1;
            }else {
                yDir = -1;
            }


            //System.out.println("x: "+piece.getX());
            //System.out.println("y: "+piece.getY());
        }
       // for(Move m:whitePlayer.allLegalMoves){
       //     matrix.setColor(m.xTo,m.yTo,LightColor.GREEN);
       // }



    }

    private static void updateBoard() {
        // reset board
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                game.matrix.setOff(column, row);
            }
        }

        // white
        for (Piece piece : game.whitePlayer.getAllPieces()) {
            game.matrix.setColor(piece.getX(), piece.getY(), game.whitePlayer.getColor().getColor());
        }

        // black
        for (Piece piece : game.blackPlayer.getAllPieces()) {
            game.matrix.setColor(piece.getX(), piece.getY(), game.blackPlayer.getColor().getColor());
        }
        // check if game has ended

    }

    private static List<Move> move(Move move) {
        Piece movingPiece = game.currentPlayer.getPiece(move.xFrom, move.yFrom);
        movingPiece.setXY(move);
        if(move.isDouble()) {

            if(move.yFrom<move.yTo){
                if(move.xFrom<move.xTo) {
                    game.currentPlayer.removePieceFromOpponent(move.xFrom + 1, move.yFrom + 1);
                }else{
                    game.currentPlayer.removePieceFromOpponent(move.xFrom - 1, move.yFrom + 1);
                }
            }else {
                if(move.xFrom<move.xTo) {
                    game.currentPlayer.removePieceFromOpponent(move.xFrom + 1, move.yFrom - 1);
                }else{
                    game.currentPlayer.removePieceFromOpponent(move.xFrom - 1, move.yFrom - 1);
                }

            }
            getLegalMoves(game.currentPlayer.isWhite());
            List<Move> legalMovesOfPiece = game.currentPlayer.getLegalMovesOfPiece(movingPiece);
            List<Move> filteredLegalMoves = new ArrayList<>();
            for (Move value : legalMovesOfPiece) {
                if (value.isDouble()) {
                    filteredLegalMoves.add(value);
                }
            }
            legalMovesOfPiece = filteredLegalMoves;
            return legalMovesOfPiece;
        }
        return null;

    }

    private static void boardColors(){
        // board init
        game.matrix.showWindow();
        for (int row = 0; row < game.matrix.getHeight(); row++) {
            for (int column = 0; column < game.matrix.getWidth(); column++) {
                game.matrix.setBackground(column, row,
                        (column + row) % 2 == 0
                                ? LightColor.CHESSBOARD_DARK
                                : LightColor.CHESSBOARD_LIGHT);
            }
        }
    }

   private static boolean checkIfPlayerHasLost(Player player){
        return player.allLegalMoves.size()==0 || player.getAllPieces().size() == 0;
   }


    public static void main(String[] args) {
        new Main();
    }

}
