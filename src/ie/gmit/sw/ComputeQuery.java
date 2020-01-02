package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ComputeQuery implements Runnable {
	private Database db = null;
	private BlockingQueue<Query> queue = null;
	private int k;
	private boolean keepRunning = true;

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

	// Constuctor
	public ComputeQuery(Database db, BlockingQueue<Query> queue, int k) {
		super();
		this.db = db;
		this.queue = queue;
		this.k = k;
	}

	private void parse(Query q, int... ks) {
		Language language = Language.valueOf(q.getLanguage());
		String text = q.getText();
		for (int i = 0; i <= text.length() - k; i++) {
			CharSequence kmer = text.subSequence(i, i + k);
			db.add(kmer, language);
		}
	}// parse

	// analyseQuery() - Takes in a string to be tested and outputs a language that
	// matches with its current DB.
	public void analyseQuery(String s) {
		int frequency = 1;
		Map<Integer, LanguageEntry> queryKmer = new ConcurrentHashMap<>();
		Map<Integer, LanguageEntry> sortedMap = new ConcurrentHashMap<>();

		for (int i = 0; i <= s.length() - k; i++) {
			CharSequence kmer = s.subSequence(i, i + k);
			int kmerHash = kmer.hashCode();

			if (queryKmer.containsKey(kmerHash)) {
				frequency += queryKmer.get(kmerHash).getFrequency();
			}

			queryKmer.put(kmerHash, new LanguageEntry(kmerHash, frequency));
		}

		// Sort and Rank
		// query map (queryKmer) - into sorted list (l)
		List<LanguageEntry> l = new ArrayList<LanguageEntry>(queryKmer.values());

		int rank = 1;
		for (LanguageEntry le : l) {
			le.setRank(rank);
			sortedMap.put(le.getKmer(), le);
			if (rank == 300)
				break;
			rank++;
		}
		// Output first Language in map choosen by process - Database.java
		System.out.println("The text appears to be written in: " + db.getLanguage(sortedMap) + "\n\n");
	}

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
