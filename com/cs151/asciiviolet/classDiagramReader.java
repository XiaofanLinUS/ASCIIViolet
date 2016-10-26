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
      classDiagramReader a = new classDiagramReader();
      JFrame frame = new JFrame();

      Icon icon = new GraphIcon(a.read(text));
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
      ArrayList<Node> nodes = new ArrayList<>();
      String comand = "";
      
      while(s.hasNext())
      {
         comand = s.nextLine();
         int count = 0;
         while(comand.charAt(count) != '[')
         {
            count++;
         }
         
         String nameA = "";
         count++;
         
         while(comand.charAt(count) != ']')
         {
            nameA += comand.charAt(count);
            count++;
         }
         
         String nodeA = nameA;
         
         while(comand.charAt(count) != '[')
         {
            count++;
         }
         
         String nameB = "";
         count++;
         
         while(comand.charAt(count) != ']')
         {
            nameB += comand.charAt(count);
            count++;
         }
         
         String nodeB = nameB;
         
         
         if (nodes.size() == 0)
         {
            nodes.add(new Node(nodeA));
            nodes.add(new Node(nodeB));
            nodes.get(0).connectNeighbor(nodes.get(1));
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
            nodes.get(indexA).connectNeighbor(nodes.get(indexB));
         }
         
      }// end while
      
      for(int i = 0;i < nodes.size();i++)
        aGraph.addNode(nodes.get(i));
	   return aGraph;     
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
   
}