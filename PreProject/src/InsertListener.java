import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class InsertListener implements ActionListener {

	private Frame myFrame;
	private BinSearchTree BST;
	private DefaultListModel<String> listModelInsert;

	public InsertListener(Frame theFrame, BinSearchTree theTree, DefaultListModel<String> theList) {
		myFrame = theFrame;
		BST = theTree;
		listModelInsert = theList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Frame popup = new Frame();
		Font bodyFont = new Font("Serif", Font.PLAIN, 30);
		JPanel panelSouth = new JPanel();
		JPanel panelNorth = new JPanel();
		JPanel panelCentre = new JPanel();

		JTextField id = new JTextField(15);
		JTextField faculty = new JTextField(15);
		JTextField major = new JTextField(15);
		JTextField year = new JTextField(15);

		JLabel studentLab = new JLabel("Enter the Student ID");
		JLabel facultyLab = new JLabel("Enter Faculty");
		JLabel majorLab = new JLabel("Enter Student's Major");
		JLabel yearLab = new JLabel("Enter Year");

		studentLab.setFont(bodyFont);
		facultyLab.setFont(bodyFont);
		majorLab.setFont(bodyFont);
		yearLab.setFont(bodyFont);

		JLabel title = new JLabel("Insert a New Node");
		title.setFont(new Font("Serif", Font.PLAIN, 35));

		JButton insert = new JButton("Insert");
		JButton main = new JButton("Return to Main Window");

		insert.setFont(bodyFont);
		main.setFont(bodyFont);

		insert.setPreferredSize(new Dimension(200, 100));
		main.setPreferredSize(new Dimension(500, 100));

		panelNorth.add(title);

		panelCentre.add(studentLab);
		panelCentre.add(id);

		panelCentre.add(facultyLab);
		panelCentre.add(faculty);

		panelCentre.add(majorLab);
		panelCentre.add(major);

		panelCentre.add(yearLab);
		panelCentre.add(year);

		panelSouth.add(insert);// add button to panel
		panelSouth.add(main);// add button to panel

		popup.add(panelCentre, BorderLayout.CENTER);
		popup.add(panelSouth, BorderLayout.SOUTH);
		popup.add(panelNorth, BorderLayout.NORTH);
		popup.pack();
		popup.setSize(1000, 600);
		popup.setVisible(true);

		main.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				popup.setVisible(false); // you can't see me!
				popup.dispose(); // Destroy the JFrame object
			} // end of actionPerformed
		} // end of anonymous class and instantiation of an ActionListener object
		);

		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				listModelInsert.removeAllElements();
				BST.insert(id.getText(), faculty.getText(), major.getText(), year.getText());
				StringWriter buffer = new StringWriter();
				PrintWriter writer = new PrintWriter(buffer);
				try {
					BST.print_tree(BST.root, writer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String contents = buffer.toString();
				ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(contents.split("\\s")));
				ArrayList<String> rem = new ArrayList<>();
				rem.add(" ");
				rem.add("\n");
				rem.add("");
				tokens.removeAll(rem);
				for (int i = 0; i < tokens.size() - 3; i += 4) {
					String s = tokens.get(i) + " " + tokens.get(i + 1) + " " + tokens.get(i + 2) + " "
							+ tokens.get(i + 3);
					listModelInsert.addElement(s);
				}
			} // end of actionPerformed
		}
		// end of anonymous class and instantiation of an ActionListener object
		);
	}
}
