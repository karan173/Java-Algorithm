package dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 21/8/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge
{
    public int to, cost;
    public Edge next;

    public Edge(int t, int c, Edge head)
    {
        to = t;
        cost = c;
        next = head;
    }
}
