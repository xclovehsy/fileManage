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
    private final static FileSystemView fileSystemView = FileSystemView.getFileSystemView(); // 当前系统文件视图接口的实例；
    private final Map<String, Icon> iconCache = createMap();                        // 图标集：根据文件名返回对应的icon图标；
    private final Map<File, String> rootNameCache = new HashMap<File, String>();               // 文件名集：根据文件返回对应的文件名；
    //endregion MyJTreeRenderer - Variables

    //region MyJTreeRenderer - Functions
    /**
     * 初始化图标集
     * 创建此电脑，并存储其图标作为根结点图标
     * @return 初始化的图标集
     */
    private static Map<String, Icon> createMap() {
        Map<String, Icon> iconMap = new HashMap<String, Icon>();
        File dir = new File("此电脑.{20D04FE0-3AEA-1069-A2D8-08002B30309D}");
        dir.mkdir();
        iconMap.put("此电脑", fileSystemView.getSystemIcon(dir));
        dir.delete();
        return iconMap;
    }

    /**
     * 渲染操作
     * @param tree 待渲染的目录树
     * @param value 当前选择的树结点
     * @param sel 是否被选择
     * @param expanded 是否被展开
     * @param leaf 是否是叶结点
     * @param row 行
     * @param hasFocus 当前结点是否拥有焦点
     * @return 被渲染好的以JTable形式的结点
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        MyJTreeNode myJTreeNode = (MyJTreeNode) value;
        File file = myJTreeNode.file;
        String fileName = "此电脑"; // 默认显示；
        if (file != null)
        {
            if (myJTreeNode.isFileSystemRoot) // 系统盘；
            {
                fileName = this.rootNameCache.get(file);
                if (fileName == null)
                {
                    fileName = fileSystemView.getSystemDisplayName(file); // 获取系统盘名字；
                    this.rootNameCache.put(file, fileName);
                }
            }
            else
                if (!file.isHidden()) // 不渲染被隐藏的文件或文件夹
                    fileName = file.getName();
        }

        // 渲染一个JTable并返回；
        JLabel result = (JLabel) super.getTreeCellRendererComponent(tree, fileName, sel, expanded, leaf, row, hasFocus);
        Icon icon;        // 结点图标；
        if (file != null) // 不是根结点，根据文件本身渲染；
        {
            icon = this.iconCache.get(fileName);
            if (icon == null)
            {
                icon = fileSystemView.getSystemIcon(file);
                this.iconCache.put(fileName, icon);
            }
        }
        else             // 根结点，渲染为此电脑；
            icon = this.iconCache.get("此电脑");
        result.setIcon(icon);
        return result;
    }
    //endregion MyJTreeRenderer - Functions
}
