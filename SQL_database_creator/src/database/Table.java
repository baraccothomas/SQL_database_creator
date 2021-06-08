package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	public void create() throws NumberFormatException, IOException, DatabaseException {
		int nAttributes = 0;
		
		System.out.print("Number of attributes: ");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		nAttributes= Integer.parseInt(in.readLine());
		
		System.out.println();
		
		for(int i = 0; i < nAttributes; i++) {
			String attributeName = "";
			AttributeType type = null;
			
			System.out.print("Name of the " + (i + 1) + " attribute: ");
			
			in = new BufferedReader(new InputStreamReader(System.in));
			attributeName = in.readLine();
			
			System.out.println();
			
			for(int j = 0; j < AttributeType.values().length; j++) {
				System.out.println((j + 1) + ". " + AttributeType.values()[j]);
			}
			
			System.out.println();
			
			System.out.print("Index of the current attribute type: ");
			
			in = new BufferedReader(new InputStreamReader(System.in));
			int index = Integer.parseInt(in.readLine());
			
			System.out.println();
			
			if(index < 0 && index > AttributeType.values().length)
				throw new DatabaseException("Index out of range");
			
			type = AttributeType.values()[index - 1];
			
			Attribute attribute = new Attribute(attributeName, type);
			
			if(attribute.getType() == AttributeType.CHAR) {
				System.out.print("Size of the attribute: ");
				
				in = new BufferedReader(new InputStreamReader(System.in));
				int size = Integer.parseInt(in.readLine());
				
				System.out.println();
				
				attribute.setSize(size);
			}
			
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
			buffer.append("PRIMARY KEY(" + primaryKeys.get(0));
			primaryKeys.stream().skip(1).forEach(n -> buffer.append(", " + n));
			buffer.append("),\n");
		}
		
		Map<String, List<Attribute>> foreignKeys = attributes.stream()
													.filter(Attribute::isForeignKey)
													.collect(Collectors.groupingBy(Attribute::getForeignTableName));
		
		foreignKeys.forEach((t, l) -> {
			buffer.append("FOREIGN KEY(" + l.get(0).getName());
			l.stream().skip(1).forEach(a -> buffer.append(", " + a.getName()));
			buffer.append(") REFERENCES " + t + "(" + l.get(0).getForeignAttributeName());
			l.stream().skip(1).forEach(a -> buffer.append(", " + a.getForeignAttributeName()));
			buffer.append(")\nON UPDATE CASCADE\nON DELETE CASCADE,\n");
		});
		
		buffer.append(");");
		return buffer.toString();
	}
}
