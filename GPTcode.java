import java.io.*;
import java.util.*;

public class GPTCode {
    private String header;
    private String sequence;

    public GPTCode(String header, String sequence) {
        this.header = header;
        this.sequence = sequence;
    }

    public String getHeader() {
        return header.substring(1); // Remove the '>' character
    }

    public String getSequence() {
        return sequence;
    }

    public float getGCRatio() {
        int gcCount = 0;
        int length = sequence.length();

        for (char base : sequence.toCharArray()) {
            if (base == 'G' || base == 'C') {
                gcCount++;
            }
        }

        return (float) gcCount / length;
    }

    public static List<GPTCode> readFastaFile(String filepath) throws IOException {
        List<GPTCode> sequences = new ArrayList<>();
        String header = "";
        StringBuilder sequence = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")) {
                    // Header line
                    if (!header.isEmpty()) {
                        // Create a new GPTCode object for the previous sequence
                        sequences.add(new GPTCode(header, sequence.toString()));
                        sequence.setLength(0); // Clear sequence buffer
                    }
                    header = line;
                } else {
                    // Sequence line
                    sequence.append(line);
                }
            }

            // Add the last sequence after the loop
            if (!header.isEmpty()) {
                sequences.add(new GPTCode(header, sequence.toString()));
            }
        }

        return sequences;
    }

    public static void writeTableSummary(List<GPTCode> list, File outputFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            // Write the header row
            bw.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");

            for (GPTCode sequence : list) {
                String header = sequence.getHeader();
                String seq = sequence.getSequence();
                int numA = seq.replaceAll("[^A]", "").length();
                int numC = seq.replaceAll("[^C]", "").length();
                int numG = seq.replaceAll("[^G]", "").length();
                int numT = seq.replaceAll("[^T]", "").length();

                // Write the data row
                bw.write(header + "\t" + numA + "\t" + numC + "\t" + numG + "\t" + numT + "\t" + seq + "\n");
            }
        }
    }

    public static void main(String[] args) {
        try {
            List<GPTCode> sequences = readFastaFile("input.fasta");
            writeTableSummary(sequences, new File("output.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
