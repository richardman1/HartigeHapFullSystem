package nl.infosys.hartigehap.supplySystem.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Gregor
 */
public class CustomCellRenderer extends DefaultTableCellRenderer {

    //Constants
    Color evenRowColor, unevenRowColor;
    Font font;
    Dimension size;
    int height;
    boolean useSwitchingRowColors;

    //Parameters
    //Constructor
    public CustomCellRenderer() {
        useSwitchingRowColors = true;
        evenRowColor = new Color(198, 211, 228);
        unevenRowColor = new Color(229, 229, 229);

    }

    //Methods
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        c.setBorder(new EmptyBorder(0, 5, 1, 5));
        c.setHorizontalAlignment(JLabel.RIGHT);
        if (isSelected) {
            c.setBackground(Color.GRAY);
        } else {
            if (useSwitchingRowColors) {
                c.setBackground(row % 2 == 0 ? evenRowColor : unevenRowColor);
            } else {
                c.setBackground(evenRowColor);
            }
        }
        return c;
    }

    public Dimension getCellHeight(JTable table, int row) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, "", false, false, row, 0);
        return c.getPreferredSize();
    }
}
