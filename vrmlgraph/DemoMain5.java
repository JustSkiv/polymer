import uk.org.iscream.vrmlgraph.*;

/**
 *  Another class providing a main method for demonstrating the use
 *  of the GraphData utilities.  Some of the default values of the
 *  VRMLOutput class have been overridden again...
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
class DemoMain5 {

    private final static String _inputTextFile = "demo5_input.txt";
    private final static String _outputTextFile = "demo5_output.txt";
    private final static String _outputVRMLFile = "demo5_output.wrl";


    /**
     *  The demo main method
     *
     * @param  args  The command line arguments 
     */
    public static void main(String[] args) {

        // Create a GraphData object to store our graph in.
        GraphData graph = new GraphData();

        // Read the contents on the input file into the GraphData object.
        GraphFileInputParser parser = new GraphFileInputParser(graph);
        try {
            parser.populateFromFile(_inputTextFile);
        }
        catch (Exception e) {
            System.out.println("Could not populate the graph data: " + e);
            System.exit(1);
        }

        // Perform ten thousand calculations(!)
        NodeMover nodeMover = new NodeMover(graph);
        nodeMover.move(10000);

        // Center the graph about (0,0,0).
        ViewCenterer viewCenterer = new ViewCenterer(graph);
        viewCenterer.center();

        System.out.println("Outputting Text to "+_outputTextFile);

        // Create a text file describing the newly located nodes.
        TextOutput textOutput = new TextOutput(graph);
        try {
            textOutput.writeToFile(_outputTextFile);
        }
        catch (Exception e) {
            System.out.println("Failed to write the text file: " + e);
            System.exit(1);
        }

        System.out.println("Outputting VRML scene to "+_outputVRMLFile);

        // Create a 3-D VRML scene of the new graph.
        VRMLOutput vrmlOutput = new VRMLOutput(graph);
        vrmlOutput.setBackgroundColour("0 0 0");
        vrmlOutput.setEdgeTransparency(0.5);
        vrmlOutput.setEdgeWidth(0.2);
        vrmlOutput.setEdgeColour("1 0.5 0.5");
        vrmlOutput.setNodeColour("0.5 0.5 1");
        vrmlOutput.setNodeSize(1.0);
        try {
            vrmlOutput.writeToFile(_outputVRMLFile);
        }
        catch (Exception e) {
            System.out.println("Failed to write the VRML file: " + e);
            System.exit(1);
        }

    }

}

