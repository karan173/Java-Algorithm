package Interviews;

import java.util.Arrays;

/**
 * Created by ksb on 06-09-2014.
 */
class LinkNode
{
    LinkNode next;
    int val;
    LinkNode(int val)
    {
        this.val = val;
    }
}
class LinkList
{
    LinkNode head, tail;
    public void append(LinkNode ptr)
    {
        ptr.next = null;
        if (head == null)
        {
            head = tail = ptr;
        }
        else
        {
            tail.next = ptr;
            tail = ptr;
        }
    }

    public void append(LinkList high)
    {
        if (head == null)
        {
            head = high.head;
            tail = high.tail;
        }
        else if (high.head != null)
        {
            tail.next = high.head;
            tail = high.tail;
        }
    }

    public static LinkList getList(int[] a)
    {
        LinkList list = new LinkList ();
        for (int x : a)
        {
            list.append (new LinkNode (x));
        }
        return list;
    }

    public void printList()
    {
        LinkNode ptr = head;
        while (ptr != null)
        {
            System.out.print (ptr.val + " ");
            ptr = ptr.next;
        }
        System.out.println ();
    }
}
public class quickSortLinked
{
    public static LinkList quicksort(LinkList list)
    {
        if(list.head == null || list.head == list.tail)
        {
            return list;
        }
        //partition Now
        //choose headAsPivot
        LinkNode pivot = list.head;
        LinkNode ptr = list.head.next;
        LinkList low = new LinkList ();
        LinkList high = new LinkList ();
        while (ptr != null)
        {
            LinkNode temp = ptr.next;
            ptr.next = null;
            if(ptr.val <= pivot.val)
            {
                low.append (ptr);
            }
            else
            {
                high.append (ptr);
            }
            ptr = temp;
        }
        low = quicksort (low);
        high = quicksort (high);
        low.append (list.head);
        low.append (high);
        return low;
    }
    public static void main(String[] args)
    {
        int a[] = {9,8,7,5,3};
        LinkList list = LinkList.getList (a);
        list = quicksort (list);
        list.printList ();
        Arrays.sort (a);
        System.out.println (Arrays.toString (a));
    }
}
