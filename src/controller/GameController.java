package controller;

import view.Chessboard;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            if (!path.substring(path.length()-3).equals("txt")){
                JOptionPane.showMessageDialog(null,"文件格式错误,错误代码104");
                return null;
            }

            List<String> chessData = Files.readAllLines(Paths.get(path));
            correctLoadFile(chessData);
            chessboard.loadGame(chessData);

            //System.out.println("lgff");

            return chessData;
        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;


    }


    public Chessboard getChessboard() {
        return chessboard;
    }

    private void correctLoadFile(List<String> chessData){
        if (chessData.get(chessData.size()-1).length()!=8*17+2+5){
            System.out.println(chessData.get(chessData.size()));
            JOptionPane.showMessageDialog(null,"棋盘错误,错误代码101");
        }
        if (!checkComponent(chessData)){
            JOptionPane.showMessageDialog(null,"棋子错误,错误代码102");
        }
        if (chessData.get(chessData.size()-1).charAt(8*17)!='0'&&chessData.get(chessData.size()-1).charAt(8*17)!='1'){
            System.out.println(chessData.get(chessData.size()-1).charAt(8*17));
            JOptionPane.showMessageDialog(null,"行棋方错误,错误代码103");
        }

    }
    private boolean checkComponent(List<String> chessData){
        for (int i = 0; i < chessData.size(); i++) {
            for (int j = 0; j < 8*17; j++) {
                char c=chessData.get(i).charAt(j);
                if (      c=='R'
                        ||c=='r'
                        ||c=='P'
                        ||c=='p'
                        ||c=='Q'
                        ||c=='q'
                        ||c=='K'
                        ||c=='k'
                        ||c=='H'
                        ||c=='h'
                        ||c=='e'
                        ||c=='#'
                        ||c=='0'
                        ||c=='1'
                        ||c=='b'
                        ||c=='B'){
                    continue;
                }else {
                    System.out.println(c);
                    return false;
                }
            }
        }
        return true;
    }

}
