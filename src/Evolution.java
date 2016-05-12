import java.util.*;
import java.lang.StringBuilder;

public class Evolution 
{
	public static void main(String[] args)
	{
		System.out.println("What is your goal?");
		Scanner input = new Scanner(System.in);
		String goal = input.nextLine();
		
		System.out.println("What is the mutation rate?");
		Double r = input.nextDouble();
		
		System.out.println("What is the population?");
		int pop = input.nextInt();
		
		Evolution evol = new Evolution(goal, r, pop);
		evol.evolve();
	}
	
	public String target;
	public int targetLength;
	public double mutationRate;
	public int population;
	public int generation = 1;
	public List<Individual> currentGeneration;
	public List<Individual> newGeneration;
	public Individual fittest;
	public Individual prevFittest;
	
	public Evolution(String t, double rate, int pop)
	{
		this.target = t;
		this.targetLength = target.length();
		this.mutationRate = rate;
		this.population = pop;
		this.currentGeneration = new ArrayList<Individual>(pop);
		this.newGeneration = new ArrayList<Individual>(pop);
	}
	
	public void evolve()
	{
		generateNewest();
		generate();
	}
	
	public void generate()
	{
		while(fittest.getFitVal()>0)
		{
			crossOver();
			mutate();
			sortToFitValue();
			System.out.println("Generation: " + generation + " | Fitness: " + fittest.getFitVal() + " | " + fittest.getData());
			currentGeneration = newGeneration;
			newGeneration = new ArrayList<Individual>(population);
			generation++;
		}
	}
	
	public void generateNewest()
	{
		StringBuilder s = new StringBuilder();
		String newData="";
		
		for(int i = 0; i<population; i++)
		{
			for(int j = 0; j<target.length(); j++)
			{
				s.append(randChar());
			}	
			newData = s.toString();
			s = new StringBuilder();
			Individual newIndividual = new Individual(newData, target);
			currentGeneration.add(newIndividual);			
		}	
		sortToFitValue();
		fittest = currentGeneration.get(0);
	}
	
	public void crossOver()
	{
		Random r = new Random();
		int start = selection();
		StringBuilder s = new StringBuilder();
		int i = 1;
		while(start>0)
		{
			int rand = r.nextInt(targetLength-2);
			boolean randBool = r.nextBoolean();
			if(randBool==true){
				s.append(fittest.getData().substring(0,rand));
				s.append(newGeneration.get(i).getData().substring(rand));
			}
			else{
				s.append(newGeneration.get(i).getData().substring(0,rand));
				s.append(fittest.getData().substring(rand));
			}
			
			i++;
			Individual newIndividual = new Individual(s.toString(), target);
			newGeneration.add(newIndividual);
			s = new StringBuilder();
			start--;
		}
	}
	
	public int selection()
	{
		int removed=0;
		int highestVal;
		sortToFitValue();
		highestVal = currentGeneration.get(currentGeneration.size()-1).getFitVal();
		
		newGeneration.add(currentGeneration.get(0));
		newGeneration.add(currentGeneration.get(1));
		
		for(int i=2; i<currentGeneration.size(); i++)
		{
			if(currentGeneration.get(i).getFitVal()<highestVal)
				newGeneration.add(currentGeneration.get(i));
			else
				removed++;
		}
		return removed;
	}
	
	public void sortToFitValue()
	{
		Collections.sort(currentGeneration);
		fittest = currentGeneration.get(0);
	}
	
	public void mutate()//after crossover
	{
		Random r = new Random();
		Double randRate = 0.0;
		int randIndex = 0;
		StringBuilder s;
		for(int i = 0; i<newGeneration.size(); i++)
		{
			randRate = r.nextDouble();
			randIndex = r.nextInt(targetLength);
			
			if(randRate<=mutationRate)
			{
				s = new StringBuilder(newGeneration.get(i).getData());
				s.setCharAt(randIndex, randChar());
				newGeneration.add(new Individual(s.toString(), target));
			}
		}
	}

	public char randChar()
	{
	    return ((char)(32 + (new Random()).nextInt(95)));
	}
}
