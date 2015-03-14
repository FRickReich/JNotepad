//
//	Name:		Haddox, Anthony
//	Project:	4
//	Due:		
//	Course:		cs-245-01-f14
//	
//	Description: Java implementation of Microsoft's Notepad application
//

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class JNotepad extends JFrame{
	public final String TITLE_SUFFIX = " - JNotepad";
	//To Satisfy the warning
	private static final long serialVersionUID = 1L;
	JTextArea editorArea;
	JNotepadMenuBar mb;
	public JNotepad()	{
		super();
		
		setTitle("Untitled" + TITLE_SUFFIX);
		
		setSize(750, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		editorArea = new JTextArea();
		editorArea.setWrapStyleWord(true);
		editorArea.setLineWrap(true);
		JScrollPane editorScrollPane = new JScrollPane(editorArea);
		add(editorScrollPane);
		
		mb = new JNotepadMenuBar(editorArea, this);
		setJMenuBar(mb);
		
		addWindowListener(new WindowAdapter()	{
			public void windowClosing(WindowEvent we)	{
				mb.promptSave();
			}
		});
		
		editorArea.addMouseListener(new MouseListener()	{
			public void mousePressed(MouseEvent m)	{
				if(m.getButton() == MouseEvent.BUTTON3)	{
					JNotepadPopupMenu menu = new JNotepadPopupMenu(editorArea);
					menu.show(editorArea, m.getX(), m.getY());
				}
			}
			public void mouseReleased(MouseEvent m)	{	}
			public void mouseEntered(MouseEvent m)	{	}
			public void mouseExited(MouseEvent m)	{	}
			public void mouseClicked(MouseEvent m)	{	}
		});
		setVisible(true);
	}
	public static void main(String[] args)	{
		SwingUtilities.invokeLater(new Runnable()	{
			public void run()	{
				new JNotepad();
			}
		});
	}
}
