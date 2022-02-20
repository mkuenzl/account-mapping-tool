package main.mapping.distances;

import org.junit.Assert;
import org.junit.Test;

public class EditDistanceTest
{
	@Test
	public void distanceTest()
	{
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance("bank5");
		
		String entryString = "bank";
		int edDistance = ed.getDistance(entryString, 10);
		
		Assert.assertEquals(1, edDistance);
	}
	
	@Test
	public void distanceTest2()
	{
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance("bank5");
		
		String entryString = "ban";
		int edDistance = ed.getDistance(entryString, 10);
		
		Assert.assertEquals(2, edDistance);
	}
	
	@Test
	public void distanceTest3()
	{
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance("bank5");
		
		String entryString = "banq";
		int edDistance = ed.getDistance(entryString, 10);
		
		Assert.assertEquals(2, edDistance);
	}
	
	@Test
	public void distanceTest4()
	{
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance("investment");
		
		String entryString = "invectment";
		int edDistance = ed.getDistance(entryString, 10);
		
		Assert.assertEquals(1, edDistance);
	}
	
	@Test
	public void distanceMultipleWordsTest()
	{
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance("investment into Bank");
		
		String entryString = "investment";
		int edDistance = ed.getDistance(entryString, 10);
		
		Assert.assertEquals(10, edDistance);
	}
	
	@Test
	public void distanceMultipleWordsTest2()
	{
		GeneralEditDistance ed = GeneralEditDistances.getLevenshteinDistance("investment into Bank");
		
		String entryString = "Envestment";
		int edDistance = ed.getDistance(entryString, 15);
		
		Assert.assertEquals(11, edDistance);
	}
}
