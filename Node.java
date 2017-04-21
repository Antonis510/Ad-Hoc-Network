package ad.hoc.network;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author antonis
 */
public class Node {
    private static final int ENERGY_MAX = 10;
    private static int RADIO_MAX = 75;
    private final int x,y;    
    private List<NodeN> neighborhood = new ArrayList<NodeN>();
    private List<NodeN> TapN = new ArrayList<NodeN>();
    private final int id;
    private double a=2;
    private int i = 0;
    private int next = 0;
    private int next2=0;
    private String info ="";
    private double t;
    /**
     * Constructor 
     * 
     * @param x
     * @param y
     * @param id 
     */
    public Node(int x, int y, int id){
        this.x= x;
        this.y =y;
        this.id = id;
         
    }
    /**
     * @return coordinates x  
     */
    public int getX(){
        return x;
    }    
    /**
     * @return coordinates y
     */
    public int getY(){
        return y;
    }    
    /**
     * @return max radio range 
     */
    public int getRadio(){
        return RADIO_MAX;
    }
    /**
     * Add node(id) with coordinates x,y to neighborhood
     * @param id
     * @param distance
     * @param x
     * @param y 
     */
    public void addN(int id, double distance, int x, int y){
        neighborhood.add(new NodeN(id,distance,x,y));
        i++;
    }
    
    /**
     * @return next neighbors id 
     */
    public int takeNext(){
        if (next>=i ){
            next=0;
            return -1;
        } 
        int t = (neighborhood.get(next)).getV();
        next++;
        return t;
    }
    
    /**
     * @return next neighbors id 
     */
    public int TapNext(){
        
        try{            
            int t = (TapN.get(next2)).getV();
            next2++;
            return t;
        }catch ( IndexOutOfBoundsException ex){
            return -1;
        }
        
    }
    public void Clear(){
        //next=0;
        next2=0;
        TapN.clear();
    }
    
    /**
     * Calculates the logical neighbors 
     * @param t algorith parameter
     */    
    public void TapAlgorithm(double t){
        //this u
        NodeN v,w;
        boolean flag;
        int i,j;  
        for (i=0;i<neighborhood.size();i++){
            flag = true;
            v = neighborhood.get(i); //v    check if v will be inside E(u)
            for(j=0;j<neighborhood.size();j++){
                if (i==j)
                    continue;
                w = neighborhood.get(j); //w   if exist w   
                if(w.getD() < v.getD()){
                    if((w.Distance(v))< v.getD()){
                        if ((t*pow(v.getD(),a)) > pow(w.getD(),a) + pow(w.Distance(v),a)){
                            //v will not be inside E(u)
                            flag=false;
                            break;
                        }
                    }
                }
            }
            if (flag) // add v to E(u)
                TapN.add(v);
        }
        if (t>2){            
            Collections.sort(TapN);     //Sorting to ascending order
            Collections.reverse(TapN);  // and then reverse order
            
            for(i=0;i<((TapN.size()-1)*(t-2));i++){
                v=TapN.get(i);
                if (search(v)){ //if there is an alternative path from this to v, remove v from the list
                    TapN.remove(i);
                }
            }
        }
        //TapN will be the set of neighbors
        info = "Node has t= "+ t + "\nNode has a= "+ a;
    }
    
    public String getInfo(){
        return info;
    }
    /**
     * Search for a path between this and v
     * @param v 
     * @return true if there is at least one path
     */
    private boolean search(NodeN v){
        int i;
        NodeN temp;
        //check if v is 1-hop neighbor of my neighbors
        //if the distance between one of 1-hop neighbors and v is smaller, then there is a path
        for(i=0;i<TapN.size();i++){
            temp = TapN.get(i);
            if (temp.equals(v))
                continue;
            if(temp.Distance(v) > v.getD())
                continue;
            else
                return true;
        }
        return false;
    }
    

}
