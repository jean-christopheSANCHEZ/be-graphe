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

	
	private double estimation;
	
	public LabelStar(Node courant, Arc parent, double cout, double estimation) {
		super(courant, parent, cout);
		this.estimation = estimation;
	}

	public double getEstimation() {
		return estimation;
	}

	public void setEstimation(double estimation) {
		this.estimation = estimation;
	}
	
	@Override
	public double getTotalCost() {
		return this.getCout()+estimation;
	}

}
