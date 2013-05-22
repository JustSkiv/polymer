package uk.org.iscream.vrmlgraph;

import java.io.*;

/**
 *  This class contains layout methods for outputting VRML
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class VRMLLayout {

    private BufferedWriter _bw;

    private double _fontSize;
    private String _fontColour;
    private double _nodeSize;
    private String _nodeColour;
    private double _nodeTransparency;
    private double _edgeWidth;
    private String _edgeColour;
    private double _edgeTransparency;
    private boolean _showLabels;


    /**
     *  Constructor for the VRMLLayout object 
     *
     * @param  bw                The BufferedWriter to write to
     * @param  fontSize          The font size for Node labels
     * @param  fontColour        The font colour for Node labels
     * @param  nodeSize          The size of Nodes
     * @param  nodeColour        The colour of Nodes
     * @param  nodeTransparency  The transparency of Nodes
     * @param  edgeWidth         The thickness of Edges
     * @param  edgeColour        The colour of Edges
     * @param  edgeTransparency  The transparency of Edges
     * @param  showLabels        Specifies whether or not labels are to be displayed on the graph
     */
    public VRMLLayout(BufferedWriter bw, double fontSize, String fontColour, double nodeSize, String nodeColour, double nodeTransparency, double edgeWidth, String edgeColour, double edgeTransparency, boolean showLabels) {
        _bw = bw;
        _fontSize = fontSize;
        _fontColour = fontColour;
        _nodeSize = nodeSize;
        _nodeColour = nodeColour;
        _nodeTransparency = nodeTransparency;
        _edgeWidth = edgeWidth;
        _edgeColour = edgeColour;
        _edgeTransparency = edgeTransparency;
        _showLabels = showLabels;
    }


    /**
     *  Writes a VRML version 2.0 header followed by a blank line
     *
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeHeader() throws IOException {
        writeLine("#VRML V2.0 utf8");
        writeLine("");
        writeLine("#Please view this file in a VRML viewer.");
        writeLine("#You can obtain a free one from http://www.cai.com/cosmo/");
        writeLine("");
    }


    /**
     *  Writes WorldInfo to the VRML file
     *
     * @param  title            The title of the VRML world
     * @param  info             Information about the VRML world
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeWorldInfo(String title, String info) throws IOException {
        writeLine("WorldInfo {");
        writeLine("    title \"" + title + "\"");
        writeLine("    info \"" + info + "\"");
        writeLine("}");
        writeLine("");
    }


    /**
     *  Writes the initial viewpoint into the VRML document
     *
     * @param  zDropBack        The distance between the viewer and the origin
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeViewpoint(double zDropBack) throws IOException {
        writeLine("Viewpoint {");
        writeLine("    description \"pjm2\"");
        writeLine("    position 0.0 0.0 " + zDropBack);
        writeLine("}");
        writeLine("");
    }


    /**
     *  Writes the Background into the VRML document
     *
     * @param  colour           The background colour of the VRML world
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeBackground(String colour) throws IOException {
        writeLine("Background {");
        writeLine("    skyColor " + colour);
        writeLine("}");
        writeLine("");
    }


    /**
     *  Writes a Node to the VRML document
     *
     * @param  x                The X coordinate of the Node
     * @param  y                The Y coordinate of the Node
     * @param  z                The Z coordinate of the Node
     * @param  label            The label of the Node to be drawn
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeNode(double x, double y, double z, String label) throws IOException {
        writeLine("Transform {");
        writeLine("    translation " + x + " " + y + " " + z);
        writeLine("    children [");
        if (_showLabels) {
            writeLine("        Shape {");
            writeLine("            appearance Appearance {");
            writeLine("                material Material {");
            writeLine("                    diffuseColor " + _fontColour);
            writeLine("                }");
            writeLine("            }");
            writeLine("            geometry Text {");
            writeLine("                string [ \"" + label + "\" ]");
            writeLine("                fontStyle FontStyle {");
            writeLine("                    size " + _fontSize);
            writeLine("                }");
            writeLine("            }");
            writeLine("        }");
        }
        writeLine("        Shape {");
        writeLine("            appearance Appearance {");
        writeLine("                material Material {");
        writeLine("                    diffuseColor " + _nodeColour);
        writeLine("                    transparency " + _nodeTransparency);
        writeLine("                }");
        writeLine("            }");
        writeLine("            geometry Sphere {");
        writeLine("                radius " + _nodeSize);
        writeLine("            }");
        writeLine("        }");
        writeLine("    ]");
        writeLine("}");
    }


    /**
     *  Writes an Edge to the VRML document
     *
     * @param  midX             The X coordinate of the midpoint of the Edge
     * @param  midY             The Y coordinate of the midpoint of the Edge
     * @param  midZ             The Z coordinate of the midpoint of the Edge
     * @param  distance         The distance between the centres of the two nodes that the edge connects
     * @param  width            The thickness of the Edge
     * @param  deltaZ           The Z axis rotation
     * @param  deltaY           The Y axis rotation
     * @param  rotateX          The X axis rotation
     * @param  rotateFunny      The rotation to perform after the X rotation has already been done.
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeEdge(double midX, double midY, double midZ, double distance, double width, double deltaZ, double deltaY, double rotateX, double rotateFunny) throws IOException {
        writeLine("Transform {");
        writeLine("    rotation 0 " + deltaZ + " " + (-deltaY) + " " + rotateFunny);
        writeLine("    translation " + midX + " " + midY + " " + midZ);
        writeLine("    children [");
        writeLine("        Transform {");
        writeLine("            rotation 1 0 0 " + rotateX);
        writeLine("            children [");
        writeLine("                Shape {");
        writeLine("                    appearance Appearance {");
        writeLine("                        material Material {");
        writeLine("                            diffuseColor " + _edgeColour);
        writeLine("                            transparency " + _edgeTransparency);
        writeLine("                        }");
        writeLine("                    }");
        writeLine("                    geometry Cylinder {");
        writeLine("                        height " + (distance - 2*_nodeSize));
        writeLine("                        radius " + _edgeWidth);
        writeLine("                    }");
        writeLine("                }");
        writeLine("            ]");
        writeLine("        }");
        writeLine("    ]");
        writeLine("}");
    }


    /**
     *  Write a footer to the VRML document
     *
     * @exception  IOException  Throws an IOException if an error occurs
     */
    public void writeFooter() throws IOException {
        writeLine("");
        writeLine("# End of file!");
    }


    /**
     *  Writes a String to the VRML document, followed by a new line.
     *
     * @param  line             The line to print to the file
     * @exception  IOException  Throws an IOException if an error occurs
     */
    private void writeLine(String line) throws IOException {
        _bw.write(line);
        _bw.newLine();
    }
}

