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

   public void reset()
   {

      x = RADIUS * Math.random();
      y = RADIUS * Math.random();
   }

   public void setX(double aX)
   {

      if (aX > 0 && aX < RADIUS)
         x = aX;
   }

   public void setY(double aY)
   {
      if (aY > 0 && aY < RADIUS)
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

   public String toString()
   {
      String result = "X: " + this.getX() + "Y: " + this.getY();
      return result;
   }

   private int RADIUS = 1000;
   private ArrayList<Node> neighbors;
   private ArrayList<Node> visitedNeighbors;
   private double forceX, forceY, x, y;
}
