package nl.infosys.hartigehap.supplySystem.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Gregor
 */
public class CustomStockCellRenderer extends CustomCellRenderer {

    //Constants
    List<Integer> redRows;

    //Parameters
    //Constructor
    public CustomStockCellRenderer(List<Integer> redRows) {
        this.redRows = redRows;

    }

    //Methods
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setForeground(Color.BLACK);
        c.setFont(c.getFont().deriveFont(Font.PLAIN));
        if (redRows.size() > 0) {
            for (int redRow : redRows) {
                if (row == redRow) {
                    c.setForeground(Color.RED);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    break;
                }
            }
        }
        return c;
    }

    //Getters & setters
    public void setRedRows(List<Integer> redRows) {
        this.redRows = redRows;
    }
}
