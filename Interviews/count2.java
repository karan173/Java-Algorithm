package Interviews;

import java.util.HashMap;

/**
 * Created by ksb on 05-09-2014.
 */
public class count2
{
    /*
    To count twos in range from 0 to n
     */
    public static HashMap<Integer, Long> map = new HashMap<Integer, Long> ();

    //eg if 353
    //calls will be made for 9,99,53,3,353
    //complexity = O(logn)
    public static long count2(int n)
    {
        if(n <= 1)
        {
            return 0;
        }
        if(n <= 9)
        {
            return 1;
        }
        if (map.containsKey (n))
        {
            return map.get (n);
        }
        int x = largestPower10 (n);
        int leftDigit = n / x;
        long val = count2 (x - 1);
        long ans = (leftDigit) * val; //not leftDigit-1 since we also consider 0 as prefix
        if (leftDigit > 2)
        {
            ans += x;
        }
        val = count2 (n % x);
        ans += val;
        if (leftDigit == 2)
        {
            ans += (n % x) + 1;
        }
        map.put (n, ans);
        return ans;
    }

    public static int largestPower10(int n)
    {
        int cnt = 1;
        while ((n / 10) != 0)
        {
            n /= 10;
            cnt *= 10;
        }
        return cnt;
    }


    public static int count2sInRangeAtDigit(int number, int d) {
        int powerOf10 = (int) Math.pow(10, d);
        int nextPowerOf10 = powerOf10 * 10;
        int right = number % powerOf10;

        int roundDown = number - number % nextPowerOf10;
        int roundUp = roundDown + nextPowerOf10;

        int digit = (number / powerOf10) % 10;
        if (digit < 2) { // if the digit in spot digit is
            return roundDown / 10;
        } else if (digit == 2) {
            return roundDown / 10 + right + 1;
        } else {
            return roundUp / 10;
        }
    }
    //ctci solution
    public static int count2sInRange(int number) {
        int count = 0;
        int len = String.valueOf(number).length();
        for (int digit = 0; digit < len; digit++) {
            count += count2sInRangeAtDigit(number, digit);
        }
        return count;
    }
    public static void main(String[] args)
    {
        for (int i = 0; i <= 10000; i++)
        {
            long val1 = count2 (i);
            long val2 = count2sInRange (i);
            if (val1 != val2)
            {
                System.out.println (i + " " + val1 + " " + val2);
                break;
            }

        }
    }

}
