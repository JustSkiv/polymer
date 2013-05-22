package uk.org.iscream.vrmlgraph;

import java.util.*;

/**
 *  The class used to store 3-D graph data.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class GraphData {

    private LinkedList _nodes = new LinkedList();
    private LinkedList _edges = new LinkedList();


    /**
     *  Constructor for the GraphData object 
     */
    public GraphData() {
        // no-args constructor body.
    }


    /**
     *  Returns an Iterator of all Nodes stored within this class.
     *
     * @return    An Iterator of all Nodes stored within this class.
     */
    public Iterator getNodeIterator() {
        return _nodes.listIterator();
    }


    /**
     *  Returns an Iterator of all Nodes stored within this class,
     *  starting from the Node at index <code>index</code>
     *
     * @param  index  The index of the LinkedList to start iterating from.
     * @return        An Iterator of all Nodes starting from index <code>index</code>
     */
    public Iterator getNodeIterator(int index) {
        return _nodes.listIterator(index);
    }


    /**
     *  Returns an Iterator of all Edges stored within this class.
     *
     * @return    An Iterator of all Edges stored within this class.
     */
    public Iterator getEdgeIterator() {
        return _edges.listIterator();
    }


    /**
     *  Adds a Node to the graph data.
     *
     * @param  n  The Node to be added to the graph.
     */
    public void addNode(Node n) {
        _nodes.add(n);
    }


    /**
     *  Adds an Edge to the graph data.
     *
     * @param  e  The edge to be added to the graph.
     */
    public void addEdge(Edge e) {
        _edges.add(e);
    }


    /**
     *  Count the number of Nodes stored in the GraphData object.
     *
     * @return    The number of Nodes stored in the GraphData object.
     */
    public int countNodes() {
        return _nodes.size();
    }


    /**
     *  Clears the entire contents of the graph, including all Nodes
     *  and all Edges that may have existed in it.
     */
    public void clear() {
        _nodes.clear();
        _edges.clear();
    }

}

