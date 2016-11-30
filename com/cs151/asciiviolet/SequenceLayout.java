package com.cs151.asciiviolet;

import java.util.ArrayList;
import java.util.HashMap;

import com.horstmann.violet.CallNode;
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
      callNodes = new HashMap<>();
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
      callNodes = new HashMap<>();
   }

   private void initialize()
   {
      ImplicitParameterNode parent;

      for (Node node : nodes)
      {
         if (node.getClass().equals(ImplicitParameterNode.class))
         {
            callNodes.put((ImplicitParameterNode) node, new ArrayList<>());
         }
      }

      for (Node node : nodes)
      {
         if (node.getClass().equals(CallNode.class))
         {
            parent = ((CallNode) node).getImplicitParameter();
            callNodes.get(parent).add(((CallNode) node));
         }
      }
   }

   @Override
   public void refresh()
   {
      initialize();
      for (int i = 0; i < nodes.size(); i++)
      {
         Node node = nodes.get(i);
         if (node.getClass().equals(ImplicitParameterNode.class))
         {

            node.setX(i * l);
            node.setY(10);
         }
      }
      for (int i = 0; i < nodes.size(); i++)
      {
         Node node = nodes.get(i);
         ArrayList<CallNode> children;
         int j;
         int interval = 100;
         if (node.getClass().equals(ImplicitParameterNode.class))
         {
            j = 1;
            children = callNodes.get((ImplicitParameterNode) node);
            for (CallNode callNode : children)
            {
               callNode.setX(node.getX());
               callNode.setY(node.getY() + j * interval);
               j++;
            }
         }
      }

   }

   private double l;
   private Graph graph;
   private ArrayList<Node> nodes;
   private HashMap<ImplicitParameterNode, ArrayList<CallNode>> callNodes;
}
