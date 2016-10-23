import java.util.ArrayList;

public class Graph
{
   public Graph(double width)
   {
      nodes = new ArrayList<>();
   }

   public void add(Node aNode)
   {
      nodes.add(aNode);
   }

   public void moveNodes()
   {
      double dx, dy, distanceSquared, distance, force, fx, fy, s;
      Node node1, node2;
      int n = nodes.size();
      for (int i = 0; i < n; i++)
      {
         nodes.get(i).resetVisitedNeighbors();
         nodes.get(i).setForceX(0);
         nodes.get(i).setForceY(0);
      }

      for (int i = 0; i < n - 1; i++)
      {
         node1 = nodes.get(i);
         for (int j = i + 1; j < n; j++)
         {
            node2 = nodes.get(i);
            dx = node2.getX() - node1.getX();
            dy = node2.getY() - node1.getY();
            if (dx != 0 || dy != 0)
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
               if (dx != 0 || dy != 0)
               {
                  distance = Math.sqrt(dx * dx + dy * dy);
                  force = K_S * (distance - L);
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

   ArrayList<Node> nodes;
   int L = 20;
   int K_R = 5;
   int K_S = 5;
   int DELTA_T = 1;
   double MAX_DISPLACEMENT_SQUARED = 900;
}
