package main.mapping.distances;

public interface GeneralEditDistance
{
	
	GeneralEditDistance duplicate();
	
	int getDistance(CharSequence target, int limit);
	
}
