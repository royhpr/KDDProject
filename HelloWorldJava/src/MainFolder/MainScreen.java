package MainFolder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.DefaultCaret;

public class MainScreen extends JFrame implements MessageInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Constants
	private int padding = 10;

	//GUI Components
	private static MainScreen instance;
	
	private JPanel contentPane;
	private JLabel lblTitle;
	private JButton btnBrowse;
	private JTextField txtDirectoryPath;

	private JPanel pnlParameters;
	private JLabel lblPartition;
	private JComboBox<String> cmbPartitionPercentage;
	private JLabel lblCrossValidation;
	private JComboBox<String> cmbCrossValidation;

	private JPanel pnlRunTime;
	private JButton btnPreprocess;
	private JButton btnTrain;
	private JButton btnTest;
	private JButton btnClear;
	private JTextArea txtAreaConsole;

	//Controllers
	private static MainController masterCommandCenter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Initialize all GUI components
					MainScreen frame = MainScreen.initInstance();
					frame.setVisible(true);

					//Initialize main controller
					masterCommandCenter = MainController.initInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * UI Logic
	 */
	public static MainScreen initInstance(){
		if(instance == null){
			instance = new MainScreen();
		}
		
		return instance;
	}
	
	private MainScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 370);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(450, 370));
		setContentPane(contentPane);

		pack();//So that we can get contentPane size
		addComponents();
	}

	private void addComponents(){
		addTitleLable();
		addBrowseButton();
		addDirectoryPath();
		addParamenterGroupBox();
		addRunTimeGroupBox();
	}

	private void addTitleLable(){
		//Title label
		lblTitle = new JLabel("Music Genre Classfication Tool");
		lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblTitle.setLocation(0, 5);
		lblTitle.setSize(contentPane.getSize().width, 40);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitle);
	}

	private void addBrowseButton(){
		btnBrowse = new JButton("Browse");
		btnBrowse.setLocation(padding, lblTitle.getLocation().y + lblTitle.getSize().height + 10);
		btnBrowse.setSize(80, 30);
		btnBrowse.setToolTipText("Click to choose source file");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Click to browse the file system
				JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Choose source file");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(true);

	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	               txtDirectoryPath.setText(chooser.getSelectedFile().toString());
	               enableParameters(true);
	               enableRunTime(true);
	            }
			}
		});
		contentPane.add(btnBrowse);
	}

	private void addDirectoryPath(){
		int x = btnBrowse.getLocation().x + btnBrowse.getSize().width + padding;

		txtDirectoryPath = new JTextField();
		txtDirectoryPath.setEditable(false);
		txtDirectoryPath.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtDirectoryPath.setForeground(Color.lightGray);
		txtDirectoryPath.setText("Source file directory...");
		txtDirectoryPath.setToolTipText("Source file directory...");
		txtDirectoryPath.setLocation(x, btnBrowse.getLocation().y);
		txtDirectoryPath.setSize(contentPane.getSize().width - x - padding, 30);
		txtDirectoryPath.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		txtDirectoryPath.setToolTipText("Source File Directory");
		contentPane.add(txtDirectoryPath);
	}

	private void addParamenterGroupBox(){
		//Containing panel
		pnlParameters = new JPanel();
		pnlParameters.setLayout(null);
		pnlParameters.setLocation(padding, btnBrowse.getLocation().y + btnBrowse.getSize().height + 10);
		pnlParameters.setSize(contentPane.getSize().width - 2 * padding, 50);
		pnlParameters.setBorder(BorderFactory.createTitledBorder("Parameters"));

		//Add parameter controls
		addPartitionLable();
		addPercentageDropDownList();
		addCrossValidationLabel();
		addCrossValidationFoldDropDownList();

		contentPane.add(pnlParameters);
		enableParameters(false);
	}

	private void addPartitionLable(){
		lblPartition = new JLabel("Partition (%):");
		lblPartition.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPartition.setLocation(padding, 5);
		lblPartition.setSize(100, 40);
		lblPartition.setHorizontalAlignment(SwingConstants.LEFT);
		lblPartition.setToolTipText("Used to split source dataset into training set and test set");
		pnlParameters.add(lblPartition);
	}

	private void addPercentageDropDownList(){
		cmbPartitionPercentage = new JComboBox<String>();
		cmbPartitionPercentage.setBounds(lblPartition.getLocation().x + lblPartition.getSize().width, 5, 80, 40);
		cmbPartitionPercentage.setModel(new DefaultComboBoxModel<String>(new String[] {"60%", "65%", "70%", "75%", "80%", "85%", "90%"}));
		cmbPartitionPercentage.setToolTipText("Used to split source dataset into training set and test set");
		pnlParameters.add(cmbPartitionPercentage);
	}

	private void addCrossValidationLabel(){
		lblCrossValidation = new JLabel("Cross Validation Fold:");
		lblCrossValidation.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCrossValidation.setLocation(cmbPartitionPercentage.getLocation().x + cmbPartitionPercentage.getSize().width + 5, 5);
		lblCrossValidation.setSize(160, 40);
		lblCrossValidation.setHorizontalAlignment(SwingConstants.LEFT);
		lblCrossValidation.setToolTipText("Used to find k value of KNN");
		pnlParameters.add(lblCrossValidation);
	}

	private void addCrossValidationFoldDropDownList(){
		cmbCrossValidation = new JComboBox<String>();
		cmbCrossValidation.setBounds(lblCrossValidation.getLocation().x + lblCrossValidation.getSize().width, 5, 60, 40);
		cmbCrossValidation.setModel(new DefaultComboBoxModel<String>(new String[] {"4", "5", "6", "7", "8", "9", "10"}));
		cmbCrossValidation.setToolTipText("Used to find k value of KNN");
		pnlParameters.add(cmbCrossValidation);
	}

	private void addRunTimeGroupBox(){
		pnlRunTime = new JPanel();
		pnlRunTime.setLayout(null);
		pnlRunTime.setLocation(padding, pnlParameters.getLocation().y + pnlParameters.getSize().height + 10);
		pnlRunTime.setSize(contentPane.getSize().width - 2 * padding, 200);
		pnlRunTime.setBorder(BorderFactory.createTitledBorder("Operation"));

		//Add components
		addRunTimeButtons();
		addLogConsole();
		
		contentPane.add(pnlRunTime);
		enableRunTime(false);
	}

	private void addRunTimeButtons(){
		addPreprocessButton();
		addTrainButton();
		addTestButton();
		addClearButton();
	}

	private void addPreprocessButton(){
		btnPreprocess = new JButton("Preprocess");
		btnPreprocess.setLocation(padding, 15);
		btnPreprocess.setSize(100, 30);
		btnPreprocess.setToolTipText("Click to filter irrelevant features (dimensions). This needs to be done ONLY once for every source file!");
		btnPreprocess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					btnTrain.setEnabled(false);
					btnTest.setEnabled(false);
					
					//Get percentage
					String splitPercentage = cmbPartitionPercentage.getSelectedItem().toString();
					String ratioString = splitPercentage.substring(0, 1);
					int ratio = Integer.parseInt(ratioString);
					masterCommandCenter.filterFeatures(10 - ratio);
				}
				catch(Exception ex){
					writeToConsole(ex.getMessage());
				}
				finally{

				}
			}
		});
		pnlRunTime.add(btnPreprocess);
	}

	private void addTrainButton(){
		//Train button
		btnTrain = new JButton("Train");
		btnTrain.setLocation(btnPreprocess.getLocation().x + btnPreprocess.getSize().width + 5, 15);
		btnTrain.setSize(80, 30);
		btnTrain.setToolTipText("Click to partition and train the LAMP model");
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					btnPreprocess.setEnabled(false);
					btnTest.setEnabled(false);
					masterCommandCenter.TrainDataSet();
				}
				catch(Exception ex){
					writeToConsole(ex.getMessage()); 
				}
				finally{

				}
			}
		});
		pnlRunTime.add(btnTrain);
	}

	private void addTestButton(){
		//Test button
		btnTest = new JButton("Test");
		btnTest.setLocation(btnTrain.getLocation().x + btnTrain.getSize().width + 5, 15);
		btnTest.setSize(80, 30);
		btnTest.setToolTipText("Click to predict the test dataset on-the-flight");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					btnPreprocess.setEnabled(false);
					btnTrain.setEnabled(false);
					masterCommandCenter.TestDataSet();
				}
				catch(Exception ex){
					writeToConsole(ex.getMessage());
				}
				finally{

				}
			}
		});
		pnlRunTime.add(btnTest);
	}

	private void addClearButton(){
		//Clear button
		btnClear = new JButton("Clear");
		btnClear.setLocation(btnTest.getLocation().x + btnTest.getSize().width + 5, 15);
		btnClear.setSize(80, 30);
		btnClear.setToolTipText("Clear the console");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearConsole();
			}
		});
		pnlRunTime.add(btnClear);
	}

	private void addLogConsole(){
		txtAreaConsole = new JTextArea();
		txtAreaConsole.setEditable(false);
		txtAreaConsole.setLocation(padding, btnTrain.getLocation().y + btnTrain.getSize().height + 10);
		txtAreaConsole.setSize(pnlRunTime.getSize().width - 2 * padding, 130);
		txtAreaConsole.setToolTipText("Application Log");
		txtAreaConsole.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		pnlRunTime.add(txtAreaConsole);
	}

	/**
	 * Common sharing functions
	 */
	public void writeToConsole(String data){
		txtAreaConsole.append(data + "\n");
//		DefaultCaret caret = (DefaultCaret) txtAreaConsole.getCaret();
//		caret.setUpdatePolicy(UPDATE);
	}

	private void clearConsole(){
		txtAreaConsole.setText("");
	}
	
	private void enableParameters(Boolean flag){
		pnlParameters.setEnabled(flag);
		lblPartition.setEnabled(flag);
		cmbPartitionPercentage.setEnabled(flag);
		lblCrossValidation.setEnabled(flag);
		cmbCrossValidation.setEnabled(flag);
	}
	
	private void enableRunTime(Boolean flag){
		pnlRunTime.setEnabled(flag);
		btnPreprocess.setEnabled(flag);
		btnTrain.setEnabled(flag);
		btnTest.setEnabled(flag);
		btnClear.setEnabled(flag);
		txtAreaConsole.setEnabled(flag);
	}
}
