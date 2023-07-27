package org.gestore.view;

import javax.swing.table.DefaultTableModel;

public class ModelloTabella extends DefaultTableModel
{
    public ModelloTabella(String[] cols)
    {
        super(cols, 0);
    }

    @Override
    public void removeRow(int row)
    {
        if(row < 0 || row > this.getRowCount())
            return;

        super.removeRow(row);
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
}