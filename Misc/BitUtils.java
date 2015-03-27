package Misc;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 25/8/13
 * Time: 2:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class BitUtils
{
    public static int clearBit(int n, int i)
    {
        return n & (~(1 << i));
    }

    public static int toggleBit(int n, int i)
    {
        return n ^ (1 << i);
    }

    public static long setBit(long n, int  i)
    {
        return n | (1 << i);
    }

    /*turns off the last set bit of a number */
    public static int lastOff(int n)
    {
        return n & (n - 1);
    }

    public static boolean isIthOff(long n, int i)
    {
        return ((n & (1 << i)) == 0);
    }
}
