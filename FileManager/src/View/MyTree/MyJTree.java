package View.MyTree;

import FileOperation.FileController;
import FileOperation.FileManager;
import View.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class MyJTree {
    //region MyJTree - Variables
    /**
     * Ŀ¼��
     */
    private final JTree tree;

    /**
     * �ļ���������
     * ���µ�ǰ����λ��
     */
    private final FileManager fileManager = new FileManager();
    //endregion MyJTree - Variables

    //region MyJTree - Functions
    /**
     * ����һ��������ʼ��
     */
    public MyJTree() {
        // ����һ���Դ���Ϊ�ӽ��Ľ�㣻
        File[] disks = File.listRoots();
        MyJTreeNode rootTreeNode = new MyJTreeNode(disks);

        // �Ըչ����Ľ����Ϊ����㹹��Ŀ¼����
        this.tree = new JTree(rootTreeNode);

        // ��ʼ������
        this.tree.setRootVisible(true);                   // �˵��ԣ�
        this.tree.setCellRenderer(new MyJTreeRenderer()); // ͼ����Ⱦ��
        this.tree.setFont(new Font("΢���ź�", Font.PLAIN, 13)); // ���壻
        this.tree.setRowHeight(25);                       // �иߣ�

        // ������Ӧ�¼���
        this.tree.addTreeSelectionListener(e -> {
            if(tree.getLastSelectedPathComponent() == null) // ѡ��Ϊ�գ�����������
                return;

            String desPath = tree.getLastSelectedPathComponent().toString(); // �û�ѡ��ĵ�ַ��
            File tempFile = new File(desPath);

            if (desPath.equals(""))    // ����˵��ԣ����ش��̽���
//                MainFrame.reset();
                FileController.setPath("");
                MainFrame.renew();

            if(tempFile.isDirectory()) // ���Ŀ¼������Ŀ¼�������������Ԫ�أ���ַ�����ļ���ϸ��Ϣ����
            {
                FileController.setPath(desPath);
                MainFrame.renew();
            }
        });
    }

    /**
     * @return �ѳ�ʼ����Ŀ¼����
     */
    public JTree getJTree() {
        return this.tree;
    }
    //endregion MyJTree - Functions

    /**
     * ���Ժ���
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("File tree");
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);

                JTree tree = new MyJTree().getJTree();

                JScrollPane jScrollPane = new JScrollPane(tree);
                jScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

                frame.add(jScrollPane);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
