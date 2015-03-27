package dataStructures;

import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;



public class IndexedPQ<E>
{
    /**
     * class must implement the equals and hashcode methods individually
     * also the equals method must not solely depend on the key
     * else no duplicate keys
     */
    //1 based indexing
    //NOTE we dont insert duplicate objects since which pos will we store in indexMap?
//    no duplicate objects means (1) and(1) can both be inserted simultaneously
//    However objects like (1,"mary" ) and (1,"christie") can be inserted together

    //note though KEYS(on which comparisom is based) can be duplicates :)
    //we store indexes in a hashmap
    int maxSize;
    boolean isMinPQ;
    E pq[]; //the heap array, generic array, unsafe but no better alternative
    int ctr = 1; //current size + 1

    HashMap<E, Integer> indexMap;
    Comparator<E> comparator;
    public IndexedPQ(int maxSize, boolean isMinPQ)
    {
        this.maxSize = maxSize;
        pq = (E[])new Object[maxSize+1]; //we use 1 based indexing , unsafe cast
        indexMap = new HashMap<E, Integer>();
        this.isMinPQ = isMinPQ;
    }
    public IndexedPQ(int maxSize, boolean isMinPQ, Comparator<E> cmp)
    {
        this(maxSize, isMinPQ);  //call other constructor
        comparator = cmp;
    }

    /**
     *  clears the PQ
     */
    public void clear()
    {
        ctr = 1;
        indexMap.clear();
    }
    public void insert(E item)
    {
        if(contains(item))
            return;
        if(ctr == maxSize)
            throw new OutOfMemoryError("Priority Queue Overflow");
        pq[ctr++] =  item;
        indexMap.put(item, ctr-1);
        swimUp(ctr - 1);
    }

    public void swimUp(int i)
    {
        //compare with parent, swap if parent greater
        while ((i > 1) && greater(pq[i/2], pq[i]))
        {
            swap(i,i/2);
            i/=2;
        }
    }

    /**
     * O(logn)
     * @return the min element
     * @throws NoSuchElementException
     */
    public E deleteRoot() throws NoSuchElementException
    {
        if(size() == 0)
            throw new NoSuchElementException("Priority queue underflow");
        E retVal = pq[1];
        swap(1, ctr - 1);
        indexMap.remove(pq[ctr-1]);
        pq[--ctr] = null;
        swimDown(1);
        return retVal;
    }

    /**
     * O(logn), arbitrary element deletion
     * @param item reference to item to be deleted, note class must implement the equals and hashcode methods individually
     * @throws NoSuchElementException
     */
    public void delete(E item) throws NoSuchElementException  //arbitrary O(logn) deletion
    {
        if(size() == 0)
            throw new NoSuchElementException("Priority queue underflow");
        Integer idx = indexMap.get(item);
        if(idx == null)
            throw new NoSuchElementException("element not in PQ");
        swap(idx, ctr-1); //swap with last elem
        indexMap.remove(pq[ctr-1]);
        pq[--ctr] = null;
        swimDown(idx);
    }

    public void swimDown(int i)
    {
        while((2*i)<ctr) //has at least 1 child
        {
            if(2*i+1 == ctr) //only 1 child
            {
                if(greater(pq[i], pq[2*i])) //compare with only child
                    swap(i,2*i);
                return;
            }
            //compare with 2 children
            //if lesser than 2 children, terminate, else swap with smaller child
            if(!greater(pq[i], pq[2*i]) && !greater(pq[i], pq[2*i+1]))
                return;
            if(greater(pq[2*i], pq[2*i+1]))
            {
                //swap with 2*i+1
                swap(i,2*i+1);
                i = 2*i+1;
            }
            else
            {
                swap(i,2*i);
                i = 2*i;
            }
        }
    }

    private boolean greater(E x1, E x2)
    {   int cmp = -1;

        if(comparator == null)   //use Comparable
        {
            Comparable<? super E> X1 = (Comparable<? super E>) x1;
            cmp = X1.compareTo(x2);
        }
        else
            cmp = comparator.compare(x1, x2);
        if(!isMinPQ)
            cmp*=-1;
        if(cmp > 0)
            return true;
        else
            return false;
    }

    /**
     * O(1)
     * does pq contains item?
     */
    public boolean contains(E item)
    {
        return indexMap.containsKey(item);
    }
    private void swap(int i, int j)
    {
        E temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        //update locations in indexMap
        indexMap.put(pq[i], i);
        indexMap.put(pq[j], j);
    }
    public int size()
    {
        return ctr-1;
    }
    public boolean isEmpty()
    {
        return ctr==1;
    }

    /**
     * prints the pq in order, for debugging
     */
    public void print()
    {
        System.out.println("--Printing PQ");
        for(int i = 1; i<ctr; i++)
        {
            System.out.println(pq[i] + " loc - "+indexMap.get(pq[i]));
        }
        System.out.println("");
    }

    public static void main(String[] args)
    {
        //some testing
        IndexedPQ<Integer> pq = new IndexedPQ<Integer>(15, true);
        pq.insert(5);
        pq.insert(3);
        pq.insert(4);
        pq.insert(7);
        pq.insert(1);
        pq.insert(7);
        pq.insert(4);
        pq.print();     //1 3 4 5 7
        pq.deleteRoot();
        pq.deleteRoot();
        pq.print();                 //4 5 7
        pq.insert(3);
        pq.insert(8);
        pq.print();      //3 4 5 7 8
        pq.delete(3);
        pq.print();     //4 5 7 8
        pq.delete(7);
        pq.insert(7);
        pq.print(); //4 5 8 7
        pq.clear();
        //--->
        pq.insert(1);
        pq.insert(2);
        pq.delete(1);
        System.out.println(pq.contains(1));
        pq.insert(1);
        pq.print(); // 1 2
        //--->
        PriorityQueue<Integer> h = new PriorityQueue<Integer>();
        h.add(3);
        h.add(3);
        System.out.println(pq.size());
        //in my size would be 1

        IndexedPQ<Integer> pqMax = new IndexedPQ<Integer>(15, false);
        pqMax.insert(3);
        pqMax.insert(7);
        pqMax.insert(3);
        pqMax.insert(1);
        pqMax.insert(8);
        pqMax.print(); //8  7 1 3
        pqMax.delete(7);
        pqMax.print(); //8 3 1
    }
}
