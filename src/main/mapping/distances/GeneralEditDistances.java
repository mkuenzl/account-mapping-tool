package main.mapping.distances;

public class GeneralEditDistances
{
	
	private static class Levenshtein implements GeneralEditDistance
	{
		private ModifiedBerghelRoachEditDistance berghel;
		private MyersBitParallelEditDistance myers;
		private final CharSequence pattern;
		private final int patternLength;
		
		private Levenshtein(CharSequence pattern)
		{
			this.pattern = pattern;
			this.patternLength = pattern.length();
		}
		
		public GeneralEditDistance duplicate()
		{
			Levenshtein dup = new Levenshtein(pattern);
			if (this.myers != null)
			{
				dup.myers = (MyersBitParallelEditDistance) this.myers.duplicate();
			}
			return dup;
		}
		
		public int getDistance(CharSequence target, int limit)
		{
			if (limit <= 1)
			{
				return limit == 0 ?
						(pattern.equals(target) ? 0 : 1) :
						atMostOneError(pattern, target);
			}
			if ((patternLength > 64)
					&& (limit < (target.length() / 10)))
			{
				if (berghel == null)
				{
					berghel = ModifiedBerghelRoachEditDistance.getInstance(pattern);
				}
				return berghel.getDistance(target, limit);
			}
			
			if (myers == null)
			{
				myers = MyersBitParallelEditDistance.getInstance(pattern);
			}
			
			return myers.getDistance(target, limit);
		}
	}
	
	public static int atMostOneError(CharSequence s1, CharSequence s2)
	{
		int s1Length = s1.length();
		int s2Length = s2.length();
		int errors = 0;             /* running count of edits required */
		
		switch (s2Length - s1Length)
		{
			/*
			 * Strings are the same length.  No single insert/delete is possible;
			 * at most one substitution can be present.
			 */
			case 0:
				for (int i = 0; i < s2Length; i++)
				{
					if ((s2.charAt(i) != s1.charAt(i)) && (errors++ != 0))
					{
						break;
					}
				}
				return errors;
			
			case 1: /* s2Length > s1Length */
				for (int i = 0; i < s1Length; i++)
				{
					if (s2.charAt(i) != s1.charAt(i))
					{
						for (; i < s1Length; i++)
						{
							if (s2.charAt(i + 1) != s1.charAt(i))
							{
								return 2;
							}
						}
						return 1;
					}
				}
				return 1;
			
			/* Same as above case, with strings reversed */
			case -1: /* s1Length > s2Length */
				for (int i = 0; i < s2Length; i++)
				{
					if (s2.charAt(i) != s1.charAt(i))
					{
						for (; i < s2Length; i++)
						{
							if (s2.charAt(i) != s1.charAt(i + 1))
							{
								return 2;
							}
						}
						return 1;
					}
				}
				return 1;
			
			/* Edit distance is at least difference in lengths; more than 1 here. */
			default:
				return 2;
		}
	}
	
	public static GeneralEditDistance
	getLevenshteinDistance(CharSequence pattern)
	{
		return new Levenshtein(pattern);
	}
	
	private GeneralEditDistances()
	{
	}
}
