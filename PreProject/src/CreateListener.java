import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class CreateListener implements ActionListener {
	private Frame myFrame;
	private BinSearchTree BST;

	public CreateListener(Frame theFrame, BinSearchTree theTree) {
		myFrame = theFrame;
		BST = theTree;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String filename = JOptionPane.showInputDialog("Enter the file name:");
		Scanner input = new Scanner(System.in);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(line.split("\\s")));
				ArrayList<String> rem = new ArrayList<>();
				rem.add(" ");
				rem.add("\n");
				rem.add("");
				tokens.removeAll(rem);
				BST.insert(tokens.get(0),tokens.get(1),tokens.get(2),tokens.get(3));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
