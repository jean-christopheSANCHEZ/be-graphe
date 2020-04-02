/**
 * @author Jean-Christophe Sanchez
 *
 */

package org.insa.graphs.algorithm.shortestpath;


import org.insa.graphs.model.Arc;

import org.insa.graphs.model.Node;



public class Label {
	private Node sommetCourant;
	private Boolean marque;
	private double cout;
	private Arc pere;
	
	
	public double getCout() {
		return cout;
	}
	
}
