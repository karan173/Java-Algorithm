package dataStructures;

public class MinQueue<Item  extends Comparable<Item>>
{
    private MinStack<Item> oldS, newS;
    public MinQueue()
    {
        oldS = new MinStack<Item>();
        newS = new MinStack<Item>();
    }
    public void enqueue(Item E)
    {
        newS.push(E);
    }
    private void copyNewToOld() //private helper function
    {
        while(newS.size()!=0)
        {
            Item x = newS.pop();
            oldS.push(x);
        }
    }
    public Item dequeue()
    {
        if(oldS.size()==0)
            copyNewToOld();
        return oldS.pop();
    }
    public int size()
    {
        return oldS.size() + newS.size();
    }
    public Item peekMin() throws Exception
    {
        int s1 = newS.size();
        int s2= oldS.size();
        if(s1+s2==0)
            throw new Exception("q empty, invalid peek");
        if(s1==0)
            return oldS.peekMin();
        if(s2==0)
            return newS.peekMin();
        //else compare min of both stacks
        Item p1 = oldS.peekMin();
        Item p2 = newS.peekMin();
        if(p1.compareTo(p2)<0)
            return p1;
        return p2;
    }
    public Item peekMax() throws Exception
    {
        int s1 = newS.size();
        int s2= oldS.size();
        if(s1+s2==0)
            throw new Exception("q empty, invalid peek");
        if(s1==0)
            return oldS.peekMax();
        if(s2==0)
            return newS.peekMax();
        Item p1 = oldS.peekMax();
        Item p2 = newS.peekMax();
        if(p1.compareTo(p2)>0)
            return p1;
        return p2;
    }
    /*
    public static void main(String[] args) throws Exception //driver
    {
        MinQueue<Integer> mq = new MinQueue<Integer>();
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
                mq.enqueue(num);
            }
            if(ch==2)
            {
                System.out.println(mq.dequeue());
            }
            if(ch==3)
            {
                System.out.println("min- "+mq.peekMin()+" max- "+mq.peekMax());
            }
            if(ch==4)
            {
                System.out.println(mq.size());
            }
            if(ch==5)
                System.exit(0);
        }
    }
    */
}
