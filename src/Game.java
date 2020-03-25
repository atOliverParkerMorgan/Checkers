import cz.gyarab.util.light.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    Matrix matrix;
    Player whitePlayer;
    Player blackPlayer;
    Player currentPlayer;
    boolean followUpMove;
    List<Move> UI_moves;
    Move lastMove;
    private boolean gameHasEnded;


    Game(){
        this.gameHasEnded = false;
        this.lastMove = null;
        this.UI_moves = new ArrayList<>();
        this.followUpMove = false;
        this.matrix = Matrix.createMatrix(8, 8);
        matrix.setLightPanelFactory(new LightPanelFactory() {
            @Override
            public LightPanel createLightPanel(BasicLightMatrix matrix, String title) {
                return new SwingLightPanel(matrix,"Checkers - Oliver Morgan 1.E"){
                    @Override
                    protected void paintLight(Graphics g, Rectangle rect, int col, int row, LightColor color, LightColor background) {
                        super.paintLight(g, rect, col, row, color, background);
                        Piece whitePiece = whitePlayer.getPiece(col, row);
                        Piece blackPiece = blackPlayer.getPiece(col,row);

                        if(whitePiece!=null){
                            g.setColor(whitePlayer.getColor().getColor().getSwingColor());
                            if(whitePiece.isQueen()){
                                g.fillRect(rect.x,rect.y,rect.width,rect.height);
                            }else {
                                g.fillOval(rect.x,rect.y,rect.width,rect.height);
                            }
                        }else if(blackPiece!=null){
                            g.setColor(blackPlayer.getColor().getColor().getSwingColor());
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
        this.matrix.setBackground(LightColor.WHITE);

        this.whitePlayer = new Player(true);
        this.blackPlayer = new Player(false);
        whitePlayer.setOpponent(blackPlayer);
        blackPlayer.setOpponent(whitePlayer);
        whitePlayer.initPieces(this);
        blackPlayer.initPieces(this);

        this.currentPlayer = whitePlayer;

    }
     void EndGame(){
        this.gameHasEnded = true;
     }

    public boolean isGameHasEnded() {
        return gameHasEnded;
    }
}
