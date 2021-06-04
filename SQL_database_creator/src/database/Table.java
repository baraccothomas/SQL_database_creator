package database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import database.Attribute.AttributeType;

public class Table {
	private String name;
	private List<Attribute> attributes;
	
	public Table(String name) {
		this.name = name;
		
		attributes = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public Table addAttribute(Attribute attribute) {
		attributes.add(attribute);
		
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public void create() {
		int nAttributes = 0;
		//TODO I/O request: number of attributes
		for(int i = 0; i < nAttributes; i++) {
			String attributeName = "";
			Attribute.AttributeType type = null;
			
			//TODO I/O request: name of current attribute
			//TODO I/O request: type of current attribute
			
			Attribute attribute = new Attribute(attributeName, type);
			
			attribute.create();
			
			addAttribute(attribute);
		}
	}
	
	public String createSQL() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("CREATE TABLE " + name + " (\n");
		
		for(int i = 0; i < attributes.size(); i++) {
			buffer.append(attributes.get(i).createSQL() + "\n");
		}
		
		List<String> primaryKeys = attributes.stream()
										.filter(Attribute::isPrimaryKey)
										.collect(Collectors.mapping(Attribute::getName, Collectors.toList()));
		if(primaryKeys.size() > 0) {
			buffer.append("PRIMARY KEY (" + primaryKeys.get(0));
			primaryKeys.stream().skip(1).forEach(n -> buffer.append(", " + n));
			buffer.append("),\n");
		}
		
		attributes.stream()
			.filter(Attribute::isForeignKey)
			.forEach(a -> buffer.append("FOREIGN KEY (" + a.getName() + ") REFERENCES " + a.getForeignTableName() + " (" + a.getForeignAttributeName() + ")\nON UPDATE CASCADE\nON DELETE CASCADE,\n"));
		
		buffer.append(");");
		return buffer.toString();
	}
}
