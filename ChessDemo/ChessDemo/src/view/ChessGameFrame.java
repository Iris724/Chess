package view;

import controller.GameController;
import model.ChessComponent;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static view.Chessboard.getCurrentColor;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    //创建一个容器
//    Container ct;
    //创建背景面板。
//    BackgroundPanel bgp;
    public Mode mode;

    private GameController gameController;
    private ChessColor currentColor = ChessColor.WHITE;
    private ChessComponent chessComponent;
    private Chessboard chessboard;
    static JLabel statusLabel = new JLabel();
    static JLabel countingTime = new JLabel();
    static JButton button1 = new JButton("Load");
    static JButton button2 = new JButton("Reset");
    static JButton button3 = new JButton("Save");
    static JButton button4 = new JButton("Redo");
    static JButton button5 = new JButton("Play Back");
    static JButton change = new JButton("Change Theme");
    static JLabel label = new JLabel();
    public static boolean isChanged;
    public static int time = 60;
    public ChessGameFrame chessGameFrame;
    Music audioPlayWave1 = new Music("沐烟清 - 四小天鹅舞曲（柴可夫斯基）.wav");

    public GameController getGameController() {
        return gameController;
    }

    public ChessGameFrame(int width, int height,Mode mode) {

        setTitle("2022 CS102A Project ---- Chess"); //设置标题
        this.setLayout(null);

        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        this.mode=mode;


        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        audioPlayWave1.start();
        audioPlayWave1.Stop();

        addChessboard();
        addLabel();
        addLoadButton();
        addResetButton();
        addSaveButton();
        addRedoButton();
        addPlayBackButton();
        addChangeButton();
        addMenu();
        addBackground();
    }

    /**
     * 在游戏面板中添加棋盘和计时器
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE,mode);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        countingTime.setLocation(HEIGTH, 10);
        countingTime.setSize(200, 60);
        countingTime.setFont(new Font("Rockwell", Font.BOLD, 30));
        countingTime.setForeground(Color.BLACK);
        add(countingTime);
        setVisible(true);
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                countingTime.setText("Time left: " + time);
                time--;
                if (time < 0) {
                    countingTime.setText("Time Over");
                    changeLabel();
                    chessboard.swapColor();
                    time =60;
                }
            }
        }, 0, 1000);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel.setText("WHITE'S ROUND");
        statusLabel.setLocation(10, 10);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabel.setForeground(Color.pink);
        this.setVisible(true);
        add(statusLabel);
    }


    /**
     * 在游戏面板中增加一个按钮
     */

    //load
    private void addLoadButton() {
        button1.setLocation(HEIGTH, HEIGTH / 10);
        button1.setSize(200, 40);
        button1.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.setVisible(true);
        add(button1);
        button1.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
            chessboard.setCurrentColor(ChessColor.WHITE);
            statusLabel.setText("WHITE's round");
            time =60;
        });
    }

    //FIXME: LOAD TXT SHOULD HAVE CHANGES
    //reset
    private void addResetButton() {
        button2.addActionListener((e) -> {
            System.out.println("reset");
            String path = "resource/reset.txt";
            gameController.loadGameFromFile(path);
            gameController.getChessboard().getStoringSteps().clear();
            statusLabel.setText("WHITE'S ROUND");
            time = 60;
        });
        button2.setLocation(HEIGTH, HEIGTH / 10 + 50);
        button2.setSize(200, 40);
        button2.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.setVisible(true);
        add(button2);
    }


    //FIXME: THE UNMOVED SITUATION WILL BE STORED AS NULL
    //save
    private void addSaveButton() {
        button3.addActionListener((e) -> {
            //JOptionPane.
            System.out.println("save");
            ArrayList<String> transferToText = new ArrayList<>();
            transferToText = gameController.getChessboard().getStoringSteps();
            //this is an auto filer format: save1.txt 1represent a positive number
            writeFiles(transferToText);

        });
        button3.setLocation(HEIGTH, HEIGTH / 10 + 100);
        button3.setSize(200, 40);
        button3.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.setVisible(true);
        add(button3);
    }

    //redo
    private void addRedoButton() {
        button4.addActionListener(e -> {
            System.out.println("Click Redo");//debugger

            gameController.getChessboard().redo();
        });
        button4.setLocation(HEIGTH, HEIGTH / 10 + 150);
        button4.setSize(200, 40);
        button4.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.setVisible(true);
        add(button4);
    }


    //playback
    private void addPlayBackButton() {
        button5.addActionListener(e -> {
            System.out.println("Play back");
            Thread newTry = new Thread(new MyRunnable(this));
            newTry.start();
        });
        button5.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button5.setSize(200, 40);
        button5.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.setVisible(true);
        add(button5);
    }


    // change theme
    private void addChangeButton() {
        change.setLocation(HEIGTH, HEIGTH / 10 + 300);
        change.setSize(200, 40);
        change.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.setVisible(true);
        add(change);
        AtomicInteger n = new AtomicInteger();
        change.addActionListener((e) -> {
            n.getAndIncrement();
            if (n.get() % 2 == 1) {
                ImageIcon image1;
                isChanged = true;
                this.repaint();
                statusLabel.setForeground(Color.cyan);
                countingTime.setForeground(Color.cyan);
                button1.setBackground(Color.cyan.darker());
                button1.setOpaque(true);
                button1.setBorderPainted(true);
                button2.setBackground(Color.cyan.darker());
                button2.setOpaque(true);
                button2.setBorderPainted(true);
                button3.setBackground(Color.cyan.darker());
                button3.setOpaque(true);
                button3.setBorderPainted(true);
                button4.setBackground(Color.cyan.darker());
                button4.setOpaque(true);
                button4.setBorderPainted(true);
                button5.setBackground(Color.cyan.darker());
                button5.setOpaque(true);
                button5.setBorderPainted(true);
                change.setBackground(Color.cyan.darker());
                change.setOpaque(true);
                change.setBorderPainted(true);
                ImageIcon icon2 = new ImageIcon("./images/Background.jpg");
                label.setIcon(icon2);
                icon2.setImage(icon2.getImage().getScaledInstance(1000, 760, Image.SCALE_DEFAULT));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setSize(1000, 760);
                label.setOpaque(true);
                add(label);
                this.repaint();

            } else if (n.get() % 2 == 0) {
                ImageIcon image2;
                isChanged = false;
                this.repaint();
                statusLabel.setForeground(Color.PINK);
                countingTime.setForeground(Color.BLACK);
                button1.setBackground(Color.WHITE);
                button1.setOpaque(true);
                button1.setBorderPainted(true);
                button2.setBackground(Color.WHITE);
                button2.setOpaque(true);
                button2.setBorderPainted(true);
                button3.setBackground(Color.WHITE);
                button3.setOpaque(true);
                button3.setBorderPainted(true);
                button4.setBackground(Color.WHITE);
                button4.setOpaque(true);
                button4.setBorderPainted(true);
                button5.setBackground(Color.WHITE);
                button5.setOpaque(true);
                button5.setBorderPainted(true);
                change.setBackground(Color.WHITE);
                change.setOpaque(true);
                change.setBorderPainted(true);
                ImageIcon icon1 = new ImageIcon("./images/Background1.jpg");
                label.setIcon(icon1);
                icon1.setImage(icon1.getImage().getScaledInstance(1000, 760, Image.SCALE_DEFAULT));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setSize(1000, 760);
                label.setOpaque(true);
                add(label);
                this.repaint();
            }
        });
    }

    //todo: 下拉选项菜单
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem profile = new JMenuItem("Simple introduction");
        JMenuItem instruction = new JMenuItem("Introduction");
        JMenuItem play = new JMenuItem("Start audio");
        JMenuItem stop = new JMenuItem("Stop audio");

        this.setJMenuBar(menuBar);


        play.addActionListener(e -> {
            System.out.println("play audio");
            audioPlayWave1.Play();
        });
        stop.addActionListener(e -> {

            System.out.println("stop audio");
            audioPlayWave1.Stop();
        });

        menuBar.add(menu);

        menu.add(profile);
        menu.add(instruction);
        menu.add(play);
        menu.add(stop);

        menuBar.setVisible(true);


        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"再玩一会", "我去卷了"};
                int confirm = JOptionPane.showOptionDialog(ChessGameFrame.this, "去卷了嘛", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (confirm == 1) {
                    System.exit(0);
                }
            }
        });
    }

    private void addBackground() {
        ImageIcon icon = new ImageIcon("./images/Background1.jpg");
        label.setIcon(icon);
        icon.setImage(icon.getImage().getScaledInstance(1000, 760, Image.SCALE_DEFAULT));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(1000, 760);
//            this.setLocationRelativeTo(null);
        label.setOpaque(true);
        add(label);
    }

    //        bgp= new BackgroundPanel((new ImageIcon("images\\Background1.jpg")).getImage());
