package model;
import controller.GameController;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private static Image pb;
    private static Image pw;

    private GameController gameController;
    private Chessboard chessboard;

    private Image pawnImage;
    private Image pawnI;

    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];

   public int flag = 0;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
        if(pb==null){
            pb = ImageIO.read(new File("./images/blackpawn.png"));
        } if(pw==null){
            pw= ImageIO.read(new File("./images/whitepawn.png"));
        }
    }


    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
                pawnI = pw;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
                pawnI = pb;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (chessColor == ChessColor.BLACK) {
//            if (source.getX() == 1 && source.getX() + 1 == destination.getX() && source.getY() == destination.getY()) {
//                int row = source.getX() + 1;
//                int col = source.getY();
//                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
//                    return false;
//                }
//                return true;
//            }
            if (source.getX() == 1 && source.getX() + 2 == destination.getX() && source.getY() == destination.getY()) {
                int row = source.getX() + 2;
                int col = source.getY();
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }
            if (source.getX() >= 1 && source.getX() <= 6 && source.getX() + 1 == destination.getX() && source.getY() == destination.getY()) {
               if (source.getX() == 6){
                   int row = source.getX() + 1;
                   int col = source.getY();
                   if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                       return false;
                   }
                   changePawn(destination,chessComponents[row][col]);
                   return true;
               }
                int row = source.getX() + 1;
                int col = source.getY();
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }
            if (source.getX() >= 1 && source.getX() <= 6 && source.getX() + 1 == destination.getX() && (source.getY() + 1 == destination.getY())) {
               if (source.getX() == 6){
                   int row1 = source.getX() + 1;
                   int col1 = source.getY() + 1;
                   if ((chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                       return false;
                   }
                   changePawn(destination,chessComponents[row1][col1]);
                   return true;
               }
                // Passed - by
                if (source.getX() == 4 && destination.getX() == 5 && source.getY() + 1 == destination.getY()) {
                    int bp2 = source.getY() + 1;
                    String BP2 = Integer.toString(bp2);
                    StringBuilder lastStep2 = new StringBuilder();
                    lastStep2.append('4');
                    lastStep2.append(BP2);
                    lastStep2.append('6');
                    lastStep2.append(BP2);
                    String lastStep = lastStep2.toString();
                    int row = source.getX();
                    int col = source.getY() + 1;
                    if (getStoringSteps().get(getStoringSteps().size() - 1).substring(138, 142).equals(lastStep) && chessComponents[row][col] instanceof PawnChessComponent) {
                        return true;
                    } else {
                        int row1 = source.getX() + 1;
                        int col1 = source.getY() + 1;
                        if ((chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                            return false;
                        }
                        return true;
                    }
                } else {
                    int row1 = source.getX() + 1;
                    int col1 = source.getY() + 1;
                    if ((chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            }
            if (source.getX() >= 1 && source.getX() <= 6 && source.getX() + 1 == destination.getX() && (source.getY() - 1 == destination.getY())) {
              //ChangePawn
               if (source.getX() == 6) {
                   int row2 = source.getX() + 1;
                   int col2 = source.getY() - 1;
                   if ((chessComponents[row2][col2] instanceof EmptySlotComponent)) {
                       return false;
                   }
                   changePawn(destination,chessComponents[row2][col2]);
                   return true;
               }
                // Passed - by
                if (source.getX() == 4 && destination.getX() == 5 && source.getY() - 1 == destination.getY()) {
                    int bp2 = source.getY() - 1;
                    String BP2 = Integer.toString(bp2);
                    StringBuilder lastStep2 = new StringBuilder();
                    lastStep2.append('4');
                    lastStep2.append(BP2);
                    lastStep2.append('6');
                    lastStep2.append(BP2);
                    String lastStep = lastStep2.toString();
                    int row = source.getX();
                    int col = source.getY() - 1;
                    if (getStoringSteps().get(getStoringSteps().size() - 1).substring(138, 142).equals(lastStep) && chessComponents[row][col] instanceof PawnChessComponent) {
                        return true;
                    } else {
                        int row2 = source.getX() + 1;
                        int col2 = source.getY() - 1;
                        if ((chessComponents[row2][col2] instanceof EmptySlotComponent)) {
                            return false;
                        }
                        return true;
                    }
                } else {
                    int row2 = source.getX() + 1;
                    int col2 = source.getY() - 1;
                    if ((chessComponents[row2][col2] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (chessColor == ChessColor.WHITE) {
//            if (source.getX() == 6 && source.getX() - 1 == destination.getX() && source.getY() == destination.getY()) {
//                int row = source.getX() - 1;
//                int col = source.getY();
//                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
//                    return false;
//                }
//                return true;
//            }
            if (source.getX() == 6 && source.getX() - 2 == destination.getX() && source.getY() == destination.getY()) {
                int row = source.getX() - 2;
                int col = source.getY();
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }
            if (source.getX() <= 6 && source.getX() >= 1 && source.getX() - 1 == destination.getX() && source.getY() == destination.getY()) {
                //ChangPawn
                if (source.getX() == 1){
                    int row1 = source.getX() - 1;
                    int col = source.getY();
                    if (!(chessComponents[row1][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    changePawn(destination,chessComponents[row1][col]);
                    return true;
                }
                int row = source.getX() - 1;
                int col = source.getY();
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }
            if (source.getX() >= 1 && source.getX() <= 6 && source.getX() - 1 == destination.getX() && (source.getY() + 1 == destination.getY())) {
              //ChangPawn
               if (source.getX() == 1){
                int row1 = source.getX() - 1;
                int col1 = source.getY() + 1;
                if ((chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                    return false;
                }
                changePawn(destination,chessComponents[row1][col1]);
                return true;
            }

                // Passed - by
                if (source.getX() == 3 && destination.getX() == 2 && source.getY() + 1 == destination.getY()) {
                    int bp2 = source.getY() + 1;
                    String BP2 = Integer.toString(bp2);
                    StringBuilder lastStep2 = new StringBuilder();
                    lastStep2.append('3');
                    lastStep2.append(BP2);
                    lastStep2.append('1');
                    lastStep2.append(BP2);
                    String lastStep = lastStep2.toString();
                    int row = source.getX();
                    int col = source.getY() + 1;
                    if (getStoringSteps().get(getStoringSteps().size() - 1).substring(138, 142).equals(lastStep) && chessComponents[row][col] instanceof PawnChessComponent) {
                        return true;
                    } else {
                        int row1 = source.getX() - 1;
                        int col1 = source.getY() + 1;
                        if ((chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                            return false;
                        }
                        return true;
                    }
                } else {
                    int row1 = source.getX() - 1;
                    int col1 = source.getY() + 1;
                    if ((chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            }
            if (source.getX() >= 1 && source.getX() <= 6 && source.getX() - 1 == destination.getX() && (source.getY() - 1 == destination.getY())) {
               //ChangePawn
                if (source.getX() == 1) {
                    int row1 = source.getX() - 1;
                    int col2 = source.getY() - 1;
                    if ((chessComponents[row1][col2] instanceof EmptySlotComponent)) {
                        return false;
                    }
                   changePawn(destination,chessComponents[row1][col2]);
                    return true;
                }
                // Passed - by
                if (source.getX() == 3 && destination.getX() == 2 && source.getY() - 1 == destination.getY()) {
                    int bp2 = source.getY() - 1;
                    String BP2 = Integer.toString(bp2);
                    StringBuilder lastStep2 = new StringBuilder();
                    lastStep2.append('3');
                    lastStep2.append(BP2);
                    lastStep2.append('1');
                    lastStep2.append(BP2);
                    String lastStep = lastStep2.toString();
                    int row = source.getX();
                    int col = source.getY() - 1;
                    if (getStoringSteps().get(getStoringSteps().size() - 1).substring(138, 142).equals(lastStep) && chessComponents[row][col] instanceof PawnChessComponent) {
                        return true;
                    } else {
                        int row1 = source.getX() - 1;
                        int col2 = source.getY() - 1;
                        if ((chessComponents[row1][col2] instanceof EmptySlotComponent)) {
                            return false;
                        }
                        return true;
                    }
                } else {
                    int row1 = source.getX() - 1;
                    int col2 = source.getY() - 1;
                    if ((chessComponents[row1][col2] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!ChessGameFrame.isChanged) {
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);}
        else {super.paintComponent(g);
            g.drawImage(pawnI, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);
        }
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() , getHeight());
        }
    }

    public void changePawn(ChessboardPoint source , ChessComponent chess1){
        // The board for choosing
        JFrame f=new JFrame();
        f.setLayout(new FlowLayout());
        f.setSize(250, 250);
        f.setTitle("ChangPawn");
        f.setLocationRelativeTo(null);
        Dimension a=new Dimension(100,100);//The size of JButton
        JButton queen=new JButton("QUEEN");
        JButton bishop=new JButton("BISHOP");
        JButton knight=new JButton("KNIGHT");
        JButton rook=new JButton("ROOK");
        ArrayList<JButton> buttons=new ArrayList<>();
        buttons.add(queen);
        buttons.add(bishop);
        buttons.add(knight);
        buttons.add(rook);
        for(int i=0;i<4;i++) {
            int x=i;
            buttons.get(x).setPreferredSize(a);
            buttons.get(x).setBorder(BorderFactory.createLineBorder(Color.BLACK));
            buttons.get(x).addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (x){
                        case 0:
                            changeQ(source,chess1);
                            break;
                        case 1:
                            changeB(source,chess1);
                            break;
                        case 2:
                            changeK(source,chess1);
                            break;
                        case 3:
                            changeR(source,chess1);
                            break;
                    }
                    f.dispose(); //close the frame when the action has done.
                }
            });
        }
        f.add(queen);
        f.add(bishop);
        f.add(knight);
        f.add(rook);
        f.setVisible(true);
    }


}


