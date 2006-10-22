package david.free.lottery;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import david.util.Common;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JButton;

public class LotteryChecker extends JFrame implements LotteryListener, JackpotListener
	{

	protected static final String	aboutText	="<html><center><h3>Powerball Lottery Checker</h3><small>by </small><b>David E. Powell</b><br><i>david@depowell.com</i></center></html>";
	private static final String	SPLASH_PAGE	="http://www.depowell.com/lotterySplashMsg.html";
	private JPanel		jContentPane	=null;
	private JMenuBar	jJMenuBar		=null;
	private JMenu		fileMenu		=null;
	private JMenu		editMenu		=null;
	private JMenu		helpMenu		=null;
	private JMenuItem	exitMenuItem	=null;
	private JMenuItem	aboutMenuItem	=null;
	private JMenuItem	cutMenuItem		=null;
	private JMenuItem	copyMenuItem	=null;
	private JMenuItem	pasteMenuItem	=null;
	private JMenuItem	saveMenuItem	=null;
	private JScrollPane numberListJScrollPane = null;
	private NumberTable numberListJTable = null;
	private JLabel messageJLabel = null;
	private JPopupMenu optionJPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="596,259"
	private JMenuItem duplicateJMenuItem = null;
	private int lastRowClicked=0;
	private JMenuItem removeJMenuItem = null;
	private JDialog aboutJDialog = null;  //  @jve:decl-index=0:visual-constraint="696,10"
	private JPanel aboutJContentPane = null;
	private JLabel aboutJLabel = null;
	private JPanel infoJPanel = null;
	private JLabel infoJLabel = null;
	private JPanel jackpotJPanel = null;
	private JLabel jackpotAmountJLabel = null;
	private JLabel jLabel1 = null;
	private JPanel spacerJPanel = null;
	private JMenuItem refreshMenuItem = null;
	private String[] headers=new String[] { "Numbers", "PB", "PP", "Draw Date", "Status" };
	private JDialog duplicateCountjDialog = null;  //  @jve:decl-index=0:visual-constraint="698,214"
	private JPanel duplicateCountjContentPane = null;
	private JTextField duplicateCountjTextField = null;
	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	protected boolean running=true;
	private JDialog splashDialog = null;  //  @jve:decl-index=0:visual-constraint="11,250"
	private JPanel splashContentPane = null;
	private JLabel splashText = null;
	private JPanel jPanel = null;
	private JButton disagreeButton = null;
	private JButton agreeButton = null;
	private JPanel spacerPanel = null;
	private boolean agree=false;
	
	public LotteryChecker() throws HeadlessException
		{
		super();
		}

	public LotteryChecker(GraphicsConfiguration gc)
		{
		super(gc);
		}

	public LotteryChecker(String title) throws HeadlessException
		{
		super(title);
		}

	public LotteryChecker(String title, GraphicsConfiguration gc)
		{
		super(title, gc);
		}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize()
		{
		try
			{
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			PowerBalls.getInstance(); //initialize the news and jackpot
			}
		catch (Exception e)
			{
			e.printStackTrace();
			messageJLabel.setText(e.getMessage());
			}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.setSize(682, 234);
		this.setLocation(600, 400);
		this.setContentPane(getJContentPane());
		this.setTitle("Powerball Checker");
		this.messageJLabel.setText("");
		
		//keep the news and jackpot up to date
		final Thread newsThread=new Thread()
			{
			public void run()
				{
				while (running)
					{
					try
						{
						sleep(900000);
						PowerBalls.getInstance(); //we are already a listener
						}
					catch (Exception e){}
					}
				}
			};
			newsThread.start();
			
			this.addWindowListener(new WindowAdapter()
					{
					public void windowClosing(WindowEvent e)
						{
						running=false;
						newsThread.interrupt();
						save();
						super.windowClosing(e);
						}
				});
		}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
		{
		if (jContentPane==null)
			{
			messageJLabel = new JLabel();
			messageJLabel.setText("message area");
			messageJLabel.setForeground(java.awt.Color.red);
			messageJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jContentPane=new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(messageJLabel, java.awt.BorderLayout.SOUTH);
			jContentPane.add(getNumberListJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getInfoJPanel(), java.awt.BorderLayout.NORTH);
			}
		return jContentPane;
		}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar()
		{
		if (jJMenuBar==null)
			{
			jJMenuBar=new JMenuBar();
			jJMenuBar.add(getFileMenu());
//			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
			}
		return jJMenuBar;
		}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu()
		{
		if (fileMenu==null)
			{
			fileMenu=new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getRefreshMenuItem());
			fileMenu.add(getExitMenuItem());
			}
		return fileMenu;
		}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu()
		{
		if (editMenu==null)
			{
			editMenu=new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
			}
		return editMenu;
		}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu()
		{
		if (helpMenu==null)
			{
			helpMenu=new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
			}
		return helpMenu;
		}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem()
		{
		if (exitMenuItem==null)
			{
			exitMenuItem=new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
						{
						save();
						System.exit(0);
						}
				});
			}
		return exitMenuItem;
		}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem()
		{
		if (aboutMenuItem==null)
			{
			aboutMenuItem=new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
						{
						JDialog about=getAboutJDialog();
						about.setLocation(centerX(getAboutJDialog()), centerY(getAboutJDialog()));
						about.show();
						}

				});
			}
		return aboutMenuItem;
		}
	
	
	private int centerX(Component c)
		{
		int myX=getContentPane().getLocationOnScreen().x;
		myX+=getWidth()/2;
		myX-=c.getWidth()/2;
		return myX;
		}

	private int centerY(Component c)
		{
		int myY=getContentPane().getLocationOnScreen().y;
		myY+=getHeight()/2;
		myY-=c.getHeight()/2;
		return myY;
		}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCutMenuItem()
		{
		if (cutMenuItem==null)
			{
			cutMenuItem=new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK, true));
			}
		return cutMenuItem;
		}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCopyMenuItem()
		{
		if (copyMenuItem==null)
			{
			copyMenuItem=new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, true));
			}
		return copyMenuItem;
		}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPasteMenuItem()
		{
		if (pasteMenuItem==null)
			{
			pasteMenuItem=new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, true));
			}
		return pasteMenuItem;
		}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem()
		{
		if (saveMenuItem==null)
			{
			saveMenuItem=new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, true));
			saveMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					save();
					}
				});
			}
		return saveMenuItem;
		}

	protected void save()
		{
		((AbstractNumberTableModel)getNumberListJTable().getModel()).persistRows();
		setMessage("Lottery numbers saved.");
		}

	/**
	 * This method initializes numberListJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getNumberListJScrollPane()
		{
		if (numberListJScrollPane==null)
			{
			numberListJScrollPane=new JScrollPane();
			numberListJScrollPane.setPreferredSize(new java.awt.Dimension(452,100));
			numberListJScrollPane.setViewportView(getNumberListJTable());
			}
		return numberListJScrollPane;
		}

	/**
	 * This method initializes numberListJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private NumberTable getNumberListJTable()
		{
		if (numberListJTable==null)
			{
			numberListJTable=new NumberTable((Object[][])new Object[10][headers.length], (Object[])headers);
			TableColumnModel mod=numberListJTable.getColumnModel();
			mod.getColumn(Number.COLUMN_WHITE_NUMBERS).setCellRenderer(new LotteryCellRenderer(this));
			mod.getColumn(Number.COLUMN_POWERBALL_NUMBER).setCellRenderer(new LotteryCellRenderer(this));
			mod.getColumn(Number.COLUMN_STATUS).setPreferredWidth(50);
			mod.getColumn(Number.COLUMN_DRAW_DATE).setPreferredWidth(80);
			mod.getColumn(Number.COLUMN_DRAW_DATE).setMaxWidth(80);
			mod.getColumn(Number.COLUMN_DRAW_DATE).setMinWidth(80);
			mod.getColumn(Number.COLUMN_POWER_PLAY).setCellEditor(new DefaultCellEditor(new JCheckBox()));
			mod.getColumn(Number.COLUMN_POWER_PLAY).setMinWidth(30);
			mod.getColumn(Number.COLUMN_POWER_PLAY).setMaxWidth(30);
			mod.getColumn(Number.COLUMN_POWER_PLAY).setPreferredWidth(30);
			mod.getColumn(Number.COLUMN_POWERBALL_NUMBER).setPreferredWidth(30);
			mod.getColumn(Number.COLUMN_POWERBALL_NUMBER).setMaxWidth(30);
			mod.getColumn(Number.COLUMN_POWERBALL_NUMBER).setMinWidth(30);
			mod.getColumn(Number.COLUMN_WHITE_NUMBERS).setPreferredWidth(90);
			mod.getColumn(Number.COLUMN_WHITE_NUMBERS).setMaxWidth(90);
			mod.getColumn(Number.COLUMN_WHITE_NUMBERS).setMinWidth(90);
			((DefaultTableCellRenderer)numberListJTable.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
			numberListJTable.setToolTipText("Enter your 5 numbers here (separated by spaces), your powerball number, and the draw date.");
			numberListJTable.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
						{
						if (e.getButton()==MouseEvent.BUTTON1)
							{
							getOptionJPopupMenu().setVisible(false);
							}
						if (e.getButton()==MouseEvent.BUTTON3)
							{
							lastRowClicked=getNumberListJTable().rowAtPoint(e.getPoint()); 
							Point menuloc=e.getPoint();
//							menuloc.translate(getX(), getY()+getContentPane().getY());
							menuloc.translate(getLocationOnScreen().x,getLocationOnScreen().y);
							getOptionJPopupMenu().setLocation(menuloc);
							getOptionJPopupMenu().setVisible(true);
							e.consume();
							}
						}
				});
			numberListJTable.getModel().addTableModelListener(new TableModelListener()
					{
					public void tableChanged(TableModelEvent e) 
						{
						if (e.getSource() instanceof AbstractNumberTableModel)
							if (!((AbstractNumberTableModel)e.getSource()).isAddingEmptyRow())
								updateNumbers(e.getFirstRow());
						}
					});
			synchronizeRows((AbstractNumberTableModel)numberListJTable.getModel());
			}
		return numberListJTable;
		}

	public int getPattern(int row, int column)
		{
		List rows=((AbstractNumberTableModel)getNumberListJTable().getModel()).rows;
		if (rows.size()>row)
			{
			Number number=(Number)rows.get(row);
			if (column==Number.COLUMN_WHITE_NUMBERS) return number.getPattern(Number.COLUMN_WHITE_NUMBERS);
			else return number.getPattern(Number.COLUMN_POWERBALL_NUMBER);
			}
		else return 0;
		}
	
	private void synchronizeRows(AbstractNumberTableModel model)
		{
		Vector baddies=new Vector();
		int row=0;
		for (Iterator i=model.rows.iterator(); i.hasNext();)
			{
			Object o=i.next();
			if (o instanceof Number)
				{
				Number num=(Number)o;
				model.setValueQuietlyAt(num.getNumbers(), row, Number.COLUMN_WHITE_NUMBERS);
				model.setValueQuietlyAt(num.getPowerballNumber(), row, Number.COLUMN_POWERBALL_NUMBER);
				model.setValueQuietlyAt(num.getPowerPlay(), row, Number.COLUMN_POWER_PLAY);
				model.setValueQuietlyAt(num.getDrawingDateString(), row, Number.COLUMN_DRAW_DATE);
				num.addStatusListener(getNewNumberStatusListener(row));
				if (!num.isAlive() && !num.quit) 
					num.start();
				row++;
				}
			else baddies.add(o);
			}
		for (Iterator i=baddies.iterator();i.hasNext();)
			model.rows.remove(i.next());
		}

	protected void updateNumbers(int row)
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();

		//ignore any input until the whole row is complete
		for (int c=0;c<Number.COLUMN_STATUS;c++)
			{
			if (mod.getValueAt(row, c)==null)
				{
//				if (mod.getValueAt(row, Number.COLUMN_POWER_PLAY)==null)
//					mod.setValueQuietlyAt(new Boolean(false), row, Number.COLUMN_POWER_PLAY);
				return;
				}
			}
		Number newnum=null;
		try
			{
			newnum=new Number((String)mod.getValueAt(row, Number.COLUMN_WHITE_NUMBERS),
									(String)mod.getValueAt(row, Number.COLUMN_POWERBALL_NUMBER), 
									(Boolean)mod.getValueAt(row, Number.COLUMN_POWER_PLAY),
									(String)mod.getValueAt(row, Number.COLUMN_DRAW_DATE),
									getNewNumberStatusListener(row));
			}
		catch (Exception e)
			{
			setStatus(row,"Error");
			setMessage(e.getMessage());
			return;
			}
		
		messageJLabel.setText("");
		
//		replace with sanctified numbers
		mod.setValueQuietlyAt(newnum.getNumbers(), row, Number.COLUMN_WHITE_NUMBERS); 
		mod.setValueQuietlyAt(newnum.getPowerballNumber(), row, Number.COLUMN_POWERBALL_NUMBER); 
		mod.setValueQuietlyAt(newnum.getDrawingDateString(), row, Number.COLUMN_DRAW_DATE);

		if (mod.rows.contains(newnum)) 
			{
			setStatus(row,"Error - duplicate number.");
			setMessage("Duplicate number.");
			}
		Number oldnum=mod.getNumber(row);
		String old=null;
		if (oldnum != null) 
			{
			old=oldnum.toString();
			oldnum.quit=true;  //causes oldnum to exit
			oldnum.interrupt(); 
			}
	
		mod.setNumber(newnum,row,!mod.rows.contains(newnum)); //don't start it if it's a dupe
	
		if (oldnum==null)
			System.out.println("Number "+newnum+" added.");
		else
			{
			System.out.println("Number "+old+" replaced with "+newnum);
			}
		}

	private NumberStatusListener getNewNumberStatusListener(final int row)
		{
		return new NumberStatusListener()
			{
			public void notify(Number source, String status, String message)
				{
				setStatus(row, status);
				setMessage(message);
				}
			};
		}

	private void setStatus(int row, String text)
		{
		System.out.println("Row "+row+": "+text);
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
		mod.setValueQuietlyAt(text, row, Number.COLUMN_STATUS);
		repaint();
		}

	private void setMessage(String message)
		{
		messageJLabel.setText(message);
		if (message!=null && message.trim().length()>0)
			System.out.println(message);
		}

	/**
	 * This method initializes optionJPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getOptionJPopupMenu()
		{
		if (optionJPopupMenu==null)
			{
			optionJPopupMenu=new JPopupMenu();
			optionJPopupMenu.add(getDuplicateJMenuItem());
			optionJPopupMenu.add(getRemoveJMenuItem());
			}
		return optionJPopupMenu;
		}

	/**
	 * This method initializes duplicateJMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDuplicateJMenuItem()
		{
		if (duplicateJMenuItem==null)
			{
			duplicateJMenuItem=new JMenuItem();
			duplicateJMenuItem.setText("Duplicate this row...");
			duplicateJMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					int x=getLocationOnScreen().x+getWidth()/2-getDuplicateCountjDialog().getWidth()/2;
					int y=getLocationOnScreen().y+getHeight()/2-getDuplicateCountjDialog().getHeight()/2;
					getDuplicateCountjDialog().setLocation(x,y);
					getDuplicateCountjDialog().setVisible(true);
					}
				});
			}
		return duplicateJMenuItem;
		}

	protected void duplicateRow(int count)
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
		Number orignum=(Number)mod.rows.get(lastRowClicked);
		while (count-->0)
			{
			try
				{
				Number newnum=new Number(orignum.getNumbers(),
										 orignum.getPowerballNumber(),
										 orignum.getPowerPlay(),
										 Number.getNextDrawingAfter(orignum.getDrawingDateString()),
										 getNewNumberStatusListener(mod.rows.size()));
				mod.rows.add(newnum);
				getOptionJPopupMenu().setVisible(false);
//				if (mod.rows.size()>=10)
//					addEmptyRow();
				synchronizeRows(mod);
				orignum=(Number)mod.rows.get(mod.rows.size()-1);
				}
			catch (Exception e)
				{
				e.printStackTrace();
				break;
				}
			}
		}

	/**
	 * This method initializes removeJMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getRemoveJMenuItem()
		{
		if (removeJMenuItem==null)
			{
			removeJMenuItem=new JMenuItem();
			removeJMenuItem.setText("Remove Selected Rows");
			removeJMenuItem.addActionListener(new ActionListener()
					{
					public void actionPerformed(ActionEvent e)
						{
						removeRows();
						getOptionJPopupMenu().setVisible(false);
						}
					});
			}
		return removeJMenuItem;
		}

	/**
	 * Removes the highlighted rows from the lottery checker table.
	 *
	 */
	protected void removeRows()
		{
		int count=getNumberListJTable().getSelectedRowCount();
		if (count>0)
			{
			int first=getNumberListJTable().getSelectedRow();
			AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
			for (int i=0;i<count;i++)
				removeRow(first,mod); //all following rows move up one
			getNumberListJTable().clearSelection();
			}
		}

	private void removeRow(int row, AbstractNumberTableModel model)
		{
		model.rows.remove(row);
		for (;row<model.rows.size();row++)
			{
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_WHITE_NUMBERS), row, Number.COLUMN_WHITE_NUMBERS);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_POWERBALL_NUMBER), row, Number.COLUMN_POWERBALL_NUMBER);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_POWER_PLAY), row, Number.COLUMN_POWER_PLAY);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_DRAW_DATE), row, Number.COLUMN_DRAW_DATE);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_STATUS), row, Number.COLUMN_STATUS);
			}
		model.setValueQuietlyAt("", row, Number.COLUMN_WHITE_NUMBERS);
		model.setValueQuietlyAt("", row, Number.COLUMN_POWERBALL_NUMBER);
		model.setValueQuietlyAt(new Boolean(false), row, Number.COLUMN_POWER_PLAY);
		model.setValueQuietlyAt("", row, Number.COLUMN_DRAW_DATE);
		model.setValueQuietlyAt("", row, Number.COLUMN_STATUS);
		}

	/**
	 * This method initializes aboutJDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getAboutJDialog()
		{
		if (aboutJDialog==null)
			{
			aboutJDialog=new JDialog(LotteryChecker.this, "About", true);
			aboutJDialog.setSize(new java.awt.Dimension(250,200));
			aboutJDialog.setContentPane(getAboutJContentPane());
			}
		return aboutJDialog;
		}

	/**
	 * This method initializes aboutJContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAboutJContentPane()
		{
		if (aboutJContentPane==null)
			{
			aboutJLabel = new JLabel();
			aboutJLabel.setText(aboutText);
			aboutJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			aboutJContentPane=new JPanel();
			aboutJContentPane.setLayout(new BorderLayout());
			aboutJContentPane.add(aboutJLabel, java.awt.BorderLayout.CENTER);
			}
		return aboutJContentPane;
		}

	/**
	 * This method initializes infoJPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getInfoJPanel()
		{
		if (infoJPanel==null)
			{
			infoJLabel = new JLabel();
			infoJLabel.setText("News and Information");
			infoJLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			infoJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			infoJPanel=new JPanel();
			infoJPanel.setLayout(new BorderLayout());
			infoJPanel.add(infoJLabel, java.awt.BorderLayout.CENTER);
			infoJPanel.add(getJackpotJPanel(), java.awt.BorderLayout.EAST);
			infoJPanel.add(getSpacerJPanel(), java.awt.BorderLayout.WEST);
			}
		return infoJPanel;
		}

	/**
	 * This method initializes jackpotJPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJackpotJPanel()
		{
		if (jackpotJPanel==null)
			{
			jLabel1 = new JLabel();
			jLabel1.setText("Next Jackpot:");
			jackpotAmountJLabel = new JLabel();
			jackpotAmountJLabel.setText("(loading)");
			jackpotAmountJLabel.setForeground(java.awt.Color.blue);
			jackpotAmountJLabel.setFont(new java.awt.Font("MS Sans Serif", java.awt.Font.BOLD, 11));
			jackpotJPanel=new JPanel();
			jackpotJPanel.add(jLabel1, null);
			jackpotJPanel.add(jackpotAmountJLabel, null);
			PowerBalls.addListener(this);
			}
		return jackpotJPanel;
		}

	/**
	 * This method initializes spacerJPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpacerJPanel()
		{
		if (spacerJPanel==null)
			{
			spacerJPanel=new JPanel();
			}
		return spacerJPanel;
		}

	/**
	 * This method initializes refreshMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getRefreshMenuItem()
		{
		if (refreshMenuItem==null)
			{
			refreshMenuItem=new JMenuItem();
			refreshMenuItem.setText("Refresh");
			refreshMenuItem.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
							{
							try
								{
								refresh();
								}
							catch (NumberException e1)
								{
								e1.printStackTrace();
								setStatus(e1.row, e1.getMessage());
								}
							}
					});
			}
		return refreshMenuItem;
		}

	protected void refresh() throws NumberException
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
		int row=0;
		for (Iterator nums=mod.getRows().iterator();nums.hasNext();)
			{
			Number num=(Number)nums.next();
			Number newNum;
			try
				{
				newNum=new Number(num.getNumbers(),
										num.getPowerballNumber(),
										num.getPowerPlay(),
										num.getDrawingDateString(),
										(NumberStatusListener)num.getStatusListeners().get(0));
				}
			catch (Exception e)
				{
				e.printStackTrace();
				throw new NumberException(row, e.getMessage());
				}
			mod.rows.set(row, newNum);
//			newNum.start();  //start the new one
			num.quit=true;
			num.interrupt(); //kill the old number
			row++;
			}
		synchronizeRows(mod);
		}

	/**
	 * This method initializes duplicateCountjDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getDuplicateCountjDialog()
		{
		if (duplicateCountjDialog==null)
			{
			duplicateCountjDialog=new JDialog();
			duplicateCountjDialog.setSize(new java.awt.Dimension(188,76));
			duplicateCountjDialog.setModal(true);
			duplicateCountjDialog.setTitle("Enter a number");
			duplicateCountjDialog.setContentPane(getDuplicateCountjContentPane());
			}
		return duplicateCountjDialog;
		}

	/**
	 * This method initializes duplicateCountjContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDuplicateCountjContentPane()
		{
		if (duplicateCountjContentPane==null)
			{
			jLabel2 = new JLabel();
			jLabel2.setText("Duplicate ");
			jLabel = new JLabel();
			jLabel.setText("times.");
			duplicateCountjContentPane=new JPanel();
			duplicateCountjContentPane.setLayout(new FlowLayout());
			duplicateCountjContentPane.add(jLabel2, null);
			duplicateCountjContentPane.add(getDuplicateCountjTextField(), null);
			duplicateCountjContentPane.add(jLabel, null);
			}
		return duplicateCountjContentPane;
		}

	/**
	 * This method initializes duplicateCountjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDuplicateCountjTextField()
		{
		if (duplicateCountjTextField==null)
			{
			duplicateCountjTextField=new JTextField();
			duplicateCountjTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			duplicateCountjTextField.setPreferredSize(new java.awt.Dimension(30,20));
			duplicateCountjTextField.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					String count=((JTextField)e.getSource()).getText();
					if (Common.getInstance().isANumber(count))
						duplicateRow(Integer.parseInt(count));
					getDuplicateCountjDialog().setVisible(false);
					}
				});
			}
		return duplicateCountjTextField;
		}

	/**
	 * This method initializes splashDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getSplashDialog()
		{
		if (splashDialog==null)
			{
			splashDialog=new JDialog();
			splashDialog.setSize(new java.awt.Dimension(500,530));
			splashDialog.setModal(true);
			splashDialog.setContentPane(getSplashContentPane());
			Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
			splashDialog.setLocation(screen.width/2-splashDialog.getWidth()/2,
									 screen.height/2-splashDialog.getHeight()/2);
			}
		return splashDialog;
		}

	/**
	 * This method initializes splashContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSplashContentPane()
		{
		if (splashContentPane==null)
			{
			splashText = new JLabel();
			String msg=Common.getInstance().getPage(SPLASH_PAGE);
			if (msg.toLowerCase().indexOf("error")>=0 || msg.toLowerCase().indexOf("exception")>=0) 
				try
					{
					msg=new String(Common.getInstance().readFile("lotterySplashMsg.html"));
					}
				catch (IOException e)
					{
					e.printStackTrace();
					System.exit(-1);
					}
			splashText.setText(msg);
			splashText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			splashContentPane=new JPanel();
			splashContentPane.setLayout(new BorderLayout());
			splashContentPane.add(splashText, java.awt.BorderLayout.CENTER);
			splashContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			}
		return splashContentPane;
		}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel()
		{
		if (jPanel==null)
			{
			jPanel=new JPanel();
			jPanel.add(getDisagreeButton(), null);
			jPanel.add(getSpacerPanel(), null);
			jPanel.add(getAgreeButton(), null);
			}
		return jPanel;
		}

	/**
	 * This method initializes disagreeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDisagreeButton()
		{
		if (disagreeButton==null)
			{
			disagreeButton=new JButton();
			disagreeButton.setText("I DO NOT AGREE");
			disagreeButton.addActionListener(new ActionListener()
					{
					public void actionPerformed(ActionEvent e)
						{
						setAgree(false);
						getSplashDialog().dispose();
						}
					});
			}
		return disagreeButton;
		}

	/**
	 * This method initializes agreeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAgreeButton()
		{
		if (agreeButton==null)
			{
			agreeButton=new JButton();
			agreeButton.setText("I AGREE");
			agreeButton.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					setAgree(true);
					getSplashDialog().dispose();
					}
				});
			}
		return agreeButton;
		}

	/**
	 * This method initializes spacerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpacerPanel()
		{
		if (spacerPanel==null)
			{
			spacerPanel=new JPanel();
			spacerPanel.setPreferredSize(new java.awt.Dimension(100,10));
			}
		return spacerPanel;
		}

	/**
	 * Launches this application
	 */
	public static void main(String[] args)
		{
		LotteryChecker application=new LotteryChecker();
		try
			{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		application.getSplashDialog().show();
		if (application.isAgree())
			{
			application.initialize();
			application.show();
			}
		else System.exit(0);
		}

	public void updateJackpotAmount(int jackpotAmount)
		{
		if (jackpotAmount>0)
			jackpotAmountJLabel.setText("$"+jackpotAmount+" Million");
		else
			jackpotAmountJLabel.setText("Unknown");
		}

	public void updateNews(String news)
		{
		infoJLabel.setText("<html><b>"+news+"</b></html>");
		}

	/**
	 * @return Returns the agree.
	 */
	public boolean isAgree()
		{
		return agree;
		}

	/**
	 * @param agree The agree to set.
	 */
	public void setAgree(boolean agree)
		{
		this.agree=agree;
		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"
