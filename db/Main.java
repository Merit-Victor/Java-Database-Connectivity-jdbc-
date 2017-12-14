package eg.edu.alexu.csd.oop.db;

import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.cs68.DatabaseImp;
import eg.edu.alexu.csd.oop.db.cs68.manager;

public class Main {

	public static void main(String[] args) {
		DatabaseImp db = new DatabaseImp();
		manager manager = new manager(db);
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your query ,please");
			String query = scan.nextLine();
			manager.executeAll(query);
		}
	}

}
