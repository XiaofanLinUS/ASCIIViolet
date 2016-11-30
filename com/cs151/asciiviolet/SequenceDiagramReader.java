package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.horstmann.violet.CallEdge;
import com.horstmann.violet.CallNode;
import com.horstmann.violet.ImplicitParameterNode;
import com.horstmann.violet.ReturnEdge;
import com.horstmann.violet.SequenceDiagramGraph;
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
      this.graph = (SequenceDiagramGraph) graph;
      TopNodes = new ArrayList<ImplicitParameterNode>();
      callNodes = new HashMap<>();
   }

   private void resetGraph()
   {
      graph = new SequenceDiagramGraph();
      TopNodes = new ArrayList<ImplicitParameterNode>();
      callNodes = new HashMap<>();
   }

   @Override
   public Graph read(String input)
   {
      resetGraph();
      Scanner scan = new Scanner(input);
      while (scan.hasNextLine())
      {
         String command = scan.nextLine();
         int count = 0;
         String firstInput = "";
         String firstNum = "";
         String operator = "";
         String secondInput = "";
         String secondNum = "";
         String label = "";
         while (count < command.length())
         {
            // putting in the first input to the firstInput variable
            while (count < command.length() && command.charAt(count) != '|')
            {
               firstInput += command.charAt(count);
               count++;
            }
            count++;
            // first number
            while (count < command.length() && command.charAt(count) != '-')
            {
               firstNum += command.charAt(count);
               count++;
            }
            // skipping the operator
            while (count < command.length() && command.charAt(count) != '>')
            {
               operator += command.charAt(count);
               count++;
            }
            count++;

            // putting the second input to the secondInput variable
            while (count < command.length() && command.charAt(count) != '|')
            {
               secondInput += command.charAt(count);
               count++;
            }
            count++;
            // Second Number
            while (count < command.length() && command.charAt(count) != ':')
            {
               secondNum += command.charAt(count);
               count++;
            }
            count++;
            while (count < command.length())
            {
               label += command.charAt(count);
               count++;
            }
            connect(firstInput, Integer.parseInt(firstNum) - 1, secondInput, Integer.parseInt(secondNum) - 1, operator,
                  label);
         }

      }
      return graph;
   }

   private void connect(String firstInput, int firstNum, String secondInput, int secondNum, String operator,
         String label)
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
      operate(topNodeA, firstNum, topNodeB, secondNum, operator, label);
   }

   private void operate(ImplicitParameterNode topNodeA, int indexA, ImplicitParameterNode topNodeB, int indexB,
         String operator, String label)
   {
      CallNode callNodeA = find(topNodeA, indexA);
      CallNode callNodeB = find(topNodeB, indexB);

      if (operator.equals("-"))
      {
         Point2D callPointA = new Point2D.Double(callNodeA.getX() + 5, callNodeA.getY() + 5);
         Point2D callPointB = new Point2D.Double(callNodeB.getX() + 5, callNodeB.getY() + 5);

         if (TopNodes.indexOf(topNodeA) <= TopNodes.indexOf(topNodeB))
         {
            CallEdge edge = new CallEdge();
            edge.setMiddleLabel(label);
            graph.connect(edge, callPointA, callPointB);
         } else
         {
            ReturnEdge edge = new ReturnEdge();
            edge.setMiddleLabel(label);
            graph.connect(edge, callPointA, callPointB);
         }
      }
   }

   private CallNode find(ImplicitParameterNode topNode, int index)
   {
      if (index >= callNodes.get(topNode).size())
      {
         CallNode node = null;
         while (index >= callNodes.get(topNode).size())
         {
            node = addCallNode(topNode);
         }
         return node;
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

      while (!graph.add(callNode, new Point2D.Double(topNode.getX() + 5, 200 * Math.random())))
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
      while (!graph.add(topNode, new Point2D.Double(200 * Math.random(), 0)))
      {
      }

      callNodes.put(topNode, new ArrayList<CallNode>());
      return topNode;
   }

   private ArrayList<ImplicitParameterNode> TopNodes;
   private SequenceDiagramGraph graph;
   private HashMap<ImplicitParameterNode, ArrayList<CallNode>> callNodes;
}
