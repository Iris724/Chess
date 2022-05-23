package controller;

import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class AIController1 extends Thread{

    private Chessboard chessboard;
    private ClickController clickController;
    private static Robot robot;

    private volatile boolean robotGo=true;

    public AIController1  (Chessboard chessboard)  {
        this.chessboard = chessboard;
        this.clickController=new ClickController(chessboard);
    }



    public static void rbt() {
        try {

            robot = new Robot();

            //robot.setAutoWaitForIdle(true);

        } catch (AWTException e) {

            e.printStackTrace();

        }
    }

    public void chessReallyGo(){
        //挪动棋子anyway，必须得走
        int n=0;
        while (true){
            ChessComponent chessComponent=findingChess();
            if (chessTryGo(chessComponent)){
                chessTryGo(chessComponent) ;
                System.out.println("go");
                chessboard.repaint();
                break;
            }
            n++;
            System.out.println(n);
        }


    }


    public boolean chessTryGo(ChessComponent first){
    //尝试这个棋子所有点
        ArrayList<ChessboardPoint> pointslist=new ArrayList<>();
        //in a simple way to get the points
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardPoint point=new ChessboardPoint(i,j);
                if (first.canMoveTo(chessboard.getChessComponents(),point)){
                    pointslist.add(point);
                }
            }
        }

        // special cases should be considered
        if (pointslist.size()==0){
            return false;
        }
        Random random=new Random();
        int index;
        if (pointslist.size()==1){
            index=0;
        }else {
            index = random.nextInt(pointslist.size() - 1);
        }
        //get the point to move randomly
        ChessboardPoint p=pointslist.get(index);
        //find the chess on the point, empty is also ok
        ChessComponent second= chessboard.getChessComponents()[p.getX()][p.getY()];

        clickController.onClick(first);
        clickController.onClick(second);



//        robot.delay(200);
//        robot.mouseMove(first.getX(),first.getY());
//        robot.mousePress(InputEvent.BUTTON1_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//        robot.mouseMove(second.getX(),second.getY());
//        robot.mousePress(InputEvent.BUTTON1_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//        first.setSelected(true);
//        first.clickController.onClick(second);
//        chessboard.swapChessComponents(first,second);
//
//        chessboard.swapColor();
//        chessboard.repaint();
        return true;
    }

    public ChessComponent findingChess(){
    //this method is used to find all chess of current color
        ChessColor colorHere=ChessColor.BLACK;

        ArrayList<ChessComponent> chessList=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessComponent chess=chessboard.getChessComponents()[i][j];
                if (chess.getChessColor().equals(colorHere)){
                    chessList.add(chess);
                }
            }
        }//finish all proper chess's storing in the arraylist
        Random random=new Random();
        int index= random.nextInt(chessList.size()-1);
        return chessList.get(index);

    }

}
