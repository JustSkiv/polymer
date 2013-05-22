import uk.org.iscream.vrmlgraph.*;

/**
 * This class provides a main method that is a very simple illustration of
 * how to make and output a simple 3-D graph within a program.  This
 * example outputs a simple text file and also a 3-D VRML file to describe
 * the contents of the GraphData after the spring embedding has taken place.
 *
 * @author Paul James Mutton, paul@i-scream.org.uk
 * @created 18 December 2000
 */
class DemoMain1 {

  private final static String _outputTextFile = "demo1_output.txt";
  private final static String _outputVRMLFile = "demo1_output.wrl";


  /**
   * The demo main method
   *
   * @param args The command line arguments
   * @throws Exception This method throws an exception if an error occurs
   */
  public static void main(String[] args) throws Exception {

    // Create a GraphData object to store our graph in.
    GraphData graph = new GraphData();

    // Create some nodes for our graph.
    Node nodeA = new Node("A", 0.0, 0.0, 0.0);
    Node nodeB = new Node("B", 1.0, 1.0, 1.0);
    Node nodeC = new Node("C", 1.0, 0.0, 1.0);

    // Now create some edges for our graph.
    // Edges merely keep a track of which nodes are connected.
    Edge edge1 = new Edge(nodeA, nodeB);
    Edge edge2 = new Edge(nodeB, nodeC);

    // Now add these nodes and edges to the graph!
    graph.addNode(nodeA);
    graph.addNode(nodeB);
    graph.addNode(nodeC);

    graph.addEdge(edge1);
    graph.addEdge(edge2);

    // Perform 100 calculations to move the graph into a nice layout.
//    NodeMover nodeMover = new NodeMover(graph);
//    nodeMover.move(100);

    // Center the graph about (0,0,0).
//    ViewCenterer viewCenterer = new ViewCenterer(graph);
//    viewCenterer.center();

    System.out.println("Outputting Text to " + _outputTextFile);

    // Create a text file describing the newly located nodes.
    TextOutput textOutput = new TextOutput(graph);
    textOutput.writeToFile(_outputTextFile);

    System.out.println("Outputting VRML scene to " + _outputVRMLFile);

    // Create a 3-D VRML scene of the new graph.
    VRMLOutput vrmlOutput = new VRMLOutput(graph);
    vrmlOutput.writeToFile(_outputVRMLFile);

  }

}

