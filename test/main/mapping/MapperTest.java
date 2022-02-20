package main.mapping;

import main.database.CsvLoader;
import main.mapping.strategy.ExactMapping;
import main.mapping.strategy.LevenshteinMapping;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class MapperTest
{
	private static Mapper mapper;
	
	@BeforeClass
	public static void initialize()
	{
		mapper = new Mapper(new CsvLoader().loadData(new File("test-resources/mapping-test-database.csv")));
		mapper.setSearchField("account_name");
		mapper.setMappingField("account_mapping_eng");
	}
	
	@Test
	public void findEntry()
	{
		Assert.assertEquals("cash at bank", mapper.map(new ExactMapping(), "bank5"));
	}
	
	@Test
	public void findNearestEntry()
	{
		Assert.assertEquals("payable", mapper.map(new LevenshteinMapping(), "payable investment"));
	}
}
