import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AminoAcidQuiz {
    private static final int QUIZ_DURATION = 30; // 30 seconds
    private static final int MAX_AMINO_ACIDS = 20;
    private static final String[] AMINO_ACID_NAMES = {
        "Alanine", "Arginine", "Asparagine", "Aspartic Acid",
        "Cysteine", "Glutamine", "Glutamic Acid", "Glycine",
        "Histidine", "Isoleucine", "Leucine", "Lysine",
        "Methionine", "Phenylalanine", "Proline", "Serine",
        "Threonine", "Tryptophan", "Tyrosine", "Valine"
    };

    private static final String[] AMINO_ACID_CODES = {
        "A", "R", "N", "D", "C", "Q", "E", "G", "H", "I",
        "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"
    };

    private int score = 0;
    private Timer timer;
    private boolean quizOver = false;

    public void startQuiz() {
        List<Integer> aminoAcidIndexes = new ArrayList<>();
        for (int i = 0; i < AMINO_ACID_NAMES.length; i++) {
            aminoAcidIndexes.add(i);
        }

        Collections.shuffle(aminoAcidIndexes);
        List<String> shuffledAminoAcids = new ArrayList<>();

        for (int i = 0; i < Math.min(MAX_AMINO_ACIDS, AMINO_ACID_NAMES.length); i++) {
            shuffledAminoAcids.add(AMINO_ACID_NAMES[aminoAcidIndexes.get(i)]);
        }

        System.out.println("Welcome to the Amino Acid Quiz!");
        System.out.println("You have " + QUIZ_DURATION + " seconds to answer.");
        System.out.println("Type the one-letter code for each amino acid:");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            private int timeRemaining = QUIZ_DURATION;

            @Override
            public void run() {
                if (timeRemaining <= 0 || quizOver) {
                    endQuiz();
                } else {
                    System.out.println("\nTime remaining: " + timeRemaining + " seconds");
                    timeRemaining--;
                }
            }
        }, 0, 1000);

        Scanner scanner = new Scanner(System.in);

        for (String aminoAcid : shuffledAminoAcids) {
            if (quizOver) {
                break;
            }

            int index = aminoAcidIndexes.get(shuffledAminoAcids.indexOf(aminoAcid));
            System.out.println("\nAmino Acid: " + aminoAcid);
            String expectedCode = AMINO_ACID_CODES[index];
            System.out.print("Enter the one-letter code: ");
            String userAnswer = scanner.nextLine().trim().toLowerCase();

            if (userAnswer.equals(expectedCode.toLowerCase())) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer is: " + expectedCode);
                quizOver = true;
            }
        }

        endQuiz();
    }

    private void endQuiz() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your score: " + score + " correct answers");
        timer.cancel();
        System.exit(0);
    }

    public static void main(String[] args) {
        AminoAcidQuiz quiz = new AminoAcidQuiz();
        quiz.startQuiz();
    }
}