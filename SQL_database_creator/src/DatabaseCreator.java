import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import database.*;

public class DatabaseCreator {
	public static void main(String[] args) throws DatabaseException, IOException {
		System.out.print("Database name: ");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String name = in.readLine();
		
		System.out.println();
		
		Database database = new Database(name);
		
		database.create();
		
		System.out.println(database.createSQL());
	}
}
