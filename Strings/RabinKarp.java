package Strings;

import Maths.MathUtils;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 27/11/13
 * Time: 3:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class RabinKarp
{
    static long Q = (long) 997;
    int R = 10; //radix
    long RM; //R^(M-1) % Q
    public long stringHash(char s[], int len)
    {
        long h = 0;
        for (int i = 0; i < len; i++)
        {
            h = (h*R + s[i]) % Q;
        }
        return h;
    }

    /*
    in c++ we can take unsigned ints to have modulo 2^32
    this is also suitable for multi pattern matching, add all pHash to hash table
    NOTE check for overflow in hash by printing
    NOTE THIS HAS SCOPE FOR FALSE POSITIVES
     */
    public boolean[] match(char text[], char pattern[])
    {
        int n = text.length;
        int m = pattern.length;
        boolean match[] = new boolean[n];
        Arrays.fill (match, false);
        if (m > n)
        {
            return match;
        }
        long pHash = stringHash (pattern, m);
        long tHash = stringHash (text, m);
        RM = MathUtils.modPower (R, m - 1, Q);
        if (pHash == tHash)
        {
            match[0] = true;
        }
        for (int i = 1; i + m <= n; i++)
        {
            tHash = tHash - (text[i - 1] * RM) % Q + Q;
            tHash = tHash * R + text[i + m - 1];
            tHash %= Q;
            if (tHash == pHash)
            {
                match[i] = true;
            }
        }
        return match;
    }
}
