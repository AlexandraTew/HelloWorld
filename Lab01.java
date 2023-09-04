import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class Lab01 {

    public static void main(String[] args)
    {
        Random rand = new Random();
        int seqLength = 3;
        String seq = "";
        int countAAA = 0;
        float expFrequency = (float) (Math.pow(.25, 3) * 1000);

        // Hashmap used to connect random number selection with DNA Bases
        Map<Integer, String> baseSelect = new HashMap<>();
        baseSelect.put(0, "A");
        baseSelect.put(1, "G");
        baseSelect.put(2, "C");
        baseSelect.put(3, "T");

        for(int cap = 0; cap<1000; cap++)
        {
            for(int i = 0; i<seqLength; i++)
            {
                int num = rand.nextInt(4);
                seq = seq + baseSelect.get(num);
            }   
            if(seq.equals("AAA"))
                countAAA++;
            // System.out.println(seq);
            seq = "";
        }
        /** 
         * With 64 possible combinations being run at random with equal probability, 
         * we would expect to see approximately 15 of each 3 letter variety in a set of 1000 sequences.
         * Each time I ran this code the output was a number between 5 and 25 which is within a 10 instances from 15.
         * This outcome suggests the randomizer to be selecting letters with equal weights.
        */
        System.out.println(countAAA);
        System.out.println("The expected frequency is: " + expFrequency);
    
        // Use the same logic but add in weighted probabilites for GC rich organisms
        int weightedCountAAA = 0;
        int num = 0;
        float expFrequencyWeighted = (float) (Math.pow(.12, 3) * 1000);

        for (int cap = 0; cap < 1000; cap++) 
        {
        
            for (int i = 0; i < seqLength; i++) 
            {
                 double randomValue = rand.nextFloat();
        
                if (randomValue < 0.12) {
                    num = 0;} 
                else if (randomValue < 0.12 + 0.39) {
                    num = 1;} 
                else if (randomValue < 0.12 + 0.39 + 0.38) {
                    num = 2;} 
                else {
                    num = 3;}
                seq = seq + baseSelect.get(num);
            }

            if (seq.equals("AAA")) {
                weightedCountAAA++;
            }
            // System.out.println(seq);
            seq = "";
        }

        System.out.println(weightedCountAAA);
        System.out.println("The expected frequency including the weighted probability is: " + expFrequencyWeighted);

        /** 
         * After running the code several times the resulting number of "AAA" threemers found consistently 
         * fall within a plausible range from the expected frequency.
         * GPT's code offered similar results. To compare them I extracted a sample of the 1000 threemers 
         * from each program and used a Chi-squared test to see if the distributions of randomly generated
         * sequences were roughly equal.
        */ 

    }
}