/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.hoc.network;
import java.util.Random;
/**
 *
 * @author antonis
 */
public class World {
    private static final int WORLD_W = 100;
    private static final int WORLD_H = 100;
    private static Node[] n;
    private static int num;
    private int max = 75;
    
    public World(int num, int range){
        max =range;
        int i,x,y;
        this.num=num;
        Random gen = new Random();
        //Initiliaze Nodes
        n = new Node[num];
        for (i=0;i<num;i++){
            //place each node randomly
            x= gen.nextInt(WORLD_W)*5;
            y= gen.nextInt(WORLD_H)*5;
            n[i] = new Node(x,y,i);
        }
        setNeighborhood();
        
        for(i=0;i<num;i++){
            if (Math.sqrt((n[i].getY()-250)*(n[i].getY()-250) + (n[i].getX()-250)*(n[i].getX()-250)) <=100)
                n[i].TapAlgorithm(3);
            else
                n[i].TapAlgorithm(1);
        }
    }
    public World(int num){
        World.n = new Node[num];
        int i,x,y;
        this.num=num;
        Random gen = new Random();
        //Initiliaze Nodes
        for (i=0;i<num;i++){
            //place each node randomly
            x= gen.nextInt(WORLD_W)*5;
            y= gen.nextInt(WORLD_H)*5;
            n[i] = new Node(x,y,i);
        }
        setNeighborhood();
    }
    /**
     * Search if there is a node at x,y
     * @param x coordinates
     * @param y coordinates
     * @return information about this node
     */
    public String getNodeInfo(int x, int y){
        int i;
        x= x - x%5;
        y= y - y%5;
        for(i=0;i<num;i++){
            if(n[i].getX() != x)
                continue;
            if (n[i].getY() != y)
                continue;
            return ("Node id: "+ i+"\n" + n[i].getInfo());
        }
        
        return "";
    }
    
    /** 
     * 
     * @param pos id
     * @return Node object
     */
    public Node getNode(int pos){
        return n[pos];
    }
   
    private void setNeighborhood(){
        int i,j;
        int x,y,x2,y2;
        double distance;
        for(i=0;i<num;i++){
            x = n[i].getX();
            y = n[i].getY();
            for(j=0;j<num;j++){
                if(i==j)
                    continue;
                x2 = n[j].getX();
                y2 = n[j].getY();
                distance = Math.sqrt((y2-y)*(y2-y) + (x2-x)*(x2-x));
                //System.out.println(distance + " -- "+ (y2-y)*(y2-y) + (x2-x)*(x2-x) +"-"+ x +"-"+x2 +"-"+ y +"-"+ y2);
                if (distance>max) // +++++++
                    continue;
                n[i].addN(j, distance,x2,y2);                
            }
        }
    }
}