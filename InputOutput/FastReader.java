package InputOutput;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.InputMismatchException;

public class FastReader
{
    /*
    methods - ni - int
              nl - long
              ns - string
              line - line
              line(boolean) - line
              eof - over
              nc - character
              bigi - bigint
              iArr - int arr
              i2dArr - 2d int array
     */
    private boolean finished = false;

    public InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public FastReader(InputStream stream)
    {
        this.stream = stream;
    }

    public FastReader()
    {

    }

    public int read()
    {
        if (numChars == -1)
        {
            throw new InputMismatchException ();
        }
        if (curChar >= numChars)
        {
            curChar = 0;
            try
            {
                numChars = stream.read (buf);
            } catch (IOException e)
            {
                throw new InputMismatchException ();
            }
            if (numChars <= 0)
            {
                return -1;
            }
        }
        return buf[curChar++];
    }

    public void printArr(int a[][])
    {
        for (int x[] : a)
        {
            for (int y : x)
            {
                System.out.print (y + " ");
            }
            System.out.println ();
        }
        System.out.println ();
        System.out.println ();
    }

    public int peek()
    {
        if (numChars == -1)
        {
            return -1;
        }
        if (curChar >= numChars)
        {
            curChar = 0;
            try
            {
                numChars = stream.read (buf);
            } catch (IOException e)
            {
                return -1;
            }
            if (numChars <= 0)
            {
                return -1;
            }
        }
        return buf[curChar];
    }

    public int ni()
    {
        int c = read ();
        while (isSpaceChar (c))
            c = read ();
        int sgn = 1;
        if (c == '-')
        {
            sgn = -1;
            c = read ();
        }
        int res = 0;
        do
        {
            if (c < '0' || c > '9')
            {
                throw new InputMismatchException ();
            }
            res *= 10;
            res += c - '0';
            c = read ();
        } while (!isSpaceChar (c));
        return res * sgn;
    }

    public long nl()
    {
        int c = read ();
        while (isSpaceChar (c))
            c = read ();
        int sgn = 1;
        if (c == '-')
        {
            sgn = -1;
            c = read ();
        }
        long res = 0;
        do
        {
            if (c < '0' || c > '9')
            {
                throw new InputMismatchException ();
            }
            res *= 10;
            res += c - '0';
            c = read ();
        } while (!isSpaceChar (c));
        return res * sgn;
    }

    public String ns()
    {
        int c = read ();
        while (isSpaceChar (c))
            c = read ();
        StringBuilder res = new StringBuilder ();
        do
        {
            res.appendCodePoint (c);
            c = read ();
        } while (!isSpaceChar (c));
        return res.toString ();
    }

    public boolean isSpaceChar(int c)
    {
        if (filter != null)
        {
            return filter.isSpaceChar (c);
        }
        return isWhitespace (c);
    }

    public static boolean isWhitespace(int c)
    {
        return c==' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    private String readLine0()
    {
        StringBuilder buf = new StringBuilder ();
        int c = read ();
        while (c != '\n' && c != -1)
        {
            if (c != '\r')
            {
                buf.appendCodePoint (c);
            }
            c = read ();
        }
        return buf.toString ();
    }

    public String nline()
    {
        String s = readLine0 ();
        while (s.trim ().length () == 0)
            s = readLine0 ();
        return s;
    }

    public String nline(boolean ignoreEmptyLines)
    {
        if (ignoreEmptyLines)
        {
            return nline ();
        }
        else
        {
            return readLine0 ();
        }
    }

    public BigInteger nbigi()
    {
        try
        {
            return new BigInteger (ns ());
        } catch (NumberFormatException e)
        {
            throw new InputMismatchException ();
        }
    }

    public char nc()
    {
        int c = read ();
        while (isSpaceChar (c))
            c = read ();
        return (char) c;
    }

    public double nd()
    {
        int c = read ();
        while (isSpaceChar (c))
            c = read ();
        int sgn = 1;
        if (c == '-')
        {
            sgn = -1;
            c = read ();
        }
        double res = 0;
        while (!isSpaceChar (c) && c != '.')
        {
            if (c == 'e' || c == 'E')
            {
                return res * Math.pow (10, ni ());
            }
            if (c < '0' || c > '9')
            {
                throw new InputMismatchException ();
            }
            res *= 10;
            res += c - '0';
            c = read ();
        }
        if (c == '.')
        {
            c = read ();
            double m = 1;
            while (!isSpaceChar (c))
            {
                if (c == 'e' || c == 'E')
                {
                    return res * Math.pow (10, ni ());
                }
                if (c < '0' || c > '9')
                {
                    throw new InputMismatchException ();
                }
                m /= 10;
                res += (c - '0') * m;
                c = read ();
            }
        }
        return res * sgn;
    }

    public boolean eof()
    {
        int value;
        while (isSpaceChar (value = peek ()) && value != -1)
            read ();
        return value == -1;
    }

    public int[] iArr(int n)
    {
        int a[] = new int[n];
        for (int i = 0; i < n; i++)
        {
            a[i] = ni ();
        }
        return a;
    }
    public long[] lArr(int n)
    {
        long a[] = new long[n];
        for (int i = 0; i < n; i++)
        {
            a[i] = nl ();
        }
        return a;
    }
    public int[][] i2dArr(int n, int m)
    {
        int a[][] = new int[n][m];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                a[i][j] = ni ();
            }
        }
        return a;
    }
    public char[][] c2dArr(int n, int m)
    {
        char a[][] = new char[n][m];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                a[i][j] = nc ();
            }
        }
        return a;
    }

    public String next()
    {
        return ns ();
    }

    public SpaceCharFilter getFilter()
    {
        return filter;
    }

    public void setFilter(SpaceCharFilter filter)
    {
        this.filter = filter;
    }

    public interface SpaceCharFilter
    {
        public boolean isSpaceChar(int ch);
    }
}