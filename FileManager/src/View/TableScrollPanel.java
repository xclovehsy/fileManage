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
 * @author ���
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
     * ���ؽ���
     */
    public void load() {
        tableModel = new MyJTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ֻ����ѡ��
        // ������ݸ�ʽ��
        table.setRowHeight(25);                                        // �иߣ�
        table.setFont(new Font("΢���ź�", Font.PLAIN, 13)); // ���壻

        // ��ͷ��ʽ��
        table.getTableHeader().setPreferredSize(new Dimension(1, 32));          // �иߣ�
        DefaultTableCellRenderer defaultTableCellRenderer_Header = new DefaultTableCellRenderer();
        defaultTableCellRenderer_Header.setHorizontalAlignment(SwingConstants.LEFT);               // ����룻
        defaultTableCellRenderer_Header.setForeground(Color.BLUE);                                 // ������ɫ��
        table.getTableHeader().setDefaultRenderer(defaultTableCellRenderer_Header);

        // ȡ�������ߣ�
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        setViewportView(table);
        initInter();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // ���������Ҽ�������ʾ�����˵�
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
                    if(fileType.compareTo("�ļ���") == 0 || FileController.getPath().compareTo("") == 0){
                        String path;
                        if (FileController.getPath().compareTo("") == 0) {
                            path = (String)table.getValueAt(row, 0);
                        }else{
                            path = FileController.getPath() +File.separator+ table.getValueAt(row, 0);
                        }
                        FileController.setPath(path);

                        MainFrame.renew();  //ˢ�½���
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
                // ���������Ҽ�������ʾ�����˵�
                if (e.isMetaDown() && FileController.getPath().compareTo("")!=0) {

                    showPopupMenu2(e.getComponent(), e.getX(), e.getY());

                }
            }
        });
    }

    public static void showPopupMenu1(Component invoker, int x, int y) {
        // ���� �����˵� ����
        JPopupMenu popupMenu = new JPopupMenu();

        // ���� һ���˵�
        JMenuItem openMenuItem = new JMenuItem("��");
        JMenuItem pasteMenuItem = new JMenuItem("ճ��");
        JMenuItem copyMenuItem = new JMenuItem("����");
        JMenu newMenu = new JMenu("�½�");   // ��Ҫ ��� �����Ӳ˵� �� �˵���ʹ�� JMenu
        JMenuItem renameMenuItem = new JMenuItem("������");
        JMenuItem deleteMenuItem = new JMenuItem("ɾ��");
        JMenuItem encryptionMenuItem = new JMenuItem("�����ļ�");
        JMenuItem decryptionMenuItem = new JMenuItem("�����ļ�");
        JMenuItem compressMenuItem = new JMenuItem("ѹ��");
        JMenuItem decompressMenuItem = new JMenuItem("��ѹ");


        // ���� �����˵�
        JMenuItem newFileMenuItem = new JMenuItem("�ļ�");
        JMenuItem newFolderMenuItem = new JMenuItem("�ļ���");
        // ��� �����˵� �� "�༭"һ���˵�
        newMenu.add(newFileMenuItem);
        newMenu.add(newFolderMenuItem);

        // ��� һ���˵� �� �����˵�
        popupMenu.add(openMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        popupMenu.addSeparator();       // ���һ���ָ���
        popupMenu.add(newMenu);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(renameMenuItem);
        popupMenu.addSeparator();       // ���һ���ָ���
        popupMenu.add(encryptionMenuItem);
        popupMenu.add(decryptionMenuItem);
        popupMenu.add(compressMenuItem);
        popupMenu.add(compressMenuItem);
        popupMenu.add(decompressMenuItem);

        /**
         * ��ѡ������ļ��У��򽫼����ļ��������ļ�����Ϊ������
         */
        File file = getSelectFile();
        if(file!=null && file.exists() && file.isDirectory()){
            encryptionMenuItem.setEnabled(false);
            decryptionMenuItem.setEnabled(false);
        }


        // ��Ӳ˵���ĵ��������
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

        // TODO: 2021-11-20 �����Ӧ�¼�
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

        // ��ָ��λ����ʾ�����˵�
        popupMenu.show(invoker, x, y);
    }

    public static void showPopupMenu2(Component invoker, int x, int y) {
        // ���� �����˵� ����
        JPopupMenu popupMenu = new JPopupMenu();

        // ���� һ���˵�
        JMenuItem pasteMenuItem = new JMenuItem("ճ��");
        JMenu newMenu = new JMenu("�½�");   // ��Ҫ ��� �����Ӳ˵� �� �˵���ʹ�� JMenu

        // ���� �����˵�
        JMenuItem newFileMenuItem = new JMenuItem("�ļ�");
        JMenuItem newFolderMenuItem = new JMenuItem("�ļ���");
        // ��� �����˵� �� "�½�"һ���˵�
        newMenu.add(newFileMenuItem);
        newMenu.add(newFolderMenuItem);

        // ��� һ���˵� �� �����˵�
        popupMenu.add(pasteMenuItem);
        popupMenu.add(newMenu);


        // ��Ӳ˵���ĵ��������
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

        // ��ָ��λ����ʾ�����˵�
        popupMenu.show(invoker, x, y);
    }

    /**
     * ���±���е��ļ���Ϣ
     */
    public static void renew(){
        String path = FileController.getPath();

        if(path.compareTo("")== 0){
            initInter();
            return;
        }
        Vector<String> head = new Vector<>();
        Vector<Vector<String>> body = new Vector<Vector<String>>();

        //��ӱ�ͷ��Ϣ
        head.add("�ļ�����");
        head.add("�޸�����");
        head.add("����");
        head.add("��С");

        File father = new File(path);
        File[] children = father.listFiles();

        //��ӱ���е��ļ���Ϣ
        if(children!=null){
            if(children.length == 0){  //������ļ���Ϊ��
                Vector<String> empty = new Vector<>();
                empty.add("");
                empty.add("���ļ���Ϊ�գ�");
                body.add(empty);
            }else{  //���ļ��в�Ϊ��
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

                        if(file.isHidden()){   //����������ļ��������һ�����
                            fileInfo.add(FileManager.getFileType(file) + "(�����ļ�)");
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
        // ��"����"����ʾͼ�ꣻ
        MyJTableCellRenderer renderer = new MyJTableCellRenderer(FileIcon.getAllFileIcon(FileController.getPath()));
        TableColumn tableColumn_Name = table.getColumn("�ļ�����");
        tableColumn_Name.setCellRenderer(renderer);
        tableColumn_Name.setPreferredWidth(300);

        MainFrame.setButtonOpen();
    }

    /**
     * ��ʼ�����Ϊ������Ϣ
     */
    public static void initInter() {
        Vector<String> head = new Vector<>();
        Vector<Vector<String>> body = new Vector<Vector<String>>();

        head.add("��������");
        head.add("���ÿռ�");
        head.add("�ܿռ�");
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

        MainFrame.setButtonOFF();  //���ý����е�ĳЩ��ťʧ��
    }

    /**
     * ��ȡ��ǰ�û�ѡ�е��ļ������ļ���
     * @return �ļ��С��ļ�
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
