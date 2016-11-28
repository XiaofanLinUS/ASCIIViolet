package com.cs151.asciiviolet;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.horstmann.violet.CallEdge;
import com.horstmann.violet.CallNode;
import com.horstmann.violet.ClassRelationshipEdge;
import com.horstmann.violet.ImplicitParameterNode;
import com.horstmann.violet.SequenceDiagramGraph;
import com.horstmann.violet.framework.Direction;
import com.horstmann.violet.framework.Edge;


import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.MultiLineString;
import com.horstmann.violet.framework.Node;

/**
 * A command reader that reads a string and execute it
 * 
 * @author linxiaofan
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
	   //ImplicitParameterNode = new HashMap<>();
	   this.graph = graph;
	   TopNodes = new ArrayList<ImplicitParameterNode>();
	   //nodes = (ArrayList<Node>) graph.getNodes();
	   //oldInput = "";
   }

   private void resetGraph()
   {
      //ArrayList<ImplicitParameterNode> removedNodes = (ArrayList<ImplicitParameterNode>) TopNodes.clone();
      for (ImplicitParameterNode node : TopNodes)
      {
         graph.removeNode(node);
      }
   }
   @Override
   public void read(String input)
   {
	   int count = 0;
	   String firstInput = "";

	   //putting in the first input to the firstInput variable
	   while (count < input.length() && input.charAt(count) != '-')
       {
		   firstInput += input.charAt(count);
          count++;
       }
	   
	   String operator = "";
	   
	   //skipping the operator
	   while(count < input.length() && input.charAt(count) != '>'){
		   operator += input.charAt(count);
		   	count++;
	   }
	   
	   String secondInput= "";
	   count++;
	   
	   //putting the second input to the secondInput variable
	   while(count < input.length()){
		   secondInput += input.charAt(count);
		   count++;
	   }
	   
	   connect(firstInput,secondInput,operator);
	   //resetGraph();
	   /*
	   int width = 0;
	   for(ImplicitParameterNode node: TopNodes)
	   {
	   	   graph.add(node,new Point2D.Double(width,0));
	   	   /*
	   	   ArrayList<Node> list =  (ArrayList<Node>) node.getChildren();
	   	   int height = 100;
	   	   for(Node cnode: list)
	   	   {
	   		   graph.add(cnode, new Point2D.Double(width,height));
	   		   height += cnode.getBounds().getHeight() + 10;
	   	   }
	   	    
	   	   width += node.getBounds().getWidth() + 10;
	   }
      */
   }
   
   private void connect(String firstInput,String secondInput,String operator)
   {
	   int  width = 0;
	   
	   addTopNode(firstInput);
	   ImplicitParameterNode topNodeA = find(firstInput);
	   graph.add(topNodeA,new Point2D.Double(width,0));
	  
	   //distance between the top nodes
	   width += topNodeA.getBounds().getWidth() + 50;

	   addTopNode(secondInput);
	   ImplicitParameterNode topNodeB = find(secondInput);
	   graph.add(topNodeB,new Point2D.Double(width,0));
   	
	   operate(topNodeA, topNodeB, operator);
   }
   
   private void operate(ImplicitParameterNode topNodeA, ImplicitParameterNode topNodeB, String operator)
   {
	   CallNode callNodeA = new CallNode();
	   callNodeA.setImplicitParameter(topNodeA);
	   graph.add(callNodeA,new Point2D.Double(0,100));
	   
	   CallNode callNodeB = new CallNode();
	   callNodeB.setImplicitParameter(topNodeB);
	   graph.add(callNodeB,new Point2D.Double(topNodeB.getX(),100));
	   
	   if(operator.equals("-")){
		   CallEdge edge = new CallEdge();
		   edge.connect(callNodeA, callNodeB);
		   Direction d = new Direction(0,0);
		   graph.connect(edge,callNodeA.getConnectionPoint(d) ,callNodeB.getConnectionPoint(d));
	  }
	  /* 
	   if(operator.equals("--")){
		   //insert different kind of edge here
		   edge.connect(callNodeA, callNodeB);
		   Direction d = new Direction(0,0);
		   graph.connect(edge,callNodeA.getConnectionPoint(d) ,callNodeB.getConnectionPoint(d));
	   }
	  */ 
   }

   private ImplicitParameterNode find(String name)
   {
	   ImplicitParameterNode aNode = null;
	   for (ImplicitParameterNode node:TopNodes)
	   {
		   if(node.getName().toString().compareTo(name) == 0)
			   aNode = node;
	   }
	   return aNode;
   }
   
   private void addTopNode(String name)
   {
	   ImplicitParameterNode topNode = new ImplicitParameterNode();
	   MultiLineString string = new MultiLineString();
	   string.setText(name);
	   topNode.setName(string);
	   TopNodes.add(topNode);
   }
   private ArrayList<ImplicitParameterNode> TopNodes;
   private SequenceDiagramGraph seqGraph;
   private Graph graph;
   //private HashMap<String, Node> classNodes;
   private String oldInput;

}
