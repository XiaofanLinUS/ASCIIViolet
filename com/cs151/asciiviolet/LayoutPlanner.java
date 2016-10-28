package com.cs151.asciiviolet;

import java.util.ArrayList;

import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.Node;

public interface LayoutPlanner
{

   static LayoutPlanner defaultPlan(ArrayList<Node> nodes, ArrayList<Edge> edges)
   {
      return new ForceDirectedLayout(150, nodes, edges);
   }

   void refresh();

}
