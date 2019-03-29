import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane.*;

import javax.swing.*;

/**
 * Class StudentGUI provides a User Interface for a simple demonstration of maintaining student records
 * @author Alex Gorkoff, Matteo Messana, Usman Farooq
 */

public class StudentGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private BinSearchTree BST;
	
	private JFrame mainFrame;
	private JPanel contentPanel;
	
	private Button findButton;
	private Button insertButton;
	private Button browseButton;
	private Button createButton;
	
	private DefaultListModel<String> listModel;
	private JList<String> listArea;

	private JScrollPane scrollPanel;
	
	/**
	 * Constructor of class StudentGUI
	 * @param heightPixels : defines the default height of the main frame in pixels
	 * @param widthPixels : defines the default width of the main frame in pixels
	 * @param title : defines a title for the main frame
	 */
	public StudentGUI(int heightPixels, int widthPixels, String title) {
		
		mainFrame = new JFrame(title);
		mainFrame.setSize(heightPixels, widthPixels);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		contentPanel.add(createTitlePanel());
		contentPanel.add(createDisplayPanel());
		contentPanel.add(createButtonPanel());
		
		mainFrame.setContentPane(contentPanel);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Defines components and parameters for the top panel in the main frame
	 * @return titlePanel : the initialized title panel of the main frame
	 */
	public JPanel createTitlePanel() {
		
		JPanel titlePanel = new JPanel(new FlowLayout());
		JLabel title = new JLabel("Student Record System");
		title.setForeground(Color.BLUE);
		
		titlePanel.setMaximumSize(new Dimension(450, 25));
		titlePanel.add(title);
		
		return titlePanel;
	}
	
	/**
	 * Defines components and parameters of the center panel in the main frame
	 * @return : displayPanel : the initialized middle component of the main frame
	 */
	public JPanel createDisplayPanel() {
		
		JPanel displayPanel = new JPanel();
		
		displayPanel.setPreferredSize(new Dimension(450, 210));
		
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		
		String width = "1234567890123456789012345678901234567890";
		
		listArea.setPrototypeCellValue(width);
		listArea.setFont(new Font("Courier New", Font.BOLD, 12));
		listArea.setVisibleRowCount(15);
		
		scrollPanel = new JScrollPane(listArea);
		
		scrollPanel.setPreferredSize(new Dimension(450, 210));
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		displayPanel.add(scrollPanel);
		
		return displayPanel;
	}
	
	/**
	 * Initializes the bottom panel of the main frame 
	 * Defines action listeners for all buttons in the button panel
	 * @return buttonPanel: the initialized bottom component of the main frame.
	 */
	public JPanel createButtonPanel() {
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setMaximumSize(new Dimension(450, 25));
		
		findButton = new Button("Find");
		findButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBSTExists()) {
					try {
						printFoundMessage();
					} catch(Exception er) {
						System.err.println("Invalid number format.");
					}
				}
			}
			
		});
		
		insertButton = new Button("Insert");
		insertButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBSTExists()) {
					insertInformation();
					listModel.clear();
					printBSTToGUI();
				}
			}
			
		});
		
		browseButton = new Button("Browse");
		browseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(checkBSTExists()) {
					printBSTToGUI();
				}
			}
			
		});
		
		createButton = new Button("Create Tree from File");
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog("Please enter the name of the file: ");
				constructBST(fileName);

			}
			
		});
		
		buttonPanel.add(findButton);
		buttonPanel.add(insertButton);
		buttonPanel.add(browseButton);
		buttonPanel.add(createButton);
		
		return buttonPanel;
	}
	
	/**
	 * Verifies whether the BST has been initialized or not
	 * @return true (if BST exists) or false (if BST has not yet been created from importing text file)
	 */
	public boolean checkBSTExists() {
		
		if(BST == null) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Please load information from a file first, then try again.");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Creates a BST from the text file of student records
	 * @param fileName : the name of the file to be imported
	 */
	public void constructBST(String fileName) {
		
		File file = new File(fileName);
		String information = null;
		
		ArrayList<String> nodeData = new ArrayList<String>();
		BST = new BinSearchTree();
		
		try {
			
			Scanner inputFile = new Scanner(file);
			
			while(inputFile.hasNextLine()){
				
				information = inputFile.nextLine();
				
				for(String s: information.split("\\s+")) {
					if(s.contentEquals(""))
						continue;
					nodeData.add(s);
				}
				
				BST.insert(nodeData.get(0), nodeData.get(1), nodeData.get(2), nodeData.get(3));
				nodeData.clear();
			}
			
		} catch (FileNotFoundException e) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "File " + fileName + " could not be located.");
		}
	}
	
	/**
	 * Outputs the contents BST to the GUI center panel
	 */
	public void printBSTToGUI() {
		
		StringWriter buffer = new StringWriter();
		PrintWriter output = new PrintWriter(buffer);
		
		try {
			BST.print_tree(BST.root, output);
		} catch (IOException e1) {
			System.err.println("Failed to print BST.");
		}
		
		for(String s: buffer.toString().split("\n")) {
			listModel.addElement(s);
		}
		
	}
	
	/**
	 * Creates a user dialog to indicate the information of a student (if found) or to indicate that a record does not exist
	 */
	public void printFoundMessage() {
		
		String idString = JOptionPane.showInputDialog("Enter the ID of the student you'd like to find: ");
		Node foundNode = BST.find(BST.root, idString);
		
		JFrame frame = new JFrame();
		if(foundNode == null)
			JOptionPane.showMessageDialog(frame, "Sorry, the student could not be located.");
		else
			JOptionPane.showMessageDialog(frame, "Here's the student! \n" + foundNode);
	}
	
	/**
	 * Creates a new student record from user input and inserts it into the BST
	 */
	public void insertInformation() {
		
		JTextField studentID = new JTextField();
		JTextField faculty = new JTextField();
		JTextField major = new JTextField();
		JTextField year = new JTextField();
		
		Object [] fields = {
				"Enter Student ID: ", studentID, 
				"Enter Faculty: ", faculty,
				"Enter Student's Major: ", major,
				"Enter Year: ", year
		};
		
		JFrame frame = new JFrame();
		JOptionPane.showConfirmDialog(frame, fields);
		
		BST.insert(studentID.getText(), faculty.getText(), major.getText(), year.getText());
		
	}
	
	public static void main(String [] args) {
		StudentGUI theStudentGUI = new StudentGUI(450, 300, "Student Record Program");
	}
	
}
