package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.Node;

/**
 * A SmartGraph that will rearrange its nodes and edges with a given layout
 * 
 * @author linxiaofan
 *
 */
public abstract class SmartGraph extends Graph
{

   /**
    * Construct a graph that uses ForceDirectedLayout
    */
   public SmartGraph()
   {
      super();
      plan = LayoutPlanner.defaultPlan((ArrayList<Node>) super.getNodes(), (ArrayList<Edge>) super.getEdges());
      // Milliseconds between timer ticks
   }

   @Override
   public boolean connect(Edge e, Point2D p1, Point2D p2)
   {
      boolean success = super.connect(e, p1, p2);
      if (success)
      {
         refresh();
      }
      return success;
   }

   @Override
   public void connect(Edge e, Node start, Node end)
   {
      super.connect(e, start, end);
      refresh();
   }

   /**
    * Rearrange the positions of nodes and edges
    */
   public void refresh()
   {
      plan.refresh();
   }

   /**
    * Set a layout for this graph
    * 
    * @param aPlan
    *           tne given layout
    */
   public void setPlan(LayoutPlanner aPlan)
   {
      plan = aPlan;
   }

   LayoutPlanner plan;
}
