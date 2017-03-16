import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file. 
 *
 */
public class Reducer {
    // list of files for stocking the PQ
    private List<FileIterator> fileList;
    private String type,dirName,outFile;

    public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		Reducer r = new Reducer(type, dirName, outFile);
		
		try{
			r.run();
		} catch (PriorityQueueFullException e){
			System.out.println("Q full Error");
			System.exit(1);
		} catch (PriorityQueueEmptyException e){
			System.out.println("Q empty Error");
			System.exit(1);
		} catch (FileNotFoundException e){
			System.out.println("Out File Error");
			System.exit(1);
		}
	
    }

	/**
	 * Constructs a new instance of Reducer with the given type (a string indicating which type of data is being merged),
	 * the directory which contains the files to be merged, and the name of the output file.
	 */
    public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
    }

	/**
	 * Carries out the file merging algorithm described in the assignment description. 
	 * @throws PriorityQueueFullException 
	 * @throws FileNotFoundException 
	 * @throws PriorityQueueEmptyException 
	 */
    public void run() throws PriorityQueueFullException, FileNotFoundException, PriorityQueueEmptyException {
    	
		File dir = new File(dirName); //directory which holds files
		File[] files = dir.listFiles(); //array which holds files in the directory
		Arrays.sort(files);

		Record r = null;

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();
		
		//filling fileList
		for(int i = 0; i < files.length; i++) {
			File f = files[i];
			if(f.isFile() && f.getName().endsWith(".txt")) {
				//fileList.add(fif.makeFileIterator(f.getAbsolutePath()));
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
			}
		}
		
		//determine type for the reducer and make an appropriate record instance
		switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}
		
		//Priority queue which holds 1 fileLine from each file
		FileLinePriorityQueue queue= new FileLinePriorityQueue(fileList.size(), r.getComparator());
		
		//inserting a fileLine from each file into the queue
		for (FileIterator x: fileList)
				queue.insert(x.next());

		
		FileLine lastLine = null; //the last line added in r
		
		//PrintWriter to write to the output file
		PrintWriter writer = null; 
		writer = new PrintWriter(outFile);

		
		//loop that runs until all lines from all files are correctly sorted into the right record
		//via the priority queue and then into the correct output line 
		while (!queue.isEmpty()){
			
			FileLine curr = queue.removeMin(); //current file line to be added to r
			
			//if r is empty or keys of r and curr match, merge curr with r
			if (lastLine == null || r.getComparator().compare(curr, lastLine) == 0)
				r.join(curr);
			
			else { //print r to output file, clear r, and merge curr with r
				writer.println(r.toString());
				r.clear();
				r.join(curr);
			}
			
			//setting last line to curr for next loop iteration
			lastLine = curr; 
			
			//replacing curr in the queue with the next line from the same file
			//as curr if that file is not empty
			if (curr.getFileIterator().hasNext())
					queue.insert(curr.getFileIterator().next());

		}
		
		writer.println(r.toString()); //writing last record to output file
		r.clear();
		writer.close(); //closing PrintWriter
    }
}
