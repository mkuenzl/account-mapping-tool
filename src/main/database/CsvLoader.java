package main.database;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvLoader implements DataLoader
{
	@Override
	public List<Map<String, String>> loadData(File file)
	{
		return load(file, ';');
	}
	
	private List<Map<String,String>> load(File file, Character delimiter){
		List<Map<String, String>> parsedTable = new ArrayList<>();
		
		if (!file.exists())
		{
			return parsedTable;
		}
		
		try (FileInputStream fileInputStream = new FileInputStream(file);
		     BOMInputStream bomInputStream = new BOMInputStream(fileInputStream);
		     InputStreamReader inputStreamReader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8))
		{
			CSVParser csvParser = CSVFormat.EXCEL.withDelimiter(delimiter)
					.withFirstRecordAsHeader()
					.withIgnoreEmptyLines(true)
					.parse(inputStreamReader);
			
			for (CSVRecord record : csvParser)
			{    // each record represents a line from the csv
				if (!"".equals(record.get(0)))
				{ // prevent wrong excel formatting of .csv files to crash the program
					Map<String, String> map = record.toMap();
					parsedTable.add(map);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return parsedTable;
	}
}

