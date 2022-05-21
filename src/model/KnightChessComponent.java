package model;
import view.ChessGameFrame;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private static Image nb;
    private static Image nw;

    private Image knightImage;
    private Image knightI;
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE== null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
        if(nb==null){
            nb = ImageIO.read(new File("./images/blackknight.png"));
        } if(nw==null){
            nw= ImageIO.read(new File("./images/whiteknight.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
                knightI = nw;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
                knightI = nb;
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
        if (!ChessGameFrame.isChanged) {
        super.paintComponent(g);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);}
        else {super.paintComponent(g);
            g.drawImage(knightI, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);
        }
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() , getHeight());
        }
    }
}

