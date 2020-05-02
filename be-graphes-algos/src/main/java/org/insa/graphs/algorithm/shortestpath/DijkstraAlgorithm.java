package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

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
        Arc[] predecessorArcs = new Arc[graph.size()];
        Label[] labels ;
        labels = new Label[graph.size()];
        
        BinaryHeap <Label> tas = new BinaryHeap<Label>();
       
        
        //On initialise la source à 0 ainsi que les tableaux
        
        for(int i = 0; i < graph.size();i++) {
        	noeuds[i] = graph.get(i);
        	if(data.getOrigin().equals(graph.get(i))) {
        		Label origine = new Label(graph.get(i),null,0);
        		labels[i] = origine;
        		tas.insert(origine);
        	}else {
        		
        		labels[i] = new Label(graph.get(i),null,Double.POSITIVE_INFINITY);
        	}
        }
        boolean originOK = false;
        Label current = null;
        while(labels[data.getDestination().getId()].getMarque() == false ) { 
        	
        	current = tas.deleteMin();
        	if(originOK == false) {
        		this.notifyOriginProcessed(current.getSommetCourant());
        		originOK = true;
        	}
        	
        	current.setMarque(true);
        	this.notifyNodeMarked(current.getSommetCourant());
        	double coutTmp;
        	
        	for(Arc arcsSucessor : current.getSommetCourant().getSuccessors()) { //on parcourt tous les successeurs de current
        		
        		if(labels[arcsSucessor.getDestination().getId()].getMarque() == false && data.isAllowed(arcsSucessor) ==true) { //si y est pas marquée et si l'arc est parcourable avec notre mode de transport
        			if(labels[arcsSucessor.getDestination().getId()].getCout() > current.getCout() + arcsSucessor.getLength()) {
        				this.notifyNodeReached(current.getSommetCourant());
        				coutTmp = current.getCout() + arcsSucessor.getLength(); // on met à jour le cost de y
        				if(labels[arcsSucessor.getDestination().getId()].getCout() < Double.POSITIVE_INFINITY) {//si le cout est infini
        					tas.remove(labels[arcsSucessor.getDestination().getId()]);
        				}
        				labels[arcsSucessor.getDestination().getId()].setCout(coutTmp);
        				labels[arcsSucessor.getDestination().getId()].setPere(arcsSucessor); //mettre le parent à jour
        				predecessorArcs[arcsSucessor.getDestination().getId()] = arcsSucessor;
        				tas.insert(labels[arcsSucessor.getDestination().getId()]);
        			}
        				
        			
        		}
        	}
        }
        
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
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        
        return solution;
    }


}