//        bgp.setBounds(0, 0, WIDTH, HEIGTH);
//        ct.add(bgp);


    /**
     * Change the player
     */

    protected static void changeLabel() {
        if (getCurrentColor() == ChessColor.WHITE) {
            statusLabel.setText("BLACK'S ROUND");
        }
        if (getCurrentColor() == ChessColor.BLACK) {
            statusLabel.setText("WHITE'S ROUND");
        }
    }

    public void setTime2(int i) {
        time = i;

    }


    private void writeFiles(ArrayList<String> transfer) {
        int num = 1;
        FileWriter writer = null;


        String path = "resource/save" + num + ".txt";
        try {
            File file = new File(path);
            System.out.println("01");
            while (file.exists()) {
                num++;
                path = new String("resource/save" + num + ".txt");
                file = new File(path);
                System.out.println("02");
            }
            file.createNewFile();
            writer = new FileWriter(path);
            System.out.println("03");
            System.out.println("size");
            System.out.println(transfer.size());
            for (int i = 0; i < transfer.size(); i++) {

                String str = transfer.get(i).toString() + "\n";

                System.out.println(transfer.size());
                System.out.println("04");
                writer.write(str);

            }
            writer.close();
            System.out.println(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dead1(String warning) {
        ImageIcon icon = new ImageIcon("./images/checked(1).png");
        JOptionPane.showMessageDialog(null, warning, "CHECK", JOptionPane.PLAIN_MESSAGE, icon);
    }

    public static void dead2(String warning) {
        ImageIcon icon = new ImageIcon("./images/Win.png");
        int result = JOptionPane.showOptionDialog(null, warning, "Winner", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, new String[]{"Restart the game"}, null);
    }
}
