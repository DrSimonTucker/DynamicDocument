package uk.ac.shef.importance.framework;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class for ranking objects of the given parameter
 * 
 * @author simon
 * 
 * @param <X>
 */
public class ObjectRanker<X>
{
	/**
	 * Performs the ranking
	 * 
	 * @param objects
	 *            The objects to rank
	 * @param comp
	 *            The computer used to perform the ranking
	 * @return A map from the object to its corresponding rank
	 */
	public final Map<X, Integer> rankObjects(final Collection<X> objects, final AlphaBetaComputer<X> comp)
	{
		Map<X, Integer> mapping = new HashMap<X, Integer>();
		final AlphaBetaComputer<X> localComp = comp;

		List<X> tempList = new LinkedList<X>(objects);
		Collections.sort(tempList, new Comparator<X>()
		{
			public int compare(final X o1, final X o2)
			{
				if (localComp.scoreObject(o1) == localComp.scoreObject(o2))
					return 0;
				else if (localComp.scoreObject(o1) < localComp.scoreObject(o2))
					return 1;
				else
					return -1;
			}
		});

		for (int i = 0; i < tempList.size(); i++)
			mapping.put(tempList.get(i), i);

		return mapping;
	}
}
