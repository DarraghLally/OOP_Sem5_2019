package ie.gmit.sw;

/**
 * @author Darragh Lally - G00220290
 * @version 1.0
 * @since 1.8
 *
 *
 *This Class - Runner - is just used to call the show method from <b>Index.java<\b>
 *
 *@see Index
 */
public class Runner {

	public static void main(String[] args) throws Throwable {
		new Index().show();
	}
		/*
		int kmerSize = 4;
		boolean isFound = false;
		String fileIn;
		String query;

		File dbFile;

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);

		ComputeQuery cq = new ComputeQuery();
		System.out.println("*********************************************************");
		System.out.println("*	GMIT - Dept. Comp Science & Applied Physics	*");
		System.out.println("*							*");
		System.out.println("*		Text Language Detector			*");
		System.out.println("*							*");
		System.out.println("*********************************************************");
		do {
			// Enter File location
			System.out.println("Enter Language Dataset Location: ");
			fileIn = s.next();
			dbFile = new File(fileIn);
			// Error Handling
			if (dbFile.length() == 0) {
				isFound = false;
				System.out.println("No File");
			} else {
				isFound = true;
				System.out.println("Creating Reference Database from '" + fileIn + "'....\n");
			}
		} while (!isFound);

		Parser p = new Parser(fileIn, kmerSize);
		Database db = new Database();
		p.setDb(db);
		Thread t = new Thread(p);
		t.start();
		t.join();
		db.resize(300);

		do {
			// Enter Query location
			System.out.println("Enter Query file: ");
			fileIn = s.next();
			dbFile = new File(fileIn);
			// Error Handling
			if (dbFile.length() == 0) {
				isFound = false;
				System.out.println("\nNo File - Try again");
			} else {
				isFound = true;
				System.out.println("\nProcessing... Standby....");
			}
		} while (!isFound);

		// Clear query
		query = "";

		// Turn file into string
		try {
			query = new String(Files.readAllBytes(Paths.get(fileIn)));
		} catch (Exception e) {
			System.out.println("Error - File to String ");
		}
		// Test file
		p.analyseQuery(query);

	}
	*/
}
