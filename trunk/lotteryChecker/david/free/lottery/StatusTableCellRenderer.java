package david.free.lottery;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import david.util.DonateButton;

public class StatusTableCellRenderer extends JPanel implements TableCellRenderer, TableCellEditor
	{
	protected EventListenerList	listenerList	=new EventListenerList();
	protected ChangeEvent		changeEvent		=new ChangeEvent(this);
	protected DonateButton		donateButton	=new DonateButton(45, 15);
	private   StatusTableCellRenderer panel		=null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex,
		int vColIndex)
		{
		if (value instanceof JPanel)
			return (JPanel)value;
		else
			{
			panel=new StatusTableCellRenderer();
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
			panel.add(new JLabel((String)value));
			if (value!=null&&((String)value).indexOf("Winner")>0) 
				panel.add(donateButton);
			return panel;
			}
		}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
		{
		Component comp=null;
		if (((String)value).indexOf("Winner")>0)
			comp=panel;
		return comp;
		}

	public void addCellEditorListener(CellEditorListener listener)
		{
		listenerList.add(CellEditorListener.class, listener);
		}

	public void cancelCellEditing()
		{
		fireEditingCanceled();
		}

	public Object getCellEditorValue()
		{
		Object value=new String("Thanks for the donation!");
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panel.add(new JLabel((String)value));
		panel.add(donateButton);
		return panel;
		}

	public boolean isCellEditable(EventObject anEvent)
		{
		return true;//anEvent.getSource() instanceof JButton;
		}

	public void removeCellEditorListener(CellEditorListener listener)
		{
		listenerList.remove(CellEditorListener.class, listener);
		}

	public boolean shouldSelectCell(EventObject anEvent)
		{
		return false;
		}

	public boolean stopCellEditing()
		{
		fireEditingStopped();
		return true;
		}

	protected void fireEditingStopped()
		{
		CellEditorListener listener;
		Object[] listeners=listenerList.getListenerList();
		for (int i=0; i<listeners.length; i++)
			{
			if (listeners[i]==CellEditorListener.class)
				{
				listener=(CellEditorListener)listeners[i+1];
				listener.editingStopped(changeEvent);
				}
			}
		}

	protected void fireEditingCanceled()
		{
		CellEditorListener listener;
		Object[] listeners=listenerList.getListenerList();
		for (int i=0; i<listeners.length; i++)
			{
			if (listeners[i]==CellEditorListener.class)
				{
				listener=(CellEditorListener)listeners[i+1];
				listener.editingCanceled(changeEvent);
				}
			}
		}

	}
