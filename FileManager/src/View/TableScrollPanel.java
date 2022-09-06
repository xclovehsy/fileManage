package View;

//import FileOperation.FileIcon;

import FileOperation.FileController;
import FileOperation.FileIcon;
import FileOperation.FileManager;
import View.MyTable.MyJTableCellRenderer;
import View.MyTable.MyJTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-11 21:55
 */

public class TableScrollPanel extends JScrollPane {
    public static MainFrame mainFrame = null;
    public static JTable table = null;
    public static MyJTableModel tableModel = new MyJTableModel();
    public static PopupMenu popupMenu = null;

    public TableScrollPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        load();
    }

    /**
     * 加载界面
     */
    public void load() {
        tableModel = new MyJTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只允许单选；
        // 表格内容格式；
        table.setRowHeight(25);                                        // 行高；
        table.setFont(new Font("微软雅黑", Font.PLAIN, 13)); // 字体；

        // 表头格式；
        table.getTableHeader().setPreferredSize(new Dimension(1, 32));          // 行高；
        DefaultTableCellRenderer defaultTableCellRenderer_Header = new DefaultTableCellRenderer();
        defaultTableCellRenderer_Header.setHorizontalAlignment(SwingConstants.LEFT);               // 左对齐；
        defaultTableCellRenderer_Header.setForeground(Color.BLUE);                                 // 字体蓝色；
        table.getTableHeader().setDefaultRenderer(defaultTableCellRenderer_Header);

        // 取消网格线；
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        setViewportView(table);
        initInter();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // 如果是鼠标右键，则显示弹出菜单
                if (e.isMetaDown() && FileController.getPath().compareTo("")!=0) {
                    int row = table.getSelectedRow();
                    if(row == -1){
                        showPopupMenu2(e.getComponent(), e.getX(), e.getY());
                    }else{
                        showPopupMenu1(e.getComponent(), e.getX(), e.getY());
                    }

                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {


                super.mouseClicked(e);
                if(e.getClickCount() == 2 && e.getButton()==e.BUTTON1){
                    int row = table.getSelectedRow();

                    String fileType = (String)table.getValueAt(row, 2);
                    if(fileType.compareTo("文件夹") == 0 || FileController.getPath().compareTo("") == 0){
                        String path;
                        if (FileController.getPath().compareTo("") == 0) {
                            path = (String)table.getValueAt(row, 0);
                        }else{
                            path = FileController.getPath() +File.separator+ table.getValueAt(row, 0);
                        }
                        FileController.setPath(path);

                        MainFrame.renew();  //刷新界面
                    }else{
                        File openFile = new File(FileController.getPath()+ File.separator+table.getValueAt(row, 0));
                        mainFrame.openFile(openFile);
                    }
                }

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // 如果是鼠标右键，则显示弹出菜单
                if (e.isMetaDown() && FileController.getPath().compareTo("")!=0) {

                    showPopupMenu2(e.getComponent(), e.getX(), e.getY());

                }
            }
        });
    }

    public static void showPopupMenu1(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();

        // 创建 一级菜单
        JMenuItem openMenuItem = new JMenuItem("打开");
        JMenuItem pasteMenuItem = new JMenuItem("粘贴");
        JMenuItem copyMenuItem = new JMenuItem("复制");
        JMenu newMenu = new JMenu("新建");   // 需要 添加 二级子菜单 的 菜单，使用 JMenu
        JMenuItem renameMenuItem = new JMenuItem("重命名");
        JMenuItem deleteMenuItem = new JMenuItem("删除");
        JMenuItem encryptionMenuItem = new JMenuItem("加密文件");
        JMenuItem decryptionMenuItem = new JMenuItem("解密文件");
        JMenuItem compressMenuItem = new JMenuItem("压缩");
        JMenuItem decompressMenuItem = new JMenuItem("解压");


        // 创建 二级菜单
        JMenuItem newFileMenuItem = new JMenuItem("文件");
        JMenuItem newFolderMenuItem = new JMenuItem("文件夹");
        // 添加 二级菜单 到 "编辑"一级菜单
        newMenu.add(newFileMenuItem);
        newMenu.add(newFolderMenuItem);

        // 添加 一级菜单 到 弹出菜单
        popupMenu.add(openMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        popupMenu.addSeparator();       // 添加一条分隔符
        popupMenu.add(newMenu);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(renameMenuItem);
        popupMenu.addSeparator();       // 添加一条分隔符
        popupMenu.add(encryptionMenuItem);
        popupMenu.add(decryptionMenuItem);
        popupMenu.add(compressMenuItem);
        popupMenu.add(compressMenuItem);
        popupMenu.add(decompressMenuItem);

        /**
         * 如选择的是文件夹，则将加密文件、解密文件设置为不可用
         */
        File file = getSelectFile();
        if(file!=null && file.exists() && file.isDirectory()){
            encryptionMenuItem.setEnabled(false);
            decryptionMenuItem.setEnabled(false);
        }


        // 添加菜单项的点击监听器
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.openFile(getSelectFile());
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.copyFile();
            }
        });

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController.setCopyFile(getSelectFile());
            }
        });

        renameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.renameFile();
            }
        });

        deleteMenuItem .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.removeFile();
            }
        });

        // TODO: 2021-11-20 添加响应事件
        encryptionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.encryptFile();
            }
        });

        decryptionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.decryptFile();
            }
        });

        compressMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.compressFile();
            }
        });
        decompressMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.uncompressFile();
            }
        });

        newFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.createNewFile();
            }
        });

        newFolderMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.createNewFile();
            }
        });

        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }

    public static void showPopupMenu2(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();

        // 创建 一级菜单
        JMenuItem pasteMenuItem = new JMenuItem("粘贴");
        JMenu newMenu = new JMenu("新建");   // 需要 添加 二级子菜单 的 菜单，使用 JMenu

        // 创建 二级菜单
        JMenuItem newFileMenuItem = new JMenuItem("文件");
        JMenuItem newFolderMenuItem = new JMenuItem("文件夹");
        // 添加 二级菜单 到 "新建"一级菜单
        newMenu.add(newFileMenuItem);
        newMenu.add(newFolderMenuItem);

        // 添加 一级菜单 到 弹出菜单
        popupMenu.add(pasteMenuItem);
        popupMenu.add(newMenu);


        // 添加菜单项的点击监听器
        newFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.createNewFile();
            }
        });

        newFolderMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.createNewFolder();
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.copyFile();
            }
        });

        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }

    /**
     * 更新表格中的文件信息
     */
    public static void renew(){
        String path = FileController.getPath();

        if(path.compareTo("")== 0){
            initInter();
            return;
        }
        Vector<String> head = new Vector<>();
        Vector<Vector<String>> body = new Vector<Vector<String>>();

        //添加表头信息
        head.add("文件名称");
        head.add("修改日期");
        head.add("类型");
        head.add("大小");

        File father = new File(path);
        File[] children = father.listFiles();

        //添加表格中的文件信息
        if(children!=null){
            if(children.length == 0){  //如果该文件夹为空
                Vector<String> empty = new Vector<>();
                empty.add("");
                empty.add("此文件夹为空！");
                body.add(empty);
            }else{  //该文件夹不为空
                for(File file : children){
                    if(!FileController.hideFile && !file.isHidden()){
                        Vector<String> fileInfo = new Vector<>();
                        fileInfo.add(file.getName());
                        fileInfo.add(FileManager.getLastTime(file));
                        fileInfo.add(FileManager.getFileType(file));
                        if(file.isFile()){
                            fileInfo.add(FileManager.getFileSize(file));
                        }
                        body.add(fileInfo);

                    }else if(FileController.hideFile){
                        Vector<String> fileInfo = new Vector<>();
                        fileInfo.add(file.getName());
                        fileInfo.add(FileManager.getLastTime(file));

                        if(file.isHidden()){   //如果是隐藏文件，就添加一个标记
                            fileInfo.add(FileManager.getFileType(file) + "(隐藏文件)");
                        }else{
                            fileInfo.add(FileManager.getFileType(file));
                        }

                        if(file.isFile()){
                            fileInfo.add(FileManager.getFileSize(file));
                        }

                        body.add(fileInfo);
                    }
                }
            }

        }



        tableModel.setDataVector(body, head);
        // 在"名称"列显示图标；
        MyJTableCellRenderer renderer = new MyJTableCellRenderer(FileIcon.getAllFileIcon(FileController.getPath()));
        TableColumn tableColumn_Name = table.getColumn("文件名称");
        tableColumn_Name.setCellRenderer(renderer);
        tableColumn_Name.setPreferredWidth(300);

        MainFrame.setButtonOpen();
    }

    /**
     * 初始化表格为磁盘信息
     */
    public static void initInter() {
        Vector<String> head = new Vector<>();
        Vector<Vector<String>> body = new Vector<Vector<String>>();

        head.add("磁盘名称");
        head.add("可用空间");
        head.add("总空间");
        File[] root = File.listRoots();
        if(root != null){
            for(File disk : root){
                Vector<String> diskInfo = new Vector<>();
                diskInfo.add(disk.getPath());

                long freeSpace = disk.getFreeSpace();
                long totalSpace = disk.getTotalSpace();
                diskInfo.add(freeSpace / 1024 / 1024 / 1024 + "G");
                diskInfo.add(totalSpace / 1024 / 1024 / 1024 + "G");

                body.add(diskInfo);
            }
        }
        tableModel.setDataVector(body, head);

        MainFrame.setButtonOFF();  //设置界面中的某些按钮失灵
    }

    /**
     * 获取当前用户选中的文件或者文件夹
     * @return 文件夹、文件
     */
    public static File getSelectFile(){
        int row = table.getSelectedRow();
//        System.out.println(row);
        if(row!=-1){
            String path = FileController.getPath() +File.separator+table.getValueAt(row, 0);
//            System.out.println(path);
            File file = new File(path);
            return file;
        }else{
            return null;
        }
    }
}
