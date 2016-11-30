package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

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
      // ImplicitParameterNode = new HashMap<>();
      this.graph = graph;
      TopNodes = new ArrayList<ImplicitParameterNode>();
      callNodes = new HashMap<>();
      // nodes = (ArrayList<Node>) graph.getNodes();
      // oldInput = "";
   }

   private void resetGraph()
   {
      // ArrayList<ImplicitParameterNode> removedNodes =
      // (ArrayList<ImplicitParameterNode>) TopNodes.clone();
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
      int count = 0;
      String firstInput = "";
      resetGraph();

      // putting in the first input to the firstInput variable
      while (count < input.length() && input.charAt(count) != '-')
      {
         firstInput += input.charAt(count);
         count++;
      }
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
      while (count < input.length())
      {
         secondInput += input.charAt(count);
         count++;
      }

      connect(firstInput, secondInput, operator);
      // resetGraph();
      /*
       * int width = 0; for(ImplicitParameterNode node: TopNodes) {
       * graph.add(node,new Point2D.Double(width,0)); /* ArrayList<Node> list =
       * (ArrayList<Node>) node.getChildren(); int height = 100; for(Node cnode:
       * list) { graph.add(cnode, new Point2D.Double(width,height)); height +=
       * cnode.getBounds().getHeight() + 10; } width +=
       * node.getBounds().getWidth() + 10; }
       */
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
      // need to save calledge incase it has a return edge to other calledge
      /*
       * if(operator.equals("--")){ Direction d = new Direction(0,0);
       * graph.connect(new ReturnEdge()???,callNodeA.getConnectionPoint(d)
       * ,callNodeB.getConnectionPoint(d)); }
       */
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
   // private HashMap<String, Node> classNodes;
}
