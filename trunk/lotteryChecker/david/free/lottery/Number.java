package david.free.lottery;


import java.awt.Toolkit;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

import david.util.Common;

public class Number extends Thread implements Serializable
	{
	private String[] numbs=new String[5];
	private String pb;
	private Boolean powerPlay=new Boolean(false);
	private int maxWhite=59;
	private int maxPB=39;
	private Date drawingDate;
	private transient List statusListeners=new Vector();
	private transient PowerBalls.Draw draw;
	private boolean winner=false;
	private String status="";
	
	/**
	 * Set this to true to cause this thread to exit at next pass.
	 */
	public transient boolean quit=false;
	
	private static int tempColNum=0;
	public static final int COLUMN_WHITE_NUMBERS = tempColNum++;
	public static final int COLUMN_POWERBALL_NUMBER = tempColNum++;
	public static final int COLUMN_POWER_PLAY = tempColNum++;
	public static final int COLUMN_DRAW_DATE = tempColNum++;
	public static final int COLUMN_STATUS = tempColNum++;

	static final String numFlag="<td background=\"/images/ball_white_40.gif\" width=\"39\" height=\"40\"><b><font size=\"5\">";
	static final String pbFlag="<td background=\"/images/ball_red_40.gif\" width=\"39\" height=\"40\"><b><font size=\"5\">";
	
	private Number()
		{
		super();
		}
	public Number(String nums, String pb, Boolean powerPlay, String drawDate, NumberStatusListener listener) throws Exception
		{
		this();
		addStatusListener(listener);
		
		//sanctify the numbers
		nums=nums.trim();
		pb=pb.trim();
		drawDate=drawDate.trim();
		nums=deHTML(nums);
		pb=deHTML(pb);
		
		//validate the powerball number
		if (!isANumber(pb)) 
			throw new Exception(pb+" is not a number.");
		pb=Integer.parseInt(pb)+"";
		if (pb.length()==1)pb="0"+pb;
		if (Integer.parseInt(pb)>maxPB)
			throw new Exception("Powerball number cannot be larger than "+maxPB);
		else this.pb=pb;
		
		//separate, sort, and validate the non-powerball numbers
		int i=0;
		for (StringTokenizer tok=new StringTokenizer(nums);tok.hasMoreTokens();)
			{
			String num=tok.nextToken();
			if (!isANumber(num)) 
				throw new Exception(num+" is not a number.");
			num=Integer.parseInt(num)+"";
			if (num.length()==1)num="0"+num;
			if (Integer.parseInt(num)>maxWhite)
				throw new Exception("White number "+num+" is too large. Maximum is "+maxWhite);
			else if (Arrays.asList(numbs).contains(num))
				throw new Exception(num+" is a duplicate number");
			else 
				numbs[i++]=num;
			}
		StringBuffer buf=new StringBuffer(numbs[0]).append(numbs[1]).append(numbs[2]).append(numbs[3]).append(numbs[4]);
		if (buf.indexOf("null")>=0) 
			throw new Exception("You must enter 5 numbers, separated by spaces.");
		else Arrays.sort(numbs);

		//validate the drawing date
		Calendar c=Calendar.getInstance();
		StringTokenizer st=new StringTokenizer(drawDate, "/-. ", false);
		if (st.countTokens()<3) drawDate+="-"+c.get(Calendar.YEAR);
		drawingDate=new Date(Common.getInstance().getDateObject(drawDate+" 22:00").getTime()); //something wierd about java.sql.Date
		c.setTime(getDrawingDate());
		int dow=c.get(Calendar.DAY_OF_WEEK);
		if (dow!=Calendar.WEDNESDAY && dow!=Calendar.SATURDAY)
			throw new Exception("\""+drawDate+"\" is not a Wednesday or Saturday.");		
		if (drawingDate==null) 
			throw new Exception("\""+drawDate+"\" is not a valid date.");
		this.powerPlay=powerPlay;
//		start();  //let this thread go
		}
	
	private String deHTML(String nums)
		{
		return Common.getInstance().stripHtml(nums);
		}
	
	/**
	 * Returns an integer with a binary pattern.  The bit positions containing 
	 * a 1 indicate a match between the entered and drawn numbers.
	 * @param column
	 * @return
	 */
	public int getPattern(int column)
		{
		if (draw==null)return 0; //no winner yet
		if (column==COLUMN_POWERBALL_NUMBER)
			{
			if (getPowerballNumber().equals(draw.powerballNumber)) return 1;
			else return 0;
			}
		else if (column==COLUMN_WHITE_NUMBERS)
			{
			return buildPattern(buildDrawnNumberString());
			}
		else return 0;	
		}
	
	private int buildPattern(String drawnNums)
		{
		int pattern=0;
		drawnNums=" "+drawnNums+" ";
		for (int i=0;i<numbs.length;i++)
			{
			pattern=pattern<<1;
			if (drawnNums.indexOf(" "+numbs[i]+" ")>=0)
				pattern++;
			}
		return pattern;
		}
	
	public void run()
		{
		System.out.println("New Number thread just started.");
		quit=false;
		//check the number if it is in the future
		//If so, don't check it yet
		while(!quit)
			{
			Calendar cal=Calendar.getInstance();
			Date now=cal.getTime();
			if (now.after(getDrawingDate()))
				{
				try
					{
					int wins=checkForWinner();
					if (wins<0) //not drawn yet?
						{
						setStatus("Waiting for numbers to be posted at "+PowerBalls.PB_URL);
						notifyListeners("");
						sleep(60000);
						continue;  //wait a minute and try again
						}
					//have the numbers now
					String drawnNums="";
					if (draw!=null)
						drawnNums=buildDrawnNumberString()+" ("+draw.powerballNumber+")";
					if (powerPlay.booleanValue()) 
						drawnNums+=" (x"+draw.powerplayNumber+")";
					if (wins>0)
						{
						setWinner(true);
						String winAmount=null;
						if (wins>=15000000) 
							{
							setStatus("<html><b><font color=red><blink>GRAND PRIZE WINNER!</blink></font></b></html>");
							}
						else
							{
							winAmount="$"+NumberFormat.getInstance().format(wins);
							setStatus("<html><b><font color=red>"+winAmount+" Winner!</font></b> Drawn numbers: "+drawnNums+"</html>");
							}
						notifyListeners("");
						playSound(winAmount);
						}
					else 
						{
						setStatus("<html>"+"Sorry, you should have picked these numbers: "+drawnNums+"</html>");
						notifyListeners("");
						}
					}
				catch (InterruptedException e)
					{
					break; //someone wants to stop
					}
				catch (PowerballException e)
					{
					setStatus(e.getMessage());
					notifyListeners(e.getMessage());
					e.printStackTrace();
					}
				catch (Exception e)
					{
					setStatus("Error");
					notifyListeners(e.getMessage());
					e.printStackTrace();
					}
				break;
				}
			else 
				{
				boolean interrupted=waitForDrawing(now);
				if (interrupted) break;
				}
			}
		//thread exits here
		quit=true;
		System.out.println("Thread exiting.");
		}
	
	
	private void playSound(String winAmount)
		{
//		try
//			{
//			AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(new File("dedodedo.wav"));
//			Clip line=(Clip)AudioSystem.getLine(new DataLine.Info(Clip.class, audioInputStream.getFormat()));
//			line.open(audioInputStream);
//			line.start();
//			line.drain();
//			line.close();
//			audioInputStream.close();
//			}
//		catch (Exception e)
//			{
//			e.printStackTrace();
//			}
		Toolkit.getDefaultToolkit().beep();
		Toolkit.getDefaultToolkit().sync();
		}
		

	private boolean waitForDrawing(Date now)
		{
		try
			{
			String td=Common.getInstance().timeDifference(now, getDrawingDate());
			setStatus("Drawing in "+td);
			notifyListeners("");
			sleep(lineUpMinute(now)*1000);
//			if (td.indexOf("seconds")<0)
//				sleep(60000); //one minute
//			else
//				{
//				int s=td.indexOf("and")+3;
//				int e=td.indexOf("seconds");
//				s=Integer.parseInt(td.substring(s, e).trim());
//				sleep(Math.min(60000, s*1000));
//				}
			return false;
			}
		catch (InterruptedException e)
			{
			System.out.println("Wait was interrupted.");
			return true;
			}
		}
	private long lineUpMinute(Date now)
		{
		long t1=now.getTime();
		long t2=getDrawingDate().getTime();
		long diff=(Math.max(t1, t2)-Math.min(t1, t2))/1000; //seconds
		diff=diff%60;
		if (diff==0) return 60l;
		else return diff;
		}
	/**
	 * Return the dollar amount won on this ticket
	 * @return int
	 * @throws Exception 
	 */
	private int checkForWinner() throws Exception
		{
		//check the numbers
		int results=-1;
		setStatus("Checking...");
		notifyListeners("");
		draw=(PowerBalls.Draw)PowerBalls.getInstance().getDrawings().get(getDrawingDateString());
		if (draw!=null)
			{
			String drawnNums=buildDrawnNumberString();
			String drawnPB=draw.powerballNumber;
			if (checkGrandPrize(drawnNums,drawnPB)) results= 15000000; //grand prize
			else if (check200KPrize(drawnNums)) results= 200000; 
			else if (check10KPrize(drawnNums,drawnPB)) results= 10000; 
			else if (check100Prize(drawnNums,drawnPB)) results= 100; 
			else if (check7Prize(drawnNums,drawnPB)) results= 7; 
			else if (check4Prize(drawnNums,drawnPB)) results= 4; 
			else if (check3Prize(drawnPB)) results= 3;
			else results=0;
			if (powerPlay.booleanValue() && draw.powerplayNumber==null)
				throw new PowerballException("Error - PowerPlay value not available - check numbers manually.");
			if (powerPlay.booleanValue() 					//has powerplay
					&& !checkGrandPrize(drawnNums,drawnPB)	//and not grand prize
					&& !check200KPrize(drawnNums))			//and not 200K prize
				results*=Integer.parseInt((draw.powerplayNumber));//then multiply by powerplay
			else if (powerPlay.booleanValue()			//has powerplay
					&& check200KPrize(drawnNums))		//and 200K prize
				results*=5;								//special case, always $1M
			}
		return results;
		}
	private String buildDrawnNumberString()
		{
		String drawnNums=draw.whiteNumbers[0]+" "
		                +draw.whiteNumbers[1]+" "
		                +draw.whiteNumbers[2]+" "
		                +draw.whiteNumbers[3]+" "
                        +draw.whiteNumbers[4];
		return drawnNums;
		}
	
	private boolean check3Prize(String drawnPB)
		{
		if (getPowerballNumber().equals(drawnPB)) return true;
		else return false;
		}
	private boolean check4Prize(String drawnNums, String drawnPB)
		{ //1 white plus powerball
		if (getPowerballNumber().equals(drawnPB) &&
				matchedNumbers(drawnNums)==1) return true;
		else return false;
		
		}
	private boolean check7Prize(String drawnNums, String drawnPB)
		{ //3 white numbers, or 2 white plus powerball
		if ((matchedNumbers(drawnNums)==3) ||
			(getPowerballNumber().equals(drawnPB) &&
				matchedNumbers(drawnNums)==2)) return true;
		else return false;
		
		}
	private boolean check100Prize(String drawnNums, String drawnPB)
		{ //4 white numbers, or 3 white plus powerball
		if ((matchedNumbers(drawnNums)==4) ||
			(getPowerballNumber().equals(drawnPB) &&
				matchedNumbers(drawnNums)==3)) return true;
		else return false;
		
		}
	private boolean check10KPrize(String drawnNums, String drawnPB)
		{ //4 white numbers plus powerball
		if (getPowerballNumber().equals(drawnPB) &&
			matchedNumbers(drawnNums)==4) return true;
		else return false;
		}
	private int matchedNumbers(String drawnNums)
		{
		drawnNums=" "+drawnNums+" ";
		int matched=0;
		for (int i=0;i<numbs.length;i++)
			if (drawnNums.indexOf(" "+numbs[i]+" ")>=0)
				matched++;
		return matched;
		}
	private boolean check200KPrize(String drawnNums)
		{ //all white numbers
		if (getNumbers().equals(drawnNums)) return true; 
		else return false;
		}
	private boolean checkGrandPrize(String drawnNums, String drawnPB)
		{ //all numbers
		if ((getNumbers().equals(drawnNums) && getPowerballNumber().equals(drawnPB))) return true; 
		else return false;
		}

	private boolean isANumber(String num)
		{
		boolean isNumeric=false;
		try 
			{
			Integer.valueOf(num,10);  //Test for numeric
			isNumeric=true;
			}
		catch(NumberFormatException e)
			{
			// Must not be a number
			}
		return isNumeric;
		}
	public String getNumbers()
		{
		StringBuffer buf=new StringBuffer();
		for (int i=0;i<numbs.length;i++)
			buf.append(numbs[i]).append(" ");
		return buf.toString().trim();
		}

	public String getHTMLNumbers()
		{
		StringBuffer buf=new StringBuffer("<html>");
		int winnerPattern=getPattern(COLUMN_WHITE_NUMBERS);
		int shift=numbs.length-1;
		for (int i=0;i<numbs.length;i++)
			{
			buf.append("<font color=");
			buf.append((winnerPattern & 1<<(shift-i))>0?"RED":"BLACK");
			buf.append(">");
			buf.append(numbs[i]).append("</font> ");
			}
		return buf.toString().trim()+"</html>";
		}
	
	public String getPowerballNumber()
		{
		return pb;
		}
	public String getHTMLPowerballNumber()
		{
		int winnerPattern=getPattern(COLUMN_POWERBALL_NUMBER);
		String c=winnerPattern>0?"RED":"BLACK";
		return "<html><font color="+c+">"+pb+"</font></html>";
		}
	public String toString()
		{
		return getNumbers()+" ("+pb+") "+getDrawingDateString();
		}
	public String toDelimitedString()
		{
		return getNumbers()+";"+pb+";"+powerPlay+";"+getDrawingDateString()+"\n";
		}
	public Date getDrawingDate()
		{
		return drawingDate;
		}
	public String getDrawingDateString()
		{
		String d="error";
		try
			{
			d=Common.getInstance().normalizeDateString(getDrawingDate().toString());
			d=new StringTokenizer(d).nextToken();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		return d;
		}
	
	public void setStatusListener(NumberStatusListener listener)
		{
		getStatusListeners().clear();
		getStatusListeners().add(listener);
		}
	public void addStatusListener(NumberStatusListener listener)
		{
		if (listener!=null)
			getStatusListeners().add(listener);
		}
	private void notifyListeners(String message)
		{
		for (Iterator listeners=getStatusListeners().iterator();listeners.hasNext();)
		((NumberStatusListener)listeners.next()).notify(this, getStatus(), message);	
		}
	public List getStatusListeners()
		{
		if (statusListeners==null)
			statusListeners=new Vector();
		return statusListeners;
		}
	/**
	 * Returns a String representing the next drawing date after the 
	 * input date.
	 * @return String
	 * @throws Exception 
	 */
	public static String getNextDrawingAfter(String dateAsString) throws Exception
		{
		Date start=Common.getInstance().getDateObject(dateAsString);
		Calendar cal=new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 22);
		while (true)
			{
			cal.add(Calendar.DATE, 1);
			if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
				cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
				break;
			}
		return Common.getInstance().normalizeDateString(cal.getTime().toString());
		}
	/* (non-Javadoc)
	 * @see java.lang.Thread#destroy()
	 */
	public void destroy()
		{
		System.out.println("Thread destroyed.");
		super.destroy();
		}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
		{
		boolean eq=true;
		if (obj==null) eq=false; 
		else if	(!(obj instanceof Number)) eq=false;
		else if (obj==this) eq=false; //same object treated as not equal
		else
			{
			Number candidate=(Number)obj;
			if (buildPattern(candidate.getNumbers())<31) eq=false;
			else if (!getPowerballNumber().equals(candidate.getPowerballNumber())) eq=false;
			else if (!isPowerPlay()==candidate.isPowerPlay()) eq=false;
			else if (!getDrawingDateString().equals(candidate.getDrawingDateString())) eq=false;
			}
		return eq;
		}
	/**
	 * @return Returns the powerPlay.
	 */
	public Boolean getPowerPlay()
		{
		return powerPlay;
		}
	public boolean isPowerPlay()
		{
		return powerPlay.booleanValue();
		}
	public boolean isWinner()
		{
		return winner;
		}
	public void setWinner(boolean winner)
		{
		this.winner=winner;
		}
	public String getStatus()
		{
		return status;
		}
	public void setStatus(String status)
		{
		this.status=status;
		}
	}
