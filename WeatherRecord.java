import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging
 * weather data. Station and Date store the station and date associated with
 * each weather reading that this object stores. l stores the weather readings,
 * in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record {

	// ArrayList that stores data
	ArrayList<String> data = new ArrayList<String>();;	
	String station;
	String date;

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent
	 * constructor and then calling the clear method()
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This comparator should first compare the stations associated with the
	 * given FileLines. If they are the same, then the dates should be compared.
	 */
	private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			
			//Compare result
			int comp;
			ArrayList<String> line1 = parseLine(l1);
			ArrayList<String> line2 = parseLine(l2);
			
			//Compare the stations first at their indices
			comp = line1.get(0).compareTo(line2.get(0));
			
			//If the stations were not the same, return their compareTo value,
			//otherwise, if they were the same, return the date comparison.
			switch(comp) {
			case -1:
				return -1;
			case 1:
				return 1;
			case 0:
				return line1.get(1).compareTo(line2.get(1));
			}
			
			//This should never be the return value
			return 2;
		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the
	 * WeatherLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
	}

	/**
	 * This method should fill each entry in the data structure containing the
	 * readings with Double.MIN_VALUE
	 */
	public void clear() {
		station = null;
		date = null;
		data = new ArrayList<String>();
	}

	/**
	 * This method should parse the String associated with the given FileLine to
	 * get the station, date, and reading contained therein. Then, in the data
	 * structure holding each reading, the entry with index equal to the
	 * parameter FileLine's index should be set to the value of the reading.
	 * Also, so that this method will handle merging when this WeatherRecord is
	 * empty, the station and date associated with this WeatherRecord should be
	 * set to the station and date values which were similarly parsed.
	 */
	public void join(FileLine li) {

		ArrayList<String> splitArrayList = parseLine(li);
		station = splitArrayList.get(0);
		date = splitArrayList.get(1);
		data.add(splitArrayList.get(2));
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		
		//String to be returned
		String returnString = station + ",";
		returnString += date + ",	";
		for (int i = 0; i < data.size(); i++)
			returnString += data.get(i) + "	";
			
		return returnString;
	}
	
	private static ArrayList<String> parseLine (FileLine li){
		// Stores the split string
		String[] splitArray = li.getString().split(",");;
		// Stores the split string as array list
		ArrayList<String> splitArrayList = new ArrayList<String>(Arrays.asList(splitArray));
		
		return splitArrayList;
	}
}

