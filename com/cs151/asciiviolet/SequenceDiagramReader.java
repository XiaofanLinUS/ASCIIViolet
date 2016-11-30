package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import com.horstmann.violet.CallNode;
import com.horstmann.violet.ImplicitParameterNode;
import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.MultiLineString;
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
      for (CallNode node : callNodes)
      {
         graph.removeNode(node);
      }
   }
   
   @Override
   public void read(String input)
   {
	   //if read has been called again, reset everything
	   //resetGraph();
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
	   //separator between nodes
	   int  width = 0;
	   
	   addTopNode(firstInput);
	   ImplicitParameterNode topNodeA = findTopNode(firstInput);
	   graph.add(topNodeA,new Point2D.Double(width,0));
	  
	   //distance between the top nodes
	   width += topNodeA.getBounds().getWidth() + 50;

	   addTopNode(secondInput);
	   ImplicitParameterNode topNodeB = findTopNode(secondInput);
	   graph.add(topNodeB,new Point2D.Double(width,0));
   	
	   operate(topNodeA, topNodeB, operator);
   }
   private void addCallNode(ImplicitParameterNode aTopNode){
	   CallNode sampleNode = new CallNode();
	   sampleNode.setImplicitParameter(aTopNode);
	   callNodes.add(sampleNode);
   }
   
   
   private void operate(ImplicitParameterNode topNodeA, ImplicitParameterNode topNodeB, String operator)
   {
	   addCallNode(topNodeA);
	  // for(CallNode n : callNodes){
	//	   System.out.println(n.toString());
	  // }
	   //CallNode callNodeA = findCallNode(topNodeA);
	  // if(callNodeA != null)
	 //   graph.add(callNodeA,new Point2D.Double(0,100));
	  /*
	   CallNode callNodeB = new CallNode();
	   graph.add(callNodeB,new Point2D.Double(topNodeB.getX(),100));
	   
	   if(operator.equals("-")){
		   Direction d = new Direction(0,0);
		   graph.connect(new CallEdge(),callNodeA.getConnectionPoint(d) ,callNodeB.getConnectionPoint(d));
	  }
	   //need to save calledge incase it has a return edge to other calledge
	   /*
	   if(operator.equals("--")){
		   Direction d = new Direction(0,0);
		   graph.connect(new ReturnEdge()???,callNodeA.getConnectionPoint(d) ,callNodeB.getConnectionPoint(d));
	   }*/
   }
   
   private ImplicitParameterNode findTopNode(String name)
   {
	   ImplicitParameterNode aNode = null;
	   for (ImplicitParameterNode node:TopNodes)
	   {
		   if(node.getName().toString().compareTo(name) == 0)
			   aNode = node;
	   }
	   return aNode;
   }
   private CallNode findCallNode(ImplicitParameterNode mark)
   {
	   System.out.println("before before");
	   CallNode aNode = new CallNode();
	   System.out.println("before ");
	   int count = 0;
	   for (CallNode node: callNodes)
	   {
		   System.out.println(count++);
		   if(node.getImplicitParameter() == mark)
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
   private Vector<CallNode> callNodes;
   private Graph graph;
   private String oldInput;
   //private HashMap<String, Node> classNodes;
}
