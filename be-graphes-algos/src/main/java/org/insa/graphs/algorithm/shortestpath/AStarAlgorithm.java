package org.insa.graphs.algorithm.shortestpath;


import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        //déclaration
        Graph graph = data.getGraph();
        ArrayList<Node> list_noeuds = new ArrayList<Node>();
        
        Arc[] predecessorArcs = new Arc[graph.size()];
        LabelStar[] labelsStar ;
        labelsStar = new LabelStar[graph.size()];
        
        BinaryHeap <Label> tas = new BinaryHeap<Label>();
       
        
        //On initialise la source à 0 ainsi que les tableaux
        
        for(int i = 0; i < graph.size();i++) {
        	
        	list_noeuds.add(graph.get(i));
        	if(data.getOrigin().equals(graph.get(i))) {
        		LabelStar origine = new LabelStar(graph.get(i),null,0,0);
        		labelsStar[i] = origine;
        		tas.insert(origine);
        	}else {
        		
        		labelsStar[i] = new LabelStar(graph.get(i),null,Double.POSITIVE_INFINITY,0);
        	}
        }
        boolean originOK = false;
        Label current = null;
        double estimation = 0;
        

        
        while(tas.isEmpty() == false && labelsStar[data.getDestination().getId()].getMarque() == false) { //le tas est vide ou bien la destination est atteinte
        	
        	current = tas.deleteMin();
        	if(originOK == false) {
        		this.notifyOriginProcessed(current.getSommetCourant());
        		originOK = true;
        	}
        	
        	current.setMarque(true);
        	//System.out.println(current.getCout()); tester si le cout des labels est croissant
        	this.notifyNodeMarked(current.getSommetCourant());
        	double coutTmp;
        	
        	for(Arc arcsSucessor : current.getSommetCourant().getSuccessors()) { //on parcourt tous les successeurs de current
        		
        		if(labelsStar[arcsSucessor.getDestination().getId()].getMarque() == false && data.isAllowed(arcsSucessor) ==true) { //si y est pas marquée et si l'arc est parcourable avec notre mode de transport
        			if(labelsStar[arcsSucessor.getDestination().getId()].getCout() > current.getCout() + arcsSucessor.getLength()) {
        				this.notifyNodeReached(current.getSommetCourant());
        				/* Calculer le coût estimé à vol d'oiseau selon le mode */
        	        	
        	        	if(data.getMode() == Mode.LENGTH) { // En distance
        	        		estimation = (double) current.getSommetCourant().getPoint().distanceTo(data.getDestination().getPoint());
        	            } else { // En temps
        	                int speed = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed()); 
        	                estimation = (double) (current.getSommetCourant().getPoint().distanceTo(data.getDestination().getPoint()) / speed * 1000.d / 3600.d);
        	            }
        				coutTmp = current.getTotalCost() + arcsSucessor.getLength(); // on met à jour le cost de y
        				labelsStar[current.getSommetCourant().getId()].setEstimation(estimation);
        				if(labelsStar[arcsSucessor.getDestination().getId()].getCout() < Double.POSITIVE_INFINITY) {//si le cout est infini
        					tas.remove(labelsStar[arcsSucessor.getDestination().getId()]);
        				}
        				labelsStar[arcsSucessor.getDestination().getId()].setCout(coutTmp);
        				labelsStar[arcsSucessor.getDestination().getId()].setPere(arcsSucessor); //mettre le parent à jour
        				predecessorArcs[arcsSucessor.getDestination().getId()] = arcsSucessor;
        				tas.insert(labelsStar[arcsSucessor.getDestination().getId()]);
        				this.notifyNodeMarked(labelsStar[arcsSucessor.getDestination().getId()].getSommetCourant());
        			}
        				
        			
        		}
        	}
        }
        if(labelsStar[data.getDestination().getId()].getMarque() == true) { //destination atteinte chemin réalisable
        	this.notifyDestinationReached(current.getSommetCourant());        
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);
            
            // Create the final solution.
            Path shortest_chemin;
            shortest_chemin = new Path(graph, arcs);
            Path le_chemin = new Path(graph, arcs);
            if(le_chemin.isValid() == true) {//il existe une solution
            	if(labelsStar[data.getDestination().getId()].getCout() == shortest_chemin.getLength()) {//cette soilution est optimale
            		solution = new ShortestPathSolution(data, Status.OPTIMAL, le_chemin);
            	}else {//la solution n'est pas optimale
            		solution = new ShortestPathSolution(data, Status.FEASIBLE, le_chemin);
            	}
            }else {//pas de solution
            	solution = new ShortestPathSolution(data, Status.INFEASIBLE, le_chemin);
            }
        }else {//pas de solution
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE, null);
        }
        
        
        
        return solution;
    }
		
}
