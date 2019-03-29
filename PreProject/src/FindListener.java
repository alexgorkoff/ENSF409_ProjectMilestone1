import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class FindListener implements ActionListener{
	private Frame myFrame;
	private BinSearchTree BST;

	public FindListener(Frame theFrame, BinSearchTree theTree) {
		myFrame = theFrame;
		BST = theTree;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String studentID = JOptionPane.showInputDialog("Enter the file name:");
		Node found = BST.find(BST.root, studentID);
		if(found!=null) {
			JOptionPane.showMessageDialog(null,found);
		}
	}
}
