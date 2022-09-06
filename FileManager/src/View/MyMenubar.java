package View;

import FileOperation.FileController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ���
 * @version 1.0
 * @date 2021-11-11 21:55
 */

public class MyMenubar extends JMenuBar {
    public static MainFrame mainFrame;
    public static JMenu fileMenu, editMenu, findMenu, newItem = new JMenu("�½�");;
    public static JMenuItem openItem, exitItem, newFileItem, newFolderItem, renewItem;
    public static JMenuItem renameItem = new JMenuItem("������"), removeItem = new JMenuItem("ɾ��"),copyItem = new JMenuItem("����"),pasteItem = new JMenuItem("ճ��");
    public static JCheckBoxMenuItem hideItem;

    /**
     * ���캯��
     */
    public MyMenubar(MainFrame mainFrame) {
        super();
        MyMenubar.mainFrame = mainFrame;
        Load();
    }

    /**
     * ����������
     */
    private void Load(){
        fileMenu = new JMenu("�ļ�");
        editMenu = new JMenu("�༭");
        findMenu = new JMenu("�鿴");

        JLabel label01 = new JLabel("   ");
        label01.setVisible(true);
        add(label01);
        add(fileMenu);
        add(editMenu);
        add(findMenu);

        JLabel label02 = new JLabel("    ���ߣ���� 2505327613@qq.com");
        label02.setVisible(true);
        add(label02);


        //���á��ļ���һ���˵�
        openItem = new JMenuItem("��");
        exitItem = new JMenuItem("�˳�");
        fileMenu.add(openItem);
        fileMenu.add(newItem);
        fileMenu.add(renameItem);
        fileMenu.add(removeItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        //���á��½��������˵�
        newFileItem = new JMenuItem("�½��ļ�");
        newFolderItem = new JMenuItem("�½��ļ���");
        newItem.add(newFileItem);
        newItem.add(newFolderItem);

        //���á��༭��һ���˵�
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        //���á��鿴��һ���˵�
        hideItem = new JCheckBoxMenuItem("�鿴�����ļ�");
        renewItem = new JMenuItem("ˢ�½���");
        findMenu.add(hideItem);
        findMenu.add(renewItem);

        /*
         * �˵���ĵ��/״̬�ı��¼�����
         */
        /**
         * ˢ�½��水ť�����Ӧ�¼�
         */
        renewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.renew();
            }
        });

        /**
         * �˳�����ť�����Ӧ�¼�
         */
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /**
         * Ϊ�Ƿ�鿴�����ļ���ť�����Ӧ�¼�
         */
        hideItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController.hideFile = hideItem.isSelected();
                MainFrame.renew();
            }
        });

        /**
         * Ϊ�����ļ��С��ļ���ť�����Ӧʱ��
         */
        newFolderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.createNewFolder();
            }
        });

        newFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.createNewFile();
            }
        });

        /**
         * Ϊ���ļ���ť�����Ӧ�¼�
         */
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.openFile(TableScrollPanel.getSelectFile());
            }
        });

        /**
         * Ϊɾ���ļ������ļ��а�ť�����Ӧ�¼�
         */
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.removeFile();
            }
        });

        /**
         * ��������ť�����Ӧ�¼�
         */
        renameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.renameFile();
            }
        });

        /**
         * ���ư�ť�����Ӧ�¼�
         */
        copyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController.setCopyFile(TableScrollPanel.getSelectFile());
            }
        });

        /**
         * ճ����ť�����Ӧ�¼�
         */
        pasteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.copyFile();
            }
        });


    }

    public static void setOFF(){
//        openItem.setEnabled(false);
        newItem.setEnabled(false);
        renameItem.setEnabled(false);
        removeItem.setEnabled(false);
        copyItem.setEnabled(false);
        pasteItem.setEnabled(false);
    }

    public static void setOpen(){
        newItem.setEnabled(true);
        renameItem.setEnabled(true);
        removeItem.setEnabled(true);
        copyItem.setEnabled(true);
        pasteItem.setEnabled(true);
    }
}
