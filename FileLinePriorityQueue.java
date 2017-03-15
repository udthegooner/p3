import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {

	//Comparator
	private Comparator<FileLine> cmp;
	//stores max size of pq
	private int maxSize;
	//stores FileLine objects from index 1 to n
	private FileLine[] pq;

	/**
	 * Constructor for a new FileLinePriorityQueue
	 * 
	 * 
	 */
	public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;

		//initial size based on how many files are ready to be read
		maxSize = initialSize;

		//root of heap at array[1], so add 1 to initial size
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
		if (isEmpty()) {
			throw new PriorityQueueEmptyException();
		}

		//holds index of root of heap 
		int k = 1;

		//save original root of heap value so that we can return it
		FileLine min = pq[k];

		//replace value in the root with the value at end of the array, i.e. rightmost leaf
		pq[k] = pq[maxSize + 1];

		//remove last element, accounting for heap starting at array[1]
		pq[maxSize + 1] = null;

		//decrement size after removing
		maxSize--;

		//heapify down
		while (k * 2 <= maxSize) {

			//holds index to swap, indicates left child (+1 for right child)
			int child = k * 2;

			//compare values of children first and determine which is smaller
			//to ensure that the new root is smaller than both children
			if ((cmp.compare(pq[child], pq[child + 1]) > 0)) {
				child++;
			}

			//if the new root is less than or equal to both its children, nothing to do
			if ((cmp.compare(pq[k], pq[child]) <= 0)) {
				break;
			}
			else {
				//Temp var to allow swap
				FileLine temp = pq[k];
				//swap parent value with child that is smaller
				pq[k] = pq[child];
				pq[child] = temp;
			}
			//set k to the new parent node and continue loop
			k = child;
		}

		//return original root of heap
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
		if (maxSize == pq.length - 1) {
			throw new PriorityQueueFullException();
		}

		//holds last index of array
		int child = maxSize + 1;

		//insert FileLine fl to end of array
		//we can assume that there is room, no need to expand array
		pq[child] = fl;

		//increment size after inserting 
		maxSize++;

		//heapify up
		while ((child / 2) >= 1) {

			//if child is less than parent
			if ((cmp.compare(pq[child], pq[child / 2]) < 0)) {
				//temp var to allow swap
				FileLine temp = pq[child];

				//swap child with parent
				pq[child] = pq[child / 2];
				pq[child / 2] = temp;
			}
			
			//set child index to the new parent index and continue loop
			child = child / 2;
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
		return maxSize == 0;
	}
}
