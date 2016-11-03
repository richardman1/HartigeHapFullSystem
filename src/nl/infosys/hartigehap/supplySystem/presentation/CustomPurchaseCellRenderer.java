package nl.infosys.hartigehap.supplySystem.presentation;

import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author Gregor
 */
public class CustomPurchaseCellRenderer extends CustomCellRenderer {
    //Constants

    //Parameters
    //Constructor
    public CustomPurchaseCellRenderer() {

    }

    //Methods
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, false, row, column);

        return c;
    }

    //Getters & setters
}
