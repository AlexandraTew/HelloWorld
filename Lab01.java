import java.util.Random;

public class Lab01 {
    
    // public static void main(String[] args)
    // {
    //     String threeMer = "";
    //     threeMer = threeMer + "A";
    //     threeMer = threeMer + "T";
    //     threeMer = threeMer + "C";
    //     threeMer = threeMer + "G";
    // }

    // public static void rand(String[] args)
    // {
    //     Random random = new Random();
    //     random.nextInt(4);

    // }

    public static void main(String[] args)
    {
        Random rand = new Random();
        Integer seqLength = 3;
        char[] seq;
        seq = new char[seqLength];
        for(int i = 0; i<seqLength;i++)
        {
            int num = rand.nextInt(4);
            if(num == 0)
                seq[i] = 'A';
            if(num == 1)
                seq[i] = 'G';
            if(num == 2)
                seq[i] = 'C';
            if(num == 3)
                seq[i] = 'T';
    
        }
        return (java.util.Arrays.toString(dna));
    }
}

