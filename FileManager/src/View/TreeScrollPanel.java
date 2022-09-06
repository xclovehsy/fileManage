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
 * @author ���
 * @version 1.0
 * @date 2021-11-11 21:55
 */

public class TreeScrollPanel extends JScrollPane {
    private JTree tree = null;

    public TreeScrollPanel() {
        Load();
    }

    public void Load() {
        //����tree����
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);                         // �Զ�����
        textArea.setFont(new Font(null, Font.PLAIN, 18));   // ��������
        setViewportView(textArea);
        // ���ô�ֱ����������ʾ����
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // ����ˮƽ����������ʾ����
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tree = new MyJTree().getJTree();

        tree.setShowsRootHandles(true);
        setViewportView(tree);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // ��ȡ��ѡ�е���ؽڵ�
                TreePath treePath = e.getPath();
                Object[] paths = treePath.getPath();
                String path = paths[paths.length-1].toString();
                File file = new File(path);

//                System.out.println("tree select -> " + path);

                if(file.isDirectory()){  //������ļ��еľͽ������
                    FileController.setPath(path);
                    MainFrame.renew();
                }else{  //������ļ��Ļ��ʹ򿪸��ļ�
//                    System.out.println("���ļ� -> " + file.getName());
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
