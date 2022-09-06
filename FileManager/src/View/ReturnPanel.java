package View;

import FileOperation.FileController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author ���
 * @version 1.0
 * @date 2021-11-11 21:54
 */

public class ReturnPanel extends JPanel {

    /**
     * ���캯��
     */
    public ReturnPanel() {
        super();
        Load();
    }

    /**
     * ���غ���
     */
    private void Load(){
        setLayout(new FlowLayout());

        JLabel label01 = new JLabel("    ");
        label01.setVisible(true);
        add(label01);

        JButton diskButton = new JButton("���̽���");
        add(diskButton);
        JLabel label02 = new JLabel("    ");
        label02.setVisible(true);
        add(label02);

        JButton returnButton = new JButton("����");
        add(returnButton);

        JLabel label03 = new JLabel("    ");
        label03.setVisible(true);
        add(label03);

        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                File file = new File(FileController.getPath());
                String father = file.getParent();

                //test
//                System.out.println("���� -> " + father);

                if(father != null){
                    FileController.setPath(father);
                }else{
                    FileController.setPath("");
                }
                MainFrame.renew();

            }
        });

        diskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FileController.setPath("");
                MainFrame.renew();
            }
        });

    }
}
