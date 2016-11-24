package com.cs151.asciiviolet;

import java.util.ArrayList;

import com.horstmann.violet.ClassDiagramGraph;
import com.horstmann.violet.SequenceDiagramGraph;
import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.Node;

/**
 * An LayoutPlanner that automatically updates the position of nodes and edges
 * 
 * @author linxiaofan
 *
 */
public interface LayoutPlanner
{

   /**
    * Generate a ForceDirectedLayout with a given nodes and edges
    * 
    * @param nodes
    *           the given nodes
    * @param edges
    *           the given edges
    * @return a ForceDirectedLayout
    */
   static LayoutPlanner defaultPlan(ArrayList<Node> nodes, ArrayList<Edge> edges)
   {
      return new ForceDirectedLayout(120, nodes, edges);
   }

   /**
    * Get a plan corresponding to a given graph
    * 
    * @param graph
    *           the given graph
    * @return the plan
    */
   static LayoutPlanner getPlan(Graph graph)
   {
      if (graph.getClass().equals(ClassDiagramGraph.class))
      {
         return new ForceDirectedLayout(120, graph);
      } else if (graph.getClass().equals(SequenceDiagramGraph.class))
      {
         return new SequenceLayout(120, (ArrayList<Node>) graph.getNodes());
      } else
      {
         return defaultPlan((ArrayList<Node>) graph.getNodes(), (ArrayList<Edge>) graph.getEdges());
      }
   }

   /**
    * Update the position of nodes and edges in the graph
    */
   void refresh();

}
