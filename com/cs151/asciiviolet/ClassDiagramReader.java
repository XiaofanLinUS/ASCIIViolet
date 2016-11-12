package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Scanner;

import com.horstmann.violet.ClassDiagramGraph;
import com.horstmann.violet.ClassNode;
import com.horstmann.violet.ClassRelationshipEdge;
import com.horstmann.violet.NoteEdge;
import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.MultiLineString;

/**
 * A command reader that reads a string and execute it
 * 
 * @author linxiaofan, benny3946
 * @version 11/11/16
 *
 */
public class ClassDiagramReader
{

   /**
    * Construct a ClassDiagramReader with a given graph
    * 
    * @param aGraph
    *           the given graph
    */
   public ClassDiagramReader(ClassDiagramGraph aGraph)
   {

      graph = aGraph;
      nodes = (ArrayList<ClassNode>) graph.getNodes();
   }

   /**
    * Read the input and execute it
    * 
    * @param input
    *           the given input
    */
   public void read(String input)
   {
      Scanner s = new Scanner(input);
      String command = "";

      while (s.hasNext())
      {
         command = s.nextLine();
         int count = 0;

         while (count < command.length())
         {
            // find nodeA
            while (count < command.length() && command.charAt(count) != '[')
            {
               count++;
            }
            String nameA = "";
            count++;

            while (count < command.length() && command.charAt(count) != ']')
            {
               nameA += command.charAt(count);
               count++;
            }
            // find operator
            String operator = "";
            count++;
            while (count < command.length() && command.charAt(count) != '[')
            {
               operator += command.charAt(count);
               count++;
            }
            // find nodeB
            String nameB = "";
            count++;

            while (count < command.length() && command.charAt(count) != ']')
            {
               nameB += command.charAt(count);
               count++;
            }
            // for the case of two nodes.
            if (nameA != "" && nameB != "")
               connect(nameA, nameB, operator);
            // for the case of only one node.
            else if (nameA != "" && nameB == "")
               createClassNode(nameA);
         } // end while
      } // end while

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
         while (!graph.add(nodeA, new Point2D.Double(80 * Math.random(), 80 * Math.random())))
         {
         }

      }

