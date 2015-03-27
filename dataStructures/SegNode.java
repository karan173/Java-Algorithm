package dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 21/6/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */

public class SegNode
{
    //add state values here and static variables from main to assist update/initialise
    //split and merge never have null l and r

    /*pass child update flags to children and update children */
    void split(SegNode l, SegNode r){}
    /* done worry about update flags in merge */
    void merge(SegNode l, SegNode r){}
    /* change state of current node, and set flag for children to be modified*/
    void update(){}
    void initialiseLeaf(int range){}
    public static boolean hasUpdate; //or has split
}