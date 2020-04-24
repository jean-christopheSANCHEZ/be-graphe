package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        //déclaration
        Graph graph = data.getGraph();
        Node[] noeuds;
        noeuds = new Node[graph.size()];
        Arc[] arcs ;
        arcs = new Arc[graph.size()];
        Label[] labels ;
        labels = new Label[graph.size()];
        
        BinaryHeap <Label> tas = new BinaryHeap<Label>();
       
        
        //On initialise la source à 0 ainsi que les tableaux
        
        for(int i = 0; i < graph.size();i++) {
        	noeuds[i] = graph.get(i);
        	if(data.getOrigin()==graph.get(i)) {
        		Label origine = new Label(graph.get(i),null,0);
        		tas.insert(origine);
        	}else {
        		
        		labels[i] = new Label(graph.get(i),null,Float.POSITIVE_INFINITY);
        	}
        }
        
        while(tas.isEmpty()) {
        	Label current = tas.deleteMin();
        	current.setMarque(true);
        	for(Arc arcsSucessor : current.getSommetCourant().getSuccessors()) { //on parcourt tout les successeurs de current
        		
        		if(labels[arcsSucessor.getDestination().getId()].getMarque() == false) { //si y est pas marquée
        			if(labels[arcsSucessor.getDestination().getId()].getCout() > current.getCout() + arcsSucessor.getLength()) {
        				labels[arcsSucessor.getDestination().getId()].setCout(current.getCout() + arcsSucessor.getLength()); // on met à jour le cost de y
        				if(tas.) {
        					tas.remove(labels[arcsSucessor.getDestination().getId()]);
        					tas.insert(labels[arcsSucessor.getDestination().getId()]);
        				}
        				else {
        					tas.insert(labels[arcsSucessor.getDestination().getId()]);
        				}
        			}
        				
        			
        		}
        	}
        }
        
        
        
        
        
        return solution;
    }


}
