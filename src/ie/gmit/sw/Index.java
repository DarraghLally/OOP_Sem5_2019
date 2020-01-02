package ie.gmit.sw;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Darragh Lally - G00220290
 * @version 1.0
 * @since 1.8
 * 
 * This class is where the user is prompted for the 
 * language dataset location and the query file location 
 * in order for it to process a result
 */
public class Index {
	
	//Variables
	private int kmerSize = 4;
	private volatile boolean isFound = true;
	private String fileIn;
	private String query;
	File dbFile;
	private Scanner s = new Scanner(System.in); // Scanner, reading user menu choice
	// public Parser p = new Parser(); // Instance of Parser class

	/**
	 *
	 * @throws Exception
	 * 
	 *  method show() displays header and calls handle method, sets keepGoing to 
	 * false when process is complete
	 */ 
	public void show() throws Exception {
		while (isFound) {
			showHeader();
			handle();
			isFound = false;
		}
	}// show()

	/**
	 * Prints header to console
	 */
	private void showHeader() {
		System.out.println("*********************************************************");
		System.out.println("*	GMIT - Dept. Comp Science & Applied Physics	*");
		System.out.println("*							*");
		System.out.println("*		Text Language Detector			*");
		System.out.println("*							*");
		System.out.println("*********************************************************");
	}// showHeader

	/**
	 * Asks for user input for the following:
	 * 1) Language Dataset Location - Packaged in project
	 * 		1.a) wili-2018-Large-117500-Edited.txt
	 * 		1.b) wili-2018-Small-11750-Edited.txt
	 * 
	 * 2) Query file location - test docs packaged in project
	 * 
	 * It then passes quere into analyseQuery() method from class Parser
	 * 
	 * @see Parser
	 */
	private void handle() {
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
		try {
			t.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

}
