package View;

import FileOperation.FileController;
import View.MyTree.MyJTree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;

/**
 * @author 徐聪
 * @version 1.0
 * @date 2021-11-11 21:55
 */

public class TreeScrollPanel extends JScrollPane {
    private JTree tree = null;

    public TreeScrollPanel() {
        Load();
    }

    public void Load() {
        //设置tree参数
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);                         // 自动换行
        textArea.setFont(new Font(null, Font.PLAIN, 18));   // 设置字体
        setViewportView(textArea);
        // 设置垂直滚动条的显示策略
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // 设置水平滚动条的显示策略
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tree = new MyJTree().getJTree();

        tree.setShowsRootHandles(true);
        setViewportView(tree);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // 获取被选中的相关节点
                TreePath treePath = e.getPath();
                Object[] paths = treePath.getPath();
                String path = paths[paths.length-1].toString();
                File file = new File(path);

//                System.out.println("tree select -> " + path);

                if(file.isDirectory()){  //如果是文件夹的就进入更新
                    FileController.setPath(path);
                    MainFrame.renew();
                }else{  //如果是文件的话就打开该文件
//                    System.out.println("打开文件 -> " + file.getName());
                }

            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ScrollPane tree");
        frame.setSize(1000, 400);
        frame.setLocationRelativeTo(null);

        TreeScrollPanel treeScrollPane = new TreeScrollPanel();
        treeScrollPane.setVisible(true);

        frame.add(treeScrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
