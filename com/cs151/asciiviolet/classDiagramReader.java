package com.cs151.asciiviolet;

import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;


import java.util.Scanner;
import java.util.ArrayList;

public class classDiagramReader
{
   public static void main(String args[])
   {
      String text ="[a]-[b]\n[a]-[c]\n[d]-[b]\n[d]-[c]\n[e]-[b]\n[e]-[f]\n[e]-[g]\n[g]-[f]\n[g]-[h]\n[g]-[i]" ;
      String text2 ="[a]-[b][a]-[c][d]-[b]";
      classDiagramReader a = new classDiagramReader();
      JFrame frame = new JFrame();

      Icon icon = new GraphIcon(a.read(text2));
      final JLabel label = new JLabel(icon);
      frame.setLayout(new FlowLayout());
      frame.add(label);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);

   }
   
   public classDiagramReader()
   {
   }
   
   public Graph read(String input)
   {
      Scanner s = new Scanner(input);
      Graph aGraph = new Graph(80);
      nodes = new ArrayList<>();
      String comand = "";
      
      while(s.hasNext())
      {
         comand = s.nextLine();
         int count = 0;
         
         while(count < comand.length())
         {
            //find nodeA
            while(count < comand.length() && comand.charAt(count) != '[' )
            {  
               count++;
            }         
            String nameA = "";
            count++;
         
            while(count < comand.length() && comand.charAt(count) != ']')
            {
               nameA += comand.charAt(count);
               count++;
            }
            //find operand
            String operand = "";
            count++;
            while(count < comand.length() && comand.charAt(count) != '[')
            {
               operand += comand.charAt(count);
               count++;
            }
            //find nodeB        
            String nameB = "";
            count++;
         
            while(count < comand.length() && comand.charAt(count) != ']')
            {
               nameB += comand.charAt(count);
               count++;
            }
            
            if(nameA != "" && nameB != "") 
               connect(nameA,nameB,operand);
          }//end while          
       }// end while
      
      for(int i = 0;i < nodes.size();i++)
        aGraph.addNode(nodes.get(i));
	   return aGraph;     
   }  
   private void connect(String nodeA, String nodeB, String op)
   {
      if (nodes.size() == 0)
            {
               nodes.add(new Node(nodeA));
               nodes.add(new Node(nodeB));
               operator(nodes.get(0),nodes.get(1),op);
            }
            else
            {
               int indexA = find(nodeA,nodes);
               int indexB = find(nodeB,nodes);
            
               if(indexA == -99)
               {
                  nodes.add(new Node(nodeA));
                  indexA = nodes.size()-1;
               }
               if(indexB == -99)
               {
                  nodes.add(new Node(nodeB));
                  indexB = nodes.size()-1;
               }
               
               operator(nodes.get(indexA),nodes.get(indexB),op);
             }   
   }
   private void operator(Node a,Node b,String op)
   {
      if("-".compareTo(op) == 0)
         a.connectNeighbor(b);
   }
   private int find(String cn , ArrayList<Node> nodes)
   {
      int result = -99;
      for(int i = 0;i < nodes.size() && result == -99 ;i++)
         {
            if(nodes.get(i).getName().compareTo(cn) == 0)
            {
               result = i;
            }   
         }
      return result; 
   }
 
   private ArrayList<Node> nodes;
}