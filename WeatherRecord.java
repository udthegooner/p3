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

	String[] data = new String[getNumFiles()]; 	//array that stores data values
	String station; //station number
	String date; //date of data value

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
			
			int comp; //result of comparison
			String[] line1 = l1.getString().split(","); //splits l1 into an array
			String[] line2 = l2.getString().split(","); //splits l2 into an array
			
			//Comparing the stations
			comp = Integer.valueOf(line1[0]).compareTo(Integer.valueOf(line2[0]));
			
			//If the stations were not the same, return their compareTo value,
			//otherwise, if they were the same, return the date comparison.
			switch(comp) {
			case 0:
				return Integer.valueOf(line1[1]).compareTo(Integer.valueOf(line2[1]));
			default:
				return comp;
			}
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
		data = new String[getNumFiles()];
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
		String[] splitArray = li.getString().split(","); //splits li into an array
		
		//setting station and date
		station = splitArray[0];
		date = splitArray[1];
		
		//setting data value in the correct position based on fileiterator index
		data[li.getFileIterator().getIndex()] = splitArray[2];
	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		String returnString = station + "," + date + ",";
		
		//loop that adds all data values to return string
		for (int i = 0; i < data.length; i++){
			if (data[i] == null) 
				returnString += "-";
			else
				returnString += String.format("%.1f", Double.parseDouble(data[i]));
			if (i != data.length - 1) returnString += ",";
		}
		
		return returnString;
	}
}

