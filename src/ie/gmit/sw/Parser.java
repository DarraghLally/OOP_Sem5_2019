package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Parser implements Runnable {
	
	//Variables
	private Database db = null;
	private String file;
	private int k;
	
	//Constructor
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
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			
			while ((line = br.readLine())!=null) {
				String[] record = line.trim().split("@");
				if(record.length != 2) {
					continue;
				}
			parse(record[0], record[1]);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}//run
	
	private void parse(String text, String lang, int...ks) {
		Language language = Language.valueOf(lang);
		
		for(int i = 0; i<= text.length() - k; i++) {
			CharSequence kmer = text.subSequence(i, i + k);
			db.add(kmer, language);
		}
	}//parse
	
	private void analyseQuery(String s) {
		//Test against known languages here
		
	}
	
	public static void main (String[] args) throws Throwable{
		Parser p = new Parser("wili-2018-Small-11750-Edited.txt", 1);
		
		Database db = new Database();
		p.setDb(db);
		Thread t = new Thread(p);
		t.start();
		t.join();
		
		db.resize(300);
		
		//Add tests here
		String s = "A quick brown fox";
		p.analyseQuery(s);
	}

}
