package dataStructures;

import java.util.ArrayDeque;
import java.util.Arrays;

/*
 * find minimum in each sliding minimum
 *seen from programmingpraxis
 */
public class SlidingMinMax
{
    private static class Pair
    {
        int value;
        int deathIndex;
        public Pair(int v, int i)
        {
            value=v;
            deathIndex=i;
        }
    }
    public static int[]  getSlidingMinimas(int a[], int k) //k is window size, O(n) since each element added/removed only once
    {
        int n = a.length;
        int []minArr = new int[n-k+1];
        int ctrMinArr=0;
        ArrayDeque<Pair> deq = new ArrayDeque<Pair>(k); //max no of elements in deq at a given time = k, for ascending order window
        for(int i=0; i<n; i++)
        {
            int elem = a[i];
            //1. we remove all elements >= a[i] since they may never be a minimum of a window now
            //size the list will be sorted we just have to peek from rear
            for(Pair rear = deq.peekLast(); rear!=null && rear.value>=elem; rear = deq.peekLast())
            {
                deq.removeLast();
            }
            
            //2.see if the element on front of deq, the last windows's minimum is out of range
            Pair first = deq.peekFirst();
            if(first != null && i>=first.deathIndex)
            {
                deq.removeFirst();
            }
            
            //3.insert element at rear, since now deq contains elements only < elem
            deq.addLast(new Pair(elem, i+k)); //death index of current element is i+k
            if(i>=k-1) //in a window
                minArr[ctrMinArr++] = deq.peekFirst().value;
        }
        return minArr;
    }
    
    public static int[]  getSlidingMaximas(int a[], int k) //k is window size
    {
        int n = a.length;
        int []maxArr = new int[n-k+1];
        int ctrMaxArr=0;
        ArrayDeque<Pair> deq = new ArrayDeque<Pair>(k); 
        for(int i=0; i<n; i++)
        {
            int elem = a[i];
            //1. we remove all elements <= a[i] 
            for(Pair rear = deq.peekLast(); rear!=null && rear.value<=elem; rear = deq.peekLast())
            {
                deq.removeLast();
            }
            
            //2.see if the element on front of deq, the last windows's minimum is out of range
            Pair first = deq.peekFirst();
            if(first != null && i>=first.deathIndex)
            {
                deq.removeFirst();
            }
            
            //3.insert element at rear
            deq.addLast(new Pair(elem, i+k)); //death index of current element is i+k
            if(i>=k-1) //in a window
                maxArr[ctrMaxArr++] = deq.peekFirst().value;
        }
        return maxArr;
    }
    
    public static void main(String[] args) //driver
    {
        int a[] = {4, 3, 2, 1, 5, 7, 6, 8, 9};
        System.out.println(Arrays.toString(getSlidingMinimas(a, 3)));
        System.out.println(Arrays.toString(getSlidingMaximas(a, 3)));
    }
}
