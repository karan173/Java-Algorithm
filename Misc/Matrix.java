package Misc;

import Maths.MathUtils;

import java.util.Arrays;

//static nested class cant access enclosing classes variables
//based on http://fusharblog.com/solving-linear-recurrence-for-programming-contest/

public class Matrix
{
    public int m, n;
    public long val[][];
    
    public Matrix(int i, int j)
    {
        m = i;
        n = j;
        val = new long[m][n];
    }

    public Matrix(int i, int j, long[][] val)
    {
        m = i;
        n = j;
        this.val = val;
    }

    // returns transform matrix k is size and c is constant vector
    // its a utility method and doesnt refer to instance variables hence
    // static
    // note the vector c should be c1,c2,c3, ..ck
    public static Matrix makeTransform(int k, long[] c)
    {
        Matrix T = new Matrix(k, k);

        for (int i = 0; i < k - 1; i++)
        {
            Arrays.fill(T.val[i], 0);
            T.val[i][i + 1] = 1;
        }
        for (int i = 0; i < k; i++)
        {
            T.val[k - 1][i] = c[k - 1 - i];
        }
        // since last row of c is ck, ck-1, ...c3, c2 ,c1
        return T;
    }

    // multiply + take mod of each element
    public Matrix multiply(Matrix B, long MOD)
    {
        Matrix A = this;
        // to find C = AXB
        Matrix C = new Matrix(A.m, B.n);
        if (A.n != B.m)
            throw new UnsupportedOperationException(
                    "incompatible matrix multiplication");
        for (int i = 0; i < C.m; i++)
        {
            for (int j = 0; j < C.n; j++)
            {
                C.val[i][j] = 0;
                for (int k = 0; k < A.n; k++)
                {
                    long temp = C.val[i][j];
                    C.val[i][j] += MathUtils.fastMult (A.val[i][k], B.val[k][j], MOD);
                    C.val[i][j] %= MOD;
//                    C.val[i][j] = (((A.val[i][k]) * (B.val[k][j])) % MOD + C.val[i][j])
//                            % MOD;
                    assert C.val[i][j] >= 0 : "a-" + A.val[i][k] + " b-"
                            + B.val[k][j] + " t-" + temp + " c-" + C.val[i][j];
                }
            }
        }
        return C;
    }

    public Matrix multiply(Matrix B)
    {
        Matrix A = this;
        // to find C = AXB
        Matrix C = new Matrix(A.m, B.n);
        if (A.n != B.m)
            throw new UnsupportedOperationException(
                    "incompatible matrix multiplication");
        for (int i = 0; i < C.m; i++)
        {
            for (int j = 0; j < C.n; j++)
            {
                C.val[i][j] = 0;
                for (int k = 0; k < A.n; k++)
                {
                    C.val[i][j] = (((A.val[i][k]) * (B.val[k][j])) + C.val[i][j]);
                    assert C.val[i][j] >= 0 : "a-" + A.val[i][k] + " b-"
                            + B.val[k][j] + "c-" + C.val[i][j];
                }
            }
        }
        return C;
    }

    public Matrix pow(long n, long MOD)
    {
        if (n == 1)
            return this;
        if (n < 1)
            throw new UnsupportedOperationException("wron n value = " + n
                    + "in powMod");
        Matrix retVal = pow(n / 2, MOD);
        retVal = retVal.multiply(retVal, MOD);
        if (n % 2 == 0) // even
        {
            return retVal;
        }
        else
        {
            return retVal.multiply(this, MOD);
        }
    }

    public static Matrix getIdentity(int n)
    {
        long val[][] = new long[n][n];
        for (int i = 0; i < n; i++)
        {
            Arrays.fill (val[i], 0);
            val[i][i] = 1;
        }
        return new Matrix (n, n, val);
    }

    public Matrix sum(Matrix T, long mod)
    {
        if (T.n != n || T.m != m)
        {
            throw new UnsupportedOperationException ();
        }
        Matrix x = new Matrix (n, m);
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                x.val[i][j] = val[i][j] + T.val[i][j];
                x.val[i][j] %= mod;
            }
        }
        return x;
    }

//    public Matrix pow(long l, int n) // overloaded
//    {
//        if (n == 1 || n==0)
//            return this;
//        if (n < 1)
//            throw new UnsupportedOperationException("wron n value = " + n
//                    + "in powMod");
//        Matrix retVal = pow(n / 2);
//        retVal = retVal.multiply(retVal);
//        if (n % 2 == 0) // even
//        {
//
//            return retVal;
//        }
//        else
//        {
//            return retVal.multiply(this);
//        }
//    }

    public String toString()
    {
        String retString = "printing matrix\n";
        retString += "m " + m + " n " + n + '\n';
        for (long[] a : this.val)
        {
            retString += Arrays.toString(a);
            retString += '\n';
        }
        return retString;
    }
}