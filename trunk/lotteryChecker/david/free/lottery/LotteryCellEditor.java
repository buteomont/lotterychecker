package david.free.lottery;

import java.awt.Component;

import javax.swing.*;

import david.util.Common;

public class LotteryCellEditor extends DefaultCellEditor
	{
	public LotteryCellEditor()
		{
		super(new JTextField());
		setClickCountToStart(1);
		}

	// This method is called when a cell value is edited by the user.
	// It strips the HTML from the value before allowing it to be edited.
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex)
		{
		Component component=super.getTableCellEditorComponent(table, value, isSelected, rowIndex, vColIndex);
		((JTextField)component).setText(Common.getInstance().stripHtml((String)value));

		return component;
		}
	}
