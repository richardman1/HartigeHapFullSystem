package nl.infosys.hartigehap.supplySystem.presentation;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gregor
 */
public class CustomTableDataModel extends DefaultTableModel {

    private boolean editable;
    private int[] editableColums;

    public CustomTableDataModel(Object[] headerData) {
        super(headerData, 0);
        editable = false;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (editable == true) {
            return (column == 1 || column == 2);
        } else {
            return false;
        }
    }

    public void setColumsEditable(int[] editableColums) {
        this.editable = true;
        this.editableColums = editableColums;
    }

    public void clearEditableComlums() {
        this.editable = false;
        this.editableColums = new int[0];
    }
}
