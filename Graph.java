import java.util.ArrayList;

public class Graph
{
   public Graph()
   {
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
      for (int i = 0; i < 500; i++)
      {
         moveNodes();
      }
   }

   public void moveNodes()
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
            if (Math.abs(dx) >= 5 || Math.abs(dy) >= 5)
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
               fx = Math.random() * L;
               fy = Math.random() * L;
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
               if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1)
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

   public void initialize()
   {
      for (int i = 0; i < nodes.size(); i++)
      {
         nodes.get(i).resetVisitedNeighbors();
         nodes.get(i).setForceX(0);
         nodes.get(i).setForceY(0);
      }
   }

   ArrayList<Node> nodes;
   double R = 0.02;
   double L = 50;
   double K_R = 6250;
   double K_S = K_R / (R * L * L * L);
   double DELTA_T = 0.1;
   double MAX_DISPLACEMENT_SQUARED = 9000;
}
