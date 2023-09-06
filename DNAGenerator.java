import java.util.Random;

// I offered the instructions to chat gpt and this is the outputted code:

public class DNAGenerator {
    public static void main(String[] args) {
        int numberOfSamples = 1000;
        String targetSequence = "AAA";
        int targetCount = 0;

        // Define the probabilities of A, C, G, and T (uniform distribution)
        double pA = 0.25;
        double pC = 0.25;
        double pG = 0.25;
        double pT = 0.25;

        // Create a random number generator
        Random rand = new Random();

        // Generate DNA sequences and track the frequency of "AAA"
        for (int i = 0; i < numberOfSamples; i++) {
            StringBuilder dnaSequence = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                double randValue = rand.nextDouble();
                if (randValue < pA) {
                    dnaSequence.append("A");
                } else if (randValue < pA + pC) {
                    dnaSequence.append("C");
                } else if (randValue < pA + pC + pG) {
                    dnaSequence.append("G");
                } else {
                    dnaSequence.append("T");
                }
            }

            // Check if the generated sequence is equal to "AAA"
            if (dnaSequence.toString().equals(targetSequence)) {
                targetCount++;
            }

            // Print the generated DNA sequence
            // System.out.println(dnaSequence.toString());
        }

        // Calculate the expected frequency of "AAA" for uniform distribution
        double expectedFrequencyUniform = Math.pow(pA, 3);

        // Print the results
        System.out.println("\nGenerated " + numberOfSamples + " DNA 3-mers.");
        System.out.println("Frequency of \"" + targetSequence + "\": " + targetCount);
        System.out.println("Expected frequency of \"" + targetSequence + "\" (Uniform): " + expectedFrequencyUniform);

        // Modify probabilities for A, C, G, and T
        pA = 0.12;
        pC = 0.38;
        pG = 0.39;
        pT = 0.11;

        // Reset the target count
        targetCount = 0;

        // Generate DNA sequences with the modified probabilities
        for (int i = 0; i < numberOfSamples; i++) {
            StringBuilder dnaSequence = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                double randValue = rand.nextDouble();
                if (randValue < pA) {
                    dnaSequence.append("A");
                } else if (randValue < pA + pC) {
                    dnaSequence.append("C");
                } else if (randValue < pA + pC + pG) {
                    dnaSequence.append("G");
                } else {
                    dnaSequence.append("T");
                }
            }

            // Check if the generated sequence is equal to "AAA"
            if (dnaSequence.toString().equals(targetSequence)) {
                targetCount++;
            }

            // Print the generated DNA sequence
            // System.out.println(dnaSequence.toString());
        }

        // Calculate the expected frequency of "AAA" with modified probabilities
        double expectedFrequencyModified = Math.pow(pA, 3);

        // Print the results for modified probabilities
        System.out.println("\nGenerated " + numberOfSamples + " DNA 3-mers (Modified Probabilities).");
        System.out.println("Frequency of \"" + targetSequence + "\": " + targetCount);
        System.out.println("Expected frequency of \"" + targetSequence + "\" (Modified): " + expectedFrequencyModified);
    }
}


