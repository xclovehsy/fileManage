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
     * 目录树
     */
    private final JTree tree;

    /**
     * 文件操作对象
     * 更新当前所处位置
     */
    private final FileManager fileManager = new FileManager();
    //endregion MyJTree - Variables

    //region MyJTree - Functions
    /**
     * 创建一棵树并初始化
     */
    public MyJTree() {
        // 构建一个以磁盘为子结点的结点；
        File[] disks = File.listRoots();
        MyJTreeNode rootTreeNode = new MyJTreeNode(disks);

        // 以刚构建的结点作为根结点构建目录树；
        this.tree = new JTree(rootTreeNode);

        // 初始化树；
        this.tree.setRootVisible(true);                   // 此电脑；
        this.tree.setCellRenderer(new MyJTreeRenderer()); // 图标渲染；
        this.tree.setFont(new Font("微软雅黑", Font.PLAIN, 13)); // 字体；
        this.tree.setRowHeight(25);                       // 行高；

        // 树的响应事件；
        this.tree.addTreeSelectionListener(e -> {
            if(tree.getLastSelectedPathComponent() == null) // 选择为空，不做操作；
                return;

            String desPath = tree.getLastSelectedPathComponent().toString(); // 用户选择的地址；
            File tempFile = new File(desPath);

            if (desPath.equals(""))    // 点击此电脑，返回磁盘界面
//                MainFrame.reset();
                FileController.setPath("");
                MainFrame.renew();

            if(tempFile.isDirectory()) // 点击目录，进入目录，更新主界面的元素（地址栏，文件详细信息）；
            {
                FileController.setPath(desPath);
                MainFrame.renew();
            }
        });
    }

    /**
     * @return 已初始化的目录树；
     */
    public JTree getJTree() {
        return this.tree;
    }
    //endregion MyJTree - Functions

    /**
     * 测试函数
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
