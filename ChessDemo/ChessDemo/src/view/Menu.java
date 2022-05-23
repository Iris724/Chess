package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame implements ActionListener {
    private JButton start;
    private JRadioButton normal, humanMachines, cheat;
    private JTextField width, height, mines;

    //创建一个主菜单
    public Menu(String title) {
        ImageIcon image;
        JLabel label;
        JLayeredPane pane = new JLayeredPane();  // 分层网格
        JPanel panel1 = new JPanel();
        image = new ImageIcon("images\\BackgroundMenu.jpg");
        //设置组件透明
        label = new JLabel(image);		//把背景图片添加到标签里
        panel1.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());   //把标签设置为和图片等高等宽
        panel1 = (JPanel)this.getContentPane(); 	//把我的面板设置为内容面板
        panel1.add(label);

        // 设置菜单标题。
        setTitle(title);
        // 创建菜单子标题。
        JLabel subtitle = new JLabel("Choose one mode");
        //由 x和 y指定左上角的新位置，由 width 和 height 指定新的大小。
        subtitle.setSize(350,50 );
        subtitle.setFont(new Font("Rockwell", Font.BOLD, 40));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBounds(320, 10, 380, 70);
        subtitle.setOpaque(false);
        pane.add(subtitle);

        //一般选择按钮。
        normal = new JRadioButton("General mode");
        normal.setSize(300, 30);
        normal.setFont(new Font("Rockwell", Font.ITALIC, 30));
        normal.setForeground(Color.WHITE);
        normal.setBounds(320, 100, 300, 60);
        normal.setOpaque(false);
        pane.add(normal);

        humanMachines = new JRadioButton("Human-machine mode");
        humanMachines.setSize(370, 30);
        humanMachines.setFont(new Font("Rockwell", Font.ITALIC, 30));
        humanMachines.setForeground(Color.WHITE);
        humanMachines.setBounds(320, 210, 370, 60);
        humanMachines.setOpaque(false);
        pane.add(humanMachines);

//        cheat = new JRadioButton("Cheat ");
//        cheat.setBounds(320, 400, 300, 60);
//        cheat.setSize(300, 30);
//        cheat.setFont(new Font("Rockwell", Font.ITALIC, 30));
//        cheat.setForeground(Color.WHITE);
//        cheat.setOpaque(false);
//        pane.add(cheat);

        //游戏用户排行
        JLabel player1 = new JLabel("No1. Pokemon");
        player1.setSize(350,50 );
        player1.setFont(new Font("Rockwell", Font.BOLD, 30));
        player1.setForeground(Color.WHITE);
        player1.setBounds(320, 300, 380, 70);

        JLabel player2 = new JLabel("No2. BigBinary");
        player2.setSize(350,50 );
        player2.setFont(new Font("Rockwell", Font.BOLD, 30));
        player2.setForeground(Color.WHITE);
        player2.setBounds(320, 350, 380, 70);

        JLabel player3 = new JLabel("No3. ChessChampion");
        player3.setSize(350,50 );
        player3.setFont(new Font("Rockwell", Font.BOLD, 30));
        player3.setForeground(Color.WHITE);
        player3.setBounds(320, 400, 380, 70);

        pane.add(player1);
        pane.add(player2);
        pane.add(player3);

        // 创建 "开始游戏" 选择按钮。
        start = new JButton("Start Playing");
        start.setSize(300, 30);
        start.setFont(new Font("Rockwell", Font.BOLD, 30));
        start.setForeground(Color.BLACK);
        start.setBounds(320, 500, 300, 60);
        start.setOpaque(true);
        pane.add(start);

        pane.add(panel1,JLayeredPane.DEFAULT_LAYER);
        pane.add(subtitle,JLayeredPane.MODAL_LAYER);
        pane.add(normal, JLayeredPane.MODAL_LAYER);
        pane.add(humanMachines, JLayeredPane.MODAL_LAYER);
        pane.add(start, JLayeredPane.MODAL_LAYER);



        this.setBounds(1000,618,image.getIconWidth(), image.getIconHeight());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayeredPane(pane);
        this.setSize(1000,680);
        this.setVisible(true);

        // 在每个按键上添加监听事件。

        normal.addActionListener(this);
        humanMachines.addActionListener(this);
        start.addActionListener(this);

        // 确保单选。
        ButtonGroup group = new ButtonGroup();
        group.add(normal);
        group.add(humanMachines);
        group.add(cheat);

        // 初始化菜单实例。
        normal.setSelected(true);
        setSize(1000, 681);
        //将容器的布局设为绝对布局
        setLayout(null);
        //可视化窗口
        setVisible(true);
        //大小不能改变
        setResizable(false);
        //关闭按钮，点击它时，退出程序，并结束进程
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //显示坐标
        setLocationRelativeTo(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Mode mode=null;
        if (e.getSource() == start) {

        }
        if (normal.isSelected()){
            mode=Mode.Normal;
        }else if (humanMachines.isSelected()){
            mode=Mode.Ai1;
        }



        this.dispose();
        ChessGameFrame mainFrame = new ChessGameFrame(1000, 760,mode);
        mainFrame.setVisible(true);
    }
}
