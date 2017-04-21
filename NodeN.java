package ad.hoc.network;

/**
 *
 * @author antonis
 */
public class NodeN implements Comparable<NodeN>{
    private final int v; //id
    private final double d;
    private final int x,y;
    
    /**
     * Constructor for nodes inside neighborhood
     * 
     * @param v id of neighbor
     * @param d distance between this node and pramary node
     * @param x coordinates
     * @param y coordinates
     */
    public NodeN(int v, double d, int x, int y){
        this.v=v;
        this.d=d;
        this.x=x;
        this.y=y;
    }
    /**
     * 
     * @return id 
     */
    public int getV(){
        return v;
    }
    /**
     * 
     * @return distance between initial node and this
     */
    public double getD(){
        return d;
    }
    /**
     * Calculate the destance
     * @param v NodeN object
     * @return distance beteween the two nodes
     */
    public double Distance(NodeN v){
        return Math.sqrt((v.gety()-y)*(v.gety()-y) + (v.getx()-x)*(v.getx()-x));
    }
    /**
     * 
     * @return coordinates 
     */
    public int getx(){
        return x;
    }
    /**
     * 
     * @return coordinates
     */
    public int gety(){
        return y;
    }
    /**
     * Compare this node with t
     * @param t NodeN object
     * @return 0 if equal distance, 1 for this or -1 for t
     */
    @Override
    public int compareTo(NodeN t) {
        if (this.d > t.getD())
            return 1;
        else if (this.d < t.getD())
            return -1;
        return 0;
    }
}
