package View;

import FileOperation.FileController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-11 21:55
 */

public class MyMenubar extends JMenuBar {
    public static MainFrame mainFrame;
    public static JMenu fileMenu, editMenu, findMenu, newItem = new JMenu("新建");;
    public static JMenuItem openItem, exitItem, newFileItem, newFolderItem, renewItem;
    public static JMenuItem renameItem = new JMenuItem("重命名"), removeItem = new JMenuItem("删除"),copyItem = new JMenuItem("复制"),pasteItem = new JMenuItem("粘贴");
    public static JCheckBoxMenuItem hideItem;

    /**
     * 构造函数
     */
    public MyMenubar(MainFrame mainFrame) {
        super();
        MyMenubar.mainFrame = mainFrame;
        Load();
    }

    /**
     * 加载主界面
     */
    private void Load(){
        fileMenu = new JMenu("文件");
        editMenu = new JMenu("编辑");
        findMenu = new JMenu("查看");

        JLabel label01 = new JLabel("   ");
        label01.setVisible(true);
        add(label01);
        add(fileMenu);
        add(editMenu);
        add(findMenu);

        JLabel label02 = new JLabel("    作者：徐聪 2505327613@qq.com");
        label02.setVisible(true);
        add(label02);


        //设置“文件”一级菜单
        openItem = new JMenuItem("打开");
        exitItem = new JMenuItem("退出");
        fileMenu.add(openItem);
        fileMenu.add(newItem);
        fileMenu.add(renameItem);
        fileMenu.add(removeItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        //设置“新建”二级菜单
        newFileItem = new JMenuItem("新建文件");
        newFolderItem = new JMenuItem("新建文件夹");
        newItem.add(newFileItem);
        newItem.add(newFolderItem);

        //设置“编辑”一级菜单
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        //设置“查看”一级菜单
        hideItem = new JCheckBoxMenuItem("查看隐藏文件");
        renewItem = new JMenuItem("刷新界面");
        findMenu.add(hideItem);
        findMenu.add(renewItem);

        /*
         * 菜单项的点击/状态改变事件监听
         */
        /**
         * 刷新界面按钮添加响应事件
         */
        renewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.renew();
            }
        });

        /**
         * 退出程序按钮添加响应事件
         */
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /**
         * 为是否查看隐藏文件按钮添加相应事件
         */
        hideItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController.hideFile = hideItem.isSelected();
                MainFrame.renew();
            }
        });

        /**
         * 为创建文件夹、文件按钮添加相应时间
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
         * 为打开文件按钮添加相应事件
         */
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.openFile(TableScrollPanel.getSelectFile());
            }
        });

        /**
         * 为删除文件或者文件夹按钮添加相应事件
         */
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.removeFile();
            }
        });

        /**
         * 重命名按钮添加相应事件
         */
        renameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.renameFile();
            }
        });

        /**
         * 复制按钮添加相应事件
         */
        copyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController.setCopyFile(TableScrollPanel.getSelectFile());
            }
        });

        /**
         * 粘贴按钮添加响应事件
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
