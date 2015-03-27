package Geometry;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Comparator;

public class MyPoint2D
{
    static double eps = 1e-8;
    public double x, y;
    public MyPoint2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    // return Euclidean distance between this point and that point
    public static double distanceTo(MyPoint2D a, MyPoint2D b)
    {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // return square of Euclidean distance between this point and that point
    public static double distanceSquaredTo(MyPoint2D a, MyPoint2D b)
    {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return dx * dx + dy * dy;
    }


    public static MyPoint2D subtract(MyPoint2D a, MyPoint2D o)
    {
        return new MyPoint2D (a.x - o.x, a.y - o.y);
    }

    //is p1->p2->p3 a clockwise turn?
    // returns false if p2 on line p1p3
    public static boolean isClockwiseTurn(MyPoint2D p1, MyPoint2D p2, MyPoint2D p3)
    {
        MyPoint2D v1 = MyPoint2D.subtract (p2, p1);
        MyPoint2D v2 = MyPoint2D.subtract (p3, p2);
        return MyPoint2D.cross (v1, v2) < 0;
    }
    /*
    returns z component of cross product of vectors p1,p2
     */
    public static double cross(MyPoint2D p1, MyPoint2D p2)
    {
        return p1.x * p2.y - p1.y * p2.x;
    }

    /*
    dot product of vectors p1,p2
     */
    public static double dot(MyPoint2D p1, MyPoint2D p2)
    {
        return p1.x * p2.x + p1.y * p2.y;
    }

    // does this point equal y?
    public boolean equals(Object other)
    {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        MyPoint2D that = (MyPoint2D) other;
        return this.x == that.x && this.y == that.y;
    }

    // convert to string
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    /*
    returns hull in clockwise order, doesnt return hull cycle rather chain
    Andrews monotone chain algorithm
    set returned is minimal
    Points on edges are not included
    note ordering of points is changes
     */
    public static MyPoint2D[] getConvexHull(MyPoint2D[] p)
    {
        Arrays.sort (p, new Comparator<MyPoint2D> ()
        {
            @Override
            public int compare(MyPoint2D o1, MyPoint2D o2)
            {
                int cmp = Double.compare (o1.x, o2.x);
                if (cmp == 0)
                {
                    cmp = Double.compare (o1.y, o2.y);
                }
                return cmp;
            }
        });
        int n = p.length;
        //build upper hull
        MyPoint2D upper[] = new MyPoint2D[n];
        int upperCtr = 0;
        for (int i = 0; i < n; i++)
        {
            //now we have to add p[i] to upper hull
            //first ensure that it causes a clockwise turn(to ensure convexity)
            while (upperCtr >= 2)
            {
                if(isClockwiseTurn (upper[upperCtr - 2], upper[upperCtr - 1], p[i]))
                {
                    break;
                }
                //else remove last point and check again
                upperCtr--;
            }
            upper[upperCtr++] = p[i];
        }

        //build lower hull
        MyPoint2D lower[] = new MyPoint2D[n];
        int lowerCtr = 0;
        for (int i = n - 1; i >= 0; i--)
        {
            //now we have to add p[i] to lower hull
            //first ensure that it causes a clockwise turn(to ensure convexity)
            while (lowerCtr >= 2)
            {
                if (isClockwiseTurn (lower[lowerCtr - 2], lower[lowerCtr - 1], p[i]))
                {
                    break;
                }
                //else remove last point and check again
                lowerCtr--;
            }
            lower[lowerCtr++] = p[i];
        }

        //last point in array p was(see xorder)
        // point with max x and max y, which must be in hull
        //so in both upper and lower

        //hence
        //first of upper = last of lower
        //last of upper = first of lower

        //now join the 2 hulls
        MyPoint2D[] convexHull = new MyPoint2D[upperCtr + lowerCtr - 2];  //2 common
        int hullCtr = 0;
        for (int i = 0; i < upperCtr; i++)
        {
            convexHull[hullCtr++] = upper[i];
        }
        for (int i = 1; i < lowerCtr-1; i++)
        {
            convexHull[hullCtr++] = lower[i];
        }


//        System.out.println (Arrays.toString (convexHull));
//        System.out.println (Arrays.toString (upper));
//        System.out.println (Arrays.toString (lower));
        return convexHull;
    }

    /*
    assumes points are given in order of quad i.e in order ABCD
    0 - does not form a proper QUAD -> self intersection or adjacent lines parallel
    1 - square
    2 - rhombus
    3 - rect
    4 - parallelogram
    5 - quadrilateral
     */
    public static int getQuadType(MyPoint2D p[])
    {
        if (p.length != 4)
        {
            throw new IllegalArgumentException ();
        }
        LineSegment l[] = new LineSegment[4];
        for (int i = 0; i < 4; i++)
        {
            l[i] = new LineSegment (p[i], p[(i + 1) % 4]);
        }
        boolean oppEqual = (equal (l[0].sqdLength, l[2].sqdLength) && equal (l[1].sqdLength, l[3].sqdLength));
        boolean adjEqual = true;
        for (int i = 0; i < 4; i++)
        {
            if (!equal (l[i].sqdLength, l[(i + 1) % 4].sqdLength))
            {
                adjEqual = false;
                break;
            }
        }
        boolean all90 = true;
        for (int i = 0; i < 4; i++)
        {

            if (!equal (0.0, MyPoint2D.dot (MyPoint2D.subtract (p[(i + 1) % 4], p[i]), MyPoint2D.subtract (p[(i + 2) % 4], p[(i + 1) % 4]))))
            {
                all90 = false;
                break;
            }
        }
        boolean cross0 = false;
        //if any 2 adjacent sides are || i.e collinear -> then not a quad.
        for (int i = 0; i < 4; i++)
        {
            if (equal (0.0, MyPoint2D.cross (MyPoint2D.subtract (p[(i + 1) % 4], p[i]), MyPoint2D.subtract (p[(i + 2) % 4], p[(i + 1) % 4]))))
            {
                cross0 = true;
                break;
            }
        }
        if (cross0)
        {
            return 0;
        }
        if (l[0].intersect (l[2]) || l[1].intersect (l[3]))
        {
            return 0;
        }

        if (all90 && adjEqual)   //square
        {
            return 1;
        }
        if (adjEqual)      //rhombus
        {
            return 2;
        }
        if (oppEqual && all90)     //rect
        {
            return 3;
        }
        if (oppEqual)   //parallelogram
        {
            return 4;
        }
        return 5;     //quad
    }

    public static boolean equal(double x, double y)
    {
        return Math.abs (x - y) < eps;
    }

    //area is calculated as 1/2 * cross(p2-p1, p3-p1)  --> returns signed area
    public static double triangleArea(MyPoint2D p1, MyPoint2D p2, MyPoint2D p3)
    {
        MyPoint2D a = new MyPoint2D (p2.x - p1.x, p2.y - p1.y);
        MyPoint2D b = new MyPoint2D (p3.x - p1.x, p3.y - p1.y);
        return 0.5 * cross (a, b);
    }

    /*
    1 - in
    2 - out
    3 - on boundary
    see http://geomalgorithms.com/a03-_inclusion.html
     */
    public static int isInPoly(MyPoint2D poly[], MyPoint2D p)
    {
        int n = poly.length;
        int cnt = 0;
        for (int i = 0; i < n; i++)
        {
            MyPoint2D a = poly[i];
            MyPoint2D b = poly[(i + 1) % n];
            if (equal (0.0, ptSegDistanceSqr (a, b, p)))   //on edge
            {
                return 3;
            }
            //exclude ending for upward
            boolean isUpward = (p.y >= a.y && b.y > p.y);
            //exclude starting for downward
            boolean isDownward = (a.y > p.y && p.y >= b.y);
            if (isUpward || isDownward)
            {
                double slope = (b.y - a.y) / (b.x - a.x);
                //find intersection with horizontal ray passing through p
                double xIntersect = (p.y - a.y) / slope + a.x;
                if (xIntersect > p.x)
                {
                    cnt++;
                }
            }
        }
        if (cnt % 2 == 0)
        {
            return 2;
        }
        return 1;
    }

    public static MyPoint2D midPt(MyPoint2D a, MyPoint2D b)
    {
        return new MyPoint2D ((a.x + b.x) / 2.0, (a.y + b.y) / 2.0);
    }
    public static double magnSqr(MyPoint2D p)
    {
        return p.x * p.x + p.y * p.y;
    }
    public static MyPoint2D multiply(MyPoint2D p, double s)
    {
        return new MyPoint2D (p.x * s, p.y * s);
    }
    //see wiki
    public static double ptLineDistanceSqr(MyPoint2D p1, MyPoint2D p2, MyPoint2D p)
    {
        MyPoint2D v = subtract (p2, p);
        MyPoint2D D = unit (subtract (p1, p2));
        MyPoint2D vAlongD = multiply (D, dot (v, D));
        MyPoint2D vPerpendicularD = subtract (v, vAlongD);
        return magnSqr (vPerpendicularD);
    }

    private static MyPoint2D unit(MyPoint2D p)
    {
        double mag = Math.sqrt (magnSqr (p));
        return new MyPoint2D (p.x / mag, p.y / mag);
    }

    //line is p1,p2
    public static double ptSegDistanceSqr(MyPoint2D p1, MyPoint2D p2, MyPoint2D p3)
    {
        MyPoint2D D = subtract (p1, p2);
        MyPoint2D a = subtract (p3, p2);
        MyPoint2D b = subtract (p3, p1);
        if (dot (a, D) <= 0) //p1,p2,p3 have obtuse angle
        {
            return magnSqr (a);
        }
        if (dot (b, D) >= 0)
        {
            return magnSqr (b);
        }
        //closest point is inside line segment
        return ptLineDistanceSqr (p1, p2, p3);
    }

    /*
    area of a simple polygon by triangulation
    can be convex/concave but not self-intersecting
     */
    public static double polyArea(MyPoint2D[] poly)
    {
        int n = poly.length;
        if (n <= 2)
        {
            throw new IllegalArgumentException ();
        }
        double area = 0; //triangulating wrt point 0
        for (int i = 1; i < n - 1; i++)
        {
            area += MyPoint2D.triangleArea (poly[0], poly[i], poly[(i + 1)]);
        }
        return area;
    }

    public static boolean isClockwise(MyPoint2D[] poly)
    {
        return polyArea (poly) < 0;
    }






}

