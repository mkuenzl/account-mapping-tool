package main.mapping.strategy;

import main.database.CsvLoader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactMappingTest
{
	private static List<Map<String, String>> mappingTable;
	private static AMappingStrategy exactMapping;
	
	@BeforeClass
	public static void initialize()
	{
		mappingTable = new CsvLoader().loadData(new File("test-resources/mapping-test" +
				"-database.csv"));
		exactMapping = new ExactMapping();
		exactMapping.setSearchField("account_name");
	}
	
	@Test
	public void findExactMatch()
	{
		Map expectedMap = new HashMap<>()
		{{
			put("fund", "aqcuila");
			put("account_number", "16");
			put("account_name", "Payable to investment - 4");
			put("account_mapping_eng", "payable");
			put("account_mapping_ger", "bezahlen");
		}};
		
		Assert.assertEquals(expectedMap, exactMapping.findMatch(mappingTable, "Payable to investment - 4"));
	}
	
	@Test
	public void findNoMatch()
	{
		Map expectedMap = new HashMap<>();
		
		Assert.assertEquals(expectedMap, exactMapping.findMatch(mappingTable, "No Account with this name."));
	}
}
