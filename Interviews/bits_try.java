package Interviews;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 25/7/13
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class bits_try
{
    int mask1(int i,int j)
    {
        return ~( ((1<<(j-i+1)) - 1) << i );
    }


    int mask2(int i,int j)
    {
        int l=-1, r=-1;
        l = ( (~0) << (j+1))  ;
        r = ((1<<i)-1)         ;
        return l|r              ;

    }

    int getFirstOneBit(int num)
    {
        assert (num > 0);
        int i = 0;
        while ((num & (1<<i)) == 0)
        {
            i++;
        }
        return i;
    }

    int getNext(int num)
    {
        int i = getFirstOneBit (num);
        if (i == -1)
        {
            return -1;
        }
        num -= (1 << i);
//        System.out.println (Integer.toBinaryString (num));
        i++;
//        System.out.println ("--"+(num&(1<<i)));
        while (((num & (1 << i)) != 0))
        {
//            System.out.println ("--" + Integer.toBinaryString (num));
            if (i == 31)
            {
                return -1;
            }
            i++;
        }
//        System.out.println ("--"+i);
        //current i is 0
        num += (1 << i);
        return num;
    }
    int swapOddEven1(int x)
    {
        return (((x & 0xaaaaaaaa) >> 1) | ((x & 0x55555555) << 1));
    }

    long swapOddEven2(int x)
    {
        long xx = x;
        long l = xx<<1;
        long r = xx>>1;
        long ans = 0;
        for (long num = 1,i = 0; num <= l; num = num << 1,i++)
        {
            if (i % 2 == 0)
            {
                ans += r & num;
            }
            else
            {
                ans += l & num;
            }
        }
        return ans;
    }
    public static void main(String args[])
    {
        bits_try t = new bits_try ();
//         System.out.println (Integer.toBinaryString (t.mask1 (2,6)));
//        System.out.println (Integer.toBinaryString (t.mask2 (2, 6)));
//        System.out.println (Integer.toBinaryString (-8));
        System.out.println (Integer.toBinaryString (Integer.MAX_VALUE));
//        System.out.println (Integer.toBinaryString (t.getNext (13948)));
        System.out.println (Long.toBinaryString (t.swapOddEven2 (Integer.MAX_VALUE)));
        System.out.println (Integer.toBinaryString (t.swapOddEven1 (Integer.MAX_VALUE)));
    }
}
