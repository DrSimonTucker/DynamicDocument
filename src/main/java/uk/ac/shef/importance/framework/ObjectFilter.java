package uk.ac.shef.importance.framework;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ObjectFilter<X>
{
	Map<X, Double> scoreMap;
	AlphaBetaComputer<X> comp;
	
	public ObjectFilter(AlphaBetaComputer<X> comp)
	{
		this.comp = comp;
	}
	
	public List<X> filter(List<X> objects, double percentage)
	{
		List<X> toRet = new Vector<X>();
		
		computeScoreMap(objects);
		
		//Sort the objects according to their score - in DESCENDING order, hence the negative
		Collections.sort(objects, new Comparator<X>(){
			public int compare(X o1, X o2)
			{
				return -scoreMap.get(o1).compareTo(scoreMap.get(o2));
			}
		});
		
		//Then pick off the top N
		toRet.addAll(objects.subList(0, (int)(objects.size()*percentage)));
		
		return toRet;
	}
	
	protected void computeScoreMap(Collection<X> objects)
	{
		if (scoreMap == null)
			scoreMap = new HashMap<X, Double>();
		
		for (X x : objects)
			if (!scoreMap.containsKey(x))
			{
				double score = comp.scoreObject(x);
				scoreMap.put(x, score);
			}
	}
}
