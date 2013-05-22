package uk.org.iscream.vrmlgraph;

import java.util.*;

/**
 *  A class used for centering a graph about the origin
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class ViewCenterer {

    GraphData _graph;


    /**
     *  Constructor for the ViewCenterer object 
     *
     * @param  graph  The GraphData object to be centered
     */
    public ViewCenterer(GraphData graph) {
        _graph = graph;
    }


    /**
     *  Calculates the average x, y and z coordinates of all of the
     *  nodes in the GraphData object that was passed via its
     *  constructor.  It then used this to move each Node such
     *  that the average coordinate is at the origin.
     */
    public void center() {

        double averageX = 0;
        double averageY = 0;
        double averageZ = 0;

        Iterator nodeIt = _graph.getNodeIterator();
        while (nodeIt.hasNext()) {
            Node node = (Node) nodeIt.next();
            averageX += node.getX();
            averageY += node.getY();
            averageZ += node.getZ();
        }

        averageX = averageX/_graph.countNodes();
        averageY = averageY/_graph.countNodes();
        averageZ = averageZ/_graph.countNodes();

        nodeIt = _graph.getNodeIterator();
        while (nodeIt.hasNext()) {
            Node node = (Node) nodeIt.next();
            node.setX(node.getX() - averageX);
            node.setY(node.getY() - averageY);
            node.setZ(node.getZ() - averageZ);
        }

    }
}

