package david.free.lottery;

import java.io.*;
import java.util.*;

import javax.swing.table.DefaultTableModel;

import david.util.Common;

public abstract class AbstractNumberTableModel extends DefaultTableModel
	{
	public final static String filename="lottery.ini";
	private List	rows	=new Vector(); //this has all of the Number objects
	protected boolean	addingEmptyRow;
	private Map options=new Hashtable();  //this has all other options

	public AbstractNumberTableModel(Object[][] data, Object[] columnNames)
		{
		super(data, columnNames);
		try
			{
			hydrateRows();
			hydrateOptions();
			}
		catch (Exception e)
			{
			// Ok if it failed - could be first time run
			e.printStackTrace();
			}
		}
	/*
	* JTable uses this method to determine the default renderer/
	* editor for each cell. If we didn't implement this method,
	* then the powerplay column would contain text ("true"/"false"),
	* rather than a check box.
	*/
	public Class getColumnClass(int column) 
		{
		Object content=getValueAt(0, column);
		if (content!=null)
			return content.getClass();
		else if (column==Number.COLUMN_POWER_PLAY)
			return Boolean.class;
		else
			return String.class;
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

	/**
	 * Initialize the lottery table with data from a disk file.
	 * @throws Exception
	 */
	public void hydrateRows() throws Exception
		{
		rows.clear();
		Common common=Common.getInstance();
		String data=new String(common.readFile(filename));
		for (StringTokenizer lines=new StringTokenizer(data,"\n");lines.hasMoreTokens();)
			{
			String candidate=lines.nextToken();
			if (candidate.indexOf("=")<0) //anything with an "=" is not a ticket row
				{
				StringTokenizer line=new StringTokenizer(candidate,";");
				rows.add(new Number(line.nextToken(),line.nextToken(),Boolean.valueOf(line.nextToken()),line.nextToken(),null));
				}
			}
		}

	/**
	 * Initialize the lottery table with data from a disk file.
	 * @throws Exception
	 */
	public void hydrateOptions() throws Exception
		{
		Common common=Common.getInstance();
		String data=new String(common.readFile(filename));
		for (StringTokenizer lines=new StringTokenizer(data,"\n");lines.hasMoreTokens();)
			{
			String line=lines.nextToken();
			int equals=line.indexOf("=");
			if (equals>0)
				{
				options.put(line.substring(0,equals),line.substring(equals+1));
				}
			}
		}

	/**
	 * Save the lottery table data to a disk file in text format.
	 * @throws IOException
	 */
	public void persistRows(boolean append) throws IOException
		{
		Common common=Common.getInstance();
		for (Iterator i=rows.iterator();i.hasNext();)
			{
			Number num=(Number)i.next();
			byte[] data=num.toDelimitedString().getBytes();
			common.writeFile(filename, data, append);
			append=true;
			}
		}
	
	/**
	 * Save the lottery table data to a disk file in text format.
	 * @throws IOException
	 */
	public void persistOptions(boolean append) throws IOException
		{
		Common common=Common.getInstance();
		for (Iterator i=getOptions().keySet().iterator();i.hasNext();)
			{
			String name=(String)i.next();
			byte[] data=(name+"="+getOptions().get(name)+"\n").getBytes();
			common.writeFile(filename, data, append);
			append=true;
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
	public Map getOptions()
		{
		return options;
		}
	public void setOptions(Map options)
		{
		this.options=options;
		}
	}
