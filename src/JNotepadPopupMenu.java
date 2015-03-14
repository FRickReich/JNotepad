import java.awt.event.*;

import javax.swing.*;

public class JNotepadPopupMenu extends JPopupMenu{
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	public JNotepadPopupMenu(JTextArea txtArea)	{
		textArea = txtArea;
		JMenuItem menuItem = new JMenuItem("Cut");
		menuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.cut();
			}
		});
		add(menuItem);
		
		menuItem = new JMenuItem("Copy");
		menuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.copy();
			}
		});
		add(menuItem);
		
		menuItem = new JMenuItem("Paste");
		menuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.paste();
			}
		});
		add(menuItem);
	}
}
