package david.util;

import java.io.IOException;
import java.util.*;

/**
 * Saves and restores configuration information
 *
 */
public class Initializer
	{
	/**
	 * Name-value pairs of configuration data.  Interpreted externally.
	 */
	Map data=new HashMap();
	String filename;
	
	private Initializer()
		{
		super();
		}

	public Initializer(String filename)
		{
		this();
		this.filename=filename;
		}

	/**
	 * Initialize the data map from a disk file.
	 * @throws Exception
	 */
	public void hydrate() throws Exception
		{
		data.clear();
		Common common=Common.getInstance();
		String pairs=new String(common.readFile(filename));
		for (StringTokenizer lines=new StringTokenizer(pairs,"\n");lines.hasMoreTokens();)
			{
			String name=null;
			String value=null;
			StringTokenizer line=new StringTokenizer(lines.nextToken(),"=");
			if (line.hasMoreTokens()) name=line.nextToken();
			if (line.hasMoreTokens()) value=line.nextToken();
			if (value!=null)
				data.put(name, value);
			}
		}

	/**
	 * Save the  data to a disk file in text format.
	 * @throws IOException
	 */
	public void persist() throws IOException
		{
		boolean append=false;
		Common common=Common.getInstance();
		for (Iterator i=data.keySet().iterator();i.hasNext();)
			{
			String name=(String)i.next();
			String value=(String)data.get(name);
			common.writeFile(filename, 
							 (name+"="+value+"\n").getBytes(),
							 append);
			append=true;
			}
		}

	/**
	 * @return Returns the data.
	 */
	public Map getData()
		{
		return data;
		}

	/**
	 * @param data The data to set.
	 */
	public void setData(Map data)
		{
		this.data=data;
		}
	
	public String get(String name)
		{
		return (String)data.get(name); 
		}
	}
