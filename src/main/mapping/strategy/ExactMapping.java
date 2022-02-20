package main.mapping.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactMapping extends AMappingStrategy
{
	@Override
	public Map<String, String> findMatch(List<Map<String, String>> mappingTable, String toFind)
	{
		for (Map<String, String> entry : mappingTable)
		{
			if (entry.get(searchField).equals(toFind)) return entry;
		}
		return new HashMap<>();
	}
}
