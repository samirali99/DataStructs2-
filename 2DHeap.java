// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    
    /**
     * Construct the binary heap.
     */
    public DHeap( )
    {
        this( DEFAULT_CAPACITY );
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public DHeap( int capacity )
    {
        currentSize = 0;
        array = (AnyType[]) new Integer[ capacity + 1 ];
    }
    
    /**
     * Construct the binary heap given an array of items.
     */
    @SuppressWarnings
    // Odin did not like the AnyType class
    public DHeap( AnyType [ ] items )
    {
        currentSize = items.length;
        array = (AnyType[]) new Integer[ ( currentSize + 2 ) * 11 / 10 ];
        
        int i = 1;
        for( AnyType item : items )
            array[ i++ ] = item;
        buildHeap( );
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    @SuppressWarnings
    public void insert( AnyType x )
    {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

            // Percolate up
        int hole = ++currentSize;
        //point_to_parent(hole, heapDepth);
        for( ; hole > 1 && x.compareTo( (AnyType) point_to_parent( (int) array[hole], heapDepth)) < 0; hole /= 2 )
            array[ hole ] = array[ hole / 2 ];
        array[ hole ] = x;
    }


    public int point_to_parent(int index, int depth)
    {
        if (depth < 3)
        {
            return ( (int) Math.ceil((index - 1) / (depth)) + 1);
        }
        else
        {
            return (int) Math.ceil((index - 1) / (depth));
        }
    }

    public int point_to_child(int index, int depth)
    {
        if (depth < 3)
        {
            return (((depth * index) - (depth / 2)) + 1);
        }
        else
        {
            return ((depth * index) - (depth / 2));
        }
    }


    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Integer[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];        
    }
    
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap()
    {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

    public void print( )
    {
        for( int i = 0; i < currentSize; i++ )
            System.out.printf("%d ",  array[i+1]);
        System.out.println();

    }

    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array
    private static int heapDepth; // The heap depth

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
        int child;
        AnyType tmp = array[ hole ];

        for( ; hole * 2 <= currentSize; hole = child )
        {
            child = point_to_child(hole, heapDepth);

            for (int i = 1; i < heapDepth; i++)
            {
                int temp = (int) array[child];
                if ((array[child + 1]).compareto(temp) < 0)
                {
                    temp = (int) array[child + 1];
                }
                child++;
            }
            
            /*if( child != currentSize &&
                    array[ child + 1 ].compareTo( (AnyType) point_to_child( (int) array[ child ], heapDepth )) < 0 )
                child++;
                */

            if( (array[ child ]).compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];
            else
                break;
        }
        array[ hole ] = tmp;
    }

        // Test program
    public static void main( String [ ] args )
    {
        DHeap<Integer> h = new DHeap<Integer>( );

        Scanner in = new Scanner(System.in);
        //ArrayList<Int> heapList = new ArrayList<Int>();
        //int [] heaplist;

        System.out.println("Enter heap elements: ");
        while (in.hasNextInt())
        {
            
            int input = in.nextInt();
            h.insert(input);
            //heapList.add(input);
        }

        //Int[] intArray = new Int[heapList.size()];
        //intArray = heapList.toArray(intArray);

        System.out.println("Enter d: ");
        heapDepth = in.nextInt();

        System.out.println("Output: Heap (d=" + heapDepth + " : ");
        h.print();
        System.out.println("I have" + 5 + "hands");


        System.out.println("Press 1) for insert, 2) for deleteMin, 3) for buildHeap with new d value, 4) to quit");
        System.out.println("Enter choice: ");
        int choice = in.nextInt();

        while (choice != 4)
        {
            if (choice == 1) //insert
            {
                System.out.println("Enter element to insert: ");
                int add = in.nextInt();
                h.insert(add);
                System.out.println("Output: Heap (d=" + heapDepth + " : ");
                h.print();
            }
            else if (choice == 2) //deleteMin
            {
                h.deleteMin();
                System.out.println("Output: Heap (d=" + heapDepth + " : ");
                h.print();
            }
            else if (choice == 3) //buildHeap
            {
                System.out.println("Enter d: ");
                heapDepth = in.nextInt();
                h.buildHeap();
                System.out.println("Output: Heap (d=" + heapDepth + " : ");
                h.print();
            }
            System.out.println();
            System.out.println("Press 1) for insert, 2) for deleteMin, 3) for buildHeap with new d value, 4) to quit");
            System.out.println("Enter choice: ");
            choice = in.nextInt();
        }
        System.out.println("Program Terminated");
        in.close();

        /*for( int i = 10; i > 0; i--) {
            h.insert( i );
            h.print();
        }

        System.out.println("\nPerform deleteMin 10 times:");

        for( int i = 0; i < 10; i++) {
               h.deleteMin();
               h.print();
        }
        */
    }
}
