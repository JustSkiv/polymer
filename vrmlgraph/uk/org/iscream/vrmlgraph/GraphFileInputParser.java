package uk.org.iscream.vrmlgraph;

import java.io.*;
import java.util.*;

/**
 *  An instance of this class can be used to parse a text file
 *  that contains a description of connected nodes.  This information
 *  is then added to the GraphData object that was passed via the
 *  constructor of this class.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class GraphFileInputParser {

    private GraphData _graph;
    
    // Limit the random Node placement from (0,0,0) to (20,20,20)
    private final double _randomSpace = 20.0;


    /**
     *  Constructor for the GraphFileInputParser object 
     *
     * @param  graph  A GraphData object to store the graph in.
     */
    public GraphFileInputParser(GraphData graph) {
        _graph = graph;
    }


    /**
     *  This method reads from the file specified in <code>inputFile</code>
     *  and parses the data into the GraphData object. Any unexpected
     *  results will cause an exception to be thrown.  Nodes are added
     *  to a random location between (0,0,0) and (20,20,20).
     *
     * @param  inputFile      The filename to read from
     * @exception  Exception  An exception is thrown if any errors occur
     */
    public void populateFromFile(String inputFile) throws Exception {

        String s = "";
        int lineNumber = 0;

        try {

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            s = br.readLine();

            while (s != null) {

                s = s.trim();
                lineNumber++;

                // ignore blank lines.
                if (s.equals("")) {
                    s = br.readLine();
                    continue;
                }

                // ignore lines that start with a comment marker ("#").
                if (s.substring(0, 1).equals("#")) {
                    s = br.readLine();
                    continue;
                }

                StringTokenizer tokenizer = new StringTokenizer(s, " ");
                String from = tokenizer.nextToken();
                String to = tokenizer.nextToken();

                // disallow edges that connect a node to itself.
                if (from.equals(to)) {
                    String errorMsg = "Illegal syntax on line " + lineNumber
                             + " of " + inputFile + ": \"" + s + "\" - Edges"
                             + " are not allowed to connect a node to"
                             + "itself.";
                    throw new Exception(errorMsg);
                }

                Node nodeA = null;
                Node nodeB = null;

                // See if the node's already been stored...
                boolean fromDefined = false;

                // See if the node's already been stored...
                boolean toDefined = false;
                Iterator nodes = _graph.getNodeIterator();
                while (nodes.hasNext()) {
                    Node node = (Node) nodes.next();
                    if (node.getLabel().equals(from)) {
                        nodeA = node;
                        fromDefined = true;
                        break;
                    }
                }
                nodes = _graph.getNodeIterator();
                while (nodes.hasNext()) {
                    Node node = (Node) nodes.next();
                    if (node.getLabel().equals(to)) {
                        nodeB = node;
                        toDefined = true;
                        break;
                    }
                }

                if (!fromDefined) {
                    double x = Math.random()*_randomSpace;
                    double y = Math.random()*_randomSpace;
                    double z = Math.random()*_randomSpace;
                    nodeA = new Node(from, x, y, z);
                    _graph.addNode(nodeA);
                }

                if (!toDefined) {
                    double x = Math.random()*_randomSpace;
                    double y = Math.random()*_randomSpace;
                    double z = Math.random()*_randomSpace;
                    nodeB = new Node(to, x, y, z);
                    _graph.addNode(nodeB);
                }

                _graph.addEdge(new Edge(nodeA, nodeB));

                s = br.readLine();
            }

            br.close();

        }
        catch (NoSuchElementException e) {
            String errorMsg = "Illegal syntax on line " + lineNumber + " of "
                     + inputFile + ": \"" + s + "\"";
            throw new Exception(errorMsg);
        }
        catch (IOException e) {
            throw new Exception("IOException while reading the input file: "+e);
        }
        catch (Exception e) {
            throw new Exception("Exception while reading the input file: "+e);
        }

    }

}

