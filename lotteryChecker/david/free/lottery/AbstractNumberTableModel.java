package david.free.lottery;

import java.io.*;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public abstract class AbstractNumberTableModel extends DefaultTableModel
	{
	public final static String filename="lottery.ser";
	List	rows	=new Vector(); //this gets serialized to disk when saving
	protected boolean	addingEmptyRow;

	public AbstractNumberTableModel(Object[][] data, Object[] columnNames)
		{
		super(data, columnNames);
		hydrateRows();
		}
	/*
	* JTable uses this method to determine the default renderer/
	* editor for each cell. If we didn't implement this method,
	* then the last column would contain text ("true"/"false"),
	* rather than a check box.
	*/
	public Class getColumnClass(int column) 
		{
		Object content=getValueAt(0, column);
		if (content!=null)
			return content.getClass();
		else return Object.class;
		}

	public Number setNumber(Number number, int row, boolean startNumber)
		{
		Number oldNum=getNumber(row);
		if (oldNum==null)
			for (int i=rows.size();i<=row;i++)
				if (getNumber(i)==null)
					rows.add(i, "");
		rows.set(row, number);
		if (startNumber) number.start();
		return oldNum;
		}

	public Number getNumber(int row)
		{if (rows.size()>row)
			return (Number)rows.get(row);
		else return null;
		}

	public void hydrateRows()
		{
		FileInputStream fis=null;
		ObjectInputStream in=null;
		try
			{
			fis=new FileInputStream(filename);
			in=new ObjectInputStream(fis);
			rows=(Vector)in.readObject();
			in.close();
//			for (Iterator nums=rows.iterator();nums.hasNext();)
//				{
//				((Number)nums.next()).start();
//				}
			}
		catch (IOException ex)
			{
//			ex.printStackTrace();
			}
		catch (ClassNotFoundException e)
			{
			e.printStackTrace();
			}
		}
	
	public void persistRows()
		{
		FileOutputStream fos=null;
		ObjectOutputStream out=null;
		try
			{
			fos=new FileOutputStream(filename);
			out=new ObjectOutputStream(fos);
			out.writeObject(rows);
			out.close();
			}
		catch (IOException ex)
			{
			ex.printStackTrace();
			}
		}
	
	public abstract void setValueQuietlyAt(Object value, int row, int col);

	/**
	 * @return Returns the rows.
	 */
	public List getRows()
		{
		return rows;
		}

	public boolean isAddingEmptyRow()
		{
		return addingEmptyRow;
		}

	}
