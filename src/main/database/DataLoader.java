package main.database;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DataLoader
{
	List<Map<String,String>> loadData(File file);
}
