import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
public class JNotepadFontDialog extends JDialog{
	//To satisfy the warning
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private String allFonts[];
	private String fontSize[] = { "8", "9", "10", "12",
								  "14", "16", "18", "20", "22",
								  "24", "26", "28", "30", "32",
								  "34", "36", "38", "40", "42",
								  "56", "72" };
	private String fontStyle[] = { "Normal", "Bold", "Italic" };
	private JList<String> fontList;
	private JList<String> fontSizeList;
	private JList<String> fontStyleList;
	private JLabel previewLabel;
	
	public JNotepadFontDialog(JFrame parent, String title, JTextArea txtA)	{
		super(parent, title, false);

		textArea = txtA;
		
		previewLabel = new JLabel("The quick brown fox jumps over the lazy dog. 0123456789");
		previewLabel.setFont(textArea.getFont());
		allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontList = new JList<String>(allFonts);
		
		fontList.addListSelectionListener(new ListSelectionListener()	{
			public void valueChanged(ListSelectionEvent le)	{
				int ndx = fontList.getSelectedIndex();
				if(ndx != 1)
					previewLabel.setFont(new Font(allFonts[ndx], previewLabel.getFont().getStyle(), previewLabel.getFont().getSize()));
			}
		});
		
		fontSizeList = new JList<String>(fontSize);
		fontSizeList.addListSelectionListener(new ListSelectionListener()	{
			public void valueChanged(ListSelectionEvent le)	{
				int ndx = fontSizeList.getSelectedIndex();
				if(ndx < 0)
					System.out.println(ndx);
				if(ndx != 1)
					previewLabel.setFont(new Font(previewLabel.getFont().getFontName(), previewLabel.getFont().getStyle(), Integer.parseInt(fontSize[ndx])));
			}
		});
		
		fontStyleList = new JList<String>(fontStyle);
		fontStyleList.addListSelectionListener(new ListSelectionListener()	{
			public void valueChanged(ListSelectionEvent le)	{
				int ndx = fontStyleList.getSelectedIndex();
				System.out.println(ndx);
				switch(ndx)
				{
				case 0:
					previewLabel.setFont(new Font(previewLabel.getFont().getFontName(), Font.PLAIN, previewLabel.getFont().getSize()));
					break;
				case 1:
					previewLabel.setFont(new Font(previewLabel.getFont().getFontName(), Font.BOLD, previewLabel.getFont().getSize()));
					break;
				case 2:
					previewLabel.setFont(new Font(previewLabel.getFont().getFontName(), Font.ITALIC, previewLabel.getFont().getSize()));
					break;
				}
			}
		});
		setSize(450, 550);
		setLayout(new GridLayout(3, 0, 2, 2));
		
		
		/*
		 * Selections Panel Init
		 */
		JPanel selectionsPanel = new JPanel(new GridLayout(0, 3, 2, 2));
		JScrollPane tempPane = new JScrollPane(fontList);
		tempPane.setBorder(new TitledBorder("Font Name:"));
		selectionsPanel.add(tempPane);
		tempPane = new JScrollPane(fontSizeList);
		tempPane.setBorder(new TitledBorder("Font Size:"));
		selectionsPanel.add(tempPane);
		tempPane = new JScrollPane(fontStyleList);
		tempPane.setBorder(new TitledBorder("Font Style:"));
		selectionsPanel.add(tempPane);
		
		add(selectionsPanel);
		
		JPanel previewPanel = new JPanel(new BorderLayout());
		previewPanel.add(previewLabel, BorderLayout.CENTER);
		add(previewPanel);
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.setFont(previewLabel.getFont());
				dispose();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				dispose();
			}
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		add(buttonPanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
}
