package model;

import controller.ClickController;
import view.ChessGameFrame;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent {

    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private static Image bb;
    private static Image bw;


    /**
     *
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image bishopImage;
    private Image bishopI;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
        if(bb==null){
            bb = ImageIO.read(new File("./images/balckbishop.png"));
        } if(bw==null){
            bw= ImageIO.read(new File("./images/whitebishop.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
                bishopI = bw;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
                bishopI = bb;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX()-source.getY()==destination.getX()-destination.getY()) {
            int col = Math.min(source.getY(),destination.getY())+1;
            int row = Math.min(source.getX(),destination.getX())+1;
            for (;col<Math.max(source.getY(),destination.getY())
                    ;col++,row++)
            {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)){
                    return false;
                }
            }
        } else if (source.getX()+source.getY()==destination.getY()+destination.getX()){
            int col = Math.max(source.getY(),destination.getY())-1;
            int row = Math.min(source.getX(),destination.getX())+1;
            for (;row<Math.max(source.getX(),destination.getX());row++,col--){
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)){
                    return false;
                }
            }
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (!ChessGameFrame.isChanged) {
            super.paintComponent(g);
            g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);}
        else {super.paintComponent(g);
            g.drawImage(bishopI, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);
        }
            if (isSelected()) { // Highlights the model if selected.
                g.setColor(Color.RED);
                g.drawRect(0, 0, getWidth(), getHeight());
            }
        }
}



