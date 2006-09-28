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
	public static final String PB_MAIN_PAGE_URL="http://www.powerball.com/";
	public static String jackpotAmountStart="<strong>&nbsp;&nbsp;$";
	public static String jackpotAmountEnd="Million</strong>";
	public static String newsStart="var marqueecontent='<nobr>";
	public static String newsEnd="</nobr>'";
	
	private Map drawings=new HashMap();
	protected static PowerBalls instance;
	private static Calendar fetched;
	private static int jackpotAmount;
	private static String news;
	private static List jackpotListeners=new Vector();
	
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
		Common handy=Common.getInstance();
		String page=handy.getPage(PB_URL); //get the numbers in plain text
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
		
		//update the news and jackpot amount, and notify the jackpotListeners
		
		//the amount first:
		page=handy.getPage(PB_MAIN_PAGE_URL); //get the main lottery page
		if (page==null || page.length()==0) throw new Exception("Unable to fetch powerball jackpot amount.");
		int start=page.indexOf(jackpotAmountStart)+jackpotAmountStart.length();
		int end=page.indexOf(jackpotAmountEnd, start);
		String jackpot=page.substring(start,end).trim();
		if (handy.isANumber(jackpot))
			jackpotAmount=Integer.parseInt(jackpot);
		else 
			throw new Exception("Unable to find powerball jackpot amount.");
		
		//now the news:
		start=page.lastIndexOf(newsStart)+newsStart.length();
		end=page.indexOf(newsEnd, start);
		news=page.substring(start,end).trim();
		news=handy.substitute(news, "\\", "");
		if (news==null || news.length()==0)
			throw new Exception("Unable to find powerball news.");

		notifyJackpotListeners();
		}
	
	private static void notifyJackpotListeners()
		{
		for (Iterator listeners=getJackpotListeners().iterator();listeners.hasNext();)
			{
			JackpotListener listener=(JackpotListener)listeners.next();
			listener.updateJackpotAmount(jackpotAmount);
			listener.updateNews(news);
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
	public int getJackpotAmount()
		{
		return jackpotAmount;
		}
	protected static void setJackpotAmount(int jackpotAmount)
		{
		PowerBalls.jackpotAmount=jackpotAmount;
		}
	public static List getJackpotListeners()
		{
		return jackpotListeners;
		}
	public static void addListener(JackpotListener listener)
		{
		PowerBalls.jackpotListeners.add(listener);
		if (jackpotAmount>0)
			notifyJackpotListeners(); //no need to wait for next load
		}
	}
