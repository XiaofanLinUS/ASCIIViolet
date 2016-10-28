package com.cs151.asciiviolet;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.Timer;

import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.Node;

public abstract class SmartGraph extends Graph
{

   public SmartGraph()
   {
      super();
      plan = LayoutPlanner.defaultPlan((ArrayList<Node>) super.getNodes(), (ArrayList<Edge>) super.getEdges());
      // Milliseconds between timer ticks
   }

   public boolean connect(Edge e, Point2D p1, Point2D p2)
   {
      boolean success = super.connect(e, p1, p2);
      if (success)
      {
         refresh();
      }
      return success;
   }

   public void refresh()
   {
      plan.refresh();
   }

   public void setPlan(LayoutPlanner aPlan)
   {
      plan = aPlan;
   }

   LayoutPlanner plan;
   Timer t;
   final int DELAY = 100;
}
