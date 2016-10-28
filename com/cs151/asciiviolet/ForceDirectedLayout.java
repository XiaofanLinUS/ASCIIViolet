package com.cs151.asciiviolet;

import java.util.ArrayList;
import java.util.HashMap;

import com.horstmann.violet.framework.Edge;
import com.horstmann.violet.framework.Node;

public class ForceDirectedLayout implements LayoutPlanner
{
   public ForceDirectedLayout(double length, ArrayList<Node> thatNodes, ArrayList<Edge> thatEdges)
   {
      l = length;
      K_S = K_R / (R * l * l * l);
      forces = new HashMap<>();
      nodes = thatNodes;
      edges = thatEdges;
      initialize();
   }

   public void refresh()
   {
      done = false;
      System.out.println("refresh");
      move();
   }

   private void initializeForce(Node node)
   {

      System.out.println("initializeForce");
      ArrayList<Double> force = new ArrayList<>();
      force.add(0.0);
      force.add(0.0);
      forces.put(node, force);

   }

   private void move()
   {
      System.out.println("move");
      while (!done)
      {
         moveNodes();
      }
      reset();
   }

   private void reset()
   {

      System.out.println("reset");
      double Xminimum = minX();
      double Yminimum = minY();
      for (Node node : nodes)
      {

         node.setX(node.getX() - Xminimum);
         node.setY(node.getY() - Yminimum);
      }
   }

   private void moveNodes()
   {
      double dx, dy, distanceSquared, distance, force, fx, fy, s;
      ArrayList<Double> force1, force2;
      Node node1, node2;
      int n = nodes.size();
      initialize();

      System.out.println("moveNodes");
      for (int i = 0; i < n - 1; i++)
      {
         node1 = nodes.get(i);
         force1 = forces.get(node1);
         for (int j = i + 1; j < n; j++)
         {
            node2 = nodes.get(j);
            force2 = forces.get(node2);
            dx = node2.getX() - node1.getX();
            dy = node2.getY() - node1.getY();
            if (Math.abs(dx) >= 0 || Math.abs(dy) >= 0)
            {
               distanceSquared = dx * dx + dy * dy;
               distance = Math.sqrt(distanceSquared);
               force = K_R / distanceSquared;
               fx = force * dx / distance;
               fy = force * dy / distance;
               force1.set(0, force1.get(0) - fx);
               force1.set(1, force1.get(1) - fy);
               force2.set(0, force2.get(0) + fx);
               force2.set(1, force2.get(1) + fy);
            } else
            {
               fx = Math.random() * 10 * l;
               fy = Math.random() * 10 * l;
               if ((int) Math.random() * 2 == 1)
               {
                  force1.set(0, force1.get(0) - fx);
                  force1.set(1, force1.get(1) - fy);
                  force2.set(0, force2.get(0) + fx);
                  force2.set(1, force2.get(1) + fy);
               } else
               {
                  force1.set(0, force1.get(0) + fx);
                  force1.set(1, force1.get(1) + fy);
                  force2.set(0, force2.get(0) - fx);
                  force2.set(1, force2.get(1) - fy);
               }
            }
         }

      }

      for (Edge e : edges)
      {
         node1 = e.getStart();
         node2 = e.getEnd();
         force1 = forces.get(node1);
         force2 = forces.get(node2);
         dx = node2.getX() - node1.getX();
         dy = node2.getY() - node1.getY();
         if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1)
         {
            distance = Math.sqrt(dx * dx + dy * dy);
            force = K_S * (distance - l);
            fx = force * dx / distance;
            fy = force * dy / distance;
            force1.set(0, force1.get(0) + fx);
            force1.set(1, force1.get(1) + fy);
            force2.set(0, force2.get(0) - fx);
            force2.set(1, force2.get(1) - fy);
         }

      }

      for (Node node : nodes)
      {
         force1 = forces.get(node);
         dx = DELTA_T * force1.get(0);
         dy = DELTA_T * force1.get(1);
         distanceSquared = dx * dx + dy * dy;
         if (distanceSquared > MAX_DISPLACEMENT_SQUARED)
         {
            s = Math.sqrt(MAX_DISPLACEMENT_SQUARED / distanceSquared);
            dx = dx * s;
            dy = dy * s;
         }
         if (dx > 0.00009 || dy > 0.00009)
         {
            done = false;
         }
         node.translate(dx, dy);
         System.out.println(dx + " " + dy);
      }
   }

   private double maxX()
   {
      double max = Double.MIN_VALUE;
      for (Node aNode : nodes)
      {
         if (aNode.getX() > max)
         {
            max = aNode.getX();
         }
      }
      return max;
   }

   private double maxY()
   {
      double max = Double.MIN_VALUE;
      for (Node aNode : nodes)
      {
         if (aNode.getY() > max)
         {
            max = aNode.getY();
         }
      }
      return max;
   }

   private double minX()
   {
      double min = Double.MAX_VALUE;
      for (Node aNode : nodes)
      {
         if (aNode.getX() < min)
         {
            min = aNode.getX();
         }
      }
      return min;
   }

   private double minY()
   {
      double min = Double.MAX_VALUE;
      for (Node aNode : nodes)
      {
         if (aNode.getY() < min)
         {
            min = aNode.getY();
         }
      }
      return min;
   }

   private void initialize()
   {
      System.out.println("initialize");
      done = true;
      for (Node node : nodes)
      {
         initializeForce(node);
      }
   }

   private ArrayList<Node> nodes;
   private ArrayList<Edge> edges;
   private HashMap<Node, ArrayList<Double>> forces;

   private static final double R = 1;
   private static final double K_R = 8000;
   private static final double DELTA_T = 2;

   private double K_S;
   private double l;
   private double MAX_DISPLACEMENT_SQUARED = 9000;
   private boolean done;

}
