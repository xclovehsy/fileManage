package View.MyTree;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MyJTreeRenderer extends DefaultTreeCellRenderer {
    //region MyJTreeRenderer - Variables
    private final static FileSystemView fileSystemView = FileSystemView.getFileSystemView(); // ��ǰϵͳ�ļ���ͼ�ӿڵ�ʵ����
    private final Map<String, Icon> iconCache = createMap();                        // ͼ�꼯�������ļ������ض�Ӧ��iconͼ�ꣻ
    private final Map<File, String> rootNameCache = new HashMap<File, String>();               // �ļ������������ļ����ض�Ӧ���ļ�����
    //endregion MyJTreeRenderer - Variables

    //region MyJTreeRenderer - Functions
    /**
     * ��ʼ��ͼ�꼯
     * �����˵��ԣ����洢��ͼ����Ϊ�����ͼ��
     * @return ��ʼ����ͼ�꼯
     */
    private static Map<String, Icon> createMap() {
        Map<String, Icon> iconMap = new HashMap<String, Icon>();
        File dir = new File("�˵���.{20D04FE0-3AEA-1069-A2D8-08002B30309D}");
        dir.mkdir();
        iconMap.put("�˵���", fileSystemView.getSystemIcon(dir));
        dir.delete();
        return iconMap;
    }

    /**
     * ��Ⱦ����
     * @param tree ����Ⱦ��Ŀ¼��
     * @param value ��ǰѡ��������
     * @param sel �Ƿ�ѡ��
     * @param expanded �Ƿ�չ��
     * @param leaf �Ƿ���Ҷ���
     * @param row ��
     * @param hasFocus ��ǰ����Ƿ�ӵ�н���
     * @return ����Ⱦ�õ���JTable��ʽ�Ľ��
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        MyJTreeNode myJTreeNode = (MyJTreeNode) value;
        File file = myJTreeNode.file;
        String fileName = "�˵���"; // Ĭ����ʾ��
        if (file != null)
        {
            if (myJTreeNode.isFileSystemRoot) // ϵͳ�̣�
            {
                fileName = this.rootNameCache.get(file);
                if (fileName == null)
                {
                    fileName = fileSystemView.getSystemDisplayName(file); // ��ȡϵͳ�����֣�
                    this.rootNameCache.put(file, fileName);
                }
            }
            else
                if (!file.isHidden()) // ����Ⱦ�����ص��ļ����ļ���
                    fileName = file.getName();
        }

        // ��Ⱦһ��JTable�����أ�
        JLabel result = (JLabel) super.getTreeCellRendererComponent(tree, fileName, sel, expanded, leaf, row, hasFocus);
        Icon icon;        // ���ͼ�ꣻ
        if (file != null) // ���Ǹ���㣬�����ļ�������Ⱦ��
        {
            icon = this.iconCache.get(fileName);
            if (icon == null)
            {
                icon = fileSystemView.getSystemIcon(file);
                this.iconCache.put(fileName, icon);
            }
        }
        else             // ����㣬��ȾΪ�˵��ԣ�
            icon = this.iconCache.get("�˵���");
        result.setIcon(icon);
        return result;
    }
    //endregion MyJTreeRenderer - Functions
}
