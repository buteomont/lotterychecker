package david.free.lottery;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class NumberTable extends JTable
	{
	public NumberTable(final Object[][] rowData, final Object[] columnNames)
		{
		super(new AbstractNumberTableModel()
				{
					public String getColumnName(int column)
						{
						return columnNames[column].toString();
						}

					public int getRowCount()
						{
						return rowData.length;
						}

					public int getColumnCount()
						{
						return columnNames.length;
						}

					public Object getValueAt(int row, int col)
						{
						return rowData[row][col];
						}

					public boolean isCellEditable(int row, int column)
						{
						return true;
						}

					public void setValueAt(Object value, int row, int col)
						{
						rowData[row][col]=value;
						fireTableCellUpdated(row, col);
						}

					public void setValueQuietlyAt(Object value, int row, int col)
						{
						rowData[row][col]=value;
						}

				});
		}

	public TableModel getModel()
		{
		return super.getModel();
		}
	}
