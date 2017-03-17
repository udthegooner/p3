import java.util.ArrayList;
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging
 * weather data. Station and Date store the station and date associated with
 * each weather reading that this object stores. l stores the weather readings,
 * in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record {

	// ArrayList that stores Stations
	ArrayList<String> stations = new ArrayList<String>();
	// ArrayList that stores Dates
	ArrayList<String> dates = new ArrayList<String>();
	// 2D ArrayList that stores data
	ArrayList<Object> data = new ArrayList<Object>();

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

			//Compare the stations first at their indices
			comp = stations.get(l1.getFileIterator().getIndex()).compareTo(stations.get(l2.getFileIterator().getIndex()));
			
			//If the stations were not the same, return their compareTo value,
			//otherwise, if they were the same, return the date comparison.
			switch(comp) {
			case -1:
				return -1;
			case 1:
				return 1;
			case 0:
				return dates.get(l1.getFileIterator().getIndex()).compareTo(stations.get(l2.getFileIterator().getIndex()));
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

		// Reset Stations.
		for (int i = 0; i < stations.size(); i++) {
			stations.add(i, Double.toString(Double.MIN_VALUE));
		}

		// Reset Dates.
		for (int i = 0; i < dates.size(); i++) {
			dates.add(i, Double.toString(Double.MIN_VALUE));
		}

		// Reset Data.
		for (int i = 0; i < data.size(); i++) {
			data.add(i, Double.toString(Double.MIN_VALUE));
		}

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

		// Stores the split string
		String[] splitter = new String[25];
		// Stores the split string as array list
		ArrayList<String> splitArray = new ArrayList<String>();

		// Parse out the fileLine string
		splitter = li.getString().split(",");
		for (int i = 0; i < splitter.length; i++) {
			splitArray.add(splitter[i]);
		}

		// Assign specific data to its data structure
		stations.add(li.getFileIterator().getIndex(), splitArray.get(0));
		dates.add(li.getFileIterator().getIndex(), splitArray.get(1));
		splitArray.remove(0);
		splitArray.remove(0);

		// Add all data from li into the corresponding index of data ArrayList.
		data.add(li.getFileIterator().getIndex(), splitArray);

	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		
		//String to be returned
		String returnString = "";
		
		for (int i = 0; i < stations.size(); i++) {
			
			//Checks if valid info at that index.
			if (stations.get(i).contains(Double.toString(Double.MIN_VALUE)) || stations.get(i).isEmpty()) {
				continue;
			}
			//If valid info, add to the string.
			returnString.concat(stations.get(i));
			returnString.concat(",");
			returnString.concat(dates.get(i));
			returnString.concat(",");
			for (int j = 0; j < data.size(); j++) {
				//Special case if at end of list (no comma)
				if (j == data.size() - 1) {
					returnString.concat((String) data.get(j));
				}
				returnString.concat(data.get(j) + ",");
			}
			
			//If at the end of the list, add a new line.
			if (i == stations.size() - 1) {
				returnString.concat("\n");
			}
			
		}

		return returnString;
	}
}
