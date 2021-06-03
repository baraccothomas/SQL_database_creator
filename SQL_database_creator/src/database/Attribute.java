package database;

public abstract class Attribute {
	public static enum AttributeType {
		INT,
		CHAR,
		DATE,
		YEAR,
		SMALLINT
	}
	
	private String name;
	private AttributeType type;
	
	private boolean isPrimaryKey;
	private boolean isForeignKey;
	
	private String foreignTableName, foreignAttributeName;
	
	/**
	 * Constructor of the attribute class
	 * @param name name of the attribute
	 * @param type type of the attribute
	 */
	public Attribute(String name, AttributeType type) {
		this.name = name;
		this.type = type;
		
		isPrimaryKey = false;
		isForeignKey = false;
		
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
		Attribute other = (Attribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * Sets the attribute as a primary key
	 */
	public void setAsPrimaryKey() {
		isPrimaryKey = true;
	}
	
	/**
	 * Sets the attribute as a foreign key
	 * @param foreignTableName name of the foreign table
	 * @param foreignAttributeName name of the attribute of the foreign table
	 * 
	 * @apiNote the foreign table and the attribute must exist in the current database
	 */
	public void setAsForeignKey(String foreignTableName, String foreignAttributeName) {
		isForeignKey = true;
		this.foreignTableName = foreignTableName;
		this.foreignAttributeName = foreignAttributeName;
	}
	
	
}
