package model;
import controller.AIController1;
import controller.GameController;
import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private GameController gameController;
    private Chessboard chessboard;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
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
            if (source.getX() >= 1 && source.getX() < 6 && source.getX() + 1 == destination.getX() && source.getY() == destination.getY()) {
                int row = source.getX() + 1;
                int col = source.getY();
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }
            if (source.getX() >= 1 && source.getX() < 6 && source.getX() + 1 == destination.getX() && (source.getY() + 1 == destination.getY())) {
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
            if (source.getX() >= 1 && source.getX() < 6 && source.getX() + 1 == destination.getX() && (source.getY() - 1 == destination.getY())) {
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
            if (source.getX() <= 6 && source.getX() > 1 && source.getX() - 1 == destination.getX() && source.getY() == destination.getY()) {
                int row = source.getX() - 1;
                int col = source.getY();
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }
            if (source.getX() > 1 && source.getX() <= 6 && source.getX() - 1 == destination.getX() && (source.getY() + 1 == destination.getY())) {
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
            if (source.getX() > 1 && source.getX() <= 6 && source.getX() - 1 == destination.getX() && (source.getY() - 1 == destination.getY())) {
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
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}


