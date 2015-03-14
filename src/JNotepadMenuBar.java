import java.awt.Event;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;

public class JNotepadMenuBar extends JMenuBar{
	//To satisfy the warning
	private static final long serialVersionUID = 1L;
	private JNotepad parent;
	private JNotepadFindDialog findDialog;
	private JTextArea textArea;
	
	private JFileChooser fileChooser;
	private File workingFile;
	
	public JNotepadMenuBar(JTextArea txtArea, JNotepad par)	{
		super();
		
		parent = par;
		textArea = txtArea;
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text(.txt) & JAVA(.java) Files", "txt", "java"));
		
		/*
		 * File Menu Init
		 */
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		JMenuItem fileMenuItem = new JMenuItem("New");
		fileMenuItem.setMnemonic('N');
		fileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		fileMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				int selection;
				if(workingFile != null)	{
					selection = promptSave();
					if(selection == JOptionPane.YES_OPTION)	{
						textArea.setText("");
						workingFile = null;
						parent.setTitle("Untitled" + parent.TITLE_SUFFIX);
						return;
					}
					else if(selection == JOptionPane.NO_OPTION)	{
						textArea.setText("");
						workingFile = null;
						parent.setTitle("Untitled" + parent.TITLE_SUFFIX);
						return;
					}
					else
						return;
				}
				else if(!textArea.getText().equals(""))	{
					selection = promptSave();
					if(selection == JOptionPane.YES_OPTION)	{
						textArea.setText("");
						workingFile = null;
						parent.setTitle("Untitled" + parent.TITLE_SUFFIX);
						return;
					}
					else if(selection == JOptionPane.NO_OPTION)	{
						textArea.setText("");
						workingFile = null;
						parent.setTitle("Untitled" + parent.TITLE_SUFFIX);
						return;
					}
					else
						return;
				}
				else
					textArea.setText("");
			}
		});
		fileMenu.add(fileMenuItem);
		
		fileMenuItem = new JMenuItem("Open...");
		fileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		fileMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				if(!textArea.getText().equals("")){
					int selection = promptSave();
					if(selection == JOptionPane.YES_OPTION)
						openFile();
					else if(selection == JOptionPane.NO_OPTION)
						openFile();
					else
						return;
				}
				else	{
					openFile();
				}
			}
		});
		fileMenu.add(fileMenuItem);
		
		fileMenuItem = new JMenuItem("Save");
		fileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		fileMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				//Alter if file already exists;
				if(workingFile != null)	{
					try
					{
						PrintWriter fileWriter = new PrintWriter(workingFile);
						fileWriter.write(textArea.getText());
						fileWriter.close();
						return;
					}
					catch(FileNotFoundException e)	{
						JOptionPane.showMessageDialog(textArea, ("The file " + workingFile.getName() + " could not be found. Save canceled."));
						return;
					}
				}
				else	{
					saveFile();
					return;
				}
			}
		});
		fileMenu.add(fileMenuItem);
		
		fileMenuItem = new JMenuItem("Save As...");
		fileMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				saveFile();
			}
		});
		fileMenu.add(fileMenuItem);
		
		fileMenu.addSeparator();
		
		fileMenuItem = new JMenuItem("Page Setup...");
		fileMenuItem.setMnemonic('u');
		fileMenuItem.setEnabled(false);
		fileMenu.add(fileMenuItem);
		
		fileMenuItem = new JMenuItem("Print");
		fileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		fileMenuItem.setEnabled(false);
		fileMenu.add(fileMenuItem);
		
		fileMenu.addSeparator();
		
		fileMenuItem = new JMenuItem("Exit");
		fileMenuItem.setMnemonic('x');
		fileMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				if(!textArea.getText().equals("")){
					promptSave();
					workingFile = null;
					System.exit(0);
				}
				else
					workingFile = null;
					System.exit(0);
			}
		});
		fileMenu.add(fileMenuItem);
		
		
		
		add(fileMenu);
		
		/*
		 * Edit Menu Init
		 */
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		
		JMenuItem editMenuItem = new JMenuItem("Undo");
		editMenuItem.setEnabled(false);
		editMenu.add(editMenuItem);
		
		editMenu.addSeparator();
		
		editMenuItem = new JMenuItem("Cut");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.cut();
			}
		});
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Copy");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.copy();
			}
		});
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Paste");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.paste();
			}
		});
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Delete");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				//Delete function
			}
		});
		editMenu.add(editMenuItem);
		
		editMenu.addSeparator();
		
		editMenuItem = new JMenuItem("Find...");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
		findDialog = new JNotepadFindDialog(parent, "Find", textArea);
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				findDialog.setVisible(true);
			}
		});
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Find Next");
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				findDialog.findText();
			}
		});
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Replace...");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		editMenuItem.setEnabled(false);
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Go To...");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Event.CTRL_MASK));
		editMenuItem.setEnabled(false);
		editMenu.add(editMenuItem);
		
		editMenu.addSeparator();
		
		editMenuItem = new JMenuItem("Select All");	//I May implement this
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		editMenuItem.setEnabled(false);
		editMenu.add(editMenuItem);
		
		editMenuItem = new JMenuItem("Time/Date");
		editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		editMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				SimpleDateFormat sdf = new SimpleDateFormat("h:mma MM/dd/yyyy");
				textArea.append(sdf.format(new Date()));
			}
		});
		editMenu.add(editMenuItem);
		
		add(editMenu);
		
		
		/*
		 * Format Menu Init
		 */
		
		JMenu formatMenu = new JMenu("Format");
		formatMenu.setMnemonic('o');
		
		//Final so ActionListener can access
		final JCheckBoxMenuItem wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap", textArea.getLineWrap());
		wordWrapMenuItem.setMnemonic('W');
		wordWrapMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				textArea.setLineWrap(!textArea.getLineWrap());
				
			}
		});
		formatMenu.add(wordWrapMenuItem);
		
		JMenuItem formatMenuItem = new JMenuItem("Font...");
		formatMenuItem.setMnemonic('F');
		formatMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				new JNotepadFontDialog(parent, "Font", textArea);
			}
		});
		formatMenu.add(formatMenuItem);
		
		add(formatMenu);
		
		
		/*
		 * View Menu init
		 */
		
		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic('V');
		
		JCheckBoxMenuItem viewMenuItem = new JCheckBoxMenuItem("Status Bar");	//May implement
		viewMenuItem.setMnemonic('S');
		viewMenuItem.setEnabled(false);
		viewMenu.add(viewMenuItem);
		
		add(viewMenu);
		
		/*
		 * Help Menu init
		 */
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		
		JMenuItem helpMenuItem = new JMenuItem("View Help");
		helpMenuItem.setMnemonic('H');
		helpMenuItem.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent ae)	{
				JOptionPane.showMessageDialog(textArea, "(c) Anthony Haddox 2014", "About JNotepad", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(helpMenuItem);
		
		add(helpMenu);
	}
	
	public void openFile()	{
		int selectionValue = fileChooser.showOpenDialog(textArea);
		switch(selectionValue)
		{
		case JFileChooser.APPROVE_OPTION:
			workingFile = fileChooser.getSelectedFile();
			Scanner fileScanner;
			try {
				StringBuilder fileContents = new StringBuilder();
				fileScanner = new Scanner(workingFile);
				while(fileScanner.hasNext())	{
					fileContents.append(fileScanner.nextLine());
				}
				textArea.setText(fileContents.toString());
				parent.setTitle(workingFile.getName() + parent.TITLE_SUFFIX);
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(textArea, ("The file" + workingFile.getName() + " could not be found."));
			}
			
			break;
		case JFileChooser.CANCEL_OPTION:
			break;
		}
	}
	
	public void saveFile()	{
		int selectionValue = fileChooser.showSaveDialog(textArea);
		switch(selectionValue)
		{
		case JFileChooser.APPROVE_OPTION:
			try
			{
			workingFile = fileChooser.getSelectedFile();
			PrintWriter fileWriter = new PrintWriter(workingFile);
			fileWriter.write(textArea.getText());
			fileWriter.close();
			parent.setTitle(workingFile.getName() + parent.TITLE_SUFFIX);
			}
			catch(FileNotFoundException e)	{
				JOptionPane.showMessageDialog(textArea, ("The file " + workingFile.getName() + " could not be found. Save canceled."));
			}
			break;
		case JFileChooser.CANCEL_OPTION:
			break;
		}
	}
	
	public int promptSave()	{
		int choice = JOptionPane.showConfirmDialog(textArea, "Do you want to save?");
		switch(choice)
		{
		case JOptionPane.YES_OPTION:
			saveFile();
			return JOptionPane.YES_OPTION;
		case JOptionPane.NO_OPTION:
			return JOptionPane.NO_OPTION;
		case JOptionPane.CANCEL_OPTION:
			return JOptionPane.CANCEL_OPTION;
		default:
			return JOptionPane.CANCEL_OPTION;
		}
	}
	
	public File getWorkingFile()	{	return workingFile;	}
}
