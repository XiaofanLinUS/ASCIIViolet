package com.cs151.asciiviolet;

import java.util.ArrayList;

import com.horstmann.violet.ImplicitParameterNode;
import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.Node;

/**
 * A Seuence Layout that arranges each nodes in order of their created time from
 * left to right
 * 
 * @author linxiaofan
 *
 */
public class SequenceLayout implements LayoutPlanner
{

   /**
    * Construct a SequenceLayout with a given length of the edges and nodes
    * 
    * @param length
    *           the given length
    * @param thatNodes
    *           the given nodes
    */
   public SequenceLayout(double length, ArrayList<Node> thatNodes)
   {
      l = length;
      nodes = thatNodes;
   }

   /**
    * Construct a SequenceLayout with a given length and a given graph
    * 
    * @param length
    *           the given length
    * @param agraph
    *           the given graph
    */
   public SequenceLayout(double length, Graph agraph)
   {
      l = length;
      graph = agraph;
      nodes = (ArrayList<Node>) graph.getNodes();
   }

   @Override
   public void refresh()
   {
      for (int i = 0; i < nodes.size(); i++)
      {
         Node node = nodes.get(i);
         node.setX(i * l);
         if (node.getClass().equals(ImplicitParameterNode.class))
         {
            node.setY(10);
         }
      }
   }

   private double l;
   private Graph graph;
   private ArrayList<Node> nodes;

}
