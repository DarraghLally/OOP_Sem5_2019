package ie.gmit.sw;

/**
 * This class is used to creat an entry, by passing the hashed kmer, and its frequency
 * LanguageEntry inherites from Comparable
 * @author Darragh Lally, G00220290
 * @version 1.0
 * @since Java 1.8
 * @see Comparable
 *
 */
public class LanguageEntry implements Comparable<LanguageEntry> {
	//Variables
	private int kmer; //to hold the hashed kmer
	private int frequency; //to hold its frequency
	private int rank; //to hold a rank for its placein the queue

	/**
	 * Constructor takes two arguments
	 * @param kmer hashed version of the string kmer
	 * @param frequency a total of its occurance in the file
	 */
	public LanguageEntry(int kmer, int frequency) {
		super();
		this.kmer = kmer;
		this.frequency = frequency;
	}

	//Accessor methods
	public int getKmer() {
		return kmer;
	}

	public void setKmer(int kmer) {
		this.kmer = kmer;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Overridden compareTo, compares this entries frequency to the next
	 */
	@Override
	public int compareTo(LanguageEntry next) {
		return - Integer.compare(frequency, next.getFrequency());
	}
	
	@Override
	public String toString() {
		return "[" + kmer + "/" + frequency + "/" + rank + "]";
	}
}
