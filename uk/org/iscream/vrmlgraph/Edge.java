package uk.org.iscream.vrmlgraph;

/**
 *  The Edge class acts as an object that holds a reference to the two
 *  nodes that it connects.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class Edge {

    private Node _from, _to;


    /**
     *  Constructor for the Edge object 
     *
     * @param  from  One of the Node objects that this Edge connects.
     * @param  to    The other Node object that this Edge connects.
     */
    public Edge(Node from, Node to) {
        _from = from;
        _to = to;
    }


    /**
     *  Gets the Node that this edge is connected from. 
     *
     * @return    The Node that this Edge is connected from.
     */
    public Node getFrom() {
        return _from;
    }


    /**
     *  Gets the Node that this Edge is connected to.
     *
     * @return    The Node that this Edge is connected to.
     */
    public Node getTo() {
        return _to;
    }
}

