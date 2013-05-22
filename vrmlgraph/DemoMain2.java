import uk.org.iscream.vrmlgraph.*;

/**
 *  This class provides a main method which uses the GraphFileInputParser to
 *  read a graph description from the text file "demo2_input.txt".
 *  It then outputs a text file to describe the new spring-embedded graph,
 *  along with a nice funky VRML file as well.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
class DemoMain2 {

    private final static String _inputTextFile = "demo2_input.txt";
    private final static String _outputTextFile = "demo2_output.txt";
    private final static String _outputVRMLFile = "demo2_output.wrl";


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

        // Perform all of the calculations to move the graph into a nice layout.
        NodeMover nodeMover = new NodeMover(graph);
        nodeMover.move(1000);

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
        try {
            vrmlOutput.writeToFile(_outputVRMLFile);
        }
        catch (Exception e) {
            System.out.println("Failed to write the VRML file: " + e);
            System.exit(1);
        }

    }

}

