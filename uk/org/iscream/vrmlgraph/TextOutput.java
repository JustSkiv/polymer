package uk.org.iscream.vrmlgraph;

import java.io.*;
import java.util.*;

/**
 *  This class is used to create a text file representation of the current
 *  contents of the GraphData object that was passed via its constructor
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class TextOutput {

    private GraphData _graph;

    private String _title = "3D Spring Embedder Graph - Output results.";
    private String _info = "Created using a program by Paul James Mutton, copyright 2000.";


    /**
     *  Constructor for the TextOutput object 
     *
     * @param  graph  The GraphData object to be output as text.
     */
    public TextOutput(GraphData graph) {
        _graph = graph;
    }


    /**
     *  Write a representation of the current GraphData oject to
     *  a text file
     *
     * @param  outFile        The filename to be output to
     * @exception  Exception  An exception is throw if an error occurs
     */
    public void writeToFile(String outFile) throws Exception {

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(outFile));
        }
        catch (IOException e) {
            throw new Exception("Could not write the text file: " + e);
        }

        TextLayout layout = new TextLayout(bw);

        try {
            layout.writeHeader(_title, _info);

            layout.writeNodeHeader();

            // write each node
            Iterator nodeIt = _graph.getNodeIterator();
            while (nodeIt.hasNext()) {
                Node node = (Node) nodeIt.next();
                layout.writeNode(node.getX(), node.getY(), node.getZ(), node.getLabel());
            }

            layout.writeEdgeHeader();

            // write each edge
            Iterator edgeIt = _graph.getEdgeIterator();
            while (edgeIt.hasNext()) {
                Edge edge = (Edge) edgeIt.next();
                layout.writeEdge(edge.getFrom().getLabel(), edge.getTo().getLabel());
            }

            bw.flush();
            bw.close();

        }
        catch (IOException e) {
            throw new Exception("Could not finish writing the text file: " + e);
        }

    }
}

