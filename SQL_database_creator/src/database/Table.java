package database;

import java.util.HashSet;
import java.util.Set;

public class Table {
	private String name;
	private Set<Attribute> attributes;
	
	public Table(String name) {
		this.name = name;
		
		attributes = new HashSet<>();
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
}
