package view;

import controller.GameController;
import com.sun.xml.internal.ws.api.pipe.ClientPipeAssemblerContext;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static view.ChessGameFrame.*;
import static view.ChessGameFrame.statusLabel;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {


    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */

    private static ArrayList<String> storingSteps=new ArrayList<>();
    private int numberOfStepsNow=0;

    private static final int CHESSBOARD_SIZE = 8;



    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private static ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    public final int CHESS_SIZE;
    public boolean whiteWin;
    public boolean blackWin;
    public boolean ifSwap;
//    public boolean checked;


    public static void setCurrentColor(ChessColor currentColor) {
        Chessboard.currentColor = currentColor;
    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();


        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        //FIXME: Queen
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(7,3,ChessColor.WHITE);
        //FIXME: Bishop
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(7,2,ChessColor.WHITE);
        initBishopOnBoard(7,5,ChessColor.WHITE);
        //FIXME: Pawn
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1,i,ChessColor.BLACK);
            initPawnOnBoard(6,i,ChessColor.WHITE);
        }
        //FIXME: King
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(7,4,ChessColor.WHITE);
        //FIXME: Knight
        initKnightOnBoard(0,6,ChessColor.BLACK);
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(7,1,ChessColor.WHITE);
        initKnightOnBoard(7,6,ChessColor.WHITE);



    }
    public void Reset(){
        initiateEmptyChessboard();


        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        //FIXME: Queen
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(7,3,ChessColor.WHITE);
        //FIXME: Bishop
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(7,2,ChessColor.WHITE);
        initBishopOnBoard(7,5,ChessColor.WHITE);
        //FIXME: Pawn
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1,i,ChessColor.BLACK);
            initPawnOnBoard(6,i,ChessColor.WHITE);
        }
        //FIXME: King
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(7,4,ChessColor.WHITE);
        //FIXME: Knight
        initKnightOnBoard(0,6,ChessColor.BLACK);
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(7,1,ChessColor.WHITE);
        initKnightOnBoard(7,6,ChessColor.WHITE);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }




    public static ChessColor getCurrentColor() {
        return currentColor;
    }




    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }


    public boolean ifPassBy(ChessComponent chess1,ChessComponent chess2){
        boolean a = chess1.getChessboardPoint().getY()+1 == chess2.getChessboardPoint().getY();
        boolean b = chess1.getChessboardPoint().getY()-1 == chess2.getChessboardPoint().getY();
        boolean c = chess1.getChessboardPoint().getX()+1 == chess2.getChessboardPoint().getX();
        boolean d = chess1.getChessboardPoint().getX()-1 == chess2.getChessboardPoint().getX();
        if (chess2 instanceof EmptySlotComponent && ((a&&c) ||(a&&d)||(b&&c)||(b&&d)) && chess1 instanceof PawnChessComponent){
            return true;
        }
        return false;
    }

    public void eating(ChessComponent chess1,ChessComponent chess2,boolean plusOrMinus){
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        ChessComponent capturedPawn;
        if (plusOrMinus) {
            capturedPawn = chessComponents[row1][col1 + 1];
        }else {
            capturedPawn = chessComponents[row1][col1-1];
        }
        remove(capturedPawn);
        add(capturedPawn = new EmptySlotComponent(capturedPawn.getChessboardPoint(),capturedPawn.getLocation(),clickController,CHESS_SIZE));
        chessComponents[row1][col1+1] = capturedPawn;
        repaint();
    }

    //判断将军（下子即将）
    public boolean isChecked(ChessComponent attacker){
        ChessboardPoint checkedKing = new ChessboardPoint(8,8);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() != currentColor){
                    checkedKing = new ChessboardPoint(i,j);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                attacker = chessComponents[i][j];
                if (attacker.canMoveTo(chessComponents,checkedKing) && attacker.getChessColor() == currentColor){

                    return true;
                }
            }
        }
        return false;
    }

    //判断将军（有子可将）
    public boolean isChecked1(ChessComponent attacker){
        ChessboardPoint checkedKing = new ChessboardPoint(8,8);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == currentColor){
                    checkedKing = new ChessboardPoint(i,j);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                attacker = chessComponents[i][j];
                if (attacker.canMoveTo(chessComponents,checkedKing) && attacker.getChessColor() != currentColor){

                    return true;
                }
            }
        }
        return false;
    }


    //FIXME: store what we have done in the arraylist storingSteps
    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        if (ifPassBy(chess1, chess2)) {
            if (chess1.getChessColor() == ChessColor.BLACK) {
                if (chess1.getChessboardPoint().getY() + 1 == chess2.getChessboardPoint().getY()) {
                    eating(chess1, chess2, true);
                }
                if (chess1.getChessboardPoint().getY() - 1 == chess2.getChessboardPoint().getY()) {
                    eating(chess1, chess2, false);
                }
            } else if (chess1.getChessColor() == ChessColor.WHITE) {
                if (chess1.getChessboardPoint().getY() + 1 == chess2.getChessboardPoint().getY()) {
                    eating(chess1, chess2, true);
                }
                if (chess1.getChessboardPoint().getY() - 1 == chess2.getChessboardPoint().getY()) {
                    eating(chess1, chess2, false);
                }
            }
            chess1.swapLocation(chess2);
            int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
            chessComponents[row1][col1] = chess1;
            int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
            chessComponents[row2][col2] = chess2;
            if (chess1 instanceof RookChessComponent || chess1 instanceof KingChessComponent) {
                chess1.setStayTillNow(false);
            }
            storeThisStep(row1, col1, row2, col2);
            chess1.repaint();
            chess2.repaint();
            changeLabel();
        } else if (!(ifPassBy(chess1,chess2))) {
            if (chess2 instanceof KingChessComponent) {
                if (currentColor == ChessColor.BLACK) {
                    blackWin = true;
                } else if (currentColor == ChessColor.WHITE) {
                    whiteWin = true;
                }
            }
            if (!(chess2 instanceof EmptySlotComponent)) {
                remove(chess2);
                add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
                //storeThisStep();
            }
            chess1.swapLocation(chess2);
            int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
            chessComponents[row1][col1] = chess1;
            int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
            chessComponents[row2][col2] = chess2;
            if (chess1 instanceof RookChessComponent || chess1 instanceof KingChessComponent) {
                chess1.setStayTillNow(false);
            }
            storeThisStep(row1, col1, row2, col2);
            chess1.repaint();
            chess2.repaint();
            //将军警告
            if (isChecked(chess1)) {
                if (currentColor == ChessColor.BLACK) {
                    dead1("The white king is checked!");
                }
                if (currentColor == ChessColor.WHITE) {
                    dead1("The black king is checked!");
                }
            }else if (!isChecked(chess1)){
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                       ChessComponent chess =  chessComponents[i][j];
                       if (isChecked1(chess)){
                           if (currentColor == ChessColor.WHITE) {
                               dead1("The white king is checked!");
                               return;
                           }
                           if (currentColor == ChessColor.BLACK) {
                               dead1("The black king is checked!");
                               return;
                           }
                       }
                    }
                }
            }
            //判断胜负
            String warning1 = "The black win!";
            String warning2 = "The white win!";
            if (blackWin){
                dead2(warning1);
                Reset();
//                storingSteps.clear();
//                storingSteps.add("R0H0B0Q0K0B0H0R0#P0P0P0P0P0P0P0P0#e0e0e0e0e0e0e0e0#e0e0e0e0e0e0e0e0#e0e0e0e0e0e0e0e0#e0e0e0e0e0e0e0e0#p0p0p0p0p0p0p0p0#r0h0b0q0k0b0h0r0#1#0000#");

                setCurrentColor(ChessColor.BLACK);
                blackTurn();
                repaint();
                blackWin = false;
            }else if (whiteWin){
                dead2(warning2);
                Reset();
//                storingSteps.clear();
//                storingSteps.add("R0H0B0Q0K0B0H0R0#P0P0P0P0P0P0P0P0#e0e0e0e0e0e0e0e0#e0e0e0e0e0e0e0e0#e0e0e0e0e0e0e0e0#e0e0e0e0e0e0e0e0#p0p0p0p0p0p0p0p0#r0h0b0q0k0b0h0r0#1#0000#");
                setCurrentColor(ChessColor.BLACK);
                whiteTurn();
                whiteWin = false;
                repaint();
            }
            changeLabel();
        }
    }
    public void whiteTurn() {
        statusLabel.setText("WHITE'S ROUND");
    }
    public void blackTurn(){
        statusLabel.setText("BLACK'S ROUND");
    }


    public void redo(){
        //悔棋步骤可能也会存进storingSteps里面

        if (numberOfStepsNow==0) {
            numberOfStepsNow = storingSteps.size() - 2;//to get the index
        }else {
            numberOfStepsNow--;
        }


        if (numberOfStepsNow<0){
            JOptionPane.showMessageDialog(this,"别点啦，没有技能的,初始化了哥");
            numberOfStepsNow=0;
            return;

        }
        System.out.println(storingSteps.get(numberOfStepsNow));
        loadLastOne(storingSteps.get(numberOfStepsNow).toString());
        repaint();
        changeLabel();

    }


    public void loadLastOne(String loadLastBoard){
        String[] loadList=loadLastBoard.split("#");

        removeAll();

        initiateEmptyChessboard();

        System.out.println("empty board is ready");
        repaint();
        System.out.println("refreshed");

        for (int i = 0; i < 8; i++) {
            String lineHere = loadList[i];
            System.out.println(lineHere);
            for (int j = 0; j < 8; j++) {

                char puttin=lineHere.charAt(2*j);
                switch (puttin){
                    case 'K':
                        initKingOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'k':
                        initKingOnBoard(i,j,ChessColor.WHITE);;
                        break;
                    case 'Q':
                        initQueenOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'q':
                        initQueenOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'R':
                        initRookOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'r':
                        initRookOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'B':
                        initBishopOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'b':
                        initBishopOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'H':
                        initKnightOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'h':
                        initKnightOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'P':
                        initPawnOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'p':
                        initPawnOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'e':
                        break;
                }
            }
        }
        if (loadList[8].equals("1")){
            System.out.println("load of correct color");
            currentColor=ChessColor.WHITE;
        }else if (loadList[8].equals("0")){
            System.out.println("load of correct color");
            currentColor=ChessColor.BLACK;
        }else {
            JOptionPane.showMessageDialog(this,"bad save file\n小心封你号");
        }


        repaint();
    }


    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
            currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row,int col, ChessColor color){
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row ,int col,ChessColor color){
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row ,int col,ChessColor color){
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKnightOnBoard(int row ,int col,ChessColor color){
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row ,int col,ChessColor color){
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setStroke(new BasicStroke(4f));

    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }
    //FIXME:FIX LOAD GAME HERE
//    public void loadGame(List<String> chessData) {
//
//        String loadLastBoard;
//        loadLastBoard=chessData.get(chessData.size()-1);
//
////        chessData.forEach(System.out::println);//debugger
//
//
//
//    }

    public static ArrayList<String> getStoringSteps() {
        return storingSteps;
    }

    //FIXME:FIX LOAD GAME HERE
    public void loadGame(List<String> chessData) {
        String loadLastBoard;
        loadLastBoard=chessData.get(chessData.size()-1);
        loadLastOne(loadLastBoard);

    }
//FIXME:LOAD HERE LOOK AT ME

    private void storeThisStep(int x1,int y1,int x2,int y2){
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j]instanceof RookChessComponent){
                    if (chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)){
                        str.append("R");
                    }else {
                        str.append("r");
                    }
                    String bll;
                    if (chessComponents[i][j].isStayTillNow()){
                        bll="0";
                    }else {
                        bll="1";
                    }
                    str.append(bll);
                }
                else if (chessComponents[i][j]instanceof BishopChessComponent){
                    if (chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)){
                        str.append("B");
                    }else {
                        str.append("b");
                    }
                    str.append(0);
                }
                else if (chessComponents[i][j]instanceof KingChessComponent){
                    if (chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)){
                        str.append("K");
                    }else {
                        str.append("k");
                    }
                    String bll;
                    if (chessComponents[i][j].isStayTillNow()){
                        bll="0";
                    }else {
                        bll="1";
                    }
                    str.append(bll);
                }
                else if (chessComponents[i][j]instanceof KnightChessComponent){
                    if (chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)){
                        str.append("H");
                    }else {
                        str.append("h");
                    }
                    str.append(0);
                    System.out.println("knight");//debugger
                }
                else if (chessComponents[i][j]instanceof PawnChessComponent){
                    if (chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)){
                        str.append("P");
                    }else {
                        str.append("p");
                    }
                    str.append(0);
                }
                else if (chessComponents[i][j]instanceof QueenChessComponent){
                    if (chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)){
                        str.append("Q");
                    }else {
                        str.append("q");
                    }
                    str.append(0);
                }
                else if (chessComponents[i][j]instanceof EmptySlotComponent){
                    str.append("e");
                    str.append(0);
                }

            }
            str.append("#");//easy to use split in the future
        }
        //store whose turn now
        //black is 1, white is 0
        int turn;
        if (currentColor==ChessColor.BLACK){
            turn=1;
        }else {
            turn=0;
        }
        str.append(turn);
        str.append("#");
        str.append(x1);
        str.append(y1);
        str.append(x2);
        str.append(y2);
        str.append("#");

        // source and des is stored in each step, format x1 y1 x2 y2 which are source and destination's location
        storingSteps.add(str.toString());
    }
    public void changeQ(ChessboardPoint source , ChessComponent chess1){
        int row = source.getX();
        int col = source.getY();
        if (!(chessComponents[row][col] instanceof PawnChessComponent)) {
            remove(chessComponents[row][col]);
            chessComponents[row][col] = chess1;
        }
        if (chessComponents[row][col] instanceof PawnChessComponent){
            chess1 = chessComponents[row][col];
            remove(chess1);
        }
//        remove(chess1);
        if (currentColor == ChessColor.BLACK) {
            add(chess1= new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.WHITE, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
        if (currentColor == ChessColor.WHITE){
            add(chess1 = new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.BLACK, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
    }

    public void changeB(ChessboardPoint source , ChessComponent chess1){
        int row = source.getX();
        int col = source.getY();
        if (!(chessComponents[row][col] instanceof PawnChessComponent)) {
            remove(chessComponents[row][col]);
            chessComponents[row][col] = chess1;
        }
        if (chessComponents[row][col] instanceof PawnChessComponent){
            chess1 = chessComponents[row][col];
            remove(chess1);
        }
        //remove(chess1);
        if (currentColor == ChessColor.BLACK) {
            add(chess1 = new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.WHITE, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
        if (currentColor == ChessColor.WHITE){
            add(chess1 = new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.BLACK, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
    }

    public void changeK(ChessboardPoint source , ChessComponent chess1){
        int row = source.getX();
        int col = source.getY();
        if (!(chessComponents[row][col] instanceof PawnChessComponent)) {
            remove(chessComponents[row][col]);
            chessComponents[row][col] = chess1;
        }
        if (chessComponents[row][col] instanceof PawnChessComponent){
            chess1 = chessComponents[row][col];
            remove(chess1);
        }
//        remove(chess1);
        if (currentColor == ChessColor.WHITE) {
            add(chess1 = new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.BLACK, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
        if (currentColor == ChessColor.BLACK) {
            add(chess1 = new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.WHITE, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
    }

    public void changeR(ChessboardPoint source , ChessComponent chess1){
        int row = source.getX();
        int col = source.getY();
        if (!(chessComponents[row][col] instanceof PawnChessComponent)) {
            remove(chessComponents[row][col]);
            chessComponents[row][col] = chess1;
        }
        if (chessComponents[row][col] instanceof PawnChessComponent){
            chess1 = chessComponents[row][col];
            remove(chess1);
        }
        if (currentColor == ChessColor.WHITE){
            add(chess1 = new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.BLACK, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
        if (currentColor == ChessColor.BLACK) {
            add(chess1 = new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), ChessColor.WHITE, clickController, getCHESS_SIZE()));
            chess1.repaint();
        }
    }
}

