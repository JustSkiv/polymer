package uk.org.iscream.vrmlgraph;

import java.util.*;

/**
 *  An instance of this class can be used to perform 3-D spring embedding on
 *  the GraphData that is passed via its constructor.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class NodeMover {

    private final double _C1 = 2.0;
    private final double _C2 = 1.0;
    private final double _C3 = 2.0;
    private final double _C4 = 0.1;
    private final long _M = 1000;

    private GraphData _graph;


    /**
     *  Constructor for the NodeMover object 
     *
     * @param  graph  The GraphData object to be spring embedded
     */
    public NodeMover(GraphData graph) {
        _graph = graph;
    }



    /**
     *  Apply the spring embedding method to the graph data
     *  Calling this method will use the default values for
     *  the embedding method.
     */
    public void move() {
        move(_C1, _C2, _C3, _C4, _M);
    }


    /**
     *  Apply the spring embedding method to the graph data
     *  Calling this method allows the number of calculations
     *  to be specified, but all other values shall take their
     *  defaults.
     *
     * @param  M  The number of calculations to perform during the spring embedding
     */
    public void move(long M) {
        move(_C1, _C2, _C3, _C4, M);
    }


    /**
     *  Apply the spring embedding method to the graph data.
     *
     * @param  C1  A constant value used in the spring embedding
     * @param  C2  A constant value used in the spring embedding
     * @param  C3  A constant value used in the spring embedding
     * @param  C4  A constant value used in the spring embedding
     * @param  M   The number of calculations to perform during the spring embedding
     */
    public void move(double C1, double C2, double C3, double C4, long M) {

        // for each iteration...
        for (long iteration = 0; iteration < M; iteration++) {

            // for each node, work out the repulsive forces on it....
            int skipTo = 0;
            Iterator nodeItA = _graph.getNodeIterator();

            while (nodeItA.hasNext()) {

                Node nodeA = (Node) nodeItA.next();

                // For each node after the current one...
                Iterator nodeItB = _graph.getNodeIterator(++skipTo);
                while (nodeItB.hasNext()) {

                    // [nodeA] <---FORCE---> [nodeB]
                    Node nodeB = (Node) nodeItB.next();
                    double deltaX = nodeB.getX() - nodeA.getX();
                    double deltaY = nodeB.getY() - nodeA.getY();
                    double deltaZ = nodeB.getZ() - nodeA.getZ();

                    double distanceSquared = deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ;
                    
                    // Avoid division by zero error or Nodes flying off to
                    // infinity.  Pretend there is an arbitrary distance between
                    // the Nodes.
                    if (distanceSquared == 0.0) {
                        distanceSquared = 1.0;
                    }

                    double distance = Math.sqrt(distanceSquared);

                    double repulsiveForce = C3/distanceSquared;

                    // Update the resultant forces on the nodes...
                    nodeB.setFX(nodeB.getFX() + repulsiveForce*deltaX/distance);
                    nodeB.setFY(nodeB.getFY() + repulsiveForce*deltaY/distance);
                    nodeB.setFZ(nodeB.getFZ() + repulsiveForce*deltaZ/distance);
                    nodeA.setFX(nodeA.getFX() - repulsiveForce*deltaX/distance);
                    nodeA.setFY(nodeA.getFY() - repulsiveForce*deltaY/distance);
                    nodeA.setFZ(nodeA.getFZ() - repulsiveForce*deltaZ/distance);
                }

            }

            // for each edge, work out the attractive forces on both nodes...
            Iterator edgeIt = _graph.getEdgeIterator();

            while (edgeIt.hasNext()) {

                // [nodeA] ----->FORCE<----- [nodeB]
                Edge edge = (Edge) edgeIt.next();
                Node nodeA = edge.getFrom();
                Node nodeB = edge.getTo();

                double deltaX = nodeB.getX() - nodeA.getX();
                double deltaY = nodeB.getY() - nodeA.getY();
                double deltaZ = nodeB.getZ() - nodeA.getZ();

                double distanceSquared = deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ;

                // Avoid division by zero error or Nodes flying off to
                // infinity.  Pretend there is an arbitrary distance between
                // the Nodes.
                if (distanceSquared == 0.0) {
                    distanceSquared = 1.0;
                }

                double distance = Math.sqrt(distanceSquared);

                double attractiveForce = C1*Math.log(distance/C2)/Math.log(10);

                // Update the resultant forces on the nodes...
                nodeB.setFX(nodeB.getFX() - attractiveForce*deltaX/distance);
                nodeB.setFY(nodeB.getFY() - attractiveForce*deltaY/distance);
                nodeB.setFZ(nodeB.getFZ() - attractiveForce*deltaZ/distance);
                nodeA.setFX(nodeA.getFX() + attractiveForce*deltaX/distance);
                nodeA.setFY(nodeA.getFY() + attractiveForce*deltaY/distance);
                nodeA.setFZ(nodeA.getFZ() + attractiveForce*deltaZ/distance);
            }

            // move each node by the appropriate amount.
            // And then reset the forces on the node for the next main loop.
            Iterator nodeIt = _graph.getNodeIterator();
            while (nodeIt.hasNext()) {
                Node node = (Node) nodeIt.next();
                node.setX(node.getX() + C4*node.getFX());
                node.setY(node.getY() + C4*node.getFY());
                node.setZ(node.getZ() + C4*node.getFZ());
                node.setFX(0);
                node.setFY(0);
                node.setFZ(0);
            }

        }

    }

}

