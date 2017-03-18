//////////////////////////////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2017
//PROJECT:          P3
//FILE:             FileIterator.java
//
//TEAM:    #46 Paras
//Authors: Udhbhav Gupta, Collin Lacy
//
////////////////////////////////////////104 columns wide /////////////////////////////////////////////
import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {

	private Comparator<FileLine> cmp; //Comparator
	private int numItems; //number of items in the queue
	private FileLine[] pq; //stores FileLine objects

	/**
	 * Constructor for a new FileLinePriorityQueue
	 */
	public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;

		//root of heap at array[1], so add 1 to initial numItems
		pq = new FileLine[initialSize + 1];
	}

	/**
	 * Removes the minimum element from the Priority Queue, and returns it.
	 *
	 * @return the minimum element in the queue, according to the compareTo()
	 * method of FileLine.
	 * @throws PriorityQueueEmptyException if the priority queue has no elements
	 * in it
	 */
	public FileLine removeMin() throws PriorityQueueEmptyException {

		//if there are no items in the array, throw exception
		if (isEmpty()) 	throw new PriorityQueueEmptyException();

		int k = 1; //tracks index of current node 
		FileLine min = pq[1]; //original root of heap

		//replace value in the root with the value at end of the array
		pq[k] = pq[numItems];

		//remove last element
		pq[numItems] = null;
		numItems--;

		//heapify down
		while (k * 2 <= numItems) {
			
			int child = k * 2; //holds index of left child (+1 for right child)

			//compare values of children first and determine which is smaller
			if (child + 1 <= numItems)
				if ((cmp.compare(pq[child], pq[child + 1]) > 0)) 
					child++;

			//if the parent is greater than min child, swap
			if ((cmp.compare(pq[k], pq[child]) > 0)) {
				
				FileLine temp = pq[k]; //Temp var to allow swap
				pq[k] = pq[child];
				pq[child] = temp;
			}
			
			//set k to the new parent node
			k = child;
		}

		return min;
	}

	/**
	 * Inserts a FileLine into the queue, making sure to keep the shape and
	 * order properties intact.
	 *
	 * @param fl the FileLine to insert
	 * @throws PriorityQueueFullException if the priority queue is full.
	 */
	public void insert(FileLine fl) throws PriorityQueueFullException {
		
		//if the pq is full, throw exception
		if (numItems == pq.length - 1) throw new PriorityQueueFullException();

		int k = numItems + 1; //index of current node

		//insert FileLine fl to end of array
		//we can assume that there is room, no need to expand array
		pq[k] = fl;

		//increment numItems after inserting 
		numItems++;

		//heapify up
		while ((k / 2) >= 1) {

			//if child is less than parent, swap
			if ((cmp.compare(pq[k], pq[k / 2]) < 0)) {
				
				FileLine temp = pq[k]; //temp var to allow swap
				pq[k] = pq[k / 2];
				pq[k / 2] = temp;
			}
			
			//set current node index to the new parent index
			k = k / 2;
		}
	}

	/**
	 * Checks if the queue is empty.
	 * e.g. 
	 * 
	 * <pre>
	 * {@code
	 * m = new MinPriorityQueue(); 
	 * // m.isEmpty(): true
	 * m.insert(FileLine fl);
	 * // m.isEmpty(): false
	 * m.remove();
	 * // m.isEmpty(): true
	 * }
	 * </pre>
	 *
	 * @return true, if it is empty; false otherwise
	 */
	public boolean isEmpty() {
		return numItems == 0;
	}
}

