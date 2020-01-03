package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author Darragh Lally, G00220290
 * @version 1.0
 * @since Java 1.8
 *
 *This class contains a method run in which we read in the dataset
 */
public class Parser implements Runnable {

	// Variables
	private String file;
	private BlockingQueue<Query> q= null;

	/**
	 * Constructor for class Parser
	 * @param file
	 * @param q
	 * 
	 * file = Wili language dataset
	 * q = BlockingQueue 
	 */
	public Parser(String file, BlockingQueue<Query> q) {
		super();
		this.file = file;
		this.q = q;
	}

	/**
	 * Method Run: reads dataset file in and splits each line at the '@' symbol and saves either side of
	 * it into their own String variables (1. Language Text |2. Language Type) then puts them into 
	 * the BlockingQueue for use on the consumer side.
	 * 
	 * The last element added to the queue is what is used to stop the run method in ComputeQuery ie our Poison
	 * 
	 * @see ComputeQuery
	 * @see Query
	 */
	@Override
	public void run() {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) { //While somethiing to read
				String[] record = line.trim().split("@"); //trim and split at @
				if (record.length != 2) { //If we dont have a Language Text and Language type get out
					continue;
				}
				q.put(new Query(record[0], record[1])); // put into a blocking queue - A new instance of Query
			}
			br.close(); //close reader - be polite
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				q.put(new Query("Poison", "ENDRUN")); //Added last - poison to kill the run method in ComputeQuery
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}// run

	

}
