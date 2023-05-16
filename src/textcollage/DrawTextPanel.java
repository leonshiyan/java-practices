package textcollage;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * A panel that contains a large drawing area where strings
 * can be drawn.  The strings are represented by objects of
 * type DrawTextItem.  An input box under the panel allows
 * the user to specify what string will be drawn when the
 * user clicks on the drawing area.
 */
public class DrawTextPanel extends JPanel  {
	
	// As it now stands, this class can only show one string at at
	// a time!  The data for that string is in the DrawTextItem object
	// named theString.  (If it's null, nothing is shown.  This
	// variable should be replaced by a variable of type
	// ArrayList<DrawStringItem> that can store multiple items.
	
    private ArrayList<DrawTextItem> strings; // ArrayList to hold multiple strings

	
	private Color currentTextColor = Color.BLACK;  // Color applied to new strings.

	private Canvas canvas;  // the drawing area.
	private JTextField input;  // where the user inputs the string that will be added to the canvas
	private SimpleFileChooser fileChooser;  // for letting the user select files
	private JMenuBar menuBar; // a menu bar with command that affect this panel
	private MenuHandler menuHandler; // a listener that responds whenever the user selects a menu command
	private JMenuItem undoMenuItem;  // the "Remove Item" command from the edit menu
	private DrawTextItem selectedString; // Reference to the currently selected string

	
	/**
	 * An object of type Canvas is used for the drawing area.
	 * The canvas simply displays all the DrawTextItems that
	 * are stored in the ArrayList, strings.
	 */
	private class Canvas extends JPanel {
		Canvas() {
			setPreferredSize( new Dimension(800,600) );
			setBackground(Color.LIGHT_GRAY);
			setFont( new Font( "Serif", Font.BOLD, 24 ));
		}
		protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            for (DrawTextItem string : strings) {
                string.draw(g);
            }
        }
	}
	
	/**
	 * An object of type MenuHandler is registered as the ActionListener
	 * for all the commands in the menu bar.  The MenuHandler object
	 * simply calls doMenuCommand() when the user selects a command
	 * from the menu.
	 */
	private class MenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			doMenuCommand( evt.getActionCommand());
		}
	}

	/**
	 * Creates a DrawTextPanel.  The panel has a large drawing area and
	 * a text input box where the user can specify a string.  When the
	 * user clicks the drawing area, the string is added to the drawing
	 * area at the point where the user clicked.
	 */
	public DrawTextPanel() {
		fileChooser = new SimpleFileChooser();
		undoMenuItem = new JMenuItem("Remove Item");
		undoMenuItem.setEnabled(false);
		menuHandler = new MenuHandler();
		setLayout(new BorderLayout(3,3));
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.add(new JLabel("Text to add: "));
		input = new JTextField("Hello World!", 40);
		bottom.add(input);
		add(bottom, BorderLayout.SOUTH);
		canvas.addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				doMousePress( e );
			}
		} );
		
	    strings = new ArrayList<>(); // Initialize the ArrayList to hold strings

	}
		
	/**
	 * This method is called when the user clicks the drawing area.
	 * A new string is added to the drawing area.  The center of
	 * the string is at the point where the user clicked.
	 * @param e the mouse event that was generated when the user clicked
	 */
	public void doMousePress( MouseEvent e ) {
		String text = input.getText().trim();
		if (text.length() == 0) {
			input.setText("Hello World!");
			text = "Hello World!";
		}
		DrawTextItem s = new DrawTextItem(text, e.getX(), e.getY());
	    s.setTextColor(currentTextColor);
	    s.setSize(24); // Default font size
	    s.setRotationAngle(0); // Default rotation angle
	    strings.add(s); // Add the newly created string to the ArrayList
	    selectedString = s; // Select the newly created string
	    undoMenuItem.setEnabled(true);
	    canvas.repaint();
	}
	
	/**
	 * Returns a menu bar containing commands that affect this panel.  The menu
	 * bar is meant to appear in the same window that contains this panel.
	 */
	public JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			
			String commandKey; // for making keyboard accelerators for menu commands
			if (System.getProperty("mrj.version") == null)
				commandKey = "control ";  // command key for non-Mac OS
			else
				commandKey = "meta ";  // command key for Mac OS
			
			JMenu fileMenu = new JMenu("File");
			menuBar.add(fileMenu);
			JMenuItem saveItem = new JMenuItem("Save...");
			saveItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "N"));
			saveItem.addActionListener(menuHandler);
			fileMenu.add(saveItem);
			JMenuItem openItem = new JMenuItem("Open...");
			openItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "O"));
			openItem.addActionListener(menuHandler);
			fileMenu.add(openItem);
			fileMenu.addSeparator();
			JMenuItem saveImageItem = new JMenuItem("Save Image...");
			saveImageItem.addActionListener(menuHandler);
			fileMenu.add(saveImageItem);
			
			JMenu editMenu = new JMenu("Edit");
			menuBar.add(editMenu);
			undoMenuItem.addActionListener(menuHandler); // undoItem was created in the constructor
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Z"));
			editMenu.add(undoMenuItem);
			editMenu.addSeparator();
			JMenuItem clearItem = new JMenuItem("Clear");
			clearItem.addActionListener(menuHandler);
			editMenu.add(clearItem);
			
			JMenu optionsMenu = new JMenu("Options");
			menuBar.add(optionsMenu);
			JMenuItem colorItem = new JMenuItem("Set Text Color...");
			colorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "T"));
			colorItem.addActionListener(menuHandler);
			optionsMenu.add(colorItem);
			JMenuItem bgColorItem = new JMenuItem("Set Background Color...");
			bgColorItem.addActionListener(menuHandler);
			optionsMenu.add(bgColorItem);
			// New menu items for setting size and rotation angle
	        JMenuItem setSizeItem = new JMenuItem("Set Size...");
	        setSizeItem.addActionListener(menuHandler);
	        editMenu.add(setSizeItem);
	        JMenuItem setRotationItem = new JMenuItem("Set Rotation Angle...");
	        setRotationItem.addActionListener(menuHandler);
	        editMenu.add(setRotationItem);
			
		}
		return menuBar;
	}
	
	/**
	 * Carry out one of the commands from the menu bar.
	 * @param command the text of the menu command.
	 */
	private void doMenuCommand(String command) {
		if (command.equals("Save...")) { // save all the string info to a file
			 File outputFile = fileChooser.getOutputFile(this, "Save Collage Data", "collage.txt");
		        if (outputFile != null) {
		            try (PrintWriter writer = new PrintWriter(outputFile)) {
		                // Save background color
		                Color backgroundColor = canvas.getBackground();
		                writer.println(backgroundColor.getRed());
		                writer.println(backgroundColor.getGreen());
		                writer.println(backgroundColor.getBlue());

		                // Save DrawTextItems
		                for (DrawTextItem string : strings) {
		                    writer.println(string.getString());
		                    writer.println(string.getX());
		                    writer.println(string.getY());
		                    writer.println(string.getTextColor().getRed());
		                    writer.println(string.getTextColor().getGreen());
		                    writer.println(string.getTextColor().getBlue());
		                }
		            } catch (Exception e) {
		                JOptionPane.showMessageDialog(this,
		                        "Error saving collage data: " + e.getMessage(),
		                        "Save Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		}
		// Open a file and restore the contents of the drawing area
		else if (command.equals("Open...")) {
		    File file = fileChooser.getInputFile(this, "Open File");
		    if (file != null) {
		        try {
		            Scanner scanner = new Scanner(file);
		            Color backgroundColor = new Color(Integer.parseInt(scanner.nextLine()),
		                    Integer.parseInt(scanner.nextLine()),
		                    Integer.parseInt(scanner.nextLine()));
		            canvas.setBackground(backgroundColor); // Restore background color

		            strings.clear(); // Clear the current strings

		            while (scanner.hasNextLine()) {
		                String text = scanner.nextLine();
		                int posX = Integer.parseInt(scanner.nextLine());
		                int posY = Integer.parseInt(scanner.nextLine());
		                Color textColor = new Color(Integer.parseInt(scanner.nextLine()),
		                        Integer.parseInt(scanner.nextLine()),
		                        Integer.parseInt(scanner.nextLine()));
		                DrawTextItem string = new DrawTextItem(text, posX, posY);
		                string.setTextColor(textColor);
		                strings.add(string); // Restore each string
		            }

		            scanner.close();
		            undoMenuItem.setEnabled(!strings.isEmpty());
		            canvas.repaint();
		            JOptionPane.showMessageDialog(this, "File opened successfully.");
		        } catch (Exception e) {
		            JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage());
		        }
		    }
		}
		else if (command.equals("Clear")) {  // remove all strings
			strings.clear(); // Remove all strings from the ArrayList
			undoMenuItem.setEnabled(false);
			canvas.repaint();
		}
		else if (command.equals("Remove Item")) { // remove the most recently added string
			if (!strings.isEmpty()) {
				strings.remove(strings.size() - 1); // Remove the most recently added string
				if (strings.isEmpty()) {
					undoMenuItem.setEnabled(false);
				}
				canvas.repaint();
			}
		}
		else if (command.equals("Set Text Color...")) {
			Color c = JColorChooser.showDialog(this, "Select Text Color", currentTextColor);
			if (c != null)
				currentTextColor = c;
		}
		else if (command.equals("Set Background Color...")) {
			Color c = JColorChooser.showDialog(this, "Select Background Color", canvas.getBackground());
			if (c != null) {
				canvas.setBackground(c);
				canvas.repaint();
			}
		}
		else if (command.equals("Save Image...")) {  // save a PNG image of the drawing area
			File imageFile = fileChooser.getOutputFile(this, "Select Image File Name", "textimage.png");
			if (imageFile == null)
				return;
			try {
				// Because the image is not available, I will make a new BufferedImage and
				// draw the same data to the BufferedImage as is shown in the panel.
				// A BufferedImage is an image that is stored in memory, not on the screen.
				// There is a convenient method for writing a BufferedImage to a file.
				BufferedImage image = new BufferedImage(canvas.getWidth(),canvas.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = image.getGraphics();
				g.setFont(canvas.getFont());
				canvas.paintComponent(g);  // draws the canvas onto the BufferedImage, not the screen!
				boolean ok = ImageIO.write(image, "PNG", imageFile); // write to the file
				if (ok == false)
					throw new Exception("PNG format not supported (this shouldn't happen!).");
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, 
						"Sorry, an error occurred while trying to save the image:\n" + e);
			}
		}else if (command.equals("Set Size...")) {
	        if (selectedString != null) {
	            String input = JOptionPane.showInputDialog(this, "Enter the font size for the selected string:");
	            if (input != null) {
	                try {
	                    int size = Integer.parseInt(input);
	                    selectedString.setSize(size);
	                    canvas.repaint();
	                } catch (NumberFormatException e) {
	                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid font size.");
	                }
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "No string selected. Select a string first.");
	        }
	    } else if (command.equals("Set Rotation Angle...")) {
	        if (selectedString != null) {
	            String input = JOptionPane.showInputDialog(this, "Enter the rotation angle (in degrees) for the selected string:");
	            if (input != null) {
	                try {
	                    double angle = Double.parseDouble(input);
	                    selectedString.setRotationAngle(angle);
	                    canvas.repaint();
	                } catch (NumberFormatException e) {
	                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid rotation angle.");
	                }
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "No string selected. Select a string first.");
	        }
	    }
	}
}
