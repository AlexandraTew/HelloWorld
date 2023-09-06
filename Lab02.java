/* 	
    Instructions for the Quiz:
    -It is 30 seconds long.
	-The quiz ends after 30 seconds or when there is a single incorrect answer.
	-The program displays the full name of an amino acid (like “alanine” ) 
		and asks the user to type in the one character code (like a)
	-The quiz ignores case in the answer.
	-The program displays the amino acids in random order, can repeat amino
	     acids and can show more than 20 if the user gets that many correct.
	-The total score is the number of correct answers…
 */
import java.util.Random;
import java.util.Scanner;
// import java.util.HashMap;
// import java.util.Map;

public class Lab02 {

    // Create a question generator that randomly pulls an indexed selection out of the two arrays
    // Use indices to input hashmap values into the question
    // Evaluate response for correctness and keep a tally of correct answers and time taken to answer
    // Print both of these values to the screen
    // Create a termination response when an incorrect answer is recieved
    // Create a total score output that triggers upon termination of the quiz
    // Add in the timing function to terminate the quiz automatically after 30 seconds

   
    public static String[] SHORT_NAMES = 
        {"A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", 
        "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };

    public static String[] FULL_NAMES = 
        {
        "alanine","arginine", "asparagine", 
        "aspartic acid", "cysteine",
        "glutamine",  "glutamic acid",
        "glycine" ,"histidine","isoleucine",
        "leucine",  "lysine", "methionine", 
        "phenylalanine", "proline", 
        "serine","threonine","tryptophan", 
        "tyrosine", "valine"};

        public static void main(String[] args)
        {
            Random rand = new Random();
            int num = rand.nextInt(20);
            Scanner obj = new Scanner(System.in);

            System.out.println("Amino Acid Quiz! How fast can you go??");
            // answer = obj.nextLine(); /* Take string input and assign to variable */
            // System.out.println(answer); /* Print */
        }
}
