package daphshez.codejam.cj_2018_practice;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

// could be improved by using a priority queue
public class SenateEvacuation1
{
    @Test
    public void sample()
    {
        int[] ar = new int[]{2, 2};
        verify(Arrays.copyOf(ar, ar.length), evacuate(Arrays.copyOf(ar, ar.length)));
        ar = new int[]{3, 2, 2};
        verify(Arrays.copyOf(ar, ar.length), evacuate(Arrays.copyOf(ar, ar.length)));

        ar = new int[]{1, 1, 2};
        verify(Arrays.copyOf(ar, ar.length), evacuate(Arrays.copyOf(ar, ar.length)));


        ar = new int[]{2, 3, 1};
        verify(Arrays.copyOf(ar, ar.length), evacuate(Arrays.copyOf(ar, ar.length)));

    }

    public void verify(int[] senators, String plan) throws RuntimeException
    {
        System.out.println(plan);
        System.out.println(Arrays.toString(senators));
        String[] planSteps = plan.split(" ");
        for (String step : planSteps) {
            senators[step.charAt(0) - 'A']--;
            if (step.length() > 1) {
                senators[step.charAt(1) - 'A']--;
            }

            int totalSenators = Arrays.stream(senators).sum();
            System.out.println(Arrays.toString(senators));

            for (int i = 0; i < senators.length; i++) {
                assertTrue(senators[i] >= 0);
                assertTrue(senators[i] <= totalSenators / 2);
            }

        }

        assertEquals(0, Arrays.stream(senators).sum());
    }


    // we aren't required to return an optimal plan (minimum steps)
    private String evacuate(int[] senators)
    {
        int totalSenators = Arrays.stream(senators).sum();

        StringBuilder sb = new StringBuilder();

        while (totalSenators > 0) {
            if (sb.length() > 0) sb.append(' ');

            int maxParty = partyWithMostSenators(senators);

            sb.append((char) (maxParty + 'A'));
            senators[maxParty]--;
            totalSenators--;

            int maxParty2 = partyWithMostSenators(senators);
            if (maxParty2 == -1) break;

            // would getting this senator our leave a party with majority?
            senators[maxParty2]--;
            totalSenators--;
            int maxParty3 = partyWithMostSenators(senators);
            if (maxParty3 != -1 && senators[maxParty3] > (totalSenators) / 2) {
                senators[maxParty2]++;
                totalSenators++;
            } else {
                sb.append((char) (maxParty2 + 'A'));
            }

        }

        return sb.toString();
    }

    private int partyWithMostSenators(int[] sentators)
    {
        int mx = 0;
        int mxParty = -1;
        for (int i = 0; i < sentators.length; i++) {
            if (sentators[i] > mx) {
                mx = sentators[i];
                mxParty = i;
            }
        }

        return mxParty;
    }

}
