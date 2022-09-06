package View.MyTable;

import javax.swing.table.DefaultTableModel;

/**
 * @author Ðì´Ï
 * @version 1.0
 * @date 2021-11-19 18:51
 */

public class MyJTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
