package Checkers;

import AI.MiniMax;
import cz.gyarab.util.light.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private boolean playerVsPlayer = false;
    private static MiniMax miniMax = new MiniMax(4); //optimal in my opinion
    private boolean playerVsAI = false;
    private boolean AIVsAI = false;

    private  static Game game;
    private Matrix menuMatrix = Matrix.createMatrix(3, 3);
    private static Matrix gameTimeMatrix = Matrix.createMatrix(8,8);
    private Main() {
        gameTimeMatrix = Matrix.createMatrix(8, 8);
        gameTimeMatrix.setLightPanelFactory(new LightPanelFactory() {
            @Override
            public LightPanel createLightPanel(BasicLightMatrix matrix, String title) {
                return new SwingLightPanel(matrix,"Checkers - Oliver Morgan 1.E"){
                    @Override
                    protected void paintLight(Graphics g, Rectangle rect, int col, int row, LightColor color, LightColor background) {
                        super.paintLight(g, rect, col, row, color, background);
                        Piece whitePiece = game.whitePlayer.getPiece(col, row);
                        Piece blackPiece = game.blackPlayer.getPiece(col,row);

                        if(whitePiece!=null){
                            g.setColor(game.whitePlayer.getColor().getColor().getSwingColor());
                            if(whitePiece.isQueen()){
                                g.fillRect(rect.x,rect.y,rect.width,rect.height);
                            }else {
                                g.fillOval(rect.x,rect.y,rect.width,rect.height);
                            }
                        }else if(blackPiece!=null){
                            g.setColor(game.blackPlayer.getColor().getColor().getSwingColor());
                            if(blackPiece.isQueen()){
                                g.fillRect(rect.x,rect.y,rect.width,rect.height);
                            }else {
                                g.fillOval(rect.x,rect.y,rect.width,rect.height);
                            }
                        }
                    }
                };


            }
        });
        gameTimeMatrix.setBackground(LightColor.WHITE);
        // Menu
        menuMatrix.setLightPanelFactory(new LightPanelFactory() {
            @Override
            public LightPanel createLightPanel(BasicLightMatrix matrix, String title) {
                return new SwingLightPanel(matrix,"Checkers - Menu"){


                    @Override
                    protected void paintLight(Graphics g, Rectangle rect, int col, int row, LightColor color, LightColor background) {
                        super.paintLight(g, rect, col, row, color, background);
                        if(menuMatrix.getColor(col, row).equals(LightColor.GREEN)){
                            try {
                                Image image = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\Checkers\\Images\\PlayerVsPlayer.png"));
                                ImageObserver imageObserver = (image1, i, i1, i2, i3, i4) -> false;

                                g.drawImage(image,rect.x, rect.y, imageObserver);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else if(menuMatrix.getColor(col, row).equals(LightColor.LIGHT_GRAY)){
                            try {
                                Image image = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\Checkers\\Images\\PlayerVsAI.png"));
                                ImageObserver imageObserver = (image12, i, i1, i2, i3, i4) -> false;

                                g.drawImage(image,rect.x, rect.y, imageObserver);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if(menuMatrix.getColor(col, row).equals(LightColor.DARK_GRAY)){
                            try {
                                Image image = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\Checkers\\Images\\AIVsAI.png"));
                                ImageObserver imageObserver = (image13, i, i1, i2, i3, i4) -> false;
                                g.drawImage(image,rect.x, rect.y, imageObserver);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };


            }
        });
        game = new Game();
        menuMatrix.showWindow();
        menuMatrix.setBackground(LightColor.WHITE);
        menuMatrix.setColor(1,2,LightColor.GREEN);
        menuMatrix.setColor(1,1,LightColor.LIGHT_GRAY);
        menuMatrix.setColor(1,0,LightColor.DARK_GRAY);
        menuMatrix.getInteractiveLightPanel().setUserSelectionMode(
                InteractiveLightPanel.UserSelectionMode.PRESS);
       menuMatrix.getInteractiveLightPanel().setSelectionVisible(true);
       menuMatrix.getInteractiveLightPanel().addSelectListener(event -> {
           if(event.getCol()==1){
               menuMatrix.hideWindow();
               menuMatrix = null;
               if(event.getRow()==2){
                   gameTimeMatrix.showWindow();
                   playerVsPlayer = true;
               }else if(event.getRow()==1){
                   gameTimeMatrix.showWindow();
                   playerVsAI = true;
               }else if(event.getRow()==0){
                   System.out.println("!!! Click anywhere on the board to start the AI move !!!");
                   gameTimeMatrix.showWindow();
                   AIVsAI = true;
               }
           }



        });




        boardColors();
        updateBoard(game);
        getLegalMoves(game.currentPlayer.isWhite(),false, game);


        gameTimeMatrix.getInteractiveLightPanel().setUserSelectionMode(
                InteractiveLightPanel.UserSelectionMode.PRESS);
        gameTimeMatrix.getInteractiveLightPanel().setSelectionVisible(true);
        gameTimeMatrix.getInteractiveLightPanel().addSelectListener(event -> {
            if(playerVsPlayer) {
                if (!game.isGameHasEnded()) {
                    updateBoard(game);
                    boardColors();

                    if (event.getCol() < 0) {
                        return;
                    }
                    if (event.getRow() < 0) {
                        return;
                    }
                    LightColor lc = game.board.getColor(event.getCol(), event.getRow());

                    if (lc == null) {

                        for (int i = 0; i < game.UI_moves.size(); i++) {

                            if (game.UI_moves.get(i).yTo == event.getRow() && game.UI_moves.get(i).xTo == event.getCol()) {
                                game.UI_moves.get(i).setFromPosition(game.lastMove);
                                move(game.UI_moves.get(i), game, false, false);
                            }
                        }
                    } else {
                        showHint(event.getCol(), event.getRow());
                    }
                }
            }else if(playerVsAI){
                if (!game.isGameHasEnded()) {
                    if(game.currentPlayer.isWhite()) {

                        updateBoard(game);
                        boardColors();


                        if (event.getCol() < 0) {
                            return;
                        }
                        if (event.getRow() < 0) {
                            return;
                        }
                        LightColor lc = game.board.getColor(event.getCol(), event.getRow());


                        if (lc == null) {

                            for (int i = 0; i < game.UI_moves.size(); i++) {

                                if (game.UI_moves.get(i).yTo == event.getRow() && game.UI_moves.get(i).xTo == event.getCol()) {
                                    game.UI_moves.get(i).setFromPosition(game.lastMove);
                                    move(game.UI_moves.get(i), game, false, false);

                                    // AI move
                                    moveAI(game.blackPlayer, true);
                                }
                            }
                        } else {
                            showHint(event.getCol(), event.getRow());
                        }


                    }
                }

            }else if(AIVsAI) {
                updateBoard(game);
                boardColors();
                if(game.currentPlayer.isWhite()) {
                    moveAI(game.whitePlayer, true);
                }else {

                    moveAI(game.blackPlayer, true);
                }



            }
        });
    }

    private static void moveAI(Player player, boolean whereFrom){
       while (game.currentPlayer == player) {
           Game test = Game.copy(game);
           Move bestMove = miniMax.getBestMove(test, true);
           if(whereFrom) {
               game = test;
           }
           move(bestMove, game, false ,whereFrom);
       }


    }

    private static void switchPlayers(Game game) {
        game.currentPlayer = game.currentPlayer.isWhite()? game.blackPlayer : game.whitePlayer;
        if(game.currentPlayer.isWhite()) {
            gameTimeMatrix.setBackground(LightColor.WHITE);
        }else{
            gameTimeMatrix.setBackground(LightColor.BLACK);
        }
    }

     private static void endTurn(boolean switchPlayers){
        updateBoard(game);
        if(switchPlayers) {
            switchPlayers(game);
        }
        getLegalMoves(game.currentPlayer.isWhite(), false, game);

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
        for (int i = 0; i < game.board.getWidth(); i++) {
            if(game.board.isOn(i,game.board.getHeight()-1)){
                if(game.board.getColor(i,game.board.getHeight()-1)==Color.WHITE.getColor()) {
                    game.whitePlayer.setQueen(i,game.board.getHeight()-1);
                    break;
                }
            }if(game.board.isOn(i,0)){
                if(game.board.getColor(i,0)==Color.BLACK.getColor()) {
                    game.blackPlayer.setQueen(i, 0);
                    break;
                }
            }
        }

    }

    private static void showHint(int eventCol, int eventRow){

        game.UI_moves = new ArrayList<>();

        for (Move move : game.currentPlayer.allLegalMoves) {
            if (move.yFrom == eventRow && move.xFrom == eventCol) {
                game.UI_moves.add(move);
                gameTimeMatrix.setBackground(move.xTo, move.yTo, LightColor.CYAN);
            }
        }
        game.lastMove = new Move(eventCol, eventRow);
    }


    private static void getLegalMoves(boolean isWhite, boolean followUp, Game game){
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
            if(piece.isQueen()){
                for (int i = 0; i < 4; i++) {
                    int addX;
                    int addY;
                    if(i==0){
                        addX = 1;
                        addY = 1;
                    }else if(i==1){
                        addX = -1;
                        addY = 1;
                    }else if(i==2){
                        addX = 1;
                        addY = -1;
                    }else {
                        addX = -1;
                        addY = -1;
                    }

                    int currentX = piece.getX();
                    int currentY = piece.getY();
                    currentX = currentX + addX;
                    currentY = currentY + addY;

                    while (currentX <= 7 && currentX >= 0 && currentY <= 7 && currentY >= 0) {
                        if(game.board.isOff(currentX,currentY)){
                            player.allLegalMoves.add(new Move(piece.getX(),piece.getY(),currentX,currentY,false));
                        }else {
                            if(!game.board.getColor(currentX,currentY).equals(player.getColor().getColor())) {
                                if(currentX < 7 && currentX > 0 && currentY < 7 && currentY > 0) {

                                    if(game.board.getColor(currentX + addX , currentY + addY ) == null) {
                                        player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), currentX + addX, currentY + addY, true));
                                    }
                                }
                            }

                            break;
                        }

                        currentX = currentX + addX;
                        currentY = currentY + addY;

                    }
                }



            }else {
                // calculating behind moves
                int loopOver = followUp? 2: 1;

                for (int i = 0; i < loopOver; i++) {

                    // move logic
                    if (piece.getX() + 1 <= 7 && yDir + piece.getY() <= 7 && yDir + piece.getY() >= 0) {
                        if (game.board.isOff(piece.getX() + 1, piece.getY() + yDir)) {
                            if (i != 1) {
                                player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() + 1, piece.getY() + yDir, false));
                            }
                        } else {
                            if (piece.getX() + 2 <= 7 && 2 * yDir + piece.getY() <= 7 && 2 * yDir + piece.getY() >= 0) {
                                if (game.board.getColor(piece.getX() + 1, piece.getY() + yDir).equals(Player.getOpponent(game).getColor().getColor()) &&
                                        game.board.isOff(piece.getX() + 2, piece.getY() + 2 * yDir)) {
                                    player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() + 2, piece.getY() + 2 * yDir, true));

                                }
                            }
                        }
                    }

                    if (piece.getX() - 1 >= 0 && yDir + piece.getY() <= 7 && yDir + piece.getY() >= 0) {
                        if (game.board.isOff(piece.getX() - 1, piece.getY() + yDir)) {
                            if (i != 1) {
                                player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() - 1, piece.getY() + yDir, false));
                            }
                        } else {

                            if (piece.getX() - 2 >= 0 && 2 * yDir + piece.getY() <= 7 && 2 * yDir + piece.getY() >= 0) {
                                if (game.board.getColor(piece.getX() - 1, piece.getY() + yDir).equals(Player.getOpponent(game).getColor().getColor()) &&
                                        game.board.isOff(piece.getX() - 2, piece.getY() + 2 * yDir)) {
                                    player.allLegalMoves.add(new Move(piece.getX(), piece.getY(), piece.getX() - 2, piece.getY() + 2 * yDir, true));

                                }
                            }
                        }
                    }
                    yDir *= -1;

                }
                if (isWhite) {
                    yDir = 1;
                } else {
                    yDir = -1;
                }
            }



        }

    }

    private static void updateBoard(Game game) {
        // reset board
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                gameTimeMatrix.setOff(column, row);
            }
        }

        // white
        for (Piece piece : game.whitePlayer.getAllPieces()) {
            gameTimeMatrix.setColor(piece.getX(), piece.getY(), game.whitePlayer.getColor().getColor());
        }

        // black
        for (Piece piece : game.blackPlayer.getAllPieces()) {
            gameTimeMatrix.setColor(piece.getX(), piece.getY(), game.blackPlayer.getColor().getColor());
        }
        // check if game has ended

    }


    public static Game getGameAfterMove(Move move, Game game){
       Game gameCopy = Game.copy(game);
       move(move, gameCopy, true, false);

       return gameCopy;
    }

    private static void synchBoard(Game game){
        game.board.nullOutPieces();

        for(Piece piece: game.whitePlayer.getAllPieces()){
            game.board.board[piece.getX()][piece.getY()].piece = piece;
        }

        for(Piece piece: game.blackPlayer.getAllPieces()){
            game.board.board[piece.getX()][piece.getY()].piece = piece;
        }
    }


    private static void move(Move move, Game game, boolean AI, boolean whereFrom) {
        Piece movingPiece = game.currentPlayer.getPiece(move.xFrom, move.yFrom);

        // Fixing Errors in miniMax
        if(movingPiece==null){
            endTurn(true);
            return;
        }

        movingPiece.setXY(move);
        if(whereFrom){gameTimeMatrix.setBackground(move.xFrom, move.yFrom, LightColor.GREEN); }
        if(move.hasTaken) {
            if(move.yFrom<move.yTo){
                if(move.xFrom<move.xTo) {
                    Player.removePieceFromOpponent(move.xTo - 1, move.yTo - 1, game);
                }else{
                    Player.removePieceFromOpponent(move.xTo + 1, move.yTo - 1, game);
                }
            }else {
                if(move.xFrom<move.xTo) {
                    Player.removePieceFromOpponent(move.xTo - 1, move.yTo + 1, game);
                }else{
                    Player.removePieceFromOpponent(move.xTo + 1, move.yTo + 1, game);
                }

            }
            synchBoard(game);
            updateBoard(game);
            getLegalMoves(game.currentPlayer.isWhite(), true, game);
            List<Move> legalMovesOfPiece = game.currentPlayer.getLegalMovesOfPiece(movingPiece);
            List<Move> filteredLegalMoves = new ArrayList<>();

            for (Move value : legalMovesOfPiece) {
                if (value.hasTaken && move.xFrom!=value.xFrom && move.yTo!=value.yTo) {
                    filteredLegalMoves.add(value);
                }
            }

            if (filteredLegalMoves.size() != 0) {
                game.currentPlayer.allLegalMoves = filteredLegalMoves;

                endTurn(false);
                updateBoard(game);
                if(!AI) {
                    game.UI_moves = filteredLegalMoves;
                    showHint(move.xTo, move.yTo);
                }


            } else {
                endTurn(true);

            }
        }else {
            synchBoard(game);
            endTurn(true);
        }
        if(!AI||whereFrom){
            game.board.printOut();
        }
    }

    private static void boardColors(){
        // board init
        for (int row = 0; row < game.board.getHeight(); row++) {
            for (int column = 0; column < game.board.getWidth(); column++) {
                gameTimeMatrix.setBackground(column, row,
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
