package com.cs151.asciiviolet;

import java.util.ArrayList;

import com.horstmann.violet.framework.Edge;
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
    * Update the position of nodes and edges in the graph
    */
   void refresh();

}
