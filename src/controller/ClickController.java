package controller;


import model.ChessComponent;
import view.Chessboard;
import view.ChessboardPoint;

import java.util.ArrayList;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public ArrayList<String> getStoringSteps() {
        return chessboard.getStoringSteps();
    }

    public int getCHESS_SIZE() {
        return chessboard.getCHESS_SIZE();
    }

    public void changeQ(ChessboardPoint source, ChessComponent chess1) {
        chessboard.changeQ(source, chess1);
    }

    public void changeB(ChessboardPoint source, ChessComponent chess1) {
        chessboard.changeB(source, chess1);
    }

    public void changeK(ChessboardPoint source, ChessComponent chess1) {
        chessboard.changeK(source, chess1);
    }

    public void changeR(ChessboardPoint source, ChessComponent chess1) {
        chessboard.changeR(source, chess1);
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}
