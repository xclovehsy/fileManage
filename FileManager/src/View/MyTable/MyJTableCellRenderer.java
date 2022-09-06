package View.MyTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

/**
 * JTable单元格渲染器，主要实现单元格的图标显示
 * @packageName View.Table
 * @className MyJTableCellRenderer
 * @author QinHaoting, 1165101405@qq.com
 * @version 1.0
 */
public class MyJTableCellRenderer extends DefaultTableCellRenderer {
    //region MyJTableCellRenderer - Variables
    /**
     * 图标集：根据文件名获取图标
     */
    private final Map<String, Icon> iconMap;
    //endregion MyJTableCellRenderer - Variables

    /**
     * 根据图标集来构建单元格渲染器
     * @param iconMap 图标集
     */
    public MyJTableCellRenderer(Map<String, Icon> iconMap) {
        this.iconMap = iconMap;
    }

    /**
     * 图标渲染实现
     * @param table 文件表格
     * @param value 单元格内容
     * @param isSelected 是否被选择
     * @param hasFocus 是否有焦点
     * @param row 行
     * @param column 列
     * @return 已被渲染好的单元格
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

//        setToolTipText("<html>" + "<p>文件名不能包含下列任何字符:</p>" +
//                                  "<p>/        \\:*?\"<>|</p>" + "</html>");
//        setToolTipText("文件名不能包含下列任何字符:\n\\/:*?\"<>|");

        setIcon(iconMap.get(value.toString()));
        // ----------------------以下为默认值----------------------
        // setEnabled(table.isEnabled());
        // setFont(table.getFont());
        // setOpaque(true); // 设置组件不透明；
        // setHorizontalAlignment(SwingConstants.LEFT);
        // ------------------------------------------------------
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}