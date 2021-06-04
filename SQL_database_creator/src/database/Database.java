package database;

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
	
	public void create() {
		int nTables = 0;
		//TODO add the request from I/O of the number of tables
		for(int i = 0; i < nTables; i++) {
			String tableName = "";
			
			//TODO add the request from I/O of the name of the current table
			
			Table table = new Table(tableName);
			
			table.create();
			
			addTable(table);
		}
	}
	
	public String createSQL() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("CREATE DATABASE IF NOT EXISTS " + name + ";\n\n");
		buffer.append("USE " + name + ";\n\n");
		
		for(int i = 0; i < tables.size(); i++)
			buffer.append(tables.get(i).createSQL() + "\n\n");
		
		return buffer.toString();
	}
}
