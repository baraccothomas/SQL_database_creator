package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private String name;
	private List<Table> tables;
	
	public Database(String name) {
		super();
		this.name = name;
		
		tables = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public Database addTable(Table table) {
		tables.add(table);
		
		return this;
	}
	
	public void create() throws IOException, NumberFormatException, DatabaseException {
		int nTables = 0;
		
		System.out.print("Number of tables: ");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		nTables = Integer.parseInt(in.readLine());
		
		System.out.println();
		
		for(int i = 0; i < nTables; i++) {
			String tableName = "";
			
			System.out.print("Name of the " + (i + 1) + " table: ");
			
			in = new BufferedReader(new InputStreamReader(System.in));
			tableName = in.readLine();
			
			System.out.println();
			
			Table table = new Table(tableName);
			
			table.create();
			
			addTable(table);
		}
	}
	
	public String createSQL() throws DatabaseException {
		if(tables.size() == 0)
			throw new DatabaseException("Database not created");
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("CREATE DATABASE IF NOT EXISTS " + name + ";\n\n");
		buffer.append("USE " + name + ";\n\n");
		
		for(int i = 0; i < tables.size(); i++)
			buffer.append(tables.get(i).createSQL() + "\n\n");
		
		return buffer.toString();
	}
}
