package model;
import controller.AIController1;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    private Image knightImage;
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE== null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() -2 == destination.getX() ){
            if (source.getY() -1 == destination.getY()  || source.getY() +1 == destination.getY() ){
                return true;
            }
        }else if (source.getX() -1 == destination.getX() ){
            if (source.getY() -2 == destination.getY()  || source.getY() +2== destination.getY() ){
                return true;
            }
        }else if (source.getX() +1 == destination.getX() ){
            if (source.getY() -2 == destination.getY()  || source.getY() +2 == destination.getY() ){
                return true;
            }
        }else if (source.getX() +2 == destination.getX() ){
            if (source.getY() -1 == destination.getY()  || source.getY() +1 == destination.getY() ){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}

