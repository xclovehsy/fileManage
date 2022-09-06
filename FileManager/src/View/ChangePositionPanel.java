package View;

import FileOperation.FileController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-11 21:54
 */

public class ChangePositionPanel extends JPanel {
    static JTextField field = null;
    public MainFrame mainFrame = null;

    /**
     * 构造函数
     */
    public ChangePositionPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        load();
    }

    /**
     * 加载界面
     */
    private void load(){
        field = new JTextField(55);
        field.setFont(new Font(null, Font.BOLD, 16));   // 设置字体
        field.setSize(850, 100);
        field.setEditable(true);
        add(field);

        JButton button = new JButton("跳转");
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = field.getText();
                if(path.compareTo("")!=0){
                    if(path.lastIndexOf("\\") != path.length()-1){
                        path+= "\\";
                    }

                    File file = new File(path);
                    if(file.exists() && file.isDirectory()){
//                        System.out.println("跳转到 -> " + path);
                        FileController.setPath(path);
                    }else if(file.exists() && file.isFile()){
//                        System.out.println("打开文件 -> " + file.getName());
                        mainFrame.openFile(file);
                    }
                    MainFrame.renew();
                }
            }
        });
    }

    public static void renew(){
        field.setText(FileController.getPath());
    }
}
