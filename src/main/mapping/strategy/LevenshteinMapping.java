package main.mapping.strategy;

import org.apache.commons.text.similarity.LevenshteinDistance;

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
		
		for (Map<String, String> entry : mappingTable)
		{
			String entryString = entry.get(searchField);

			LevenshteinDistance levenshteinDistance = new LevenshteinDistance(bestDistance);
			Integer distance = levenshteinDistance.apply(toFind, entryString);
			
			// For some reason the algorithm return -1 sometimes.
			if (distance < 0) continue;
			if (distance < bestDistance)
			{
				bestDistance = distance;
				closestMatch = entry;
			}
		}
		return closestMatch;
	}
}
