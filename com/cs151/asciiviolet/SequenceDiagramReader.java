package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.horstmann.violet.CallEdge;
import com.horstmann.violet.CallNode;
import com.horstmann.violet.ImplicitParameterNode;
import com.horstmann.violet.ReturnEdge;
import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.MultiLineString;

/**
 * A command reader that reads a string and execute it
 * 
 * @version 11/26/2016
 */
public class SequenceDiagramReader implements Reader
{

   /**
    * Construct a SequenceReader with a given graph
    * 
    * @param graph
    *           the given graph
    */
   public SequenceDiagramReader(Graph graph)
   {
      this.graph = graph;
      TopNodes = new ArrayList<ImplicitParameterNode>();
      callNodes = new HashMap<>();
   }

   private void resetGraph()
   {
      for (ImplicitParameterNode node : TopNodes)
      {
         graph.removeNode(node);
      }
      TopNodes = new ArrayList<ImplicitParameterNode>();
      callNodes = new HashMap<>();
   }

   @Override
   public void read(String input)
   {
      resetGraph();
      Scanner scan = new Scanner(input);
      while(scan.hasNextLine())
      {
          String command = scan.nextLine();
          int count = 0;
          String firstInput = "";

          // putting in the first input to the firstInput variable
          while (count < command.length() && command.charAt(count) != '-')
          {
             firstInput += command.charAt(count);
             count++;
          }
          String operator = "";

          // skipping the operator
          while (count < command.length() && command.charAt(count) != '>')
          {
             operator += command.charAt(count);
             count++;
          }

          String secondInput = "";
          count++;

          // putting the second input to the secondInput variable
          while (count < command.length())
          {
             secondInput += command.charAt(count);
             count++;
          }

          connect(firstInput, secondInput, operator);
      }
   }

   private void connect(String firstInput, String secondInput, String operator)
   {
      // separator between nodes
      ImplicitParameterNode topNodeA = find(firstInput);
      ImplicitParameterNode topNodeB = find(secondInput);

      if (topNodeA == null)
      {
         topNodeA = addTopNode(firstInput);
      }
      if (topNodeB == null)
      {
         topNodeB = addTopNode(secondInput);
      }
      operate(topNodeA, 1, topNodeB, 1, operator);
   }

   private void operate(ImplicitParameterNode topNodeA, int indexA, ImplicitParameterNode topNodeB, int indexB,
         String operator)
   {
      CallNode callNodeA = find(topNodeA, indexA);
      System.out.println(callNodeA);
      CallNode callNodeB = find(topNodeB, indexB);
      System.out.println(callNodeB);
      if (callNodeA == null)
      {
         callNodeA = addCallNode(topNodeA);
      }

      System.out.println("a");
      if (callNodeB == null)
      {
         callNodeB = addCallNode(topNodeB);
      }

      System.out.println("b");
      if (operator.equals("-"))
      {
         Point2D callPointA = new Point2D.Double(callNodeA.getX(), callNodeA.getY());
         Point2D callPointB = new Point2D.Double(callNodeB.getX(), callNodeB.getY());

         if (TopNodes.indexOf(topNodeA) <= TopNodes.indexOf(topNodeB))
         {
            graph.connect(new CallEdge(), callPointA, callPointB);
         } else
         {
            graph.connect(new ReturnEdge(), callPointA, callPointB);
         }

      }
   }

   private CallNode find(ImplicitParameterNode topNode, int index)
   {
      if (index >= callNodes.get(topNode).size())
      {
         return null;
      } else
      {
         return callNodes.get(topNode).get(index);
      }
   }

   private ImplicitParameterNode find(String name)
   {
      ImplicitParameterNode aNode = null;
      for (ImplicitParameterNode node : TopNodes)
      {
         if (node.getName().toString().compareTo(name) == 0)
            aNode = node;
      }
      return aNode;
   }

   private CallNode addCallNode(ImplicitParameterNode topNode)
   {
      CallNode callNode = new CallNode();
      callNode.setImplicitParameter(topNode);
      callNodes.get(topNode).add(callNode);

      while (!graph.add(callNode, new Point2D.Double(topNode.getX(), 200 * Math.random())))
      {
      }
      ;
      return callNode;
   }

   private ImplicitParameterNode addTopNode(String name)
   {
      ImplicitParameterNode topNode = new ImplicitParameterNode();
      MultiLineString string = new MultiLineString();
      string.setText(name);
      topNode.setName(string);
      TopNodes.add(topNode);
      while (!graph.add(topNode, new Point2D.Double(100 * Math.random(), 100 * Math.random())))
      {
      }

      callNodes.put(topNode, new ArrayList<CallNode>());
      return topNode;
   }

   private ArrayList<ImplicitParameterNode> TopNodes;
   private ArrayList<CallNode> callnodes;
   private Graph graph;
   private String oldInput;
   private HashMap<ImplicitParameterNode, ArrayList<CallNode>> callNodes;
}
