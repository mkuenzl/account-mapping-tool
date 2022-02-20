package main.table;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.config.Configuration;
import main.database.CsvLoader;
import java.io.File;
import main.mapping.Mapper;
import main.mapping.strategy.ExactMapping;
import main.mapping.strategy.LevenshteinMapping;
import java.util.HashMap;

public class TableManipulatorTest
{
	private static TableManipulator tableManipulator;
	private static final String sourceFile = "test-resources/mapping-source-test-file.xlsx";
	private static final String targetFile = "test-resources/mapping-target-test-file.xlsx";
	private static final String databaseFile = "test-resources/mapping-test-database.csv";
	
	
	@BeforeClass
	public static void initialize()
	{
		tableManipulator = new TableManipulator();
		tableManipulator.configure(sourceFile, Configuration.sheetName,
				Configuration.tableName);
	}
	
	@Test
	public void readTableTest()
	{
		tableManipulator.readTable();
	}
	
	@Test
	public void writeTableTest()
	{
		tableManipulator.writeTable(targetFile);
	}
	
	@Test
	public void mappingExactTest()
	{
		Mapper mapper = new Mapper(new CsvLoader().loadData(new File(databaseFile)));
		mapper.setSearchField(Configuration.searchField);
		mapper.setMappingField(Configuration.mappingField);
		
		tableManipulator.createMapping(mapper, new ExactMapping(), Configuration.toMatchColumn, Configuration.exactMatchColumn);
		
		tableManipulator.writeTable(targetFile);
	}
	
	@Test
	public void mappingNearestTest()
	{
		Mapper mapper = new Mapper(new CsvLoader().loadData(new File(databaseFile)));
		mapper.setSearchField(Configuration.searchField);
		mapper.setMappingField(Configuration.mappingField);
		
		tableManipulator.createMapping(mapper, new LevenshteinMapping(), Configuration.toMatchColumn, Configuration.closestMatchColumn);
		
		tableManipulator.writeTable(targetFile);
	}
	
	@Test
	public void mappingCombinedStrategiesTest()
	{
		Mapper mapper = new Mapper(new CsvLoader().loadData(new File(databaseFile)));
		mapper.setSearchField(Configuration.searchField);
		mapper.setMappingField(Configuration.mappingField);
		
		tableManipulator.createMapping(mapper, new ExactMapping(), Configuration.toMatchColumn, Configuration.exactMatchColumn);
		tableManipulator.createMapping(mapper, new LevenshteinMapping(), Configuration.toMatchColumn, Configuration.closestMatchColumn);
		
		tableManipulator.writeTable(targetFile);
	}
	
	@Test
	public void getHeaderByColumnTest()
	{
		HashMap<String, Integer> expectedHeader = new HashMap<>()
		{{
			put("account-mapping", 0);
			put("fund", 1);
			put("account", 2);
			put("account-name", 3);
			put("account-closing-balance", 4);
			put("account-exact-match", 5);
			put("account-closest-match", 6);
		}};
		tableManipulator.setHeader();
		
		Assert.assertEquals(expectedHeader, tableManipulator.getHeader());
	}
}
