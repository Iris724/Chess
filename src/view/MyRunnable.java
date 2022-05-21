package view;

import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;

import java.util.List;

import static view.ChessGameFrame.statusLabel;
import static view.Chessboard.getStoringSteps;
import static view.Chessboard.setCurrentColor;

public class MyRunnable implements Runnable{

    public ChessGameFrame chessGameFrame;
    public MyRunnable(ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
    }

    @Override
    public void run() {
        List<String> list=getStoringSteps();
        //chessGameFrame.getGameController().getChessboard().getStoringSteps();
        for (int i = 0; i <list.size(); i++) {
            //chessGameFrame.getGameController().getChessboard().redo();

            if (Chessboard.getStoringSteps().get(getStoringSteps().size() - 1).charAt(Chessboard.getStoringSteps().get(getStoringSteps().size() - 1).length() - 1) == 'w') {
                setCurrentColor(ChessColor.BLACK);
            }
            if (Chessboard.getStoringSteps().get(getStoringSteps().size() - 1).charAt(Chessboard.getStoringSteps().get(getStoringSteps().size() - 1).length() - 1) == 'b') {
                setCurrentColor(ChessColor.WHITE);
            }
            System.out.println(list.get(i));
            chessGameFrame.getGameController().getChessboard().loadLastOne(list.get(i));
            //chessGameFrame.getGameController().getChessboard().repaint();
            //chessGameFrame.repaint();
            try {
                whiteTurn();
                if (Chessboard.getStoringSteps().get(i).charAt(Chessboard.getStoringSteps().get(getStoringSteps().size() - 1).length() - 1) == 'w') {
                    whiteTurn();
                }
                if (Chessboard.getStoringSteps().get(i).charAt(Chessboard.getStoringSteps().get(getStoringSteps().size() - 1).length() - 1) == 'b') {
                    blackTurn();
                }
                chessGameFrame.repaint();
                Thread.sleep(2000);


            } catch (Exception e) {

            }
        }
        chessGameFrame.getGameController().getChessboard().Reset();
        whiteTurn();
        setCurrentColor(ChessColor.WHITE);
        chessGameFrame.repaint();

    }

    private void whiteTurn() {
        statusLabel.setText("WHITE'S ROUND");
    }
    private void blackTurn(){
        statusLabel.setText("BLACK'S ROUND");
    }


}
