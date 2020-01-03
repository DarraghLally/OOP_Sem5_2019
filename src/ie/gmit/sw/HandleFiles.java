package ie.gmit.sw;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class is where the user is prompted for the 
 * language dataset location (wili - short or long included in project) and the 
 * query file location (test files included in project) 
 * in order for it to process a result
 * 
 * @author Darragh Lally, G00220290
 * @version 1.0
 * @since Java 1.8
 * 
 * @see ComputeQuery - for analyseQuery method
 */
public class HandleFiles {
	
	//Variables
	private int kmerSize = 4; //Size of kmer/ngram
	private volatile boolean isFound = true; //volatile so it wont be cached
	private String fileIn; //to hold dataset
	private String query; //to hold query
	private File dbFile; //for fileIn
	private Scanner s = new Scanner(System.in); //For user input
	
	/**
	 * Method show() displays header and calls handle method, sets keepGoing to 
	 * false when process is complete therefor ending the program
	 * 
	 * @throws Exception
	 *
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
	 * Asks for user input for the following: <br>
	 * 1) Language Dataset Location - Packaged in project <br>
	 * 		1.a) wili-2018-Large-117500-Edited.txt <br>
	 * 		1.b) wili-2018-Small-11750-Edited.txt <br>
	 * 2) Query file location - test files packaged in project<br>
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
			if (dbFile.length() == 0) { // Error Handling
				isFound = false;
				System.out.println("No File");
			} else { // If there is a database - set boolean, inform user
				isFound = true;
				System.out.println("Creating Reference Database from '" + fileIn + "'....\n");
			}
		} while (!isFound); //keep going - until user enters correct file

		BlockingQueue<Query> queue = new ArrayBlockingQueue<Query>(20); //Create ArrayBlockingQueue with capacity of 20
		Parser p = new Parser(fileIn, queue); //instance of parser with dataset and ABQ
		Database db = new Database(); //instance of database
		ComputeQuery cq = new ComputeQuery(db, queue, kmerSize); //instance of ComputeQuery with database(dataset), ABQ, and size of kmer 

		Thread t = new Thread(p); //create producer thread		
		Thread c = new Thread(cq); //create consumer thread
		Thread c1 = new Thread(cq); //Another consumer thread
		
		t.start(); //Start threads
		c.start();
		c1.start();
		try {
			t.join();
			c.join();
			c1.join();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		db.resize(300); //Resize DB

		do {
			// Enter Query location
			System.out.println("Enter Query file: ");
			fileIn = s.next();
			dbFile = new File(fileIn);
			// Error Handling
			if (dbFile.length() == 0) {
				isFound = false;
				System.out.println("\nNo File - Try again");
			} else { //If query file found set boolean, inform user
				isFound = true;
				System.out.println("Processing... Standby....");
			}
		} while (!isFound);// Keep going until valid query file entered

		// Clear query
		query = "";

		// Turn file into string
		try {
			query = new String(Files.readAllBytes(Paths.get(fileIn)));
		} catch (Exception e) {
			System.out.println("Error - File to String ");
		}
		// Test file by passing into analyseQuery()
		cq.analyseQuery(query);
	}

}
