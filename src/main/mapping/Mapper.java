package main.mapping;

import main.mapping.strategy.MappingStrategy;
import java.util.List;
import java.util.Map;

public class Mapper
{
	private final List<Map<String, String>> mappingTable;
	private String searchField;
	private String mappingField;
	
	public Mapper(List<Map<String, String>> mappingTable)
	{
		this.mappingTable = mappingTable;
	}
	
	public String map(MappingStrategy mappingStrategy, String toFind)
	{
		mappingStrategy.setSearchField(searchField);
		
		Map<String, String> match = mappingStrategy.findMatch(mappingTable, toFind);
		if (match.containsKey(mappingField))
			return match.get(mappingField);
		return "";
	}
	
	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}
	
	public void setMappingField(String mappingField)
	{
		this.mappingField = mappingField;
	}
}

