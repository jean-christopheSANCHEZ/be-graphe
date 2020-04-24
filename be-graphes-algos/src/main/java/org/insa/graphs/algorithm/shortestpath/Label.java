/**
 * @author Jean-Christophe Sanchez
 *
 */

package org.insa.graphs.algorithm.shortestpath;


import org.insa.graphs.model.Arc;

import org.insa.graphs.model.Node;



public class Label implements Comparable<Label>{
	private Node sommetCourant;
	private Boolean marque;
	private double cout;
	private Arc pere;
	
	public Label(Node courant, Arc parent, double cout) {
		this.sommetCourant = courant;
		this.marque = false;
		this.cout = Float.POSITIVE_INFINITY;
		this.pere = parent;
	}
	
	
	public Node getSommetCourant() {
		return sommetCourant;
	}




	public void setSommetCourant(Node sommetCourant) {
		this.sommetCourant = sommetCourant;
	}




	public Boolean getMarque() {
		return marque;
	}




	public void setMarque(Boolean marque) {
		this.marque = marque;
	}




	public Arc getPere() {
		return pere;
	}




	public void setPere(Arc pere) {
		this.pere = pere;
	}




	public void setCout(double cout) {
		this.cout = cout;
	}




	public double getCout() {
		return cout;
	}


	@Override
	public int compareTo(Label arg0) {
		int result;
		if (this.getCout() < arg0.getCout()) {
			result = -1;
		}
		else if (this.getCout() == arg0.getCout()) {
			result = 0;
		}
		else {
			result = 1;
		}
		return result;
	}
	
}
