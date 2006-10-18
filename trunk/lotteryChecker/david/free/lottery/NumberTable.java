package david.free.lottery;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class NumberTable extends JTable
	{
	public NumberTable(final Object[][] rowData, final Object[] columnNames)
		{
		super(new AbstractNumberTableModel(rowData, columnNames)
			{
				public boolean isCellEditable(int row, int column)
					{
					return column!=Number.COLUMN_STATUS;
					}

				public void setValueAt(Object value, int row, int col)
					{
					if (row>super.getRowCount()-2) //always have at least one blank row
						addEmptyRow();
			        Vector rowVector = (Vector)dataVector.elementAt(row);
			        rowVector.setElementAt(value, col);
					fireTableCellUpdated(row, col);
					}

				private void addEmptyRow()
					{
					try
						{
						addingEmptyRow=true;
						addRow((Vector)null);
						}
					finally
						{
						addingEmptyRow=false;
						}
					}

				public void setValueQuietlyAt(Object value, int row, int col)
					{
					if (row>super.getRowCount()-2) //always have at least one blank row
						addEmptyRow();
			        Vector rowVector = (Vector)dataVector.elementAt(row);
			        rowVector.setElementAt(value, col);
					}
				
			});
		}

	public TableModel getModel()
		{
		return super.getModel();
		}
	}
