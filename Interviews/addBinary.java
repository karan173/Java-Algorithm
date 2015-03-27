package Interviews;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 27/7/13
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class addBinary
{
    public static int add(int a, int b)
    {
        int ans = 0;
        int carry = 0;
        //i<32 is necessary for negative numbers
        for (int i = 0; (a!=0  || b!=0 || carry!=0) && i<32; a>>=1, b >>=1, i++)
        {
            //sum = a^b^c
            //carry = ab + (a^b)c
            int sum = (a^b^carry)&1;
            carry = ( (a&b) | ((a^b)&carry) )&1; //both | and ^ are same here
            ans |= sum << i;
        }
        return ans;
    }
    public static int twoComp(int x)
    {
        return add (~x, 1);
    }
    public static int subtract(int a, int b)
    {
        return add (a, twoComp (b));
    }

    public static int add2(int a, int b)
    {
        if (b == 0)
        {
            return a;
        }
        int sum = a^b;
        int carry = (a&b)<<1;
        return add2 (sum, carry);
    }
    public static void main(String[] args)
    {
//        for (int i = 0; i < 10; i++)
//        {
//            for (int j = 0; j < 10; j++)
//            {
//                System.out.println (i + " " + j + " = "+add (i, j));
//            }
//        }
        System.out.println (subtract (-100, 64));

    }
}
