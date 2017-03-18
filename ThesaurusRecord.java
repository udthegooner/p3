import java.util.ArrayList;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 */

public class ThesaurusRecord extends Record{
	// ArrayList that stores Stations
	ArrayList<String> word = new ArrayList<String>();
	// ArrayList that stores Synonyms
	ArrayList<String> synonyms = new ArrayList<String>();

	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
	public ThesaurusRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This Comparator should simply behave like the default (lexicographic) compareTo() method
	 * for Strings, applied to the portions of the FileLines' Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class will simply return an
	 * instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			//Compare result
			int comp;

			//Compare the word at their indices
			comp = word.get(l1.getFileIterator().getIndex()).compareTo(word.get(l2.getFileIterator().getIndex()));
			switch(comp) {
			case -1:
				return -1;
			case 1:
				return 1;
			}
			//This should never be the return value
			return 2;

		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the ThesaurusLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
	}

	/**
	 * This method should (1) set the word to null and (2) empty the list of synonyms.
	 */
	public void clear() {
		// Reset Words.
		for (int i = 0; i < word.size(); i++) {
			word.set(i, null);
		}
		// Reset synonyms
		for (int i = 0; i < word.size(); i++) {
			word.remove(i);
		}

	}

	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
	public void join(FileLine w) {
		String wLine = w.toString();
		String[] parts = wLine.split(":");

		//store the words in the Arraylist of words
		String wordPart = parts[0];
		word.add(wordPart);
		
		//split the synonyms part
		String synPart = parts[1];
		String[] syn = synPart.split(",");
		
		//store the synonyms in the Arraylist of synonyms
		for (int i = 0; i < syn.length; i++) {
			if(!synonyms.contains(syn[i]))
			synonyms.add(syn[i]);
		}
		
	}

	/**
	 * See the assignment description and example runs for the exact output format.
	 */
	public String toString() {
		//String to be returned
		String returnString = "";
		//Checks if valid info at that index.
		for (int i = 0; i < word.size(); i++) {
			if (word.get(i) == null) {
				continue;
			}
			//If valid info, add to the string.
			returnString.concat(word.get(i));
			returnString.concat(":");
			for(int j = 0; j<synonyms.size();j++){
				//Special case if at end of list (no comma)
				if (j == synonyms.size() - 1) {
					returnString.concat((String) synonyms.get(j));
				}
				returnString.concat(synonyms.get(j) + ",");
			}
			//If at the end of the list, add a new line.
			if (i == word.size() - 1) {
				returnString.concat("\n");
			}
			
		}

		return returnString;
	
	}
}
