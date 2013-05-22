package uk.org.iscream.vrmlgraph;

/**
 *  The Node class acts as an object to store information about nodes.
 *
 * @author     Paul James Mutton, paul@i-scream.org.uk
 * @created    18 December 2000 
 */
public class Node {

    // attributes
    private String _label;
    private double _x, _y, _z;
    private double _fx, _fy, _fz;


    /**
     *  Constructor for the Node object 
     *
     * @param  label  The label of the node
     * @param  x      The x coordinate of the node
     * @param  y      The y coordinate of the node
     * @param  z      The z coordinate of the node
     */
    public Node(String label, double x, double y, double z) {
                _label = label;
        _x = x;
        _y = y;
        _z = z;
        _fx = 0;
        _fy = 0;
        _fz = 0;
    }


    // mutators
    /**
     *  Sets the X attribute of the Node object 
     *
     * @param  x  The new x coordinate of the node
     */
    public void setX(double x) {
        _x = x;
    }


    /**
     *  Sets the Y attribute of the Node object 
     *
     * @param  y  The new y coordinate of the node
     */
    public void setY(double y) {
        _y = y;
    }


    /**
     *  Sets the Z attribute of the Node object 
     *
     * @param  z  The new z coordinate of the node
     */
    public void setZ(double z) {
        _z = z;
    }


    /**
     *  Sets the FX attribute of the Node object 
     *
     * @param  fx  The new x component of the force
     */
    public void setFX(double fx) {
        _fx = fx;
    }


    /**
     *  Sets the FY attribute of the Node object 
     *
     * @param  fy  The new y component of the force
     */
    public void setFY(double fy) {
        _fy = fy;
    }


    /**
     *  Sets the FZ attribute of the Node object 
     *
     * @param  fz  The new z component of the force 
     */
    public void setFZ(double fz) {
        _fz = fz;
    }


    // accessors
    /**
     *  Gets the Label attribute of the Node object 
     *
     * @return    The label of the node
     */
    public String getLabel() {
        return _label;
    }


    /**
     *  Gets the X attribute of the Node object 
     *
     * @return    The X coordinate value 
     */
    public double getX() {
        return _x;
    }


    /**
     *  Gets the Y attribute of the Node object 
     *
     * @return    The Y coordinate value 
     */
    public double getY() {
        return _y;
    }


    /**
     *  Gets the Z attribute of the Node object 
     *
     * @return    The Z coordinate value 
     */
    public double getZ() {
        return _z;
    }


    /**
     *  Gets the FX attribute of the Node object 
     *
     * @return    The force currently acting along the x axis value 
     */
    public double getFX() {
        return _fx;
    }


    /**
     *  Gets the FY attribute of the Node object 
     *
     * @return    The force currently acting along the y axis value 
     */
    public double getFY() {
        return _fy;
    }


    /**
     *  Gets the FZ attribute of the Node object 
     *
     * @return    The force currently acting along the z axis value 
     */
    public double getFZ() {
        return _fz;
    }
}

