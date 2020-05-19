/**
 * 
 */
package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

/**
 * @author jean-christophe Sanchez
 *
 */
public class LabelStar extends Label{

	public LabelStar(Node courant, Arc parent, double cout) {
		super(courant, parent, cout);
		
	}

}
