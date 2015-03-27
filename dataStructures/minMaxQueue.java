package dataStructures;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * Created by ksb on 04-09-2014.
 */
public class minMaxQueue
{
    Deque<Integer> dequeMin = new ArrayDeque<Integer> ();
    Deque<Integer> dequeMax = new ArrayDeque<Integer> ();
    Queue<Integer> queue = new ArrayDeque<Integer> ();
    public void enqueue(int x)
    {
        queue.add (x);
        addMinDeque (x);
        addMaxDeque(x);
    }

    private void addMinDeque(int x)
    {
        while (!dequeMin.isEmpty () && dequeMin.peekLast () > x)
        {
            dequeMin.pollLast ();
        }
        dequeMin.addLast (x);
    }

    private void addMaxDeque(int x)
    {
        while (!dequeMax.isEmpty () && dequeMax.peekLast () < x)
        {
            dequeMax.pollLast ();
        }
        dequeMax.addLast (x);
    }

    public int remove()
    {
        if(queue.isEmpty ())
        {
            throw new RuntimeException ();
        }
        int x = queue.remove ();
        if (dequeMin.peekFirst () == x)
        {
            dequeMin.pollFirst ();
        }
        if (dequeMax.peekFirst () == x)
        {
            dequeMax.pollFirst ();
        }
        return x;
    }
    public int getMin()
    {
        if (queue.isEmpty ())
        {
            throw new RuntimeException ();
        }
        return dequeMin.peekFirst ();
    }
    public int getMax()
    {
        if (queue.isEmpty ())
        {
            throw new RuntimeException ();
        }
        return dequeMax.peekFirst ();
    }
    public int size()
    {
        return queue.size ();
    }
    public boolean isEmpty()
    {
        return queue.isEmpty ();
    }
}