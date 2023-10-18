import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FastaSequence implements Comparable<FastaSequence> {
    private String header;
    private StringBuilder sequence = new StringBuilder();

    @Override
    public int compareTo(FastaSequence other) {
        return Integer.compare(this.sequence.length(), other.sequence.length());
    }

    public FastaSequence(String header) {
        this.header = header;
    }

    public void addToSequence(String line) {
        sequence.append(line);
    }

    public String getHeader() {
        return header;
    }

    public String getSequence() {
        return sequence.toString();
    }

    public float getGCRatio() {
        int gcCount = 0;
        for (char base : sequence.toString().toCharArray()) {
            if (base == 'G' || base == 'C') {
                gcCount++;
            }
        }
        return (float) gcCount / sequence.length();
    }


    public static List<FastaSequence> readFastaFile(String filePath) throws Exception {
        List<FastaSequence> fastaList = new ArrayList<>();
        String localHeader = null;
        FastaSequence localFasta = null;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith(">")) {
                    if (localHeader != null && localFasta != null) {
                        fastaList.add(localFasta);
                    }
                    localHeader = line.substring(1);
                    localFasta = new FastaSequence(localHeader);
                } else {
                    // Add data to fasta sequence regardless of line breaks
                    localFasta.addToSequence(line);
                }
            }
            if (localHeader != null && localFasta != null) {
                fastaList.add(localFasta);
            }
        } catch (Exception e) {
            // Exception handling
            System.err.println("Error reading FASTA file: " + e.getMessage());
            System.err.println("The file may be in a non-traditional format.");
            System.err.println("Please check and reformat the file.");
            throw e;
        }
        return fastaList;
    }

    public static void writeTableSummary(List<FastaSequence> fastaList, File outputFile) throws Exception {
        try (FileWriter fileWriter = new FileWriter(outputFile);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence");

            for (FastaSequence fs : fastaList) {
                String header = fs.getHeader();
                String sequence = fs.getSequence();
                int numA = countBase(sequence, 'A');
                int numC = countBase(sequence, 'C');
                int numG = countBase(sequence, 'G');
                int numT = countBase(sequence, 'T');

                writer.println(header + "\t" + numA + "\t" + numC + "\t" + numG + "\t" + numT + "\t" + sequence);
            }
        }
    }

    private static int countBase(String sequence, char base) 
    {
        int count = 0;
        for (char c : sequence.toCharArray()) {
            if (c == base) {
                count++;
            }
        }
        return count;
    }

    private int countValidCharacters() 
    {
        int count = 0;
        for (char c : sequence.toString().toCharArray()) {
            if (c == 'A' || c == 'C' || c == 'G' || c == 'T') {
                count++;
            }
        }
        return count;
    }
    public static void main(String[] args)
    {
        Comparator<FastaSequence> sequenceAlphabeticalComparator = Comparator.comparing(FastaSequence::getSequence);
        Comparator<FastaSequence> headerAlphabeticalComparator = Comparator.comparing(FastaSequence::getHeader);
        Comparator<FastaSequence> gcContentComparator = Comparator.comparingDouble(FastaSequence::getGCRatio);
        Comparator<FastaSequence> validCharCountComparator = Comparator.comparing(FastaSequence::countValidCharacters);

        try 
        {
            List<FastaSequence> fastaList = readFastaFile("c:\\Users\\Ali_Tew\\advancedprogramming\\HelloWorld\\loc0114.fasta");

            for (FastaSequence fs : fastaList) {
                System.out.println(fs.getHeader());
                System.out.println(fs.getSequence());
                System.out.println(fs.getGCRatio());
            }

            // Sort by sequence length
            Collections.sort(fastaList);
            // Sort alphabetically by sequence
            Collections.sort(fastaList, sequenceAlphabeticalComparator);
            // Sort alphabetically by header
            Collections.sort(fastaList, headerAlphabeticalComparator);
            // Sort by GC content
            Collections.sort(fastaList, gcContentComparator);
            // Sort by valid character count
            Collections.sort(fastaList, validCharCountComparator);

            writeTableSummary(fastaList, new File("c:\\Users\\Ali_Tew\\advancedprogramming\\HelloWorld\\loc0114_Sortedout.txt"));
        } 
        catch (Exception e) 
        {
            // Exception Handling continued
            System.err.println("c:\\Users\\Ali_Tew\\advancedprogramming\\HelloWorld\\loc0114_out.txt");
        }
    }
}

/*
Extra Credit:
 * The GPT Code for this assignment is structured similarly to mine given the specificity of the instructions. 
 * There are however lots of distinctions as well such as in excpetion handling and java utilies that
 * are utilized in each case. In spite of GPT using a larger breadth of utilities and distilling code down into 
 * simpler syntax than I am yet able to in some places, I was still able to break it fairly easily and would have had to spend
 * time adjusting my instructions in order to try to get what I needed from the bot. It does, however, accomplish the 
 * task set out for it with a properly formatted fasta file and would be an excellent starting place for someone with
 * familiarity with java looking to build this parsing tool. In my build I used ChatGPT to help me properly format my 
 * try/catch statements, to understand how to use the file writer/reader, and in formatting the tab delineated output file. 
 */