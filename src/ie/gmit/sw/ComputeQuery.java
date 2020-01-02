package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ComputeQuery implements Runnable {

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
			// System.out.println("DEGUB: " + kmerHash + " " + frequency);
			// System.out.println("DEGUB: " + queryKmer);
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
		System.out.println("Language: " + db.getLanguage(sortedMap));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
