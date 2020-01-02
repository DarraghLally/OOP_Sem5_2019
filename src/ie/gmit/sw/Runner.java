package ie.gmit.sw;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Runner {
		
	public static void main(String[] args) throws Throwable {

		int kmerSize = 4;
		boolean isFound = false;
		String fileIn;
		String query;;
		File dbFile;
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		//Language file
		Parser p = new Parser("wili-2018-Small-11750-Edited.txt", kmerSize);
		//Parser p = new Parser("test.txt", kmerSize);
		
		do {
			//Enter File location
			System.out.println("Enter File Location:");
			fileIn = s.next();
			dbFile = new File(fileIn);
			//Error Handling
			if(dbFile.length() == 0) {
				isFound = false;
				System.out.println("No File");
			}else {
				isFound = true;
				System.out.println("Creating DataBase....");
			}
		} while (!isFound);
		
		Database db = new Database();
		p.setDb(db);
		Thread t = new Thread(p);
		t.start();
		t.join();
		db.resize(300);
		
		do {
			//Enter Query location
			System.out.println("Enter Query Location:");
			fileIn = s.next();
			dbFile = new File(fileIn);
			//Error Handling
			if(dbFile.length() == 0) {
				isFound = false;
				System.out.println("No File");
			}else {
				isFound = true;
				System.out.println("Working on it....");
			}
		} while (!isFound);
		
		//Clear query
		query="";
		
		//Turn file into string
		try {
			query = new String(Files.readAllBytes(Paths.get(fileIn)));
		} catch (Exception e) {						
			System.out.println("Error - File to String ");
		}
		//Test file
		p.analyseQuery(query);

	}
}
