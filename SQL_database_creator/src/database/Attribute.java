package database;

public class Attribute {
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
	private boolean isNullable;
	
	private int size;
	
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
		
		foreignTableName = null;
		foreignAttributeName = null;
		isNullable = true;
		
		size = -1;
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
		setAsNotNullable();
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
		setAsNotNullable();
	}
	
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	
	public boolean isForeignKey() {
		return isForeignKey;
	}

	public String getForeignTableName() {
		return foreignTableName;
	}

	public String getForeignAttributeName() {
		return foreignAttributeName;
	}

	public AttributeType getType() {
		return type;
	}
	
	public void setAsNotNullable() {
		isNullable = false;
	}
	
	public boolean isNullable() {
		return isNullable;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void create() {
		//TODO I/O request: ask if it's primary key
		//TODO I/O request: ask if it's foreign key
		//TODO I/O request: if not above, ask if it's nullable
	}
	
	public String createSQL() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(name + " " + type.toString());
		
		if(size != -1)
			buffer.append(" (" + size + ")");
		
		if(isPrimaryKey)
			buffer.append(" PRIMARY KEY");
		if(!isNullable)
			buffer.append(" NOT NULL");
		
		buffer.append(",");
		return buffer.toString();
	}
}
