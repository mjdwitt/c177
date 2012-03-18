package Project03;

/** 
 * Project 3: Sudoku Helper
 * 
 * @author Michael DeWitt
 * course: cs177
 * section: 0901
 * date: 11 October 2009
 */

import java.io.*;
import java.util.*;

public class Project3 {
	private static int[][] master = new int[9][9]; //This 9x9 grid uses only ones and zeros.
								   				   // Ones represent values original to the starting grid.
	
	/**
	 * loadPuzzle() loads a puzzle from a file
	 * 
	 * @param puzzle
	 * the puzzle to return
	 * @param filename
	 * the path for the file to load
	 */
	private static int[][] loadPuzzle(int[][] puzzle, String filename) 
		throws IOException {
		boolean done = true;
		
		// attempts to locate the given filepath and load the values to an array
		try {
			Scanner file = new Scanner(new File(filename));
			
			for (int i = 0; i < puzzle.length; i++)
				for (int j = 0; j < puzzle[i].length; j++)
					puzzle[i][j] = file.nextInt();
			
			file.close();
		}
		catch (FileNotFoundException e) {
			done = false;
		}
		catch (InputMismatchException e) {
			done = false;
		}
		
		if (done == false)
			throw new IOException("The file could not be found.");
		else
			return puzzle;
	}
	
	/**
	 * isPlacementOK() is used to check values being added to a specific space on the grid.
	 * 
	 * @param puzzle
	 * represents the sudoku board
	 * @param row
	 * x coordinate
	 * @param col
	 * y coordinate
	 * @param num
	 * number to be placed at (x, y)
	 */
	private static boolean isPlacementOK(int[][] puzzle, int row, int col, int num) {
		boolean done = true;
		int rowGroup = 0,
			colGroup = 0;
		
		if (num < 1 || num > 9)
			done = false;
		else {
			// tests rows and columns for legal placement
			for (int i = 0; i < puzzle.length; i++)
				if (puzzle[i][col] == num || puzzle[row][i] == num)
					done = false;
			
			// tests specific sub-grid for legal placement:
			
				// finds group of rows
			if (row < 3 && row >=0)
				rowGroup = 0;
			else if (row < 6 && row >= 3)
				rowGroup = 3;
			else if (row < 9 && row >=6)
				rowGroup = 6;

				// finds group of columns
			if (col < 3 && col >=0)
				colGroup = 0;
			else if (col < 6 && col >= 3)
				colGroup = 3;
			else if (col < 9 && col >=6)
				colGroup = 6;
			
				// tests sub-grid for duality
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					if (num == puzzle[rowGroup+i][colGroup+j])
						done = false;
			
			// tests for pre-filled values
			if (master[row][col] == 1)
				done = false;
		}
		
		return done;
	}

	/**
	 * getValidLocation() is used to prompt the user for to integer numbers
	 * representing a location on the grid.  It then checks those numbers and 
	 * ensures that they are, in fact, a valid location on the grid.
	 * 
	 * @param msg -  the message used to prompt the user for input
	 * @return
	 * two integers in a two-element array representing a valid location
	 */
	private static int[] getValidLocation(String msg) {
		Scanner in = new Scanner(System.in);	// input object
		//int[] position = new int[2];		// location on grid (row, column)
		boolean exit = false;			// loop control
		int x = -1, y = -1;
		
		do {
			System.out.print(msg);
			try {
				// attempts to read ints from terminal, kicks to catch if bad input
				x = in.nextInt();
				y = in.nextInt();
				exit = true;
				
				// makes sure that the int values are valid values for the grid
				if (!(x > 0 && x < 10 && y > 0 && y < 10)) {
					System.out.println("What you entered, " + x + ", " + y + ", is not a valid position.");
					exit = false;
				}
				//checks against master position grid
				else if (master[x-1][y-1] != 0)
					System.out.println(x + ", " + y + " is a predetermined position.");
			} catch (InputMismatchException e) {
				System.out.println("What you entered, " + in.next() + ", is not a valid position.");
				exit = false;
			}
		} while (!exit);
		
		return new int[] {x-1, y-1}; 
	}

