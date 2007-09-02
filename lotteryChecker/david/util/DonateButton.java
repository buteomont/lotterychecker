package david.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DonateButton extends JButton
	{
//	private static final String paypal="https://www.paypal.com/us/cgi-bin/webscr?cmd=p/ema/index-outside";
	private static final String paypal="https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=david%40depowell%2ecom&item_name=Lottery%20Checker%20Donation";
	public DonateButton()
		{
		setText("Donate");
		setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		setVerticalAlignment(javax.swing.SwingConstants.TOP);
		setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		addActionListener(new ActionListener()
			{
			public void actionPerformed(ActionEvent e)
				{
				BrowserControl.displayURL(paypal);
				}
			});
		}
	}
