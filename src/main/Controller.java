package main;

import main.database.CsvLoader;
import main.config.Configuration;

import java.io.File;
import main.mapping.Mapper;
import main.mapping.strategy.ExactMapping;
import main.mapping.strategy.LevenshteinMapping;
import main.table.TableManipulator;

public class Controller
{
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Invalid parameter.");
			System.exit(0);
		}
		
		Configuration.databasePath = args[0];
		Configuration.filePath = args[1];
		
		Mapper mapper = new Mapper(new CsvLoader().loadData(new File(Configuration.databasePath)));
		mapper.setSearchField(Configuration.searchField);
		mapper.setMappingField(Configuration.mappingField);
		
		TableManipulator tableManipulator = new TableManipulator();
		tableManipulator.configure(Configuration.filePath, Configuration.sheetName,
				Configuration.tableName);
		
		tableManipulator.createMapping(mapper, new ExactMapping(), Configuration.toMatchColumn, Configuration.exactMatchColumn);
		tableManipulator.createMapping(mapper, new LevenshteinMapping(), Configuration.toMatchColumn, Configuration.closestMatchColumn);
		
		tableManipulator.writeTable(Configuration.filePath);
	}
}
