package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Scanner;

import com.horstmann.violet.ClassDiagramGraph;
import com.horstmann.violet.ClassNode;
import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.MultiLineString;;

public class ClassDiagramReader
{

   public ClassDiagramReader(ClassDiagramGraph aGraph)
   {

      graph = aGraph;
      nodes = (ArrayList<ClassNode>) graph.getNodes();
   }

   public void read(String input)
   {
      Scanner s = new Scanner(input);
      String comand = "";

      while (s.hasNext())
      {
         comand = s.nextLine();
         int count = 0;

         while (count < comand.length())
         {
            // find nodeA
            while (count < comand.length() && comand.charAt(count) != '[')
            {
               count++;
            }
            String nameA = "";
            count++;

            while (count < comand.length() && comand.charAt(count) != ']')
            {
               nameA += comand.charAt(count);
               count++;
            }
            // find operator
            String operator = "";
            count++;
            while (count < comand.length() && comand.charAt(count) != '[')
            {
               operator += comand.charAt(count);
               count++;
            }
            // find nodeB
            String nameB = "";
            count++;

            while (count < comand.length() && comand.charAt(count) != ']')
            {
               nameB += comand.charAt(count);
               count++;
            }
            //for the case of two nodes.
            if (nameA != "" && nameB != "")
               connect(nameA, nameB, operator);
            //for the case of only one node.   
            else if (nameA != "" && nameB =="")
               createClassNode(nameA);
         } // end while
      } // end while

      for (int i = 0; i < nodes.size(); i++)
         graph.add(nodes.get(i), new Point2D.Double(500 * Math.random() + 50, 500 * Math.random() + 50));
   }

   private void connect(String a, String b, String op)
   {
      ClassNode nodeA = find(a);
      ClassNode nodeB = find(b);

      if (nodeA == null)
      {
         nodeA = new ClassNode();
         MultiLineString string1 = new MultiLineString();
         string1.setText(a);
         nodeA.setName(string1);
         nodes.add(nodeA);
      }

      if (find(b) == null)
      {
         nodeB = new ClassNode();
         MultiLineString string2 = new MultiLineString();
         string2.setText(b);
         nodeB.setName(string2);
         nodes.add(nodeB);
      }

      operate(nodeA, nodeB, op);

   }
   private void createClassNode(String name)
   {
      ClassNode node = find(name);

      if (node == null)
      {
         node = new ClassNode();
         MultiLineString string = new MultiLineString();
         string.setText(name);
         node.setName(string);
         nodes.add(node);
      }
   }

   private void operate(ClassNode a, ClassNode b, String op)
   {
      Edge edge;
      if ("-".compareTo(op) == 0)
      {
         edge = (Edge) graph.getEdgePrototypes()[0].clone();
         edge.connect(a, b);
         graph.connect(edge, a, b);
      }
      
   }

   private ClassNode find(String name)
   {
      for (ClassNode node : nodes)
      {
         if (node.getName().getText().equals(name))
         {
            System.out.println(name);
            return node;
         }
      }
      return null;
   }

   private ArrayList<ClassNode> nodes;
   private ClassDiagramGraph graph;

}