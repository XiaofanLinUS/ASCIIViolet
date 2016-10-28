package com.cs151.asciiviolet;

import java.util.ArrayList;

public class Node1
{
   public Node1()
   {
     this.name = "";
      forceX = 0;
      forceY = 0;
      x = Math.random();
      y = Math.random();
      neighbors = new ArrayList<>();
      visitedNeighbors = new ArrayList<>();
   }
   
   public Node1(String name)
   {
      this.name = name;
      forceX = 0;
      forceY = 0;
      x = Math.random();
      y = Math.random();
      neighbors = new ArrayList<>();
      visitedNeighbors = new ArrayList<>();
   }
   
   public String getName()
   {
      return name;
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

   public void connectNeighbor(Node1 aNeighbors)
   {
      aNeighbors.neighbors.add(this);
      neighbors.add(aNeighbors);
   }

   public void visit(Node1 aNeightbor)
   {
      visitedNeighbors.add(aNeightbor);
   }

   public boolean visited(Node1 aNeightbor)
   {
      return visitedNeighbors.contains(aNeightbor);
   }

   public void resetVisitedNeighbors()
   {
      visitedNeighbors = new ArrayList<>();
   }

   public Node1 getNeighbor(int i)
   {
      return neighbors.get(i);
   }

   public String toString()
   {
      String result = "X: " + this.getX() + ", Y: " + this.getY();
      return result;
   }
   private String name;
   private ArrayList<Node1> neighbors;
   private ArrayList<Node1> visitedNeighbors;
   private double forceX, forceY, x, y;
}
