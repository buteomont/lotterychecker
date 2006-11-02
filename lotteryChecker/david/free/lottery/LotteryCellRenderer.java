package david.free.lottery;

import java.awt.*;
import java.util.StringTokenizer;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class LotteryCellRenderer extends DefaultTableCellRenderer
	{
	private int winnerPattern=0;
	private LotteryListener listener;
	private LotteryCellRenderer()
		{
		super();
		}
	public LotteryCellRenderer(LotteryListener listener)
		{
		super();
		this.listener=listener;
		}
	public void paint(Graphics g)
		{
		FontMetrics fm=getFontMetrics(getFont());
		int x=5;
		for (StringTokenizer tok=new StringTokenizer(getText());tok.hasMoreTokens();)
			{
			String num=tok.nextToken();
			int numWidth=(int)fm.getStringBounds(num+" ", g).getWidth();
			g.setColor((winnerPattern & 1<<tok.countTokens())>0?Color.RED:Color.BLACK);
			((Graphics2D)g).drawString(num, x, 13);
			x+=numWidth;
			}
		}
	public Component getTableCellRendererComponent(
           JTable table,
           java.lang.Object value,
           boolean isSelected,
           boolean hasFocus,
           int row, int column)
		{
		winnerPattern=listener.getPattern(row, column);
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
	