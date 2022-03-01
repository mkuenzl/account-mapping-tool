package main.table;

import main.mapping.Mapper;
import main.mapping.strategy.MappingStrategy;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class TableManipulator
{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFTable table;
	private HashMap<String, Integer> tableInformation = new HashMap<>();
	private HashMap<String, Integer> header;
	
	public void configure(String fileName, String sheetName, String tableName)
	{
		try (FileInputStream fis = new FileInputStream(fileName))
		{
			//obtaining bytes from the file
			this.workbook = new XSSFWorkbook(fis);    //creating Workbook instance that refers to .xlsx file
			
			//Set Sheet
			this.sheet = workbook.getSheet(sheetName);   //creating a Sheet object to retrieve object
			if (sheet == null) return;
			
			//Set Table
			this.table = sheet.getWorkbook().getTable(tableName);
			
			//Set TableInformation
			setTableInformation();
			setHeader();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void readTable()
	{
		//Go through all lines
		for (int i = tableInformation.get("startRow"); i <= tableInformation.get("endRow"); i++)
		{
			XSSFRow row = sheet.getRow(i);
			if (row != null)
			{
				for (int j = tableInformation.get("startColumn"); j <= tableInformation.get("endColumn"); j++)
				{
					String cellValue = "";
					XSSFCell cell = row.getCell(j);
					if (cell != null)
					{
						DataFormatter formatter = new DataFormatter();
						cellValue = formatter.formatCellValue(cell);
					}
					
					//System.out.print(header.get(j) + " " + cellValue + "\t");
					System.out.print(cellValue + "\t");
				}
				System.out.println();
			}
		}
	}
	
	public void createMapping(Mapper mapper, MappingStrategy strategy,
	                          String mappingSourceColumn, String mappingTargetColumn)
	{
		//Go through all lines
		for (int i = tableInformation.get("startRow"); i <= tableInformation.get("endRow"); i++)
		{
			XSSFRow row = sheet.getRow(i);
			if (row == null) continue;
			
			XSSFCell cellToMap = row.getCell(header.get(mappingSourceColumn));
			if (cellToMap == null) continue;
			
			DataFormatter formatter = new DataFormatter();
			String cellValue = formatter.formatCellValue(cellToMap);
			
			String mappingValue = mapper.map(strategy, cellValue);
			XSSFCell cellToWrite = row.createCell(header.get(mappingTargetColumn));
			cellToWrite.setCellValue(mappingValue);
			
			//System.out.print(cellValue + "\t" + mappingValue);
			
			//System.out.println();
		}
	}
	
	public void writeTable(String fileName)
	{
		try (FileOutputStream fileOutputStream = new FileOutputStream(fileName))
		{
			workbook.write(fileOutputStream);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	void setTableInformation()
	{
		int headerRow = table.getStartCellReference().getRow();
		int startRow = headerRow + 1;
		int endRow = table.getEndCellReference().getRow();
		int startColumn = table.getStartCellReference().getCol();
		int endColumn = table.getEndCellReference().getCol();
		
		tableInformation.put("headerRow", headerRow);
		tableInformation.put("startRow", startRow);
		tableInformation.put("endRow", endRow);
		tableInformation.put("startColumn", startColumn);
		tableInformation.put("endColumn", endColumn);
	}
	
	void setHeader()
	{
		//Create header part -> headerString - column
		HashMap<String, Integer> header = new HashMap<>();
		for (int i = tableInformation.get("startColumn"); i <= tableInformation.get("endColumn"); i++)
		{
			
			XSSFCell cell = sheet.getRow(tableInformation.get("headerRow")).getCell(i);
			if (cell != null)
			{
				DataFormatter formatter = new DataFormatter();
				String cellValue = formatter.formatCellValue(cell);
				header.put(cellValue, i);
				//System.out.print(cellValue + "\t");
			}
		}
		//System.out.println();
		
		this.header = header;
	}
	
	HashMap<String, Integer> getTableInformation()
	{
		return tableInformation;
	}
	
	HashMap<String, Integer> getHeader()
	{
		return header;
	}
}
