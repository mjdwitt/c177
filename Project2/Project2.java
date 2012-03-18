package Project2;

/**
 * Project 2: Base Conversion
 * 
 * @author Michael DeWitt
 * course: cs177
 * section: 0901
 * date: 9/30/2009
 */

import java.util.*;

public class Project2 {
	public static void main(String[] args) {
		boolean exit = false;	// controller for menu loop
		int choice = 0;				// represents the user's menu selection
		int dec = 0, oct = 0;			// represent the user-supplied value and it's equivalent in the other base
		
		Scanner in = new Scanner(System.in);	// object used for obtain user input via the command line (System.in)
		
		do {
			do {
				System.out.println("1. Decimal to Octal\n"
							     + "2. Octal to Decimal\n"
							     + "3. Exit\n"
							     + "Enter your menu selection (1, 2 or 3): ");
				
				if (in.hasNextInt()) {
					choice = in.nextInt();
					if (choice > 0 && choice < 4)
						exit = true;
					else
						System.out.println("-----Option " + in.next() + " does not exist.  Please select option 1, 2 or 3.-----");
				}
				else
					System.out.println("-----Option " + in.next() + " does not exist.  Please select option 1, 2 or 3.-----");
			}
			while (!exit);
			
			exit = false;
			
			switch (choice) {
				case 1:
					System.out.println("Please enter a decimal number: ");
					
					// tests input and either sends values to toOctal() for conversion or
					// prints an error message and returns the user to the main menu
					if (in.hasNextInt()) {
						dec = in.nextInt();
						oct = toOctal(dec);
						System.out.println("The octal representation of " + dec + " is " + oct + ".");
					}
					else
						System.out.println("-----What you entered, " + in.next() + ", is not a number.-----");
					
					break;
				case 2:
					System.out.println("Please enter an octal number: ");
					
					// variables for testing input 
					String input = in.next();
					boolean octal = true;
					
					for (int i = 0; i < input.length(); i++)
						if (!(input.charAt(i) >= '0' && input.charAt(i) <= '7'))
							octal = false;
					
					// checks results of above test and either assigns a value to be passed to the toDecimal() method
					// or prints an error message and returns the user to the main menu
					if (octal) {
						oct = Integer.parseInt(input);
						dec = toDecimal(oct);
						System.out.println("The decimal representation of " + oct + " (base 8) is " + dec + ".");
					}
					else
						System.out.println("-----What you entered, " + input + ", is not an octal number.-----");
					
					break;
				case 3:
					// Wiedersehen!
					System.out.println("-----Thank you. Good bye.-----");
					exit = true;
					break;
			}
		}
		while (!exit);
	}
	
	/**
	 * toOctal() converts a decimal-base number
	 * to a number in base-eight.
	 * 
	 * @param int num
	 * Any integer representing a decimal integer.
	 * @return int oct
	 * The octal equivalent of num.
	 */
	private static int toOctal(int num) {
		int done = 0;		// stores the return value for a single 'end' terminal
		int digit = 0;		// stores the value for the current step's ones' digit (far left digit)
		
		if (num < 8)		// tests for the end-case of the recursive loop
			done = num;
		else {				// otherwise, this step is performed until the end case is reached
			digit = num%8;							// finds the value for the current ones' digit
			done = toOctal(num/8)*10 + digit; 		// finds the remaining digits to the left, recursively
		}
		
		return done;		// returns the final computed value for itself and all the interior recursive steps
	}
	
	/**
	 * toDecimal() converts an octal-base number
	 * to a number in base-ten.  This version handles
	 * the lowest digit first and returns if the end 
	 * case is reached in the first run, otherwise
	 * it sends it's values to the overloaded version.
	 * 
	 * @param num
	 * Any integers in octal (all digits < 8)
	 * @return
	 * The decimal equivalent of num.
	 */
	private static int toDecimal(int num) {
		int done = 0;
		int digit = 0;
		
		if (num < 10)
			done = num;
		else {
			digit = num%10;
			done = digit + toDecimal(num/10, 1);
		}
		
		return done;
	}
	
	/**
	 * to Decimal() converts an octal-base number
	 * to a number in base-ten.  This overloaded version 
	 * can handle all digits of an octal number if the call 
	 * is made with the parameter count equaling zero; however, 
	 * it is intended to be encapsulated by the above single-
	 * parameter version so that the calls are simplified and 
	 * symmetrical between toDecimal() and toOctal().
	 * 
	 * @param num
	 * The octal-base number to be converted.
	 * @param count
	 * count tracks how many times this function has been called.
	 * This is necessary for counting the digits of the number and
	 * applying that information to the conversion calculation.
	 * (i.e. abcd is an octal number, decimal equivalent is found
	 * with a*8^4 + b*8^3 + ... and so on.  count is the exponent 
	 * of this function.)
	 * @return
	 * adds the conversion of that particular digit's decimal
	 * equivalent to the results of the next recursive call.
	 */
	private static int toDecimal (int num, int count) {
		int done = 0;
		int digit = 0;
		
		if (num < 10)
			done = num * (int) Math.pow(8, count);
		else {
			digit = num%10 * (int) Math.pow(8, count);
			done = digit + toDecimal(num/10, ++count);
		}
		
		return done;
	}
}