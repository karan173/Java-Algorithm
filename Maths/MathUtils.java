package Maths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MathUtils
{
    public static long gcd(long big, long small) // O(log min(a, b))
    {
        if (small == 0)
            return big;
        return gcd(small, big % small); // big%small<=small
    }

    public static long numOfDiv(long n) // O(n^0.5)
    {
        long i;
        long count = 0;
        for (i = 1; i * i <= n; i++)
        {
            if (n % i == 0)
            {
                count++;
                if (i * i != n)
                    count++;
            }
        }
        return count;
    }

    public static boolean isPrime(long n)
    {
        if (n < 2)
            return false;
        if (n == 2)
            return true;
        if (n % 2 == 0)
            return false;
        long i;
        for (i = 3; i * i <= n; i += 2)
        {
            if (n % i == 0)
                return false;
        }
        return true;
    }

   public static ArrayList<Integer> primes;
   public static boolean[] flags;
   public int MAXN = 202;
   public void sieve(int MAXN)
   {
       // note flags for even are not set
       this.MAXN = MAXN;
       primes = new ArrayList<Integer>();
       flags = new boolean[MAXN+1];
       Arrays.fill(flags, true);
       flags[1] = false;
       int primeCnt = 1; // 2 in
       int p, i;
       primes.add(2);
       for (p = 3; p * p <= MAXN; p += 2) // dont traverse even
       {
           if (flags[p]) // prime
           {
               primes.add(p);
               // flags for all before p*p have been set
               //
               for (i = p * p; i < MAXN; i += p + p)
               {
                   flags[i] = false;
               }
           }
       }
       // now we have written primes till sqrt(MAXN) so all non primes flags
       // have been set
       for (; p <= MAXN; p += 2)
       {
           if (flags[p]) // prime
           {
               primes.add(p);
           }
       }
   }
   public boolean isSievePrime(int n)
   {
       if(MAXN == -1)
           throw new RuntimeException("Sieve Not Created Yet\n");
       if(n==2)
           return true;
       if(n%2==0)
           return false;
       if(n<2 || n>=MAXN)
           return false;
       return flags[n];
   }

    public static long fastExp(long a, int p)
    {
        long res;
        if (p == 0)
            return 1;
        if (p % 2 == 0) // even
        {
            res = fastExp(a, p / 2);
            res *= res;
        }
        else
        // odd
        {
            res = fastExp(a, (p - 1) / 2);
            res *= res * a;
        }
        return res;
    }

    public static long modPower(long a, long p, long mod)
    {
        long res;
        if (p == 0)
            return 1%mod; //what if mod == 1
        if (p % 2 == 0) // even
        {
            res = modPower(a, p >>1, mod);
            res *= res;
        }
        else
        // odd
        {
            res = modPower(a, (p - 1) >>1, mod);
            res = ((res * res) % mod) * a;
        }
        if (res >= mod)
            res %= mod;
        return res;
    }

    public String changeBase(int n, int b) // b is new base
    {
        return Integer.toString(n, b).toUpperCase();
    }
    
    
    /*
     *  Using Fermats Little Theorem
     *   if m is a prime and a is an integer co-prime to m
     *   (a inverse)mod m = (a^(m-2))%m
     *   O(log(MOD)).
     */
    public  static long modInverse(int a, long MOD)
    {
        return modPower(a, MOD-2, MOD);
    }

    public static long mod(long a, long m) {
        a %= m;
        return a >= 0 ? a : a + m;
    }

    // precondition: m > 0 && gcd(a, m) = 1
    public static long modInverse2(long a, long m)
    {
        a = mod (a, m);
        return a == 0 ? 0 : mod ((1 - modInverse2 (m % a, a) * m) / a, m);
    }
    
    public long nCr1(int n, int r) // O(min(2r, 2(n-r)) --> can cause overflow, dont use
    {
        long retVal = 1, i;
        if (r > n || r < 0 || n < 0)
            return 0;
        if (r > n - r)
            return nCr1(n, n - r);
        for (i = n - r + 1; i <= n; i++)
        {
            retVal *= i;
        }
        for (i = 1; i <= r; i++)
        {
            retVal /= i;
        }
        return retVal;
    }

    // NCR DP
    // O(n*n) precomputation -> dp, no overflow

    long ncr[][] = new long[1001][];
    static int MOD = 1000000007;

    void compute()
    {
        for (int i = 1; i <= 1000; i++)
        {
            ncr[i] = new long[i + 1];
            ncr[i][0] = 1;
        }
        ncr[0] = new long[2];
        ncr[0][0] = 1;
        ncr[0][1] = 0;
        for (int n = 1; n <= 1000; n++)
        {
            // System.out.println(n);
            for (int r = 1; r <= n; r++)
            {
                // System.out.println(r);
                if (n - 1 >= r)
                    ncr[n][r] = (ncr[n - 1][r] + ncr[n - 1][r - 1]) % MOD;
                else
                    ncr[n][r] = ncr[n - 1][r - 1];
            }
        }
    }

    // FACT and INVERSE FACT and inverse array GENERATOR
    // useful in nCr, O(n)
    public long[] fact = new long[MAXN];  // n! mod m till MAXN
    public long[] inv = new long[MAXN]; // (n inverse) mod m till MAXN
    public long[] invFact = new long[MAXN];  // (n! inverse) mod m till MAXN
    //derivation=> see techniques(search for invFact)
    public void precompute()
    {
        fact[0] = 1;
        for (int i = 1; i < MAXN; ++i)
            fact[i] = (fact[i - 1] * i) % MOD;
        
        inv[1] = 1;
        for (int i = 2; i < MAXN; ++i)
            inv[i] = ((MOD - MOD / i) * inv[MOD % i]) % MOD;
        invFact[0] = invFact[1] = 1;
        for (int i = 2; i < MAXN; ++i)
            invFact[i] = (invFact[i - 1] * inv[i]) % MOD;
    }
    
    
    public long nCr2(int n, int k)
    {
        long answer = fact[n];
        answer = (answer * invFact[k]) % MOD;
        answer = (answer * invFact[n-k]) % MOD;
        return answer;
    }


    int getRandomInt(int a, int b) //returns random no b/w a and b
    {
        Random r = new Random();
        int n = b-a+1;
        int z = r.nextInt(n);  //[0,n-1]
        z += a; //[a, b]
        return z;
    }

    /*factor sieve -> O(n/2) memory      */

    private static int[] lowestPrime;
    public static void factorSieve(int MAXN)
    {
        // note flags for even are not set
        lowestPrime = new int[(MAXN+1)>>1]; //dont store evens
        //for each odd n -> index = n/2
        Arrays.fill (lowestPrime, Integer.MAX_VALUE);
        int p, i;
        for (p = 3; p * p <= MAXN; p += 2) // dont traverse even
        {
            if (lowestPrime[p>>1] == Integer.MAX_VALUE) // prime
            {
                lowestPrime[p >> 1] = p;
                // flags for all before p*p have been set
                //
                for (i = p * p; i <= MAXN; i += p + p)
                {
                    lowestPrime[i >> 1] = Math.min (lowestPrime[i >> 1], p);
                }
            }
        }
        //odd nos above sqr root(n) for which lowest prime is unset are primes
    }
    public static boolean isPrime(int n)
    {
        if (n == 2)
        {
            return true;
        }
        if (n == 1)
        {
            return false;
        }
        if ((n & 1) == 0)
        {
            return false;
        }
        if (lowestPrime[n >> 1] == n || lowestPrime[n >> 1] == Integer.MAX_VALUE)
        {
            return true;
        }
        return false;
    }

    //1 is returned for 1
    public static int getLowestPrime(int n)
    {
        if (n % 2 == 0)
        {
            return 2;
        }
        else if (lowestPrime[n>>1] == Integer.MAX_VALUE)
        {
            return n;
        }
        else
        {
            return lowestPrime[n>>1];
        }
    }

    /*
    condition a,m are coprime
     */
    public static long modInverse3(int a, int m)
    {
        int phi = getEulerTotientValue (m);
        return MathUtils.modPower (a, phi - 1, m);
    }

    /*
    it is equal to number of m such that
    1<=m<=n and
    gcd(m,n)=1
    i.e no of numbers <= n which are coprime to n
    dependency - factor sieve
    O(logn)
    Derivation is difficult to understand. Just learn that
    If n = p1^a1 * p2^a2 * ... pk^ak, then Euler function can be found using formula:
    φ (n) = n * (1 – 1/p 1) * (1 – 1/p 2) * ... * (1 – 1/p k)
    Hence at each
        ans -= ans/p
     */
    public static int getEulerTotientValue(int n)
    {
        int ans = n;
        while (n != 1)
        {
            int p = getLowestPrime (n);
            while (n % p == 0)
            {
                n /= p;
            }
            ans = ans - ans / p;
        }
        return ans;
    }
    /*
    guarantee - if retVal = k
    2^k <= n
     */
    public static int ceilLog2(int n)
    {
        int k;
        for (k = 0; (1 << k) <= n; k++) ;
        return k-1;
    }

    /*
    to do [x*times]%mod when [x%mod * times%mod] will overflow
    using binary exponentiation like method
    complexity - O(log(times))
     */
//    public static long fastMult(long x, long times, long mod)
//    {
//        if (times == 0)
//        {
//            return 0;
//        }
//        long ans = fastMult (x, times >> 1, mod);
//        ans <<= 1;
//        if (ans >= mod)
//        {
//            ans %= mod;
//        }
//        if (times % 2 != 0)
//        {
//            ans += x;
//            if (ans >= mod)
//            {
//                ans %= mod;
//            }
//        }
//        return ans;
//    }
    public static long fastMult( long a, long b, long m )
    {
        //from http://apps.topcoder.com/forums/?module=RevisionHistory&messageID=1221302
        if(a>m)
            a=a%m;
        if(b>m)
            b=b%m;
        long ret = 0;
        if(a<b)
        {
            long t = a;
            a = b;
            b = t;
        }
        while(b>0)
        {
            while(a<m)
            {
                if((b&1) != 0)
                    ret += a;
                a<<=1;
                b>>=1;
            }
            a-=m;
            while(ret>=m)
                ret-=m;
            if(a<b)
            {
                long t = a;
                a = b;
                b = t;
            }
        }
        return ret;
    }

}
