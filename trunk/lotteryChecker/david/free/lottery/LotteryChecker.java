package david.free.lottery;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class LotteryChecker extends JFrame implements LotteryListener, JackpotListener
	{

	protected static final String	aboutText	="<html><center><h3>Powerball Lottery Checker</h3><small>by </small><b>David E. Powell</b></center></html>";
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
	private JPopupMenu optionJPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="558,103"
	private JMenuItem duplicateJMenuItem = null;
	private int lastRowClicked=0;
	private JMenuItem removeJMenuItem = null;
	private JDialog aboutJDialog = null;  //  @jve:decl-index=0:visual-constraint="581,166"
	private JPanel aboutJContentPane = null;
	private JLabel aboutJLabel = null;
	private JPanel infoJPanel = null;
	private JLabel infoJLabel = null;
	private JPanel jackpotJPanel = null;
	private JLabel jackpotAmountJLabel = null;
	private JLabel jLabel1 = null;
	public LotteryChecker() throws HeadlessException
		{
		super();
		initialize();
		}

	public LotteryChecker(GraphicsConfiguration gc)
		{
		super(gc);
		initialize();
		}

	public LotteryChecker(String title) throws HeadlessException
		{
		super(title);
		initialize();
		}

	public LotteryChecker(String title, GraphicsConfiguration gc)
		{
		super(title, gc);
		initialize();
		}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
		{
		try
			{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch (Exception e)
			{
			}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.setSize(515, 234);
		this.setLocation(600, 400);
		this.setContentPane(getJContentPane());
		this.setTitle("Powerball Checker");
		this.messageJLabel.setText("");
		this.addWindowListener(new WindowAdapter()
				{
				public void windowClosing(WindowEvent e)
					{
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
						Component source=((Component)e.getSource()).getParent();
						JDialog about=getAboutJDialog();
						about.setLocation(source.getX(), source.getY());
						about.show();
						}
				});
			}
		return aboutMenuItem;
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
			numberListJTable=new NumberTable((Object[][])new String[10][4], (Object[])new String[] { "Numbers", "PB", "Draw Date", "Status" });
			TableColumnModel mod=numberListJTable.getColumnModel();
			mod.getColumn(0).setCellRenderer(new LotteryCellRenderer(this));
			mod.getColumn(1).setCellRenderer(new LotteryCellRenderer(this));
			mod.getColumn(3).setPreferredWidth(50);
			mod.getColumn(2).setPreferredWidth(80);
			mod.getColumn(2).setMaxWidth(80);
			mod.getColumn(2).setMinWidth(80);
			mod.getColumn(1).setPreferredWidth(30);
			mod.getColumn(1).setMaxWidth(30);
			mod.getColumn(1).setMinWidth(30);
			mod.getColumn(0).setPreferredWidth(90);
			mod.getColumn(0).setMaxWidth(90);
			mod.getColumn(0).setMinWidth(90);
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
							menuloc.translate(getX(), getY()+getContentPane().getY());
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
			if (column==0) return number.getPattern(Number.WHITE_NUMBERS);
			else return number.getPattern(Number.POWERBALL);
			}
		else return 0;
		}
	
	private void synchronizeRows(AbstractNumberTableModel model)
		{
		int row=0;
		for (Iterator i=model.rows.iterator(); i.hasNext();)
			{
			Number num=(Number)i.next();
			model.setValueQuietlyAt(num.getNumbers(), row, 0);
			model.setValueQuietlyAt(num.getPowerballNumber(), row, 1);
			model.setValueQuietlyAt(num.getDrawingDateString(), row, 2);
			num.addStatusListener(getNewNumberStatusListener(row));
			if (!num.isAlive()) num.start();
			row++;
			}
		
		}

	protected void updateNumbers(int row)
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();

		//ignore any input until the whole row is complete
		for (int c=0;c<3;c++)
			{
			if (mod.getValueAt(row, c)==null)
				return;
			}
		Number newnum=null;
		try
			{
			newnum=new Number((String)mod.getValueAt(row, 0),
									(String)mod.getValueAt(row, 1), 
									(String)mod.getValueAt(row, 2),
									getNewNumberStatusListener(row));
			mod.setValueQuietlyAt(newnum.getDrawingDateString(), row, 2);
			}
		catch (Exception e)
			{
			setStatus(row,"Error");
			setMessage(e.getMessage());
			return;
			}
		Number oldnum=mod.getNumber(row);
		String old=null;
		if (oldnum != null) 
			{
			old=oldnum.toString();
			oldnum.interrupt(); //causes oldnum to exit
			}
		
		mod.setNumber(newnum,row);
		
		messageJLabel.setText("");
		
//		replace with sanctified numbers
		mod.setValueQuietlyAt(newnum.getNumbers(), row, 0); 
		mod.setValueQuietlyAt(newnum.getPowerballNumber(), row, 1); 
		
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
		mod.setValueQuietlyAt(text, row, 3);
		repaint();
		}

	private void setMessage(String message)
		{
		messageJLabel.setText(message);
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
			duplicateJMenuItem.setText("Duplicate Row");
			duplicateJMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					duplicateRow();
					}
				});
			}
		return duplicateJMenuItem;
		}

	protected void duplicateRow()
		{
		AbstractNumberTableModel mod=(AbstractNumberTableModel)getNumberListJTable().getModel();
		Number orignum=(Number)mod.rows.get(lastRowClicked);
		try
			{
			Number newnum=new Number(orignum.getNumbers(),
									 orignum.getPowerballNumber(),
									 Number.getNextDrawingAfter(orignum.getDrawingDateString()),
									 getNewNumberStatusListener(mod.rows.size()));
			mod.rows.add(newnum);
			getOptionJPopupMenu().setVisible(false);
			synchronizeRows(mod);
			}
		catch (Exception e)
			{
			e.printStackTrace();
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
			model.setValueQuietlyAt(model.getValueAt(row+1, 0), row, 0);
			model.setValueQuietlyAt(model.getValueAt(row+1, 1), row, 1);
			model.setValueQuietlyAt(model.getValueAt(row+1, 2), row, 2);
			model.setValueQuietlyAt(model.getValueAt(row+1, 3), row, 3);
			}
		model.setValueQuietlyAt("", row, 0);
		model.setValueQuietlyAt("", row, 1);
		model.setValueQuietlyAt("", row, 2);
		model.setValueQuietlyAt("", row, 3);
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
	 * Launches this application
	 */
	public static void main(String[] args)
		{
		LotteryChecker application=new LotteryChecker();
		application.show();
		}

	public void updateJackpotAmount(int jackpotAmount)
		{
		jackpotAmountJLabel.setText("$"+jackpotAmount+" Million");
		}

	public void updateNews(String news)
		{
		// TODO Auto-generated method stub
		
		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"
