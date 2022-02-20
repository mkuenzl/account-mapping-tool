package main.mapping.strategy;

import java.util.List;
import java.util.Map;

public abstract class AMappingStrategy implements MappingStrategy
{
	String searchField;
	
	AMappingStrategy(){
		searchField = "";
	}
	
	@Override
	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}
	
	@Override
	public abstract Map<String, String> findMatch(List<Map<String, String>> mappingTable, String toFind);
}
