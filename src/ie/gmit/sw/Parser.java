package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author Darragh Lally
 * @version 1.0
 * @since Java 1.8
 *
 */
public class Parser implements Runnable {

	// Variables
	private String file;
	private BlockingQueue<Query> q= null;

	public Parser(String file, BlockingQueue<Query> q) {
		super();
		this.file = file;
		this.q = q;
	}

	@Override
	public void run() {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] record = line.trim().split("@");
				if (record.length != 2) {
					continue;
				}
				// put into a blocking queue -
				q.put(new Query(record[0], record[1]));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				System.out.println("/*/*/*/*/**/*/**/*/*/");
				q.put(new Query("Poison", "ENDRUN"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}// run

	

}
