public class Individual implements Comparable<Individual>{

	private String data;
	private int fitValue;
	
	public Individual(String d, String t)
	{
		data = d;
		fitValue = findFitness(t);	
	}
	
	public Individual()
	{
	}
	
	public int getFitVal()
	{
		return fitValue;
	}
	
	public String getData()
	{
		return data;
	}
	
	public int findFitness(String target)
	{
		int fitVal = target.length();
		for(int i = 0; i<data.length(); i++)
		{
			if(data.charAt(i) == target.charAt(i))
				fitVal--;
		}
		return fitVal;
	}
	
	public int compareTo(Individual another) {
        int otherFit=((Individual)another).getFitVal();
        return this.fitValue-otherFit;

    }
}
