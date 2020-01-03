package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Darragh Lally, G00220290
 * @version 1.0
 * @since Java 1.8
 *
 *This class is the 'consumer' and implements Runnable. It holds methods that parses
 * the user query file into kmers and adds them to a map for comparison
 * to the language dataset.
 *
 *@see Runnable
 *
 */
public class ComputeQuery implements Runnable {
	//Variables
	private Database db = null; //To hold dataset 
	private BlockingQueue<Query> queue = null; //used for concurrentcy 
	private int k; //to hold size of kmer/ngram
	private boolean keepRunning = true; //exit condition

	// Accessor Methods
	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	public BlockingQueue<Query> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<Query> queue) {
		this.queue = queue;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	/**
	 * Constructor - taking in a database, the blocking queue and the kmer size
	 * @param db 
	 * @param queue
	 * @param k
	 * 
	 * db = database
	 * queue = blocking queue
	 * k = kmer/ngram size
	 */
	public ComputeQuery(Database db, BlockingQueue<Query> queue, int k) {
		super();
		this.db = db;
		this.queue = queue;
		this.k = k;
	}

	/**
	 * This method takes in the the query and breaks it up into kmers of size k
	 * @param q
	 * @param ks
	 * q = query text
	 */
	private void parse(Query q, int... ks) {
		Language language = Language.valueOf(q.getLanguage());
		String text = q.getText();
		for (int i = 0; i <= text.length() - k; i++) {
			CharSequence kmer = text.subSequence(i, i + k);
			db.add(kmer, language);
		}
	}// parse

	/**
	 * This method analyseQuery takes in the query text in the form of a string (converted from file to 
	 * string in HandleFiles), analyses the query map against the dataset map and outputs a 
	 * language that has the smallest distance metric
	 * @param s
	 * 
	 * @see HandleFiles
	 */
	public void analyseQuery(String s) {
		int frequency = 1;
		Map<Integer, LanguageEntry> queryKmer = new ConcurrentHashMap<>(); //For initial input of query
		Map<Integer, LanguageEntry> sortedMap = new ConcurrentHashMap<>(); //For sorted/ranked kmers from query file

		for (int i = 0; i <= s.length() - k; i++) { //Loop over query
			CharSequence kmer = s.subSequence(i, i + k); //break into kmers
			int kmerHash = kmer.hashCode(); //convert kmer to hashcode for efficency

			if (queryKmer.containsKey(kmerHash)) { //If the kmer already exists add to its frequency - this is skipped the first time we add the kmer
				frequency += queryKmer.get(kmerHash).getFrequency();
			}

			queryKmer.put(kmerHash, new LanguageEntry(kmerHash, frequency)); //Other wise add kmer and a frequency of 1
		}

		// Sort and Rank query string into new map
		List<LanguageEntry> l = new ArrayList<LanguageEntry>(queryKmer.values()); // new list of LanguageEntry

		int rank = 1; // set rank to 1
		for (LanguageEntry le : l) { //for every entry in the list
			le.setRank(rank); //set the rank
			sortedMap.put(le.getKmer(), le); //add to sorted map
			if (rank == 300) { //Limit to the first 300, we only need this amount to find the languge been used, the next 3 - 400 will let us know what the conversation is about
				break;
			}
			rank++; //increase rank
		}
		// Output first Language in map choosen by process - Database.java
		System.out.println("\nThe text appears to be written in: " + db.getLanguage(sortedMap) + "\n\n");
	}

	/**
	 * run method keeps taking from the queue until we find the poison pill which is added
	 *  at the end of the creation of the language dataset database.
	 */
	@Override
	public void run() {
		while (keepRunning) {
			try {
				Query q = queue.take();

				if (q.getLanguage().equals("ENDRUN")) {
					keepRunning = false;
				} else {
					parse(q);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
