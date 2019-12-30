package ie.gmit.sw;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Parser implements Runnable {

	// Variables
	private Database db = null;
	private String file;
	private int k;

	// Constructor
	public Parser(String file, int k) {
		super();
		this.file = file;
		this.k = k;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	@Override
	public void run() {

		//int count = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) {
				//count++;
				String[] record = line.trim().split("@");
				if (record.length != 2) {
					continue;
				}
				parse(record[0], record[1]);
				// System.out.println("DEBUG: " +record[0] + " " + record[1] + " - Count: " +
				// count);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// run

	private void parse(String text, String lang, int... ks) {
		Language language = Language.valueOf(lang);
		// System.out.println("In parse()");
		for (int i = 0; i <= text.length() - k; i++) {
			CharSequence kmer = text.subSequence(i, i + k);
			db.add(kmer, language);
		}
	}// parse

	public void analyseQuery(String s) {
		int frequency = 1;
		Map<Integer, LanguageEntry> queryKmer = new TreeMap<>();
		Map<Integer, LanguageEntry> sortedMap = new TreeMap<>();	

		for (int i = 0; i <= s.length() - k; i++) {
			CharSequence kmer = s.subSequence(i, i + k);
			int kmerHash = kmer.hashCode();

			if (queryKmer.containsKey(kmerHash)) {
				frequency += queryKmer.get(kmerHash).getFrequency();
			}
			
			queryKmer.put(kmerHash, new LanguageEntry(kmerHash, frequency));
			//System.out.println("DEGUB: " + kmerHash + "   " + frequency);
			//System.out.println("DEGUB: " + queryKmer);
		}
		
		//Sort and Rank
		//query map (queryKmer) - into sorted list (l)
		List<LanguageEntry> l = new ArrayList<LanguageEntry>(queryKmer.values());
		
		int rank = 1;
		for (LanguageEntry le : l) {
			le.setRank(rank);
			sortedMap.put(le.getKmer(), le);			
			if (rank == 300) break;
			rank++;
		}
		
		System.out.println("Language: " + db.getLanguage(sortedMap));
		
	}

}
