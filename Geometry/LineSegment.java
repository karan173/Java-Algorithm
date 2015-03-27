package Geometry;

public class LineSegment // ax+by+c
{

    public MyPoint2D p1;
    public MyPoint2D p2;
    public double sqdLength;
    public double A, B, C; // ax + by + c
    public static double eps = 1e-7;
    public LineSegment(MyPoint2D p1, MyPoint2D p2)
    {
        this.p1 = p1;
        this.p2 = p2;
        sqdLength = MyPoint2D.distanceSquaredTo (p1, p2);
        A = p2.y - p1.y;
        B = p1.x - p2.x;
        C = A * p1.x + B * p1.y;
    }
    /*
    is P on the line segment GIVEN ITS ON THE LINE
     */
    public boolean isWithin(MyPoint2D p)
    {
        if(p.equals(p1) || p.equals(p2))
            return false;
        if(Math.min(p1.x, p2.x) <= p.x && p.x <= Math.max(p1.x, p2.x)){}
        else
            return false;
        if(Math.min(p1.y, p2.y) <= p.y && p.y <= Math.max(p1.y, p2.y))
            return true;
        return false;
    }


    public boolean intersect(LineSegment that)
    {
        double A1 = A, A2 = that.A, B1 = B, B2 = that.B, C1=C, C2= that.C;
        
        double det = A1*B2 - A2*B1;
                if(det == 0)
                {
                    //parallel lines
                    //corner case -> lines are overlapping
                    if (this.isOverlapping (that) )
                    {
                        return true;
                    }
                    return false;
                }else  //else intersects, now to check whether the intersection point lies within the 2 line segments
                {
                    double x = (B2*C1 - B1*C2)/det;
                    double y = (A1*C2 - A2*C1)/det;
                    MyPoint2D p= new MyPoint2D (x, y);
                    if(isWithin(p) && that.isWithin(p))
                        return true;
                    return false;
                }
    }

    public boolean onLine(MyPoint2D p)
    {
        return A * p.x + B * p.y + C == 0;
    }
    /*
    do the 2 lines overlap
    GIVEN they are parallel
     */
    public  boolean isOverlapping(LineSegment that)
    {
        //now they must be sub-part of extended line
        if (!this.onLine (that.p1))
        {
            return false;
        }
        //now they are parallel and have 0 distance bw them
        return that.isWithin (this.p1) || that.isWithin (this.p2)
                || this.isWithin (that.p1) || this.isWithin (that.p2);
    }

    /*
    returns sq of dist bw 2 parallell lines
     */
    private double getParallelSqdDistance(LineSegment that)
    {
        // formula is (c1-c2)/sqrt(a2+b2)
        double num = (C - that.C);
        num *= num;
        double den = A * that.A + B * that.B;
        return num / den;
    }
}