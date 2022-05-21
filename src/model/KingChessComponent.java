package model;

import view.ChessGameFrame;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private static Image kb;
    private static Image kw;

    private Image kingImage;
    private Image kingI;


    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
        if(kb==null){
            kb = ImageIO.read(new File("./images/blackking.png"));
        } if(kw==null){
            kw= ImageIO.read(new File("./images/whiteking.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
                kingI = kw;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
                kingI = kb;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            if (source.getY() +1 == destination.getY()  || source.getY() -1 == destination.getY() ) {
                return true;
            }
        } else if (source.getX() -1 == destination.getX() ) {
            if (source.getY() == destination.getY() || source.getY() +1 == destination.getY()  || source.getY() -1== destination.getY() ) {
                return true;
            }
        } else if (source.getX() +1== destination.getX() ) {
            if (source.getY() == destination.getY() || source.getY() +1== destination.getY()  || source.getY() -1 == destination.getY() ) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (!ChessGameFrame.isChanged) {
        super.paintComponent(g);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);}
        else {super.paintComponent(g);
            g.drawImage(kingI, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);
        }
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() , getHeight());
        }
    }

}
