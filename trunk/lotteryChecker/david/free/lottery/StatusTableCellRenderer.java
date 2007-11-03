package david.free.lottery;

import java.awt.*;
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
	protected final static DonateButton		donateButton	=new DonateButton(45, 15);

	public StatusTableCellRenderer()
		{
		super();
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		}


	public StatusTableCellRenderer(boolean isDoubleBuffered)
		{
		super(isDoubleBuffered);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		}


	public StatusTableCellRenderer(LayoutManager layout, boolean isDoubleBuffered)
		{
		super(layout, isDoubleBuffered);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		}


	public StatusTableCellRenderer(LayoutManager layout)
		{
		super(layout);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex,
		int vColIndex)
		{
		Number number=((Number)((AbstractNumberTableModel)table.getModel()).getNumber(rowIndex));
		if (number==null)
			{
			JLabel lab=new JLabel();
			if (isSelected)
				lab.setBackground(table.getSelectionBackground());
			else
				lab.setBackground(table.getBackground());
			return lab;
			}
		else 
			{
			removeAll();
			add(new JLabel(number.getStatus()));
			if (number.isWinner())
				add(donateButton);
			if (isSelected)
				{
				setBackground(table.getSelectionBackground());
				setForeground(Color.WHITE);
				}
			else
				{
				setBackground(table.getBackground());
				setForeground(Color.BLACK);
				}
			return this;
			}
		}


	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int column)
		{
		table.setRowSelectionInterval(rowIndex, rowIndex);
		Number number=((Number)((AbstractNumberTableModel)table.getModel()).getNumber(rowIndex));
		if (number!=null)
			{
			if (number.isWinner())
				{
				StatusTableCellRenderer instance=new StatusTableCellRenderer();
				instance.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
				instance.add(new JLabel(number.getStatus()));
				if (number.isWinner())
					instance.add(donateButton);
				if (isSelected)
					{
					instance.setBackground(table.getSelectionBackground());
					setForeground(Color.WHITE);
					}
				else
					{
					instance.setBackground(table.getBackground());
					setForeground(Color.BLACK);
					}
				return instance;
				}
			else 
				return null;
			}
		else 
			return null;
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
		cancelCellEditing();
		return this;
		}

	public boolean isCellEditable(EventObject anEvent)
		{
		return true;
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
//		fireEditingStopped();
		fireEditingCanceled();
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
