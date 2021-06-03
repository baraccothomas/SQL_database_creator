package database;

import java.util.HashSet;
import java.util.Set;

public class Database {
	private String name;
	private Set<Table> tables;
	
	public Database(String name) {
		super();
		this.name = name;
		
		tables = new HashSet<>();
	}

	public String getName() {
		return name;
	}
	
	public Database addTable(Table table) {
		tables.add(table);
		
		return this;
	}
	
}
