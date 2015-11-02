import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class OthelloProgram {
	private JFrame myFrame;
	private JPanel myPanel;
	private JPanel myPanel2;
	private JPanel myPanel3;
	private JButton[][] myButtons;
	private JLabel currentPlayerLabel;
	private JLabel canMoveLabel;
	private JLabel totalPiecesLabel;
	
	static char[][] boardSize;
	int numberOfChips = 4;
	char playerTurn = '●';
	private int xSpot = 0;
	private int ySpot = 0;
	
	public OthelloProgram()
	{
		myFrame = new JFrame("Othello");	//Makes a new window called "Othello"
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myPanel = new JPanel();
		myPanel2 = new JPanel();
		myPanel3 = new JPanel();
		currentPlayerLabel = new JLabel("Current player: Black");	//Makes a new label that displays the current player's turn
		canMoveLabel = new JLabel("Welcome to Othello!", JLabel.CENTER);
		totalPiecesLabel = new JLabel("Black Pieces: 0 White Pieces: 0");
		myPanel.setLayout(new GridLayout(8, 8));	//Makes an 8 x 8 grid panel
		myPanel2.setLayout(new GridLayout(0, 2));	//Makes a 0 x 1 grid panel for the label
		myPanel3.setLayout(new GridLayout(0, 1));
		UIManager.put("Button.disabledText", Color.BLACK);
		myButtons = new JButton[8][8];	//Makes 64 buttons placed in an 8 x 8 grid
		myFrame.pack();
		boardSize = new char[8][8];		//Sets up an 8 x 8 board
		setupBoard();
		myPanel2.add(currentPlayerLabel);	//Adds the label to the second panel;
		myPanel2.add(totalPiecesLabel, BorderLayout.EAST);
		myPanel3.add(canMoveLabel);
		myFrame.add(myPanel2, BorderLayout.NORTH);	//Adds the second panel to the very top of the window
		myFrame.add(myPanel3, BorderLayout.SOUTH);
		myFrame.add(myPanel, BorderLayout.CENTER);	//Adds the 8 x 8 grid to the center of the window
		myFrame.pack();		//Packs all components of the window
		myFrame.setVisible(true);	//Makes the window visible
		
	}
	
	public class MyActionListener implements ActionListener		//The Action Listener that tells what each button will do
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton source = (JButton)e.getSource();	//Targets the button that is being interacted on	
			int x = Integer.parseInt((String)source.getClientProperty("x"));	//Gets the x-value of the button
			int y = Integer.parseInt((String)source.getClientProperty("y"));	//Gets the y-value of the button
			playerMove(x, y);
		}
	}

	private void setupBoard()
	{
		//Setup the buttons
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				myButtons[x][y] = new JButton();	//Creates individual buttons
				myButtons[x][y].setEnabled(true);
				myButtons[x][y].putClientProperty("x", x + "");		//Stores x value of button
				myButtons[x][y].putClientProperty("y", y + "");		//Stores y value of button
				myButtons[x][y].setPreferredSize(new Dimension(48, 48));	//Sets the size of the buttons
				myButtons[x][y].setMargin(new Insets(0, 0, 0, 0));
				myButtons[x][y].setBackground(new Color(0, 190, 0));
				myButtons[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				myButtons[x][y].addActionListener(new MyActionListener());	//Gives the buttons an action when pressed
				myPanel.add(myButtons[x][y]);
			}
		}
		
		//Fills up all spaces of the board in asterisks
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				boardSize[x][y] = '*';
			}
		}
		
		//Puts the first 4 pieces in the middle of the board
		boardSize[3][3] = '○';
		myButtons[3][3].setText("○");
		boardSize[4][4] = '○';
		myButtons[4][4].setText("○");
		boardSize[3][4] = '●';
		myButtons[3][4].setText("●");
		boardSize[4][3] = '●';
		myButtons[4][3].setText("●");
		myButtons[3][3].setEnabled(false);
		myButtons[3][4].setEnabled(false);
		myButtons[4][3].setEnabled(false);
		myButtons[4][4].setEnabled(false);
		
		/*
		//Used to check all conversion functions
		//boardSize[4][3] = '1'; Place value here to trigger all conversion functions
		//Right conversion
		boardSize[5][3] = '○';
		boardSize[6][3] = '○';
		boardSize[7][3] = '●';
		
		//Left conversion
		boardSize[3][3] = '○';
		boardSize[2][3] = '○';
		boardSize[1][3] = '○';
		boardSize[0][3] = '●';
		
		//Up conversion
		boardSize[4][2] = '○';
		boardSize[4][1] = '○';
		boardSize[4][0] = '●';
		
		//Down conversion
		boardSize[4][4] = '○';
		boardSize[4][5] = '○';
		boardSize[4][6] = '○';
		boardSize[4][7] = '●';
		
		//Diagonal Up Right conversion
		boardSize[5][2] = '○';
		boardSize[6][1] = '○';
		boardSize[7][0] = '●';
		
		//Diagonal Down Right conversion
		boardSize[5][4] = '○';
		boardSize[6][5] = '○';
		boardSize[7][6] = '●';
		
		//Diagonal Left Up conversion
		boardSize[3][2] = '○';
		boardSize[2][1] = '○';
		boardSize[1][0] = '●';
		
		//Diagonal Left Down conversion
		boardSize[3][4] = '○';
		boardSize[2][5] = '○';
		boardSize[1][6] = '○';
		boardSize[0][7] = '●';
		*/
	}
	
	private int rightConversion(int xValue, int yValue)		//Used to convert pieces in the right direction
	{
		boolean cont = false;
		int x = xValue;
		
		//Checks whether another piece of the current player is somewhere on the right
		while (x < 8 && boardSize[x][yValue] != '*' && cont == false)
		{
			if (boardSize[x][yValue] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else		//Otherwise, don't convert at all
				cont = false;
			x++;
		}
		
		if (cont == true)
		{
			if (xValue < 8)
			{
			
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')	//If the current selected piece is not the same as the current player's piece and it's not an asterisk, then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");		//Have the buttons also convert (same line is applied to all the conversion methods)
				}
				else
					//boardSize[xValue][yValue] = boardSize[xValue][yValue];
					return 0;		//Otherwise do nothing
				return rightConversion(xValue + 1, yValue);		//Recalls the method to go to the next piece to the right
			}
		}
		return 0;
	}
	
	private int leftConversion(int xValue, int yValue)
	{
		boolean cont = false;
		int x = xValue;
		
		//Checks whether another piece of the current player is somewhere on the left
		while (x >= 0 && boardSize[x][yValue] != '*' && cont == false)
		{
			if (boardSize[x][yValue] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else		//Otherwise, don't convert at all
				cont = false;
			x--;
		}

		if (cont == true)
		{
			if (xValue >= 0)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')	//If the current selected piece is not the same as the current player's piece and it is not an asterisk then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else		//Otherwise do nothing
					return 0;
				return leftConversion(xValue - 1, yValue);		//Recalls the method to go to the next piece to the left
			}
		}
		return 0;
	}
	
	private int downConversion(int xValue, int yValue)
	{
		boolean cont = false;
		int y = yValue;
		
		//Checks whether another piece of the current player is somewhere below
		while (y < 8 && boardSize[xValue][y] != '*' && cont == false)
		{
			if (boardSize[xValue][y] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else		//Otherwise, don't convert at all
				cont = false;
			y++;
		}

		if (cont == true)
		{
			if (yValue < 8)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')		//If the current selected piece is not the same as the current player's piece and it is not an asterisk then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else		//Otherwise do nothing
					return 0;
				return downConversion(xValue, yValue + 1);		//Recalls the method to go to the next piece below
			}
		}
		return 0;
	}
	
	private int upConversion(int xValue, int yValue)
	{
		boolean cont = false;
		int y = yValue;
		
		//Checks whether another piece of the current player is somewhere above
		while (y >= 0 && boardSize[xValue][y] != '*' && cont == false)
		{
			if (boardSize[xValue][y] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else		//Otherwise, don't convert at all
				cont = false;
			y--;
		}
		
		if (cont == true)
		{
			if (yValue >= 0)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')		//If the current selected piece is not the same as the current player's piece and it is not an asterisk then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else		//Otherwise do nothing
					return 0;
				return upConversion(xValue, yValue - 1);		//Recalls the method to go to the next piece above
			}
		}
		return 0;
	}
	
	private int diagonalConversionUpRight(int xValue, int yValue)
	{
		boolean cont = false;
		int x = xValue;
		int y = yValue;
		
		//Checks whether another piece of the current player is somewhere to the upper right
		while ((x < 8 && y >= 0) && boardSize[x][y] != '*' && cont == false)
		{
			if (boardSize[x][y] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else		//Otherwise, don't convert at all
				cont = false;
			x++;
			y--;
		}
		
		if (cont == true)
		{
			if (xValue < 8 && yValue >= 0)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')		//If the current selected piece is not the same as the current player's piece and it is not an asterisk then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else		//Otherwise do nothing
					return 0;
				return diagonalConversionUpRight(xValue + 1, yValue - 1);		//Recalls the method to go to the next piece to the upper right
			}
		}
		return 0;
	}
	
	private int diagonalConversionDownRight(int xValue, int yValue)
	{
		boolean cont = false;
		int x = xValue;
		int y = yValue;
		
		//Checks whether another piece of the current player is somewhere to the lower right
		while ((x < 8 && y < 8) && boardSize[x][y] != '*' && cont == false)
		{
			if (boardSize[x][y] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else	//Otherwise, don't convert at all
				cont = false;
			x++;
			y++;
		}
		
		if (cont == true)
		{
			if (xValue < 8 && yValue < 8)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')		//If the current selected piece is not the same as the current player's piece and it is not an asterisk, then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else		//Otherwise do nothing
					return 0;
				return diagonalConversionDownRight(xValue + 1, yValue + 1);		//Recalls the method to go to the next piece to the lower right
			}
		}
		return 0;
	}
	
	private int diagonalConversionUpLeft(int xValue, int yValue)
	{
		boolean cont = false;
		int x = xValue;
		int y = yValue;
		
		//Checks whether another piece of the current player is somewhere to the upper left
		while ((x >= 0 && y >= 0) && boardSize[x][y] != '*' && cont == false)
		{
			if (boardSize[x][y] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else	//Otherwise, don't convert at all
				cont = false;
			x--;
			y--;
		}
		
		if (cont == true)
		{
			if (xValue >= 0 && yValue >= 0)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')		//If the current selected piece is not the same as the current player's piece and it is not an asterisk, then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else	//Otherwise do nothing
					return 0;
				return diagonalConversionUpLeft(xValue - 1, yValue - 1);		//Recalls the method to go to the next piece to the upper left
			}
		}
		return 0;
	}
	
	private int diagonalConversionDownLeft(int xValue, int yValue)
	{
		boolean cont = false;
		int x = xValue;
		int y = yValue;
		
		//Checks whether another piece of the current player is somewhere to the lower left
		while ((x >= 0 && y < 8) && boardSize[x][y] != '*' && cont == false)
		{
			if (boardSize[x][y] == playerTurn)		//If there is another piece of the same color as the player, start converting
				cont = true;
			else	//Otherwise, don't convert at all
				cont = false;
			x--;
			y++;
		}
		
		if (cont == true)
		{
			if (xValue >= 0 && yValue < 8)
			{
				if (boardSize[xValue][yValue] != playerTurn && boardSize[xValue][yValue] != '*')		//If the current selected piece is not the same as the current player's piece and it is not an asterisk, then convert
				{
					boardSize[xValue][yValue] = playerTurn;
					myButtons[xValue][yValue].setText(playerTurn + "");
				}
				else	//Otherwise, do nothing
					return 0;
				return diagonalConversionDownLeft(xValue - 1, yValue + 1);		//Recalls the method to go to the next piece to the lower left
			}
		}
		return 0;
	}
	
	private void conversion(int xValue, int yValue)
	{
		//Calls each recursive conversion method
		rightConversion(xValue + 1, yValue);
		leftConversion(xValue - 1, yValue);
		downConversion(xValue, yValue + 1);
		upConversion(xValue, yValue - 1);
		diagonalConversionUpRight(xValue + 1, yValue - 1);
		diagonalConversionDownRight(xValue + 1, yValue + 1);
		diagonalConversionUpLeft(xValue - 1, yValue - 1);
		diagonalConversionDownLeft(xValue - 1, yValue + 1);
	}
	
	private boolean checkPlayerCanMove()
	{
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (boardSize[x][y] == '*')
				{
					if (validMove(x, y) == true)
						return true;
				}
			}
		}
		return false;
	}
	
	private boolean validMove(int mx, int my)		//Determines whether the player can place their chip into the targeted location
	{
		int startX = mx;
		int startY = my;
		int x = mx;
		int y = my;
		
		//Checks if the current player has another piece somewhere to the right
		if (x + 1 < 8 && boardSize[x + 1][y] != playerTurn)		//First checks if the chip to the right is one of the current player's chips
		{
			x++;	//Skips to the right of the chosen area, otherwise the validation will fail since the chosen spot is a *
			while (x < 8 && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))		//Checks to make sure that the check remains inside the array and is either a white piece or black piece
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				x++;
			} 
		}
		x = startX;		//Resets x to starting position
		
		//Checks if the current player has another piece somewhere to the left
		if (x - 1 >= 0 && boardSize[x - 1][y] != playerTurn)		//First checks if the chip to the left is one of the current player's chips
		{
			x--;	//Skips to the left of the chosen area
			while (x >= 0 && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))	//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				x--;
			}
		}
		x = startX;		//Resets x to starting position
		
		//Checks if the current player has another piece somewhere below
		if (y + 1 < 8 && boardSize[x][y + 1] != playerTurn)		//First checks if the chip below is one of the current player's chips
		{
			y++;	//Skips to the location under the chosen area
			while (y < 8 && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))		//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				y++;
			}
		}
		y = startY;		//Resets y to starting position
		
		//Checks if the current player has another piece somewhere above
		if (y - 1 >= 0 && boardSize[x][y - 1] != playerTurn)		//First checks if the chip above is one of the current player's chips
		{
			y--;	//Skips to the location above the chosen area
			while (y >= 0 && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))	//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				y--;
			}
		}
		y = startY;
		
		//Checks if the current player has another piece somewhere to the upper right
		if ((x + 1 < 8 && y - 1 >= 0) && boardSize[x + 1][y - 1] != playerTurn)		//First checks if the chip to the upper right is one of the current player's chips
		{
			//Skips to the location to the upper right of the chosen area
			x++;	
			y--;	
			while ((x < 8 && y >= 0) && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))		//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				x++;
				y--;
			}
		}
		x = startX;
		y = startY;
		
		//Checks if the current player has another piece somewhere to the lower right
		if ((x + 1 < 8 && y + 1 < 8) && boardSize[x + 1][y + 1] != playerTurn)		//First checks if the chip to the lower right is one of the current player's chips
		{
			//Skips to the location to the lower right of the chosen area
			x++;
			y++;
			while ((x < 8 && y < 8) && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))		//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				x++;
				y++;
			}
		}
		x = startX;
		y = startY;
		
		//Checks if the current player has another piece somewhere to the upper left
		if ((x - 1 >= 0 && y - 1 >= 0) && boardSize[x - 1][y - 1] != playerTurn)		//First checks if the chip to the upper left is one of the current player's chips
		{
			//Skips to the location to the upper left of the chosen area
			x--;
			y--;
			while ((x >= 0 && y >= 0) && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))	//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				x--;
				y--;
			}
		}
		x = startX;
		y = startY;
		
		//Checks if the current player has another piece somewhere to the lower left
		if ((x - 1 >= 0 && y + 1 < 8) && boardSize[x - 1][y + 1] != playerTurn)		//First checks if the chip to the lower left is one of the current player's chips
		{
			//Skips to the location to the lower left of the chosen area
			x--;
			y++;
			while ((x >= 0 && y < 8) && (boardSize[x][y] == '○' || boardSize[x][y] == '●'))		//Refer to comment on line 346
			{
				if (boardSize[x][y] == playerTurn)
					return true;
				x--;
				y++;
			}
		}
		x = startX;
		y = startY;
		
		return false;
	}
	
	public void playerMove(int mx, int my)
	{	
		boolean cont = validMove(mx, my);		//Makes sure whether the chosen position to place the current player's piece is a valid move
		int totalBlack = 0;
		int totalWhite = 0;
		
		if (cont == true && checkPlayerCanMove() == true)		//If it is a valid move, make player moves
		{
			canMoveLabel.setText(" ");
			if (boardSize[mx][my] == '*')		//Checks if the space is an empty space
			{
				boardSize[mx][my] = playerTurn;		//Assigns the current space as the current player's piece
				if (playerTurn == '●')
					UIManager.put("Button.disabledText", Color.BLACK);
				else
					UIManager.put("Button.disabledText", Color.WHITE);
				conversion(mx, my);		//Does necessary conversions
			}
			/*
			else		//If it is not an empty space, have the player choose another position
			{
				System.out.println("Space is already taken!\n");
				return 0;
			}
			*/
			
			myButtons[mx][my].setEnabled(false);	//Makes the chosen button un-interactable
			myButtons[mx][my].setText(playerTurn + "");		//Sets the text of the button to be of the current player's piece
			numberOfChips++;	//Increases the number of chips on the board
		
			//Change the current player's turn
			if (playerTurn == '●')
			{
				playerTurn = '○';
				currentPlayerLabel.setBackground(Color.WHITE);
				currentPlayerLabel.setText("Current player: White");
			}
			else
			{
				playerTurn = '●';
				currentPlayerLabel.setBackground(Color.BLACK);
				currentPlayerLabel.setText("Current player: Black");
			}
			
			//Count the different colored pieces
			for (int y = 0; y < 8; y++)
			{
				for (int x = 0; x < 8; x++)
				{
					if (boardSize[x][y] == '●')
						totalBlack++;
					else if (boardSize[x][y] == '○')
						totalWhite++;
				}
			}

			totalPiecesLabel.setText("Black Pieces: " + totalBlack + " White Pieces: " + totalWhite);
			
			//Checks if all the spaces in the board are filled up 
			if (numberOfChips >= 64)
			{
				//Check who wins
				if (totalBlack > totalWhite)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Black wins!");
					currentPlayerLabel.setText("Winner: Black");
				}
				else if (totalWhite > totalBlack)
				{
					JOptionPane.showMessageDialog(new JFrame(), "White wins!");
					currentPlayerLabel.setText("Winner: White");
				}
				else
				{
					JOptionPane.showMessageDialog(new JFrame(), "Its a Tie!");
					currentPlayerLabel.setText("Its a Tie!");
				}
			}
		}
		else		//If it is not a valid move, make the current player choose another location
		{
			//Checks first to see if the player can move
			if (checkPlayerCanMove() == false)
			{
				if (playerTurn == '●')
				{
					canMoveLabel.setText("Black couldn't move!");
					playerTurn = '○';
				}
				else 
				{
					canMoveLabel.setText("White couldn't move!");
					playerTurn = '●';
				}
			}
			else	//If the player can move and made an invalid move, display this message instead
				canMoveLabel.setText("Invalid move!");
		}
	}
	
	public void displayBoard()
	{	
		System.out.print("   ");
	
		for (int i = 0; i < 8; i++)
			System.out.print(i + " ");
		
		System.out.println();
		
		for (int y = 0; y < 8; y++)
		{
			System.out.print(y + "| ");
			for (int x = 0; x < 8; x++)
			{
				System.out.print(boardSize[x][y]);	//Displays the items in the current row of the array
				if(x == 7 && boardSize[x][y] == '*')	//if last column, no space
					System.out.print(" ");
				else if (boardSize[x][y] != '*')
					System.out.print("   ");		//if chip, add three short spaces
				else if(boardSize[x][y] == '*')
					System.out.print(" ");			//if blank,add long space
			}
			System.out.print("|" + y);
			System.out.println();		//Once it reaches the end of a row, move down and print the next row
		}
		
		System.out.print("   ");
		for (int i = 0; i < 8; i++)
			System.out.print(i + " ");
		System.out.println();

	}

	/*
	public static void main(String[] args)
	{
		OthelloProgram game = new OthelloProgram();
		Scanner input = new Scanner(System.in);
		int y = 0;
		int x = 0;
		int totalWhitePieces = 0;
		int totalBlackPieces = 0;
		int result = 0;

		do
		{
			game.displayBoard();
			
			if (game.checkPlayerCanMove() == false)
			{	
				if (game.playerTurn == '●')
				{
					System.out.println("Black cannot move!");
					game.playerTurn = '○';
				}
				else
				{
					System.out.println("White cannot move!");
					game.playerTurn = '●';
				}
			}
			else
			{
			
			if (game.playerTurn == '●')		//Displays who's turn it is
				System.out.println("Black's turn");
			else
				System.out.println("White's turn");
			
			//System.out.println("Number of Chips: " + game.numberOfChips);		//DEBUG: Shows # of chips on board

			System.out.print("Enter column: ");
			x = input.nextInt();
			
			System.out.print("Enter row: ");
			y = input.nextInt();
				
			result = game.playerMove(x, y);
			}
		} while (result == 0);
		
		if (result == 1)
		{
			for (int yVal = 0; yVal < 8; yVal++)
			{
				for (int xVal = 0; xVal < 8; xVal++)
				{
					if (boardSize[xVal][yVal] == '●')
						totalBlackPieces++;
					else if (boardSize[xVal][yVal] == '○')
						totalWhitePieces++;
				}
			}
			if (totalBlackPieces > totalWhitePieces)
				System.out.println("Black wins!");
			else if (totalWhitePieces > totalBlackPieces)
				System.out.println("White wins!");
			else
				System.out.println("It is a tie!");
		}
		input.close();
	}
	*/
}
