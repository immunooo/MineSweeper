//Joe Berlucchi, Period 2, Final Project
//Project: Happy Face Sweeper (School appropriate Minesweeper)


//Note: tile state = a number between 0 and 8 determined by the amount of neighboring happy faces

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
public  class Main extends JFrame implements ActionListener{
	private Board game;
	private JButton ButtonTiles[][] = new JButton[16][16];
	JPanel mainPanel = new JPanel();
	File wavFile;
    URL defaultSound;
    public static Clip clip;
    public static AudioInputStream audioInputStream;
	
	public static void main(String[] args) {//Main method that opens the window for game
		new Main().setVisible(true);
	}
	
	private Main() {
		
		/*
		 Stuff for configuring the window when its opened
		 */
		super("HappyFaceSweeper");
		setSize(960, 960);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(16, 16));
		try {
			defaultSound = new URL("file:Bleep1.wav");//file for sound
		} catch (MalformedURLException e1) {
		}
		
		game = new Board();//Starts the game
		/*
		 These for loops basically add all the buttons to the grid.
		 */
		for(int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				JButton b = new JButton("");
				b.addActionListener(this);//Action listener for button
				ButtonTiles[i][j] = b;
				final int finalI = i;
				final int finalJ = j;
				ButtonTiles[i][j].addMouseListener(new MouseAdapter() {//Adds a mouse listener for each button
					public void mousePressed(MouseEvent e) {
		                if (SwingUtilities.isRightMouseButton(e)) {//To place a flag on a tile it is right click
		                	if(ButtonTiles[finalI][finalJ].getIcon() != null) {//This if else statement toggles the flag on every tile
		                		ButtonTiles[finalI][finalJ].setText("");
		                		ButtonTiles[finalI][finalJ].setIcon(null);
		                	} else if(ButtonTiles[finalI][finalJ].getText() == "") {
		                		ButtonTiles[finalI][finalJ].setIcon(new ImageIcon("flag.png"));
		                		ButtonTiles[finalI][finalJ].setText("");
		                	}
		                }
		            }
				});
				b.setFont(new Font("Arial", Font.PLAIN, 30));
				add(ButtonTiles[i][j]);
			}
		}
		
	}
	public void play() throws UnsupportedAudioFileException, IOException {//Plays the sound
            audioInputStream = AudioSystem.getAudioInputStream(defaultSound);

            try {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

            } catch (LineUnavailableException e) {
            }
    }
	public void stop() {//stops the sound
        clip.stop();
    }
	
	/*
	 Recursion method below to find neighboring zeros for given point.
	 Not going to comment the first if statement, not the rest
	 */
	public void showZeros(int row, int col) {
		ButtonTiles[row ][col].setIcon(null);//removes icon (Flag)
		ButtonTiles[row][col].setText(null);//Removes text
		ButtonTiles[row][col].setEnabled(false);//Makes the button not clickable
					if(row - 1 >= 0 && col - 1 >= 0 && row - 1 < 16 && col - 1 < 16) {//Check to see if the tile exists for given location
						if(game.getTileState(row - 1, col - 1) == 0) {// if the tile is not touching a happy face
							if(ButtonTiles[row - 1][col - 1].isEnabled()) {// if the tile is able to be clicked (This stops stack overflow exception)
								showZeros(row - 1, col - 1);//Recursion call for neighboring point
							}
						} else {
							ButtonTiles[row - 1][col - 1].setIcon(null);//Removes the flag icon if it is on the tile
							ButtonTiles[row - 1][col - 1].setText(game.getTileState(row - 1, col - 1) + "");// Sets the number of neighboring tile
							ButtonTiles[row - 1][col - 1].setEnabled(false);//Makes the tile not click-able
						}
					}
					
					if(row - 1 >= 0 && row - 1 < 16) {
						if(game.getTileState(row - 1, col) == 0) {// -1 , -
							if(ButtonTiles[row - 1][col].isEnabled()) {
								showZeros(row - 1, col);
							}
						} else {
							ButtonTiles[row - 1][col].setIcon(null);
							ButtonTiles[row - 1][col].setText(game.getTileState(row - 1, col) + "");
							ButtonTiles[row - 1][col].setEnabled(false);
						}
					}
					
					if(col - 1 >= 0 && col - 1 < 16) {
						if(game.getTileState(row, col - 1) == 0) {// - , -1
							if(ButtonTiles[row][col - 1].isEnabled()) {
								showZeros(row, col - 1 );
							}
						}else {
							ButtonTiles[row][col - 1].setIcon(null);
							ButtonTiles[row][col - 1].setText(game.getTileState(row, col - 1) + "");
							ButtonTiles[row][col - 1].setEnabled(false);
						}
					}
					
					if(row - 1 >= 0 && col + 1 >= 0 && row - 1 < 16 && col + 1 < 16) {
						if(game.getTileState(row - 1, col + 1) == 0) {// -1 , +1
							if(ButtonTiles[row - 1][col + 1].isEnabled()) {
								showZeros(row - 1, col + 1);
							}
						} else {
							ButtonTiles[row - 1][col + 1].setIcon(null);
							ButtonTiles[row - 1][col + 1].setText(game.getTileState(row - 1, col + 1) + "");
							ButtonTiles[row - 1][col + 1].setEnabled(false);
						}
					}
					
					if(row + 1 >= 0 && col - 1 >= 0 && row + 1 < 16 && col - 1 < 16) {
						if(game.getTileState(row + 1, col - 1) == 0) {// +1, -1
							if(ButtonTiles[row + 1][col - 1].isEnabled()) {
								showZeros(row + 1, col - 1);
							}
						}else {
							ButtonTiles[row + 1][col - 1].setIcon(null);
							ButtonTiles[row + 1][col - 1].setText(game.getTileState(row + 1, col - 1) + "");
							ButtonTiles[row + 1][col - 1].setEnabled(false);
						}
					}
					
					if(row + 1 >= 0 && col + 1 >= 0 && row + 1 < 16 && col + 1 < 16) {
						if(game.getTileState(row + 1, col + 1) == 0) {// +1, +1
							if(ButtonTiles[row + 1][col + 1].isEnabled()) {
								showZeros(row + 1, col + 1);
							}
							
						}else {
							ButtonTiles[row + 1][col + 1].setIcon(null);
							ButtonTiles[row + 1][col + 1].setText(game.getTileState(row + 1, col + 1) + "");
							ButtonTiles[row + 1][col + 1].setEnabled(false);
						}
					}
					
					if(row + 1 >= 0 && row + 1 < 16) {
						if(game.getTileState(row + 1, col) == 0) {// +1, -
							if(ButtonTiles[row + 1][col].isEnabled()) {
								showZeros(row + 1, col);
							}
						} else {
							ButtonTiles[row + 1][col].setIcon(null);
							ButtonTiles[row + 1][col].setText(game.getTileState(row + 1, col) + "");
							ButtonTiles[row + 1][col].setEnabled(false);
						}
					}
					
					if(col + 1 >= 0 && col + 1 < 16) {
						if(game.getTileState(row, col + 1) == 0) {// -, +1
							if(ButtonTiles[row][col + 1].isEnabled()) {
								showZeros(row, col + 1);
							}
						} else {
							ButtonTiles[row][col + 1].setIcon(null);
							ButtonTiles[row][col + 1].setText(game.getTileState(row, col + 1) + "");
							ButtonTiles[row][col + 1].setEnabled(false);
						}
					}
	}
	
	
	
	/*
	 This runs when a tile is clicked
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		int tileState = 0;
		int row = 0;
		int col = 0;
		try {
			play();//Plays a bleep when tile is clicked
		} catch (UnsupportedAudioFileException e1) {
		} catch (IOException e1) {
		}
		//TLDR: sets tile state in local variable and reveals the tile you clicked
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				if(e.getSource().equals(ButtonTiles[i][j])) {//For loop to check if the tile is the one you are clicking on
					tileState = game.getTileState(i, j);//gets tile state
					ButtonTiles[i][j].setIcon(null);//remove the icon (flag)
					ButtonTiles[i][j].setText(tileState + "");//Puts the tile state on the button
					ButtonTiles[i][j].setEnabled(false);//Makes button unclickable
					row = i;
					col = j;
					
				}
			}
		}
		
		//TLDR: if player clicks on happy face tile it ends the game
		if(tileState == -1) {//if player clicks on a tile with a happyface
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 16; j++) {
					if(game.getTileState(i, j) == -1) {//For loop goes through every tile to find happy faces
						ButtonTiles[i][j].setIcon(new ImageIcon("happyface.png"));//Sets the button icon to a happyface.png
						ButtonTiles[i][j].setText(null);
						ButtonTiles[i][j].setEnabled(false);
					} else {// else it removes the flag icons and makes it unclickable
						ButtonTiles[i][j].setIcon(null);
						ButtonTiles[i][j].setEnabled(false);
					}
				}
			}
		}
		if (tileState == 0){//If the player clicks on tile with value of zero it reveals neighboring zeros with recursion function
			showZeros(row, col);
		} 
	}
	
}
