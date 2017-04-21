package ad.hoc.network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener {
    private static final int NodeSize = 5;
    private static final int WINWIDTH = 600;
    private static final int WINHEIGHT = 620;
    private World world;
    private static int numNodes = 0;
    private static int range = 0;
    private GPanel map;
    private JTextField num_text;
    private JTextField range_text;
    private JPanel center;
    private boolean flag = true;
    private static final String Options[] ={"t = 3 for all nodes","t = 2 for all nodes","Different t Value"};
    public GUI(){
        super();
        setSize(WINWIDTH, WINHEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setUndecorated(true);
        
        this.setFocusable(true);
        this.setTitle("Network");
        this.setLayout(new BorderLayout());
        world = new World(numNodes);
        //North 
        JPanel north = new JPanel();
        north.setBackground(Color.LIGHT_GRAY);
        add(north,BorderLayout.NORTH);
        JLabel north_label = new JLabel("<html><h1>The Network</h1></html>", SwingConstants.CENTER);
        north_label.setVerticalAlignment(SwingConstants.CENTER);
        north.add(north_label);
        add(north,BorderLayout.NORTH);
        
        //Center 
        center = new JPanel();
        center.setBackground(Color.white);
        map = new GPanel();
        center.add(map);
        Handlerclass handler = new Handlerclass();
        map.addMouseListener(handler);
        map.addMouseMotionListener(handler);
        add(center,BorderLayout.CENTER);
        
               
        //South
        JPanel south = new JPanel();
        south.setBackground(Color.LIGHT_GRAY);
        south.setLayout(new BorderLayout());
        //Buttons
        JLabel num_label = new JLabel("Set the number of nodes: ");
        num_text = new JTextField("200", 5);
        JLabel range_label = new JLabel("Set the radio range of nodes: ");
        range_text = new JTextField("15", 3);
        
        JPanel south2 = new JPanel();
        south2.setLayout(new GridLayout(2,2));
        south2.setBackground(Color.LIGHT_GRAY);
        south2.add(num_label, BorderLayout.WEST);
        south2.add(num_text, BorderLayout.WEST);
        south2.add(range_label, BorderLayout.EAST);
        south2.add(range_text, BorderLayout.EAST);
        south.add(south2,BorderLayout.WEST);
        JButton next = new JButton("Start");
        JButton About = new JButton("About");
        JButton T = new JButton("Node");
        T.addActionListener(this);
        T.setActionCommand("T");
        T.setBackground(Color.LIGHT_GRAY);
        About.addActionListener(this);
        About.setActionCommand("About");
        About.setBackground(Color.LIGHT_GRAY);
        next.setBackground(Color.LIGHT_GRAY);
        next.addActionListener(this);
        next.setActionCommand("Next");
        south.add(next, BorderLayout.CENTER);
        
        JPanel south3 = new JPanel();
        south3.setLayout(new GridLayout(1,2));
        south3.add(About,BorderLayout.EAST);
        south3.add(T,BorderLayout.WEST);
        south.add(south3,BorderLayout.EAST);
        add(south,BorderLayout.SOUTH);
        
        //East
        JPanel east = new JPanel();
        east.setBackground(Color.LIGHT_GRAY);
        add(east,BorderLayout.EAST);
        
        //West
        JPanel west = new JPanel();
        west.setBackground(Color.LIGHT_GRAY);
        add(west,BorderLayout.WEST);
        
        this.setVisible(true);
    }   
    @Override
    public void actionPerformed(ActionEvent e){ 
        String action = e.getActionCommand();
        switch (action) {
            case "About":
                String info = "A Locally-Adjustable Planar Stracture for Adaptive Topology Control in Wireless Ad Hoc Networks.\n\nInfo:\n"
                        + "World Size 100x100\nNode size 1x1.\n"
                        + "With light gray are alla the available connections.\n"
                        + "With red are connections generated from 'TAP' algorithm.\n"
                        + "Nodes at center of the world, inside a circle of radius 10, have different value of t. "
                        + "They are colored by blue.\n"
                        + "Change t value by clivking 'Node'.\n"
                        + "All nodes have the same 'a' value.\n"
                        + "Click at any node too see informations.\n";
                JOptionPane.showMessageDialog(null, info, "Information About The Program ", JOptionPane.INFORMATION_MESSAGE);
                
                break;
            case "Next":
                try {
                    numNodes = Integer.parseInt(num_text.getText());
                    range = Integer.parseInt(range_text.getText());
                    
                    world = new World(numNodes,range*5);
                    //System.out.println("Start drwing");
                    flag=true;
                    center.repaint();
                               
                }catch(NumberFormatException ex){}
                break;
            case "T":
                String tvalue = (String) JOptionPane.showInputDialog(null, 
                    "Change t value",
                    "Node properties",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    Options, 
                    Options[2]);
                if(tvalue!=null){
                    ChangeT(tvalue);
                    //center.repaint();
                }
                break;
        }
    }
    private void ChangeT(String option){
        int i;
        Node temp;
        if(option.compareTo(Options[0]) == 0){
            flag =false;               
            for(i=0;i<numNodes;i++){
                temp = world.getNode(i);  
                temp.Clear();
                temp.TapAlgorithm(3);
            }
        }else if (option.compareTo(Options[1]) == 0){
            flag=false;
            for(i=0;i<numNodes;i++){
                temp = world.getNode(i);
                temp.Clear();
                temp.TapAlgorithm(2);
            }
        }else{
            flag=true;
            for(i=0;i<numNodes;i++){
                temp = world.getNode(i);
                temp.Clear();
                if (Math.sqrt((temp.getY()-250)*(temp.getY()-250) + (temp.getX()-250)*(temp.getX()-250)) <=100) 
                    temp.TapAlgorithm(3);
                else
                    temp.TapAlgorithm(1);
            }
        }
        center.repaint();
    
    }
    private class Handlerclass implements MouseListener, MouseMotionListener{
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            //search for node x,y
            String info = world.getNodeInfo(x, y);
            if (info.compareTo("") != 0)
                JOptionPane.showMessageDialog(null,info , "Information About Node ", JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    class GPanel extends JPanel {
    
        public GPanel(){
            this.setPreferredSize(new Dimension(510, 510));
        }
        @Override
        public void paintComponent(Graphics g){
            g.drawRect(0,0,500,500);
            int i;
            Node temp;
                           
            for(i=0;i<numNodes;i++){
                temp = world.getNode(i);
                g.fillOval(temp.getX(),temp.getY(),NodeSize,NodeSize);
                
            }
            
            
            //draw all the conections betwenn nodes
            int n;
            Node Near;
            for(i=0;i<numNodes;i++){
                //Draw the connections
                temp = world.getNode(i);
                while((n = temp.takeNext()) != -1){
                    Near = world.getNode(n);
                    g.setColor(Color.lightGray);
                    g.drawLine(temp.getX()+ NodeSize/2,temp.getY()+ NodeSize/2,Near.getX()+ NodeSize/2,Near.getY()+ NodeSize/2 );
                }
            }
            
            //draw the Tap connections
            for(i=0;i<numNodes;i++){
                //Draw the connections
                temp = world.getNode(i);
                while((n = temp.TapNext()) != -1){
                    Near = world.getNode(n);
                    g.setColor(Color.red);
                    g.drawLine(temp.getX()+ NodeSize/2,temp.getY()+ NodeSize/2,Near.getX()+ NodeSize/2,Near.getY()+ NodeSize/2 );                    
                }
                
            }
            
            //draw again its node
            for(i=0;i<numNodes;i++){
                temp = world.getNode(i);
                if ((Math.sqrt((temp.getY()-250)*(temp.getY()-250) + (temp.getX()-250)*(temp.getX()-250)) <=100) && (flag==true)){
                    g.setColor(Color.blue);
                    g.fillOval(temp.getX(),temp.getY(),NodeSize,NodeSize);
                }else {                  
                    g.setColor(Color.BLACK);
                    g.fillOval(temp.getX(),temp.getY(),NodeSize,NodeSize);
                }
            }
        }
    }
    
}
