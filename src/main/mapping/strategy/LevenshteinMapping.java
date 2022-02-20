package main.mapping.strategy;

import main.mapping.distances.GeneralEditDistance;
import main.mapping.distances.GeneralEditDistances;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevenshteinMapping extends AMappingStrategy
{
	@Override
	public Map<String, String> findMatch(List<Map<String, String>> mappingTable, String toFind)
	{
		int bestDistance = Integer.MAX_VALUE;
		Map<String, String> closestMatch = new HashMap<>();
		
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance(toFind);
		
		for (Map<String, String> entry : mappingTable)
		{
			String entryString = entry.get(searchField);
			int edDistance = ed.getDistance(entryString, bestDistance);
			
			if (edDistance < bestDistance)
			{
				bestDistance = edDistance;
				closestMatch = entry;
			}
		}
		return closestMatch;
	}
}
