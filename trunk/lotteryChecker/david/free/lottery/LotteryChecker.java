package david.free.lottery;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import david.util.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.lang.String;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import java.awt.Insets;

public class LotteryChecker extends JFrame 
	implements LotteryListener, JackpotListener, ActionListener
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
	private JLabel messageJLabel 		=null;
	private JPopupMenu optionJPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="587,326"
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
	private JDialog splashDialog = null;  //  @jve:decl-index=0:visual-constraint="20,324"
	private JPanel splashContentPane = null;
	private JPanel jPanel = null;
	private JButton disagreeButton = null;
	private JButton agreeButton = null;
	private JPanel spacerPanel = null;
	private boolean agree=false;
	private JButton okButton = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private DonateButton donateButton = null;
	private JLabel jLabel3 = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel4 = null;
	private JCheckBox dontAskAgainCheckBox = null;
	private boolean dontAskAgain=false;
	private int windowWidth=682;
	private int windowHeight=291;
	private int windowXLoc=600;
	private int windowYLoc=400;
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
		messageJLabel = new JLabel();
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
		initializeOptions();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.setSize(getWindowWidth(), getWindowHeight());
		this.setLocation(getWindowXLoc(), getWindowYLoc());
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

	private void initializeOptions()
		{
		Map options=((AbstractNumberTableModel)getNumberListJTable().getModel()).getOptions();
		for (Iterator opts=options.keySet().iterator();opts.hasNext();)
			{
			String name=(String)opts.next();
			String value=(String)options.get(name);
			if (name.equalsIgnoreCase("windowWidth")) 
				{
				setWindowWidth(Integer.parseInt(value));
				this.setSize(getWindowWidth(), getWindowHeight());
				}
			if (name.equalsIgnoreCase("windowHeight")) 
				{
				setWindowHeight(Integer.parseInt(value));
				this.setSize(getWindowWidth(), getWindowHeight());
				}
			if (name.equalsIgnoreCase("windowXLoc")) 
				{
				setWindowXLoc(Integer.parseInt(value));
				this.setLocation(getWindowXLoc(), getWindowYLoc());
				}
			if (name.equalsIgnoreCase("windowYLoc")) 
				{
				setWindowYLoc(Integer.parseInt(value));
				this.setLocation(getWindowXLoc(), getWindowYLoc());
				}
			if (name.equalsIgnoreCase("dontAskAgain")) 
				{
				setDontAskAgain(Boolean.valueOf(value).booleanValue());
				setAgree(Boolean.valueOf(value).booleanValue()); //implies agreement
				}

			}
		
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
			messageJLabel = new JLabel();//here for benefit of visual editor
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
			if (false) //comment out without generating compiler warning
				jJMenuBar.add(getEditMenu());
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
		try
			{
			AbstractNumberTableModel model=(AbstractNumberTableModel)getNumberListJTable().getModel();
			model.persistRows(false);
			Map opts=packOptions();
			model.setOptions(opts);
			model.persistOptions(true);
			setMessage("Lottery numbers saved.");
			}
		catch (IOException e)
			{
			e.printStackTrace();
			setMessage("Error - "+e.getMessage());
			}
		}

	private Map packOptions()
		{
		Map opts=new Hashtable();
		opts.put("dontAskAgain", new Boolean(isDontAskAgain()));
		opts.put("windowWidth", ((int)getSize().getWidth())+"");
		opts.put("windowHeight", ((int)getSize().getHeight())+"");
		opts.put("windowHeight", ((int)getSize().getHeight())+"");
		opts.put("windowXLoc", ((int)getLocation().getX())+"");
		opts.put("windowYLoc", ((int)getLocation().getY())+"");
		return opts;
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
			LotteryCellEditor lotteryCellEditor=new LotteryCellEditor();
			lotteryCellEditor.addCellEditorListener(new CellEditorListener()
				{
				public void editingCanceled(ChangeEvent e)
				{
				synchronizeRows((AbstractNumberTableModel)numberListJTable.getModel());
				}
				public void editingStopped(ChangeEvent e)
				{
				synchronizeRows((AbstractNumberTableModel)numberListJTable.getModel());
				}
				});
			mod.getColumn(Number.COLUMN_WHITE_NUMBERS).setCellEditor(lotteryCellEditor);
			mod.getColumn(Number.COLUMN_POWERBALL_NUMBER).setCellEditor(lotteryCellEditor);
			mod.getColumn(Number.COLUMN_DRAW_DATE).setCellEditor(lotteryCellEditor);

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
							Point menuloc=e.getPoint();
							NumberTable table=getNumberListJTable();
							lastRowClicked=table.rowAtPoint(menuloc);
							if (table.getSelectedRowCount()==0)
								table.setRowSelectionInterval(lastRowClicked, lastRowClicked);
							menuloc.translate(table.getLocationOnScreen().x,table.getLocationOnScreen().y);
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
		List rows=((AbstractNumberTableModel)getNumberListJTable().getModel()).getRows();
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
		for (Iterator i=model.getRows().iterator(); i.hasNext();)
			{
			Object o=i.next();
			if (o instanceof Number)
				{
				Number num=(Number)o;
				model.setValueQuietlyAt(num.getHTMLNumbers(), row, Number.COLUMN_WHITE_NUMBERS);
				model.setValueQuietlyAt(num.getHTMLPowerballNumber(), row, Number.COLUMN_POWERBALL_NUMBER);
				model.setValueQuietlyAt(num.getPowerPlay(), row, Number.COLUMN_POWER_PLAY);
				model.setValueQuietlyAt(num.getDrawingDateString(), row, Number.COLUMN_DRAW_DATE);
				num.setStatusListener(getNewNumberStatusListener(row));
				if (!num.isAlive() && !num.quit) 
					num.start();
				row++;
				}
			else baddies.add(o);
			}
		for (Iterator i=baddies.iterator();i.hasNext();)
			model.getRows().remove(i.next());
		}

	public void sortNumbers()
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
		Collections.sort(mod.getRows(), new DrawDateComparator());
		synchronizeRows(mod);
		}
	
	protected void updateNumbers(int row)
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();

		//ignore any input until the whole row is complete
		for (int c=0;c<Number.COLUMN_STATUS;c++)
			{
			if (mod.getValueAt(row, c)==null)
				{
				if (c==Number.COLUMN_POWER_PLAY)
					{
					mod.setValueQuietlyAt(new Boolean(false), row, Number.COLUMN_POWER_PLAY);
					continue; //power play could be checked or not
					}
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

//		if (mod.rows.contains(newnum)) 
//			{
//			setStatus(row,"Error - duplicate number.");
//			setMessage("Duplicate number.");
//			}
		Number oldnum=mod.getNumber(row);
		String old=null;
		if (oldnum != null) 
			{
			old=oldnum.toString();
			oldnum.quit=true;  //causes oldnum to exit
			oldnum.interrupt(); 
			}
	
		mod.setNumber(newnum,row,!mod.getRows().contains(newnum)); //don't start it if it's a dupe
	
		if (oldnum==null)
			System.out.println("Number "+newnum+" added.");
		else
			{
			System.out.println("Number "+old+" replaced with "+newnum);
			}
		
//		replace with sanctified numbers
		mod.setValueQuietlyAt(newnum.getHTMLNumbers(), row, Number.COLUMN_WHITE_NUMBERS); 
		mod.setValueQuietlyAt(newnum.getHTMLPowerballNumber(), row, Number.COLUMN_POWERBALL_NUMBER); 
		mod.setValueQuietlyAt(newnum.getDrawingDateString(), row, Number.COLUMN_DRAW_DATE);
		
//		sortNumbers(); //put them in chronological order
		}

	private NumberStatusListener getNewNumberStatusListener(final int row)
		{
		return new NumberStatusListener()
			{
			public void notify(Number source, String status, String message)
				{
				//colorize the numbers
				AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
				mod.setValueQuietlyAt(source.getHTMLNumbers(), row, Number.COLUMN_WHITE_NUMBERS); 
				mod.setValueQuietlyAt(source.getHTMLPowerballNumber(), row, Number.COLUMN_POWERBALL_NUMBER);
				
				//update the status line
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
			optionJPopupMenu=new JPopupMenu()
				{
				public void setVisible(boolean show)
					{
					if (show)
						{
						getDuplicateJMenuItem().setEnabled(true);
						getRemoveJMenuItem().setEnabled(true);
						if (getNumberListJTable().getSelectedRowCount()==0)
							{
							getDuplicateJMenuItem().setEnabled(false);
							getRemoveJMenuItem().setEnabled(false);
							}
						else if (getNumberListJTable().getSelectedRowCount()>1)
							{
							getDuplicateJMenuItem().setEnabled(false);
							}
						}
					super.setVisible(show);
					}
				};
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
			duplicateJMenuItem.setText("Duplicate selected row...");
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
		Number orignum=(Number)mod.getRows().get(getNumberListJTable().getSelectedRow());
		while (count-->0)
			{
			try
				{
				Number newnum=new Number(orignum.getNumbers(),
										 orignum.getPowerballNumber(),
										 orignum.getPowerPlay(),
										 Number.getNextDrawingAfter(orignum.getDrawingDateString()),
										 getNewNumberStatusListener(mod.getRows().size()));
				mod.getRows().add(newnum);
				getOptionJPopupMenu().setVisible(false);
//				if (mod.rows.size()>=10)
//					addEmptyRow();
				orignum=(Number)mod.getRows().get(mod.getRows().size()-1);
				}
			catch (Exception e)
				{
				e.printStackTrace();
				break;
				}
			}
		synchronizeRows(mod);
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
			removeJMenuItem.setText("Remove selected rows");
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
			synchronizeRows(mod);
			repaint();
			}
		}

	private void removeRow(int row, AbstractNumberTableModel model)
		{
		Number oldRow=(Number)model.getRows().remove(row);
		oldRow.quit=true;
		oldRow.interrupt();
		for (;row<model.getRows().size();row++)
			{
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_WHITE_NUMBERS), row, Number.COLUMN_WHITE_NUMBERS);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_POWERBALL_NUMBER), row, Number.COLUMN_POWERBALL_NUMBER);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_POWER_PLAY), row, Number.COLUMN_POWER_PLAY);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_DRAW_DATE), row, Number.COLUMN_DRAW_DATE);
			model.setValueQuietlyAt(model.getValueAt(row+1, Number.COLUMN_STATUS), row, Number.COLUMN_STATUS);
			Number num=(Number)model.getRows().get(row);
			num.setStatusListener(getNewNumberStatusListener(row));
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
			mod.getRows().set(row, newNum);
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
			duplicateCountjContentPane.add(getOkButton(), null);
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
					processDupRequest();
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
			gridBagConstraints.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.ipadx = 0;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.insets = new Insets(0, 40, 0, 20);
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.ipadx = 184;
			gridBagConstraints1.gridy = 2;
			splashContentPane=new JPanel();
			splashContentPane.setLayout(new GridBagLayout());
			splashContentPane.add(getJPanel(), gridBagConstraints1);
			splashContentPane.add(getJPanel3(), gridBagConstraints2);
			splashContentPane.add(getJPanel1(), gridBagConstraints);
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
			jLabel3 = new JLabel();
			jLabel3.setText("<html><center><h2>Good Luck!</h2></center></html>");
			jLabel3.setPreferredSize(new java.awt.Dimension(100,100));
			jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jPanel=new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jPanel.add(jLabel3, java.awt.BorderLayout.NORTH);
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
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton()
		{
		if (okButton==null)
			{
			okButton=new JButton();
			okButton.setText("OK");
			okButton.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					processDupRequest();
					}
				});
			}
		return okButton;
		}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2()
		{
		if (jPanel2==null)
			{
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setVgap(15);
			jPanel2=new JPanel();
			jPanel2.setLayout(flowLayout1);
			jPanel2.add(getDisagreeButton(), null);
			jPanel2.add(getSpacerPanel(), null);
			jPanel2.add(getAgreeButton(), null);
			}
		return jPanel2;
		}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3()
		{
		if (jPanel3==null)
			{
			BorderLayout borderLayout = new BorderLayout();
			jPanel3=new JPanel();
			jPanel3.setLayout(borderLayout);
			jPanel3.add(getDonateButton(), BorderLayout.WEST);
			jPanel3.add(getDontAskAgainCheckBox(), BorderLayout.EAST);
			}
		return jPanel3;
		}

	/**
	 * This method initializes donateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private DonateButton getDonateButton()
		{
		if (donateButton==null)
			{
			donateButton=new DonateButton();
			}
		return donateButton;
		}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1()
		{
		if (jPanel1==null)
			{
			jLabel4 = new JLabel();
			jLabel4.setPreferredSize(new java.awt.Dimension(1550,312));
			jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
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
			jLabel4.setText(msg);
			jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
			jPanel1=new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setPreferredSize(new java.awt.Dimension(1560,322));
			jPanel1.add(jLabel4, java.awt.BorderLayout.CENTER);
			}
		return jPanel1;
		}

	/**
	 * This method initializes dontAskAgainCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getDontAskAgainCheckBox()
		{
		if (dontAskAgainCheckBox==null)
			{
			dontAskAgainCheckBox=new JCheckBox();
			dontAskAgainCheckBox.setText("Don't ask me again");
			dontAskAgainCheckBox.addActionListener(this);
			}
		return dontAskAgainCheckBox;
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
		application.initialize();
		if (!application.isDontAskAgain())
			application.getSplashDialog().show();
		if (application.isAgree())
			{
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

	private void processDupRequest()
		{
		String count=getDuplicateCountjTextField().getText();
		if (Common.getInstance().isANumber(count))
			duplicateRow(Integer.parseInt(count));
		getDuplicateCountjDialog().setVisible(false);
		}

	public int getWindowHeight()
		{
		return windowHeight;
		}

	public void setWindowHeight(int windowHeight)
		{
		this.windowHeight=windowHeight;
		}

	public int getWindowWidth()
		{
		return windowWidth;
		}

	public void setWindowWidth(int windowWidth)
		{
		this.windowWidth=windowWidth;
		}

	public int getWindowXLoc()
		{
		return windowXLoc;
		}

	public void setWindowXLoc(int windowXLoc)
		{
		this.windowXLoc=windowXLoc;
		}

	public int getWindowYLoc()
		{
		return windowYLoc;
		}

	public void setWindowYLoc(int windowYLoc)
		{
		this.windowYLoc=windowYLoc;
		}

	public boolean isDontAskAgain()
		{
		return dontAskAgain;
		}

	public void setDontAskAgain(boolean dontAskAgain)
		{
		this.dontAskAgain=dontAskAgain;
		}

	public void actionPerformed(ActionEvent e)
		{
		if (e.getSource()==getDontAskAgainCheckBox())
			{
			setDontAskAgain(getDontAskAgainCheckBox().isSelected());
			}
		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"
