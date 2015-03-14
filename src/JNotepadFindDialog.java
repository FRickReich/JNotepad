import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;

public class JNotepadFindDialog extends JDialog{
	static final long serialVersionUID = 1L;
	private JButton findButton;
	private JTextArea textArea;
	private JTextField input;
	public JNotepadFindDialog(JFrame parent, String title, JTextArea txtA)	{
		super(parent, title, false);
		
		textArea = txtA;
		
		setSize(350, 125);
		setResizable(false);
		setLayout(new BorderLayout());
		
		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.add(new JLabel("Find what: "));
		input = new JTextField(15);
		input.getDocument().addDocumentListener(new DocumentListener()	{
			public void changedUpdate(DocumentEvent d)	{
				fieldChanged();
			}
			public void removeUpdate(DocumentEvent d)	{
				fieldChanged();
			}
			public void insertUpdate(DocumentEvent d)	{
				fieldChanged();
			}
		});
		
		inputPanel.add(input);
		add(inputPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		findButton = new JButton("Find Next");
		findButton.setEnabled(false);
		findButton.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				findText();
			}
		});
		buttonPanel.add(findButton, BorderLayout.NORTH);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				setVisible(false);
			}
			
		});
		buttonPanel.add(cancelButton, BorderLayout.SOUTH);
		
		add(buttonPanel, BorderLayout.EAST);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(false);
	}
	
	public void findText()	{
		if(input.getText().equals(""))	{
			setVisible(true);
		}
		if(textArea.getText().toLowerCase().indexOf(input.getText().toLowerCase(), textArea.getCaretPosition()) == -1)	{
			JOptionPane.showMessageDialog(textArea, ("No instances of \"" + input.getText() + "\" were found"));
			return;
		}
		int selectionStart = textArea.getText().toLowerCase().indexOf(input.getText(), textArea.getCaretPosition());
		textArea.requestFocusInWindow();
		textArea.select(selectionStart, selectionStart + input.getText().length());
	}
	
	public void fieldChanged()	{
		if(input.getText().equals(""))
			findButton.setEnabled(false);
		else
			findButton.setEnabled(true);
	}
	
	public JTextField getInput()	{	return input;	}
}
