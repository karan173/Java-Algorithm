package dataStructures;
//if n is not much about 10^7
/*
 * then BIT can be used for operations like 
 * 1) insert i updateF(i,1)
 * 2) delete i updateF (i, -1)
 * 3) rank of i  getCF(i-1) //no of elems smaller than i
 * 4) num > i   getCF(infinity) - getCF(i)
 * 5) kth smallest element 
 * ORDERSET and DQUERY
 */
public class BIT
{
    //POINT UPDATE, RANGE QUERY
    //use 1 based index
    public int n;
    public int tree[]; // stores partial cf
    public int f[]; // stores individual freq, for fast access we store them

    public BIT(int n)
    {
        this.n = n;
        tree = new int[n + 1]; // 1 based index
        f = new int[n+1];
        f[0] = tree[0]=0;
    }

    // returns the number of last elements stored in index i
    // eg 22(in powers of 2) = 16+4+2 , therefore 22 stores sum of last 2
    // elements i.e [21..22]
    private int getLastElementsStored(int idx)
    {
        return idx & (-idx);
    }

    // //eg 22(in powers of 2) = 16+4+2
    // cf[22] = tree[22] + cf[20] = tree[22] + tree[20] + cf[16] = tree[22] +
    // tree[20] + tree[16] + cf[0]
    // = tree[22] + tree[20] + tree[16] + 0
    //therefore 4 iterations for 22, log2(22)=4
    //complexity= no of numbers obtained when expressed as power of 2
    //= no of 1's in binary notation of idx
    //= O(no of digits in binary notation of n)
    //= O(log base2 of n) think of 2^22, has 22 digits
    public int getCF(int idx) // get cumulative freq for index i
    {
        /*return cf till idx*/
        if(idx > n)
            idx = n;
        if(idx<1)
            return 0;
        int sum = 0;
        while (idx > 0)
        {
            sum += tree[idx];
            idx -= getLastElementsStored(idx);
        }
        return sum;
    }

    // aliter in recursive form
    // int getCF(int idx)
    // {
    // if(idx<0)
    //      return 0;
    // return tree[idx] + getCF(idx-getLastElementsStored(idx));
    // }

    //return freq of index i, if we want to save space return cf[idx] - cf[idx-1]
    public int getF(int idx)
    {
        return f[idx];
    }

    //note we have to update all nodes which depend on idx
    //eq if idx=5
    //update tree(5) 5=4+1 has info for [5.5]
    //update tree[6] 6=4+2 therefore has info for [5.6]
    //update tree[8] 8=8 has info for [8..8]
    //update tree[16] 16=16 has info for [16..16] and so on till we reach n
    //O(log n) 
    public void updateF(int idx, int incr)
    {
        if(idx < 1)
            throw new IllegalArgumentException();
        if(idx>n)
            return;
        f[idx]+=incr;
        while(idx<=n)
        {
            tree[idx]+=incr;
            idx += getLastElementsStored(idx);
        }
    }

    // Scaling the entire tree by a constant factor
    public void scale(int c)
    {
        for (int i = 1 ; i <= n ; i++)
            tree[i] = tree[i] / c;
    }

    //Find index with given cumulative frequency -> binary search getIndexOfCF
    //YET TO IMPLEMENT
    public static void main(String[] args)
    {
        BIT a = new BIT(5);
        a.updateF(3, 1);
        a.updateF(5, 1);
        System.out.println(a.getCF(6)-a.getCF(3));
    }
}


