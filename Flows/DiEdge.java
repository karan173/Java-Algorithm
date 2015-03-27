package Flows;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 23/11/13
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiEdge
{
    public int u, v;
    public long capacity;
    public long flow;
    public DiEdge(int u, int v, long capacity)
    {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
        flow = 0;
        assert capacity >= 0;
    }

    public int other(int x)
    {
        if (x == u)
        {
            return v;
        }
        if (x == v)
        {
            return u;
        }
        throw new IllegalArgumentException ();
    }

    public long getResidualCapacityTo(int x)
    {
        if (x == u)
        {
            return flow;
        }
        if (x == v)
        {
            return capacity - flow;
        }
        throw new IllegalArgumentException ();
    }

    public void addFlowTo(int x, long delFlow)
    {
        if (x == u)
        {
            flow -= delFlow;
            assert flow >= 0;
        }
        else if (x == v)
        {
            flow += delFlow;
            assert flow <= capacity;
        }
        else
        {
            throw new IllegalArgumentException ();
        }
    }

    @Override
    public String toString()
    {
        return "DiEdge{" +
                "u=" + u +
                ", v=" + v +
                ", capacity=" + capacity +
                ", flow=" + flow +
                '}';
    }
}