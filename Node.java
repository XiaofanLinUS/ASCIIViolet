import java.util.ArrayList;

public class Node
{
   public Node()
   {
      forceX = 0;
      forceY = 0;
      x = RADIUS * Math.random();
      y = RADIUS * Math.random();
      neighbors = new ArrayList<>();
      visitedNeighbors = new ArrayList<>();
   }

   public void setX(double aX)
   {
      x = aX;
   }

   public void setY(double aY)
   {
      y = aY;
   }

   public double getX()
   {
      return x;
   }

   public double getY()
   {
      return y;
   }

   public double getForceX()
   {
      return forceX;
   }

   public double getForceY()
   {
      return forceY;
   }

   public void setForceX(double aForce)
   {
      forceX = aForce;
   }

   public void setForceY(double aForce)
   {
      forceY = aForce;
   }

   public int neighborSize()
   {
      return neighbors.size();
   }

   public void connectNeighbor(Node aNeighbors)
   {
      aNeighbors.neighbors.add(this);
      neighbors.add(aNeighbors);
   }

   public void visit(Node aNeightbor)
   {
      visitedNeighbors.add(aNeightbor);
   }

   public boolean visited(Node aNeightbor)
   {
      return visitedNeighbors.contains(aNeightbor);
   }

   public void resetVisitedNeighbors()
   {
      visitedNeighbors = new ArrayList<>();
   }

   public Node getNeighbor(int i)
   {
      return neighbors.get(i);
   }

   private int RADIUS = 300;
   private ArrayList<Node> neighbors;
   private ArrayList<Node> visitedNeighbors;
   private double forceX, forceY, x, y;
}