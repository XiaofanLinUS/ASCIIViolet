package com.cs151.asciiviolet;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.horstmann.violet.ClassNode;
import com.horstmann.violet.SequenceDiagramGraph;
import com.horstmann.violet.framework.Edge;


import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.MultiLineString;
import com.horstmann.violet.framework.Node;

/**
 * A command reader that reads a string and execute it
 * 
 * @author linxiaofan
 *
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
   }

   @Override
   public void read(String input)
   {
	  
	   if(input.equals("hey")){
		   ClassNode node1 = new ClassNode();
	         MultiLineString string1 = new MultiLineString();
	         string1.setText(input);
	         node1.setName(string1);
	         nodeA = node1;
	         classNodes.put(input, nodeA);
	         while (!graph.add(nodeA, new Point2D.Double(80 * Math.random(), 80 * Math.random())))
	         {
	         }
	   }
   }
   
   private HashMap<String, Node> classNodes;
   Node nodeA;
   Graph graph;
}
