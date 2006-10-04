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
					return column!=3;
					}

				public void setValueAt(Object value, int row, int col)
					{
					if (row>super.getRowCount()-2) //always have at least one blank row
						addRow((Vector)null);
			        Vector rowVector = (Vector)dataVector.elementAt(row);
			        rowVector.setElementAt(value, col);
					fireTableCellUpdated(row, col);
					}

				public void setValueQuietlyAt(Object value, int row, int col)
					{
					if (row>super.getRowCount()-2) //always have at least one blank row
						addRow((Vector)null);
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
