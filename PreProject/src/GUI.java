import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame {
	private BinSearchTree myTree;
	private DefaultListModel<String> listModel;
	private JList<String> listArea;
	private JTextField selectedTextField;

	GUI() {
		myTree = new BinSearchTree();
		JPanel panelSouth = new JPanel();
		JPanel panelNorth = new JPanel();
		JPanel panelCentre = new JPanel();
		selectedTextField = new JTextField(40);
		listModel = new DefaultListModel<String>();
		listArea = new JList<String>(listModel);
		String width = "1234567890123456789012345678901234567890";
		listArea.setPrototypeCellValue(width);
		listArea.setFont(new Font("Courier New", Font.BOLD, 20));
		listArea.setVisibleRowCount(35);
		listArea.addListSelectionListener(new ListListener());
		JScrollPane listScrollPane = new JScrollPane(listArea);

		panelCentre.add(listScrollPane);

		InsertListener insertListen = new InsertListener(this, myTree,listModel);
		CreateListener createListen = new CreateListener(this, myTree);
		BrowseListener browseListen = new BrowseListener(this, myTree);
		FindListener findListen = new FindListener(this, myTree);

		Font bodyFont = new Font("Serif", Font.PLAIN, 30);

		JLabel title = new JLabel("An Application to Maintain Student Records");
		title.setFont(new Font("Serif", Font.PLAIN, 35));

		JButton insert = new JButton("Insert");
		JButton find = new JButton("Find");
		JButton browse = new JButton("Browse");
		JButton create = new JButton("Create Tree From File");

		insert.setFont(bodyFont);
		find.setFont(bodyFont);
		browse.setFont(bodyFont);
		create.setFont(bodyFont);

		insert.setPreferredSize(new Dimension(200, 100));
		find.setPreferredSize(new Dimension(200, 100));
		browse.setPreferredSize(new Dimension(200, 100));
		create.setPreferredSize(new Dimension(300, 100));

		insert.addActionListener(insertListen);
		create.addActionListener(createListen);
		browse.addActionListener(browseListen);
		find.addActionListener(findListen);

		panelNorth.add(title);

		panelSouth.add(insert);// add button to panel
		panelSouth.add(find);// add button to panel
		panelSouth.add(browse);// add button to panel
		panelSouth.add(create);// add button to panel

		this.add(panelSouth, BorderLayout.SOUTH);
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCentre, BorderLayout.CENTER);
		this.pack();
		setSize(1000, 1000);
		setVisible(true);
	}

	public class BrowseListener implements ActionListener {
		private Frame myFrame;
		private BinSearchTree BST;

		public BrowseListener(Frame theFrame, BinSearchTree theTree) {
			myFrame = theFrame;
			BST = theTree;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			listModel.removeAllElements();
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
			for(int i = 0; i < tokens.size()-3; i+=4) {
				String s = tokens.get(i)+" "+tokens.get(i+1)+" "+tokens.get(i+2)+" "+tokens.get(i+3);
				listModel.addElement(s);
			}
		}
	}

	public class ListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			int index = listArea.getSelectedIndex();
			if (index >= 0) {
				String line = (String) listModel.get(index);
				selectedTextField.setText(line);
			}
		}
	}
}
