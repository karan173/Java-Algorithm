package dataStructures;
//class for implementing push pop and getMin in O(1)
import java.util.*;

public class MinStack<Item  extends Comparable<Item>>
{
    private Stack<Node> s;
    private class Node implements Comparable<Node>
    {
        Item val;
        Item min; //augmenting data
        Item max;
        public Node(Item a)
        {
            this.val=a;
        }
        @Override
        public int compareTo(Node that) //compare using items
        {
            return this.val.compareTo(that.val);
        }
    }
    public MinStack()
    {
        s = new Stack<Node>();
    }
    public void push(Item E)
    {
        Node top = new Node(E);
        if(s.size()==0)
        {
            top.max = E;
            top.min = E;
            s.push(top);
            return;
        }
        Node prev = s.peek();
        
        //now to set top's max and min
        if(top.compareTo(prev)<0)
        {
            top.min = top.val;
            top.max = prev.max;
        }
        else
        {
            top.min = prev.min;
            top.max = top.val;
        }
        s.push(top);
    }
    public Item peekMin()
    {
        return s.peek().min;
    }
    public Item peekMax()
    {
        return s.peek().max;
    }
    public Item pop()
    {
        return s.pop().val;
    }
    public int size()
    {
        return s.size();
    }
    
    /*
    public static void main(String[] args) throws IOException //driver
    {
        MinStack<Integer> ms = new MinStack<Integer>();
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(
                System.in)));
        
        while(true)
        {
            in.nextToken();
            int ch = (int) in.nval;
            if(ch==1) //push
            {
                in.nextToken();
                int num = (int) in.nval;
                ms.push(num);
            }
            if(ch==2)
            {
                System.out.println(ms.pop());
            }
            if(ch==3)
            {
                System.out.println("min- "+ms.peekMin()+" max- "+ms.peekMax());
            }
            if(ch==4)
            {
                System.out.println(ms.size());
            }
            if(ch==5)
                System.exit(0);
        }
    }
    */
}
