package main.config;

public class Configuration
{
	public static String exactMatchColumn = "account-exact-match";
	public static String closestMatchColumn = "account-closest-match";
	public static String toMatchColumn = "account-name";
	
	public static String filePath = "";
	public static String databasePath = "src/main/resources/mapping-database.csv";
	
	public static String sheetName = "data";
	public static String tableName = "data_table";
	
	public static String searchField = "account_name";
	public static String mappingField = "account_mapping_eng";
}
