package com.cs151.asciiviolet;

import java.util.ArrayList;

public class Graph
{
   public Graph(double length)
   {
      l = length;
      K_S = K_R / (R * l * l * l);
      nodes = new ArrayList<>();
   }

   public void add(Node aNode)
   {
      nodes.add(aNode);
   }

   public Node getNode(int i)
   {
      return nodes.get(i);
   }

   public int size()
   {
      return nodes.size();
   }

   public void move()
   {
      done = false;
      while (!done)
      {
         moveNodes();
      }
   }

   private void moveNodes()
   {
      double dx, dy, distanceSquared, distance, force, fx, fy, s;
      Node node1, node2;
      int n = nodes.size();

      initialize();
      for (int i = 0; i < n - 1; i++)
      {
         node1 = nodes.get(i);
         for (int j = i + 1; j < n; j++)
         {
            node2 = nodes.get(j);
            dx = node2.getX() - node1.getX();
            dy = node2.getY() - node1.getY();
            if (Math.abs(dx) >= 0 || Math.abs(dy) >= 0)
            {
               distanceSquared = dx * dx + dy * dy;
               distance = Math.sqrt(distanceSquared);
               force = K_R / distanceSquared;
               fx = force * dx / distance;
               fy = force * dy / distance;
               node1.setForceX(node1.getForceX() - fx);
               node1.setForceY(node1.getForceY() - fy);
               node2.setForceX(node2.getForceX() + fx);
               node2.setForceY(node2.getForceY() + fy);
            } else
            {
               fx = Math.random() * 10 * l;
               fy = Math.random() * 10 * l;
               if ((int) Math.random() * 2 == 1)
               {
                  node1.setForceX(node1.getForceX() - fx);
                  node1.setForceY(node1.getForceY() - fy);
                  node2.setForceX(node2.getForceX() + fx);
                  node2.setForceY(node2.getForceY() + fy);
               } else
               {
                  node1.setForceX(node1.getForceX() + fx);
                  node1.setForceY(node1.getForceY() + fy);
                  node2.setForceX(node2.getForceX() - fx);
                  node2.setForceY(node2.getForceY() - fy);
               }
            }
         }

      }

      for (int i = 0; i < n; i++)
      {
         node1 = nodes.get(i);
         for (int j = 0; j < node1.neighborSize(); j++)
         {
            node2 = node1.getNeighbor(j);
            if (!node2.visited(node1))
            {
               node1.visit(node2);
               dx = node2.getX() - node1.getX();
               dy = node2.getY() - node1.getY();
               if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1)
               {
                  distance = Math.sqrt(dx * dx + dy * dy);
                  force = K_S * (distance - l);
                  fx = force * dx / distance;
                  fy = force * dy / distance;
                  node1.setForceX(node1.getForceX() + fx);
                  node1.setForceY(node1.getForceY() + fy);
                  node2.setForceX(node2.getForceX() - fx);
                  node2.setForceY(node2.getForceY() - fy);
               }
            }
         }
      }

      for (int i = 0; i < n; i++)
      {
         dx = DELTA_T * nodes.get(i).getForceX();
         dy = DELTA_T * nodes.get(i).getForceY();
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
         nodes.get(i).setX(nodes.get(i).getX() + dx);
         nodes.get(i).setY(nodes.get(i).getY() + dy);
      }
   }

   public double maxX()
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

   public double maxY()
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

   public double minX()
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

   public double minY()
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

   public void initialize()
   {

      done = true;
      for (int i = 0; i < nodes.size(); i++)
      {
         nodes.get(i).resetVisitedNeighbors();
         nodes.get(i).setForceX(0);
         nodes.get(i).setForceY(0);
      }
   }

   private ArrayList<Node> nodes;
   private static final double R = 1;
   private double l;
   private static final double K_R = 8000;
   private double K_S;
   private double DELTA_T = 2;
   private double MAX_DISPLACEMENT_SQUARED = 9000;
   private boolean done;
}
