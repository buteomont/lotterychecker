package david.util;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Configurator extends JDialog
	{

	private JPanel	jContentPane	=null;
	private JTabbedPane tabbedPane = null;
	private JPanel generalSettingsPanel = null;
	private JPanel notificationsPanel = null;
	private JCheckBox sendEmailCheckBox = null;
	private JPanel emailPanel = null;
	private JTextField emailAddressTextField = null;
	private JLabel jLabel = null;
	private JPanel txtmsgPanel = null;
	private JCheckBox sendTextMessageCheckBox = null;
	private JLabel jLabel1 = null;
	private JTextField txtNumberTextField = null;
	private JPanel carrierPanel = null;
	private JRadioButton sprintRadioButton = null;
	private JRadioButton verizonRadioButton = null;
	private JRadioButton cingularRadioButton = null;
	private JRadioButton cricketRadioButton = null;
	private JPanel jPanel = null;
	private JCheckBox popupNotifyCheckBox = null;
	private JCheckBox playSoundCheckBox = null;
	private JTextField soundFileTextField = null;
	private JButton soundFilePickerButton = null;
	private JFileChooser soundFileChooser = null;  //  @jve:decl-index=0:visual-constraint="447,15"
	private JPanel generalPanel1 = null;
	private JCheckBox loadOnStartupCheckBox = null;
	private SoundFilter soundFilter = null;  //  @jve:decl-index=0:visual-constraint=""
	public Configurator() throws HeadlessException
		{
		super();
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Dialog owner) throws HeadlessException
		{
		super(owner);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Dialog owner, boolean modal) throws HeadlessException
		{
		super(owner, modal);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Frame owner) throws HeadlessException
		{
		super(owner);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Frame owner, boolean modal) throws HeadlessException
		{
		super(owner, modal);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Dialog owner, String title) throws HeadlessException
		{
		super(owner, title);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Dialog owner, String title, boolean modal) throws HeadlessException
		{
		super(owner, title, modal);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Frame owner, String title) throws HeadlessException
		{
		super(owner, title);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Frame owner, String title, boolean modal) throws HeadlessException
		{
		super(owner, title, modal);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException
		{
		super(owner, title, modal, gc);
		// TODO Auto-generated constructor stub

		initialize();
		}

	public Configurator(Frame owner, String title, boolean modal, GraphicsConfiguration gc)
		{
		super(owner, title, modal, gc);
		// TODO Auto-generated constructor stub

		initialize();
		}

	/**
	 * This method initializes tabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTabbedPane()
		{
		if (tabbedPane==null)
			{
			tabbedPane=new JTabbedPane();
			tabbedPane.setName("tabbedPane");
			tabbedPane.addTab("General", null, getGeneralSettingsPanel(), null);
			tabbedPane.addTab("Winner Notification", null, getNotificationsPanel(), null);
			}
		return tabbedPane;
		}

	/**
	 * This method initializes generalSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralSettingsPanel()
		{
		if (generalSettingsPanel==null)
			{
			generalSettingsPanel=new JPanel();
			generalSettingsPanel.setLayout(new BorderLayout());
			generalSettingsPanel.add(getGeneralPanel1(), java.awt.BorderLayout.NORTH);
			}
		return generalSettingsPanel;
		}

	/**
	 * This method initializes notificationsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNotificationsPanel()
		{
		if (notificationsPanel==null)
			{
			notificationsPanel=new JPanel();
			notificationsPanel.setLayout(new BorderLayout());
			notificationsPanel.add(getEmailPanel(), java.awt.BorderLayout.CENTER);
			notificationsPanel.add(getTxtmsgPanel(), java.awt.BorderLayout.SOUTH);
			notificationsPanel.add(getJPanel(), java.awt.BorderLayout.NORTH);
			}
		return notificationsPanel;
		}

	/**
	 * This method initializes sendEmailCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getSendEmailCheckBox()
		{
		if (sendEmailCheckBox==null)
			{
			sendEmailCheckBox=new JCheckBox();
			sendEmailCheckBox.setText("Send Email");
			sendEmailCheckBox.addChangeListener(new ChangeListener()
					{
					public void stateChanged(ChangeEvent e)
						{
						boolean checked=((JCheckBox)e.getSource()).isSelected();
						jLabel.setEnabled(checked);
						getEmailAddressTextField().setEnabled(checked);
						}
					});
			}
		return sendEmailCheckBox;
		}

	/**
	 * This method initializes emailPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getEmailPanel()
		{
		if (emailPanel==null)
			{
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new java.awt.Insets(30,3,7,57);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new java.awt.Insets(30,20,10,2);
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new java.awt.Insets(0,10,5,2);
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.NORTH;
			gridBagConstraints4.gridx = 0;
			jLabel = new JLabel();
			jLabel.setText("Address:");
			jLabel.setEnabled(false);
			emailPanel=new JPanel();
			emailPanel.setLayout(new GridBagLayout());
			emailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Email", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			emailPanel.add(getSendEmailCheckBox(), gridBagConstraints4);
			emailPanel.add(jLabel, gridBagConstraints5);
			emailPanel.add(getEmailAddressTextField(), gridBagConstraints6);
			}
		return emailPanel;
		}

	/**
	 * This method initializes emailAddressTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getEmailAddressTextField()
		{
		if (emailAddressTextField==null)
			{
			emailAddressTextField=new JTextField();
			emailAddressTextField.setPreferredSize(new java.awt.Dimension(120,20));
			emailAddressTextField.setEnabled(false);
			}
		return emailAddressTextField;
		}

	/**
	 * This method initializes txtmsgPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTxtmsgPanel()
		{
		if (txtmsgPanel==null)
			{
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new java.awt.Insets(5,3,5,24);
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new java.awt.Insets(41,3,41,2);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new java.awt.Insets(43,3,44,2);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new java.awt.Insets(0,10,32,2);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
			gridBagConstraints.gridx = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("Number:");
			jLabel1.setEnabled(false);
			txtmsgPanel=new JPanel();
			txtmsgPanel.setLayout(new GridBagLayout());
			txtmsgPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Text Message", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			txtmsgPanel.add(getSendTextMessageCheckBox(), gridBagConstraints);
			txtmsgPanel.add(jLabel1, gridBagConstraints1);
			txtmsgPanel.add(getTxtNumberTextField(), gridBagConstraints2);
			txtmsgPanel.add(getCarrierPanel(), gridBagConstraints3);
			}
		return txtmsgPanel;
		}

	/**
	 * This method initializes sendTextMessageCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getSendTextMessageCheckBox()
		{
		if (sendTextMessageCheckBox==null)
			{
			sendTextMessageCheckBox=new JCheckBox();
			sendTextMessageCheckBox.setText("<html>Send Text<br>Message</html>");
			sendTextMessageCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			sendTextMessageCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			sendTextMessageCheckBox.addChangeListener(new ChangeListener()
				{
				public void stateChanged(ChangeEvent e)
					{
					boolean checked=((JCheckBox)e.getSource()).isSelected();
					jLabel1.setEnabled(checked);
					getTxtNumberTextField().setEnabled(checked);
					getSprintRadioButton().setEnabled(checked);
					getVerizonRadioButton().setEnabled(checked);
					getCingularRadioButton().setEnabled(checked);
					getCricketRadioButton().setEnabled(checked);
					}
				});
			}
		return sendTextMessageCheckBox;
		}

	/**
	 * This method initializes txtNumberTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtNumberTextField()
		{
		if (txtNumberTextField==null)
			{
			txtNumberTextField=new JTextField();
			txtNumberTextField.setPreferredSize(new java.awt.Dimension(120,20));
			txtNumberTextField.setEnabled(false);
			}
		return txtNumberTextField;
		}

	/**
	 * This method initializes carrierPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCarrierPanel()
		{
		if (carrierPanel==null)
			{
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(4);
			gridLayout.setColumns(1);
			carrierPanel=new JPanel();
			carrierPanel.setEnabled(true);
			carrierPanel.setLayout(gridLayout);
			carrierPanel.add(getSprintRadioButton(), null);
			carrierPanel.add(getVerizonRadioButton(), null);
			carrierPanel.add(getCingularRadioButton(), null);
			carrierPanel.add(getCricketRadioButton(), null);
			}
		return carrierPanel;
		}

	/**
	 * This method initializes sprintRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getSprintRadioButton()
		{
		if (sprintRadioButton==null)
			{
			sprintRadioButton=new JRadioButton();
			sprintRadioButton.setText("Sprint");
			sprintRadioButton.setEnabled(false);
			}
		return sprintRadioButton;
		}

	/**
	 * This method initializes verizonRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getVerizonRadioButton()
		{
		if (verizonRadioButton==null)
			{
			verizonRadioButton=new JRadioButton();
			verizonRadioButton.setText("Verizon");
			verizonRadioButton.setEnabled(false);
			}
		return verizonRadioButton;
		}

	/**
	 * This method initializes cingularRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getCingularRadioButton()
		{
		if (cingularRadioButton==null)
			{
			cingularRadioButton=new JRadioButton();
			cingularRadioButton.setText("Cingular");
			cingularRadioButton.setEnabled(false);
			}
		return cingularRadioButton;
		}

	/**
	 * This method initializes cricketRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getCricketRadioButton()
		{
		if (cricketRadioButton==null)
			{
			cricketRadioButton=new JRadioButton();
			cricketRadioButton.setText("Cricket");
			cricketRadioButton.setEnabled(false);
			}
		return cricketRadioButton;
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
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new java.awt.Insets(13,3,13,17);
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridx = 3;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new java.awt.Insets(14,3,15,2);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new java.awt.Insets(13,3,13,2);
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new java.awt.Insets(5,10,5,2);
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.gridx = 0;
			jPanel=new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Local", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getPopupNotifyCheckBox(), gridBagConstraints7);
			jPanel.add(getPlaySoundCheckBox(), gridBagConstraints8);
			jPanel.add(getSoundFileTextField(), gridBagConstraints9);
			jPanel.add(getSoundFilePickerButton(), gridBagConstraints10);
			}
		return jPanel;
		}

	/**
	 * This method initializes popupNotifyCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getPopupNotifyCheckBox()
		{
		if (popupNotifyCheckBox==null)
			{
			popupNotifyCheckBox=new JCheckBox();
			popupNotifyCheckBox.setText("<html>Bring to<br>foreground</html>");
			popupNotifyCheckBox.setSelected(true);
			}
		return popupNotifyCheckBox;
		}

	/**
	 * This method initializes playSoundCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getPlaySoundCheckBox()
		{
		if (playSoundCheckBox==null)
			{
			playSoundCheckBox=new JCheckBox();
			playSoundCheckBox.setText("Play sound:");
			playSoundCheckBox.addChangeListener(new ChangeListener()
					{
					public void stateChanged(ChangeEvent e)
						{
						boolean checked=((JCheckBox)e.getSource()).isSelected();
						jLabel1.setEnabled(checked);
						getSoundFileTextField().setEnabled(checked);
						getSoundFilePickerButton().setEnabled(checked);
						}
					});
			}
		return playSoundCheckBox;
		}

	/**
	 * This method initializes soundFileTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSoundFileTextField()
		{
		if (soundFileTextField==null)
			{
			soundFileTextField=new JTextField();
			soundFileTextField.setPreferredSize(new java.awt.Dimension(150,20));
			soundFileTextField.setEnabled(false);
			}
		return soundFileTextField;
		}

	/**
	 * This method initializes soundFilePickerButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSoundFilePickerButton()
		{
		if (soundFilePickerButton==null)
			{
			soundFilePickerButton=new JButton();
			soundFilePickerButton.setText("...");
			soundFilePickerButton.setEnabled(false);
			soundFilePickerButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e)
					{
			        int returnVal = getSoundFileChooser().showOpenDialog(Configurator.this);
			        if (returnVal == JFileChooser.APPROVE_OPTION)
			        	{
			            File file = getSoundFileChooser().getSelectedFile();
			            getSoundFileTextField().setText(file.getAbsolutePath());
			        	}
					}});
			}
		return soundFilePickerButton;
		}

	/**
	 * This method initializes soundFileChooser	
	 * 	
	 * @return javax.swing.JFileChooser	
	 */
	private JFileChooser getSoundFileChooser()
		{
		if (soundFileChooser==null)
			{
			soundFileChooser=new JFileChooser();
			soundFileChooser.setDialogTitle("Choose Sound File");
			soundFileChooser.setFileSelectionMode(javax.swing.JFileChooser.OPEN_DIALOG);
			soundFileChooser.setApproveButtonText("Select");
			soundFileChooser.setSize(new java.awt.Dimension(491,368));
			soundFileChooser.setFileFilter(getSoundFilter());
			soundFileChooser.setVisible(true);
			}
		return soundFileChooser;
		}

	/**
	 * This method initializes generalPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralPanel1()
		{
		if (generalPanel1==null)
			{
			generalPanel1=new JPanel();
			generalPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			generalPanel1.add(getLoadOnStartupCheckBox(), null);
			}
		return generalPanel1;
		}

	/**
	 * This method initializes generalCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getLoadOnStartupCheckBox()
		{
		if (loadOnStartupCheckBox==null)
			{
			loadOnStartupCheckBox=new JCheckBox();
			loadOnStartupCheckBox.setText("<html>Load on startup</html>");
			}
		return loadOnStartupCheckBox;
		}

	/**
	 * This method initializes soundFilter	
	 * 	
	 * @return david.util.SoundFilter	
	 */
	private SoundFilter getSoundFilter()
		{
		if (soundFilter==null)
			{
			soundFilter=new SoundFilter();
			}
		return soundFilter;
		}

	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		// TODO Auto-generated method stub

		}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
		{
		this.setSize(427, 351);
		this.setTitle("Settings");
		this.setContentPane(getJContentPane());
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
			jContentPane=new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTabbedPane(), java.awt.BorderLayout.CENTER);
			}
		return jContentPane;
		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"
