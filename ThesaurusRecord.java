import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when merging thesaurus data.
 * The word field is the entry in the thesaurus, synonyms is the list of all associated synonyms.
 */

public class ThesaurusRecord extends Record{
	ArrayList<String> synonyms = new ArrayList<String>(); //stores synonyms
	String word = null; //stores original word

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
			
			String[] line1 = l1.getString().split(":"); //splits l1 at :
			String[] line2 = l2.getString().split(":"); //splits l2 at :
			
			//Comparing the words up to the ":"
			return line1[0].compareTo(line2[0]);
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
		synonyms = new ArrayList<String>();
		word = null;
    }
	
	/**
	 * This method should parse the list of synonyms contained in the given FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
    public void join(FileLine w) {
    	//Splits at the colon to get the word
		String[] splitArray1 = w.getString().split(":");
		word = splitArray1[0];
		
		//splitting at commas to get all the synonyms
		String[] splitArray2 = splitArray1[1].split(",");
		
		//adding all the synonyms to the list if they dont already exist
		for (int i=0; i<splitArray2.length; i++)
			if (!synonyms.contains(splitArray2[i]))
				synonyms.add(splitArray2[i]);
    }
	
	/**
	 * See the assignment description and example runs for the exact output format.
	 */
    public String toString() {
		//sorting the list of synonyms
    	Collections.sort(synonyms);

    	String returnString = word + ":"; //String to be returned
   
    	//loop that adds all synonyms but the last to returnString
    	for (int i=0; i<synonyms.size()-1; i++)
    		returnString += synonyms.get(i) + ",";
    	
    	//adding last synonym to returnString
    	returnString += synonyms.get(synonyms.size()-1);
    	
		return returnString;
	}
}