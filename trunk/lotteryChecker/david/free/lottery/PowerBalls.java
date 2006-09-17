package david.free.lottery;

import java.util.*;

import david.util.Common;

/**
 * Container for recent historical powerball numbers.  Will fetch and supply
 * all available powerball historical numbers.
 * 
 * @author David
 *
 */
public class PowerBalls
	{
	public static final String PB_URL="http://www.powerball.com/powerball/winnums-text.txt";
	private Map drawings=new HashMap();
	protected static PowerBalls instance;
	private static Calendar fetched;
	
	class Draw
	{
	String drawDate=null;
	String[] whiteNumbers=new String[5];
	String powerballNumber=null;
	String powerplayNumber=null;
	}
	
	private PowerBalls()
		{
		super();
		}
	/**
	 * Returns a PowerBalls instance with all historical powerball numbers
	 * drawn prior to this time, give or take a minute.
	 * @return
	 * @throws Exception 
	 */
	public static synchronized PowerBalls getInstance() throws Exception
		{
		if (instance==null || fetched==null)
			makeNewInstance();
		else 
			{
			Calendar now=Calendar.getInstance();
			now.add(Calendar.MINUTE, -1); //Don't let it get stale more than a minute
			if (now.after(fetched)) 
				try
					{
					makeNewInstance();
					}
				catch (Exception e)
					{
					// Can't fetch - use the old one
					e.printStackTrace();
					}
				}
		return instance;
		}
	private static void makeNewInstance() throws Exception
		{
		System.out.println("Fetching powerball numbers");
		instance=new PowerBalls();
		fetched=Calendar.getInstance();
		String page=Common.getInstance().getPage(PB_URL); //get the numbers in plain text
		if (page==null || page.length()==0) throw new Exception("Unable to fetch powerball numbers.");
		for (StringTokenizer tok=new StringTokenizer(page,"\n");tok.hasMoreTokens();)
			{
			String line=tok.nextToken();
			if (line.startsWith("Draw")) continue;
			
			Draw draw=instance.new Draw();
			StringTokenizer lTok=new StringTokenizer(line);
			if (lTok.hasMoreTokens()) draw.drawDate=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.whiteNumbers[0]=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.whiteNumbers[1]=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.whiteNumbers[2]=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.whiteNumbers[3]=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.whiteNumbers[4]=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.powerballNumber=lTok.nextToken();
			if (lTok.hasMoreTokens()) draw.powerplayNumber=lTok.nextToken();
			Arrays.sort(draw.whiteNumbers);
			instance.getDrawings().put(draw.drawDate, draw);
			}
		}
	public Map getDrawings()
		{
		return drawings;
		}
	public void setDrawings(Map drawings)
		{
		this.drawings=drawings;
		}
	}
