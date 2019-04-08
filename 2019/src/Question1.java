import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Question1
{

    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(reader.readLine());

        for (int i = 1 ; i <= t; i++)
        {
            String[] pair = findSolution(reader.readLine());
            System.out.println("Case #" + i + ": " + pair[0] + " " + pair[1]);
        }
    }

    private static String[] findSolution(String sum)
    {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        for (int i = 0; i < sum.length(); i++)
        {
            if (sum.charAt(i) == '4') {
                s1.append('3');
                s2.append('1');
            } else {
                s1.append(sum.charAt(i));
                if (s2.length() > 0)
                    s2.append('0');
            }
        }

        return new String[] { s1.toString(), s2.toString() };
    }
//
//    public static void main(String[] args)
//    {
//        Random r =new Random();
//
//        for (int i = 0; i < 100_000; i++) {
//            BigInteger sum = new BigInteger(350, r);
//            String[] sol =findSolution(sum.toString());
//            assert sum.equals(new BigInteger(sol[0]).add(new BigInteger(sol[1])));
//        }
//
//    }

}
