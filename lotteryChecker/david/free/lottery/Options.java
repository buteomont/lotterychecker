package david.free.lottery;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

public class Options extends JDialog
	{
	private static final long	serialVersionUID	=1L;
	public static final int	CHOICE_OK		=0;
	public static final int	CHOICE_CANCEL	=1;
	private int choice=1;
	private JTabbedPane contentTabbedPane = null;
	private JPanel GeneralOptionsPanel = null;
	private JCheckBox emailNotifyCheckBox = null;
	private JPanel notifyWhenPanel = null;
	private JTextField emailTextField = null;
	private JCheckBox smsCheckBox = null;
	private JTextField smsTextField = null;
	private JCheckBox foreCheckBox = null;
	private JPanel contentPanel = null;
	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private String notReadyMsg = "The code is unfinished for this feature.";  //  @jve:decl-index=0:
	
	public Options(Window owner, ModalityType modalityType)
		{
		super(owner, modalityType);
		initialize();
		}

	/**
	 * This is the default constructor
	 */
	private Options()
		{
		super();
		initialize();
		}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
		{
		this.setSize(300, 230);
		this.setContentPane(getContentPanel());
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Options");
		}

	/**
	 * This method initializes contentTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getContentTabbedPane()
		{
		if (contentTabbedPane==null)
			{
			contentTabbedPane=new JTabbedPane();
			contentTabbedPane.addTab("General", null, getGeneralOptionsPanel(), null);
			contentTabbedPane.addTab("Notify", null, getNotifyWhenPanel(), null);
			}
		return contentTabbedPane;
		}

	@Override
	public Container getContentPane()
		{
		return getContentTabbedPane();
		}

	/**
	 * This method initializes GeneralOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralOptionsPanel()
		{
		if (GeneralOptionsPanel==null)
			{
			GeneralOptionsPanel=new JPanel();
			GeneralOptionsPanel.setLayout(new GridBagLayout());
			}
		return GeneralOptionsPanel;
		}

	/**
	 * This method initializes emailNotifyCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEmailNotifyCheckBox()
		{
		if (emailNotifyCheckBox==null)
			{
			emailNotifyCheckBox=new JCheckBox();
			emailNotifyCheckBox.setText("Email");
			emailNotifyCheckBox.setEnabled(false);
			emailNotifyCheckBox.setToolTipText(notReadyMsg);
			emailNotifyCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
			emailNotifyCheckBox.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						getEmailTextField().setEnabled(emailNotifyCheckBox.isSelected());
						}
				});
			}
		return emailNotifyCheckBox;
		}

	/**
	 * This method initializes notifyWhenPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNotifyWhenPanel()
		{
		if (notifyWhenPanel==null)
			{
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridwidth = 1;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.insets = new Insets(3, 0, 3, 0);
			gridBagConstraints.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 0;
			notifyWhenPanel=new JPanel();
			notifyWhenPanel.setLayout(new GridBagLayout());
			notifyWhenPanel.setBorder(BorderFactory.createTitledBorder(null, "Notify me when I\'m a winner", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 70, 213)));
			notifyWhenPanel.add(getEmailNotifyCheckBox(), gridBagConstraints1);
			notifyWhenPanel.add(getEmailTextField(), gridBagConstraints);
			notifyWhenPanel.add(getSmsCheckBox(), gridBagConstraints2);
			notifyWhenPanel.add(getForeCheckBox(), gridBagConstraints4);
			notifyWhenPanel.add(getSmsTextField(), gridBagConstraints5);
			}
		return notifyWhenPanel;
		}

	/**
	 * This method initializes emailTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getEmailTextField()
		{
		if (emailTextField==null)
			{
			emailTextField=new JTextField();
			emailTextField.setPreferredSize(new Dimension(150, 20));
			emailTextField.setToolTipText(notReadyMsg);
			emailTextField.setEnabled(false);
			}
		return emailTextField;
		}

	/**
	 * This method initializes smsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getSmsCheckBox()
		{
		if (smsCheckBox==null)
			{
			smsCheckBox=new JCheckBox();
			smsCheckBox.setText("SMS");
			smsCheckBox.setHorizontalTextPosition(SwingConstants.TRAILING);
			smsCheckBox.setEnabled(false);
			smsCheckBox.setToolTipText(notReadyMsg);
			smsCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
			smsCheckBox.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						getSmsTextField().setEnabled(smsCheckBox.isSelected());
						}
				});
			}
		return smsCheckBox;
		}

	/**
	 * This method initializes smsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSmsTextField()
		{
		if (smsTextField==null)
			{
			smsTextField=new JTextField();
			smsTextField.setPreferredSize(new Dimension(150, 20));
			smsTextField.setToolTipText(notReadyMsg);
			smsTextField.setEnabled(false);
			}
		return smsTextField;
		}

	/**
	 * This method initializes foreCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getForeCheckBox()
		{
		if (foreCheckBox==null)
			{
			foreCheckBox=new JCheckBox();
			foreCheckBox.setText("Bring program to foreground");
			foreCheckBox.setToolTipText(notReadyMsg);
			foreCheckBox.setEnabled(false);
			}
		return foreCheckBox;
		}

	/**
	 * This method initializes contentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContentPanel()
		{
		if (contentPanel==null)
			{
			contentPanel=new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getContentTabbedPane(), BorderLayout.CENTER);
			contentPanel.add(getButtonPanel(), BorderLayout.SOUTH);
			}
		return contentPanel;
		}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel()
		{
		if (buttonPanel==null)
			{
			buttonPanel=new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
			}
		return buttonPanel;
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
			okButton.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						choice=CHOICE_OK;
						dispose();
						}
				});
			}
		return okButton;
		}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton()
		{
		if (cancelButton==null)
			{
			cancelButton=new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						choice=CHOICE_CANCEL;
						dispose();
						}
				});
			}
		return cancelButton;
		}

	public int getChoice()
		{
		return choice;
		}

	public boolean notifyByEmail()
		{
		return getEmailNotifyCheckBox().isSelected();
		}

	public boolean notifyBySms()
		{
		return getSmsCheckBox().isSelected();
		}

	public String getEmailAddress()
		{
		return getEmailTextField().getText();
		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"
