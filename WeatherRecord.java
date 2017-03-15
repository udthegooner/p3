import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging
 * weather data. Station and Date store the station and date associated with
 * each weather reading that this object stores. l stores the weather readings,
 * in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record {

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
			
			// Stores entire FileLine string to be parsed.
			String toParse1 = "";
			// Stores station parsed from string.
			String station1 = "";
			// Stores date parsed from string.
			String date1 = "";
			// Splits the parsed date by comma.
			String[] parsed1 = {};

			// Stores entire FileLine string to be parsed.
			String toParse2 = "";
			// Stores station parsed from string.
			String station2 = "";
			// Stores date parsed from string.
			String date2 = "";
			// Splits the parsed date by comma.
			String[] parsed2 = {};
			
			// Compare integer
			int comp;

			// Assigns the strings to parse
			toParse1 = l1.getString();
			toParse2 = l2.getString();
			
			// Separates by comma
			parsed1 = toParse1.split(",");
			parsed2 = toParse2.split(",");
			
			// Assign variables from parsed data
			station1 = parsed1[0];
			date1 = parsed1[1];
			station2 = parsed2[0];
			date2 = parsed2[1];

			// Parse the station from each string, then compare.
			comp = station1.compareTo(station2);
			
			switch(comp) {
			case 1:
				return 1;
			
			case -1:
				return -1;
				
			// If the stations are the same, compare dates.	
			case 0:
				comp = date1.compareTo(date2);
				break;
			}
			
			switch(comp) {
			case 1:
				return 1;
			
			case -1:
				return -1;
				
			case 0:
				return 0;
			}
			return 0;
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
		// TODO initialize/reset data members
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
		// TODO implement join() functionality
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		// TODO

		return null;
	}
}
