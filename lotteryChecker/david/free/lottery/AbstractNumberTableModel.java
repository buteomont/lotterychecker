package david.free.lottery;

import java.io.*;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractNumberTableModel extends AbstractTableModel
	{
	public final static String filename="lottery.ser";
	List	rows	=new Vector();

	public AbstractNumberTableModel()
		{
		super();
		hydrateRows();
		}

	public Number setNumber(Number number, int row)
		{
		Number oldNum=getNumber(row);
		if (oldNum==null)
			for (int i=rows.size();i<=row;i++)
				if (getNumber(i)==null)
					rows.add(i, "");
		rows.set(row, number);
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
	
	}
