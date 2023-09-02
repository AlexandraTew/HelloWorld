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
            System.out.println(seq);
            seq = "";
        }
        /** 
         * With 64 possible combinations being run at random with equal probability, 
         * we would expect to see approximately 15 of each 3 letter variety in a set of 1000 sequences.
         * Each time I ran this code the output was a number between 5 and 25 which is within a 10 instances from 15.
         * This outcome suggests the randomizer to be selecting letters with equal weights.
        */
        System.out.println(countAAA);
    
        // Use the same logic but add in weighted probabilites for GC rich organisms
        int weightedCountAAA = 0;
        int num = 0;

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
            System.out.println(seq);
            seq = "";
        }

        System.out.println(weightedCountAAA);

        // Discuss results

    }
}