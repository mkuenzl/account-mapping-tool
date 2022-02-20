package main.mapping.strategy;

import main.database.CsvLoader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevenshteinMappingTest
{
	private static List<Map<String, String>> mappingTable;
	private static AMappingStrategy levenshteinMapping;
	
	@BeforeClass
	public static void initialize()
	{
		mappingTable = new CsvLoader().loadData(new File("test-resources/mapping-test" +
				"-database.csv"));
		levenshteinMapping = new LevenshteinMapping();
		levenshteinMapping.setSearchField("account_name");
	}
	
	@Test
	public void findExactMatchTest()
	{
		Map expectedMap = new HashMap<>()
		{{
			put("fund", "aqcuila");
			put("account_number", "16");
			put("account_name", "Payable to investment - 4");
			put("account_mapping_eng", "payable");
			put("account_mapping_ger", "bezahlen");
		}};
		
		Assert.assertEquals(expectedMap, levenshteinMapping.findMatch(mappingTable, "Payable to investment - 4"));
	}
	
	@Test
	public void emptyMappingTableTest()
	{
		Map expectedMap = new HashMap<>();
		List mappingTable = new ArrayList();
		
		Assert.assertEquals(expectedMap, levenshteinMapping.findMatch(mappingTable, "Empty Mapping Table"));
	}
}
