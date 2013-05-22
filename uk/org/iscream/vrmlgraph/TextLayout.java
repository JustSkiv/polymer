package uk.org.iscream.vrmlgraph;

import java.io.*;

/**
 *  This class contains layout methods for outputting text
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class TextLayout {

    private BufferedWriter _bw;


    /**
     *  Constructor for the TextLayout object 
     *
     * @param  bw  The BufferedWriter to write to
     */
    public TextLayout(BufferedWriter bw) {
        _bw = bw;
    }


    /**
     *  Writes a header to the text file, comprising of a title and info field
     *
     * @param  title            The title of the text file
     * @param  info             Any further information about the text file
     * @exception  IOException  Throws an IOException if an error occurs 
     */
    public void writeHeader(String title, String info) throws IOException {
        writeLine("# " + title);
        writeLine("# " + info);
        writeLine("");
    }


    /**
     *  Writes a header to preceed the Node information
     *
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeNodeHeader() throws IOException {
        writeLine("NODES");
    }


    /**
     *  Writes all of the Node data to the text file, one line per Node,
     *  with each field separated by a tab
     *
     * @param  x                The X coordinate of the Node
     * @param  y                The Y coordinate of the Node
     * @param  z                The Z coordinate of the Node
     * @param  label            The label of the Node
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeNode(double x, double y, double z, String label) throws IOException {
        writeLine(label + "\t" + x + "\t" + y + "\t" + z);
    }


    /**
     *  Writes a header to preceed the Edge information
     *
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeEdgeHeader() throws IOException {
        writeLine("EDGES");
    }


    /**
     *  Writes all of the Edge Data to the text file, one line per edge,
     *  with a tab separating the two Node labels
     *
     * @param  nodeA            The label of the first Node that this edge connects
     * @param  nodeB            The label of the second Node that this edge connects
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeEdge(String nodeA, String nodeB) throws IOException {
        writeLine(nodeA + "\t" + nodeB);
    }


    /**
     *  Writes a String to the text file followed by a new line
     *
     * @param  line             The line to print to the text file
     * @exception  IOException  Throws an IOException if an error occurs
     */
    private void writeLine(String line) throws IOException {
        _bw.write(line);
        _bw.newLine();
    }

}

