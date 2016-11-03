package nl.infosys.hartigehap.supplySystem.presentation;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Gregor
 */
public class CustomJTable extends JTable
{
    //Constants

    //Parameters
    //Constructor
    public CustomJTable()
    {
        super();
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        setAutoCreateRowSorter(true);
        getTableHeader().setResizingAllowed(false);
        getTableHeader().setReorderingAllowed(false);
    }

    //Methods
    public void updateRowHeight()
    {
        try
        {
            if (super.getRowCount() > 0)
            {
                Component comp = prepareRenderer(getCellRenderer(0, 0), 0, 0);
                int newRowHeight = comp.getPreferredSize().height;

                //System.out.println(newRowHeight);
                super.setRowHeight(newRowHeight);
            }
        }
        catch (ClassCastException e)
        {
        }
    }
    
    public void setAutoSorter(boolean value)
    {
        super.setAutoCreateRowSorter(value);
    }
}
