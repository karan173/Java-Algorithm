package Strings;

import Maths.MathUtils;

/**
 * Created by ksb on 17-08-2014.
 */
public class StringHash
{
    char s[];
    int n;
    long radix = 9584612342L; //random large primes
    long mod = 34369934;
    public long hash[];
    public StringHash(char[] s)
    {
        this.s = s;
        n = s.length;
        preprocess (s);
    }
    public StringHash(char[] s, long radix, long mod)
    {
        this.s = s;
        n = s.length;
        this.radix = radix;
        this.mod = mod;
        preprocess (s);
    }


    private void preprocess(char[] s)
    {
        hash = new long[n];
        //hash[i] = value of integer for string s[0..i]
        hash[0] = s[0] % mod;
        for(int i = 1; i<n; i++)
        {
            hash[i] = (hash[i - 1] * radix + s[i]) % mod;
        }
    }

    /*
    l and r should be 0 indexed
     */
    public long getHash(int l, int r)
    {
        if (l < 0 || r < 0 || l >= n || r >= n || l > r)
        {
            throw new IllegalArgumentException ();
        }
        if(l == 0)
        {
            return hash[r];
        }
        long netHash = hash[r];
        long prefixHash = hash[l - 1];
        long pow = MathUtils.modPower (radix, r - l + 1, mod);
        long subtract = (pow * prefixHash) % mod;
        return (netHash - subtract + mod) % mod;
    }
}
