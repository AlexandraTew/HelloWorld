import java.util.Random;
import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

public class Lab02 {
  
    public static String[] letterCode = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I",
                                        "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"};
    public static String[] aminoAcids = {"alanine", "arginine", "asparagine", "aspartic acid", "cysteine", 
                                        "glutamine", "glutamic acid", "glycine", "histidine", "isoleucine", 
                                        "leucine", "lysine", "methionine", "phenylalanine", "proline",
                                        "serine", "threonine", "tryptophan", "tyrosine", "valine"};
    public static void main(String[] args) {
        int score = 0;
        Instant startTime = Instant.now();

        while (Duration.between(startTime, Instant.now()).getSeconds() < 30) {
            int randomIndex = getRandomIndex();
            String aminoAcid = aminoAcids[randomIndex];

            System.out.println("What is the one-letter code for the amino acid: " + aminoAcid + "?");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine().trim().toUpperCase();

            if (userInput.equals(letterCode[randomIndex])) {
                System.out.println("That's correct!\n");
                score++;
            } else {
                System.out.println("That's incorrect! The one letter code for " + aminoAcid + " is " + letterCode[randomIndex] + "\n");
                break;
            }

            scanner.close();
        }

        System.out.println("Your score: " + score + "/" + aminoAcids.length);
    }

    static int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(aminoAcids.length);
    }
}

/*
 * Discussion: I ran out of time before being able to dig into the extra credit tasks but just looking at my code 
 * vs ChatGPT's (AminoAcidQuiz.java), it looks like chatGPT actually over-complicated both the code and the underlying logic
 * and employs a constant countdown method that is very distracting when taking the quiz. It did seem to meet all
 * specifications for the assignment as far as I can tell but I can't say that I understand everything that its doing
 * to achieve a similar result. 
 * The benefit that this countdown has over my code is that it immediately ends the quiz at the 30 second mark instead of 
 * checking for time after each new response. 
 */