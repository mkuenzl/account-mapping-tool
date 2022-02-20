package main.mapping.strategy;

import java.util.List;
import java.util.Map;

public interface MappingStrategy
{
	Map<String, String> findMatch(List<Map<String, String>> mappingTable, String toFind);
	
	void setSearchField(String searchField);
}