	/**
	 * printGrid() prints the values in the grid as a matrix
	 * 
	 * @param puzzle
	 * the current puzzle grid
	 */
	private static void printGrid(int[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++)
				System.out.print(puzzle[i][j] + " ");
			System.out.println();
		}
	}

	/**
	 * getRandomGrid() fills an array with random values
	 * 
	 * @param fill
	 * number of random locations to fill with a random value
	 * @return
	 * the randomly filled puzzle grid
	 */
	private static int[][] getRandomGrid(int fill) {
		Random tourettes = new Random();		// tourettes as in Tourette's Syndrome -> Random object
		Scanner in = new Scanner(System.in);	// see comments in main() method for same variable
		int[][] puzzle = new int[9][9];
		
		if (fill > 81) {
			System.out.print("There are not that many locations on the grid.\n" +
							 "Please enter a number no greater than 81: ");
			fill = in.nextInt();
		}
		
		for (int i = 0; i < fill; i++) {
			int x = tourettes.nextInt(9),
				y = tourettes.nextInt(9),
				num = tourettes.nextInt(9);
			
			if (isPlacementOK(puzzle, x, y, num)) {
				puzzle[x][y] = num;
				master[x][y] = 1;
			}
			else
				i--;
		}
		
		return puzzle;
	}

	/**
	 * main() method...
	 * 
	 * @param args
	 */
	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);		// used in lieu of StdIn and imported from the standard library
		int[][] puzzle = new int[9][9];			// represents the sudoku grid
		boolean exit = false,				// loop control variable
				trip = false;			// trips the error message if true
		int selection = 0,			// represents the user's menu selection
			num = 0;			// represents a value in the grid
		int[] location;		// represents a (row, column) location on the grid
		
		// starting menu
		do {
			System.out.print("1. Load from file\n" +
						     "2. Generate one\n" +
						     "3. Exit\n" +
							 "Please choose: ");
			
			try {
				selection = in.nextInt();
				trip = true;
			} catch (InputMismatchException e) {
				System.out.println("The input you provided, "  + in.next() + ", is not a valid choice.");
				trip = false;
			} finally {
				exit = true;  // sets current do-while loop to exit
				
				// selects action to be taken based on the value of selection
				switch (selection) {
					case 1:
						System.out.print("Please input file name: ");
						
						// attempts to load a puzzle from a file
						do {
							try {
								puzzle = loadPuzzle(puzzle, in.next());
							} catch (IOException e) {
								System.out.println("What you entered is an invalid filepath.\n" +
												   "Please provide a different filepath: ");
								exit = false;	// resets loop control so that the try-statement is tried again
							}
						} while (!exit);
						
						// seeds master grid with values
						for (int i = 0; i < puzzle.length; i++)
							for (int j = 0; j < puzzle[i].length; j++)
								if (puzzle[i][j] != 0)
									master[i][j] = 1;
						
						break;
					case 2:
						System.out.print("How many numbers to fill in? ");
						puzzle = getRandomGrid(in.nextInt());
						break;
					case 3:
						// do nothing and let loop terminate
						break;
					default:
						if (selection != 0 && trip)
							System.out.println("The input you provided, " + selection + ", is not a valid choice");
						exit = false;	// sets loop for rerun
						break;
				}
			}
		} while (!exit);
		
		// displays the puzzle
		if (!(selection == 3))
			printGrid(puzzle);
		
		// main options menu
		do {
			// modification choices and exit
			System.out.print("1. Check available numbers\n" +
							 "2. Fill in a square\n" +
							 "3. Erase a square\n" +
							 "4. Exit\n" +
							 "Please select: ");
			
			try {
				selection = in.nextInt();
				trip = true;
			} catch (InputMismatchException e) {
				System.out.println("What you entered, " + in.next() + ", is not a valid choice.");
				trip = false;
			} finally {
				exit = true;	// sets current do-while loop to exit
				
				switch (selection) {
				case 1:
					location = getValidLocation("Which position to check (row column): ");	// acquire legal position
					
					ArrayList<Integer> vals = new ArrayList<Integer>();  // stores possible values
					
					// checks for possible values
					for (int i = 1; i < 10; i++)
						if (isPlacementOK(puzzle, location[0], location[1], i))
							vals.add(i);
					
					// prints possible values
					if (!vals.isEmpty()) {
						System.out.print("Available numbers: ");
						for (int i = 0; i < vals.size(); i++)
							System.out.print(vals.get(i) + " ");
						System.out.println();
					}
					
					exit = false;	// resets the option-menu loop control for repeat
					
					break;
				case 2:
					location = getValidLocation("Which position to fill in (row column): ");	// acquire legal position
					
					// acquires a legal value for the position
					do {
						try {
							System.out.print("Which number to fill in: ");
							num = in.nextInt();
							if (!isPlacementOK(puzzle, location[0], location[1], num))
								exit = false;
							else
								exit = true;
						} catch (InputMismatchException e) {
							System.out.println("What you entered, " + in.next() + ", is an invalid value.");
							exit = false;
						}
					} while (!exit);
					
					puzzle[location[0]][location[1]] = num;		// places the value in the grid
					
					exit = false;	// resets the option-menu loop control for repeat
					
					break;
				case 3:
					location = getValidLocation("Which position to erase (row column): ");
					
					if (master[location[0]][location[1]] != 1)
						if (puzzle[location[0]][location[1]] != 0) {
							puzzle[location[0]][location[1]] = 0;
							System.out.println("Sucessful");
						}
						else
							System.out.println("Location is already empty.");
					
					exit = false;
					
					break;
				case 4:
					exit = true;	// sets option-menu loop to terminate
					break;
				}
			}
			if (selection != 4)
				printGrid(puzzle);
		} while (!exit);
	}
}