      if (nodeB == null)
      {
         nodeB = new ClassNode();
         MultiLineString string2 = new MultiLineString();
         string2.setText(b);
         nodeB.setName(string2);
         while (!graph.add(nodeB, new Point2D.Double(80 * Math.random(), 80 * Math.random())))
         {
         }
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
	  ClassRelationshipEdge edge;
      char[] ope = op.toCharArray();
      int endInd = ope.length - 1;
      
      if (ope[0] == '-' 
    		  &&  ope[endInd -1] == '-' && ope[endInd] == '>' && ope.length >=3)// -->
      {
         edge = (ClassRelationshipEdge) graph.getEdgePrototypes()[0].clone();
         if (ope.length> 4)
        	 setLabel(edge,ope,1,2);
         graph.connect(edge, a, b);
      }
      else if (ope[0] == '-' && ope[1] !='-' //to check if it is not --|>
  		  	&&  ope[endInd -1] == '|' && ope[endInd] == '>' && ope.length >=3)//-|>
      {
       edge = (ClassRelationshipEdge) graph.getEdgePrototypes()[1].clone();
       if (ope.length> 4)
      	 setLabel(edge,ope,1,2);
       graph.connect(edge, a, b);
      }
      else if (ope[0] == '-' &&  ope[1] == '-'
    		  &&  ope[endInd -1] == '|' && ope[endInd] == '>' && ope.length >=4)//--|>
      {
         edge = (ClassRelationshipEdge) graph.getEdgePrototypes()[2].clone();
         if (ope.length> 5)
        	 setLabel(edge,ope,2,2);
         graph.connect(edge, a, b);
      }
      else if (ope[0] == '-'
  		  		&& ope[endInd] == '>' && ope.length >=2)//->
      {
    	 edge = (ClassRelationshipEdge) graph.getEdgePrototypes()[3].clone();
         if (ope.length> 3)
        	 setLabel(edge,ope,1,1);
         graph.connect(edge, a, b);
      }
      else if (ope[0] == '<' &&  ope[1] == '>' 
    		  	&& ope[endInd] == '-' && ope.length >=3)//<>-
      {
         edge = (ClassRelationshipEdge) graph.getEdgePrototypes()[4].clone();
         if (ope.length> 4)
        	 setLabel(edge,ope,2,1);
         graph.connect(edge, a, b);
      }
      else if (ope[0] == '<' &&  ope[1] == '.' && ope[2] == '>' 
    		  	&& ope[endInd] == '-' && ope.length >=4)//<.>-
      {
         edge = (ClassRelationshipEdge) graph.getEdgePrototypes()[5].clone();
         if (ope.length> 5)
        	 setLabel(edge,ope,3,1);
         graph.connect(edge, a, b);
      }
      if (ope[0] == '-'
		  		&& ope[endInd] == '-' && ope.length >=2)//--
      {
         NoteEdge nEdge = (NoteEdge) graph.getEdgePrototypes()[6].clone();
         graph.connect(nEdge, a, b);
      }
   }
   /**
    * set the label on the edge by reading the string in operater(ope)
    * using '+' sign as an seperater to seperate the label
    * if there is 1 label (a) a would be the middleLabel.
    * if there is 2 labels (a+b) a would be the startLabel and b would be the endLabel.
    * if there is 3 labels (a+b+c) a would be the startLabel, b would be the middle label 
    * and,c would be the endLabel
    *   
    * @param edge the edge for the setting.
    * @param ope the operater
    * @param start the number of charactor should omit from the start 
    * @param end the number of charactor should omit at the end 
    */
   private void setLabel(ClassRelationshipEdge edge, char[] ope,int start,int end)
   {
	String startLabel = "";
	String midLabel = "";
	String endLabel = "";
	
	//counting the number of labels
	int label = 0; 
	for (int i = start; i < ope.length - end;i++)
		if (ope[i] == '+' && ope[i-1] != '\\')
				label++;
	
	if(label == 0) //if only 1 label, set up the middle label
	{
		for (int i = start; i < ope.length - end;i++)
		{
			if(ope[i] == '\\' && i+1 < ope.length && ope[i+1] == '+')
				i++;
			midLabel += ope[i];
		}
	}
	else if (label == 1) //if there is 2 labels, set up the start and the end label
	{
		int i = start; 
		while(ope[i] != '+' || ope[i-1] =='\\')
		{
			if(ope[i] == '\\' && i+1 < ope.length && ope[i+1] == '+')
				i++;
			startLabel += ope[i];
			i++;
		}
		i++;
		while(i < ope.length - end)
		{
			if(ope[i] == '\\' && i+1 < ope.length && ope[i+1] == '+')
				i++;
			endLabel += ope[i];
			i++;
		}
	}
	else if(label ==2) // if there is 3 labels, set the label acordinly
	{
		int i = start; 
		while(ope[i] != '+' || ope[i-1] =='\\')
		{
			if(ope[i] == '\\' && i+1 < ope.length && ope[i+1] == '+')
				i++;
			startLabel += ope[i];
			i++;
		}
		i++;
		while(ope[i] != '+' || ope[i-1] =='\\')
		{
			//increment the i if a '\' following with a '+'.
			if(ope[i] == '\\' && i+1 < ope.length && ope[i+1] == '+')
				i++;
			midLabel += ope[i];
			i++;
		}
		i++;
		while(i < ope.length - end)
		{
			if(ope[i] == '\\' && i+1 < ope.length && ope[i+1] == '+')
				i++;
			endLabel += ope[i];
			i++;
		}
	}
	edge.setStartLabel(startLabel);
	edge.setMiddleLabel(midLabel);
	edge.setEndLabel(endLabel);
   }
   
   private ClassNode find(String name)
   {
      for (ClassNode node : nodes)
      {
         if (node.getName().getText().equals(name))
         {
            return node;
         }
      }
      return null;
   }

   private ArrayList<ClassNode> nodes;
   private ClassDiagramGraph graph;

}