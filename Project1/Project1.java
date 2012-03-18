package Project1;
/**
 *	Project 1: Rock Paper Scissors
 *		This program uses two terminal arguments as input
 *		for a randomized game of Rock, Paper, Scissors.
 *
 *	@author Michael DeWitt
 *	Course	 : CS 177
 *	Section  : 0901
 *	@date 9/14/2009
 */

public class Project1 {
	public static void main (String[] args) {
		/* Instantiates the variables for the user's 
		 * name <userName> and hand <userHand> as well
		 * as the PC's hand <pcHand> and the <winner>
		 */
		String userName,
			   userHand,
			   pcHand,
			   winner;
		
		/* Declares <userName> and <userHand> as 
		 * arguments one and two, respectively
		 */
		userName = args[0];
		userHand = args[1];
		
		/* Uses <Math.random()> and if-else statements
		 * to determine an integer value for <pcHand>
		 */
		double r = Math.random();
		
		if (r < 1/3.0)
			pcHand = "rock";
		else if (r < 2/3.0)
			pcHand = "scissors";
		else
			pcHand = "paper";
		
		/* Compares <pcHand> with <userHand> and declares
		 * a winner via rules of Rock, Paper, Scissors
		 */
		if (userHand.equalsIgnoreCase(pcHand))
			winner = "Tie!";
		else if (userHand.equalsIgnoreCase("rock"))
			if (pcHand.equals("scissors"))
				winner = userName + " wins!";
			else
				winner = "The computer wins!";
		else if (userHand.equalsIgnoreCase("scissors"))
			if (pcHand.equals("rock"))
				winner = "The computer wins!";
			else
				winner = userName + " wins!";
		else if (userHand.equalsIgnoreCase("paper"))
			if (pcHand.equals("rock"))
				winner = userName + " wins!";
			else
				winner = "The computer wins!";
		else
			winner = "Michael can't write Java anymore..."; 	// added to make Eclipse warning about possible null variable go away
		
		// And finally, output:
		System.out.println("Rock Paper Scissors\n"
						 + "************************************************************\n"
						 + "Player name: " + userName + "\n"
						 + "Player hand: " + userHand + "\n"
						 + "Computer generated value: " + r + "\n"
						 + "Computer hand: " + pcHand + "\n"
						 + winner + "\n"
						 + "************************************************************");
	}
}