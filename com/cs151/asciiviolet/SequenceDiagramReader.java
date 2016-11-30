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
      while (scan.hasNextLine())
      {
<<<<<<< HEAD
         String command = scan.nextLine();
         int count = 0;
         String firstInput = "";
         String firstNum = "";
         String operator = "";
         String secondInput = "";
         String secondNum = "";

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
         while (count < command.length())
         {
            secondNum += command.charAt(count);
            count++;
         }

         connect(firstInput, Integer.parseInt(firstNum) - 1, secondInput, Integer.parseInt(secondNum) - 1, operator);
      }
||||||| merged common ancestors
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
<<<<<<< HEAD
      
=======
      String operator = "";

      // skipping the operator
      while (count < input.length() && input.charAt(count) != '>')
      {
         operator += input.charAt(count);
         count++;
      }

      String secondInput = "";
      count++;

      // putting the second input to the secondInput variable
      while (count < input.length() && input.charAt(count) != '\n')
      {
         secondInput += input.charAt(count);
         count++;
      }

      connect(firstInput, secondInput, operator);
>>>>>>> 1553e6cc25416df0a50253c5dfac19585b218410
      // resetGraph();
      /*
       * int width = 0; for(ImplicitParameterNode node: TopNodes) {
       * graph.add(node,new Point2D.Double(width,0)); /* ArrayList<Node> list =
       * (ArrayList<Node>) node.getChildren(); int height = 100; for(Node cnode:
       * list) { graph.add(cnode, new Point2D.Double(width,height)); height +=
       * cnode.getBounds().getHeight() + 10; } width +=
       * node.getBounds().getWidth() + 10; }
       */
=======
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
>>>>>>> c7ffc380a85c0e5f2bc685b45009141386ad4fe8
   }

   private void connect(String firstInput, int firstNum, String secondInput, int secondNum, String operator)
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
      operate(topNodeA, firstNum, topNodeB, secondNum, operator);
   }

   private void operate(ImplicitParameterNode topNodeA, int indexA, ImplicitParameterNode topNodeB, int indexB,
         String operator)
   {
      CallNode callNodeA = find(topNodeA, indexA);
      CallNode callNodeB = find(topNodeB, indexB);

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
   private Graph graph;
   private String oldInput;
   private HashMap<ImplicitParameterNode, ArrayList<CallNode>> callNodes;
}
