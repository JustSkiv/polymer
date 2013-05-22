package uk.org.iscream.vrmlgraph;

import java.io.*;
import java.util.*;

/**
 *  This class is used to output a VRML representation of the currently
 *  stored GraphData object.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class VRMLOutput {

    private GraphData _graph;

    private double _fontSize = 0.6;
    private String _fontColour = "0 0 1";
    private double _nodeSize = 0.5;
    private String _nodeColour = "1 0.95 0.7";
    private double _nodeTransparency = 0.0;
    private String _backgroundColour = "0.9 0.9 1";
    private double _zDropBack = 20.0;
    private double _edgeWidth = 0.1;
    private String _edgeColour = "0.5 0.5 1";
    private double _edgeTransparency = 0.0;
    private String _title = "3D Spring Embedder Graph";
    private String _info = "Produced using 3D Spring Embedder software written by Paul James Mutton, copyright 2000.";
    private boolean _showLabels = false;


    /**
     *  Constructor for the VRMLOutput object 
     *
     * @param  graph  The GraphData object to be represented as VRML
     */
    public VRMLOutput(GraphData graph) {
        _graph = graph;
    }


    /**
     *  Sets the fontSize attribute of the VRMLOutput object 
     *
     * @param  fontSize  The new font size to be used in VRML output.
     */
    public void setFontSize(double fontSize) {
        _fontSize = fontSize;
    }


    /**
     *  Sets the fontColour attribute of the VRMLOutput object 
     *
     * @param  fontColour  The new font colour to be used in VRML output
     */
    public void setFontColour(String fontColour) {
        _fontColour = fontColour;
    }


    /**
     *  Sets the nodeSize attribute of the VRMLOutput object 
     *
     * @param  nodeSize  The new size of the Nodes to be drawn.
     */
    public void setNodeSize(double nodeSize) {
        _nodeSize = nodeSize;
    }


    /**
     *  Sets the nodeColour attribute of the VRMLOutput object 
     *
     * @param  nodeColour  The new colour of the Nodes to be drawn
     */
    public void setNodeColour(String nodeColour) {
        _nodeColour = nodeColour;
    }


    /**
     *  Sets the nodeTransparency attribute of the VRMLOutput object 
     *
     * @param  nodeTransparency  The new transparency of the nodes
     */
    public void setNodeTransparency(double nodeTransparency) {
        _nodeTransparency = nodeTransparency;
    }


    /**
     *  Sets the backgroundColour attribute of the VRMLOutput object 
     *
     * @param  backgroundColour  The new background colour of the VRML scene
     */
    public void setBackgroundColour(String backgroundColour) {
        _backgroundColour = backgroundColour;
    }


    /**
     *  Sets the zDropBack attribute of the VRMLOutput object 
     *
     * @param  zDropBack  Set the distance between the viewer and the centre of the graph
     */
    public void setZDropBack(double zDropBack) {
        _zDropBack = zDropBack;
    }


    /**
     *  Sets the edgeWidth attribute of the VRMLOutput object 
     *
     * @param  edgeWidth  Set the thickness of the edges to be drawn
     */
    public void setEdgeWidth(double edgeWidth) {
        _edgeWidth = edgeWidth;
    }


    /**
     *  Sets the edgeColour attribute of the VRMLOutput object 
     *
     * @param  edgeColour  Set the colour of the edges to be drawn
     */
    public void setEdgeColour(String edgeColour) {
        _edgeColour = edgeColour;
    }


    /**
     *  Sets the edgeTransparency attribute of the VRMLOutput object 
     *
     * @param  edgeTransparency  Set the transparency of the edges
     */
    public void setEdgeTransparency(double edgeTransparency) {
        _edgeTransparency = edgeTransparency;
    }


    /**
     *  Sets the title attribute of the VRMLOutput object 
     *
     * @param  title  The new title of the VRML scene
     */
    public void setTitle(String title) {
        _title = title;
    }


    /**
     *  Specify whether or not Node labels are to be drawn on the VRML scene.
     *
     * @param  b  Set to true to drawn Node labels.  Default is false.
     */
    public void showLabels(boolean b) {
        _showLabels = b;
    }
    

    /**
     *  Writes a representation of the current graph to a VRML file
     *
     * @param  outFile        The filename to output to
     * @exception  Exception  An Exception is thrown if an error occurs.
     */
    public void writeToFile(String outFile) throws Exception {

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(outFile));
        }
        catch (IOException e) {
            throw new Exception("Could not write the VRML file: " + e);
        }

        VRMLLayout layout = new VRMLLayout(bw, _fontSize, _fontColour, _nodeSize, _nodeColour, _nodeTransparency, _edgeWidth, _edgeColour, _edgeTransparency, _showLabels);

        try {
            layout.writeHeader();
            layout.writeWorldInfo(_title, _info);
            layout.writeViewpoint(_zDropBack);
            layout.writeBackground(_backgroundColour);

            // 'draw' each node.
            Iterator nodeIt = _graph.getNodeIterator();
            while (nodeIt.hasNext()) {
                Node node = (Node) nodeIt.next();
                layout.writeNode(node.getX(), node.getY(), node.getZ(), node.getLabel());
            }

            // 'draw' each edge after it's rotated into place.
            Iterator edgeIt = _graph.getEdgeIterator();
            while (edgeIt.hasNext()) {
                Edge edge = (Edge) edgeIt.next();
                Node nodeA = edge.getFrom();
                Node nodeB = edge.getTo();
                double midX = (nodeA.getX() + nodeB.getX())/2;
                double midY = (nodeA.getY() + nodeB.getY())/2;
                double midZ = (nodeA.getZ() + nodeB.getZ())/2;
                double deltaX = nodeB.getX() - nodeA.getX();
                double deltaY = nodeB.getY() - nodeA.getY();
                double deltaZ = nodeB.getZ() - nodeA.getZ();
                double distanceSquared = deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ;
                double distance = Math.sqrt(distanceSquared);
                double rotateX = -Math.atan(-deltaZ/deltaY);
                double rotateFunny = Math.asin(deltaX/distance);
                layout.writeEdge(midX, midY, midZ, distance, _edgeWidth, deltaZ, deltaY, rotateX, rotateFunny);
            }

            layout.writeFooter();

            bw.flush();
            bw.close();

        }
        catch (IOException e) {
            throw new Exception("Could not finish writing the VRML file: " + e);
        }

    }

}

