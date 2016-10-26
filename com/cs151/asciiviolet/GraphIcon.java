package com.cs151.asciiviolet;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.*;
import javax.swing.*;

import javax.swing.Icon;

public class GraphIcon implements Icon
{

   public GraphIcon(Graph aGraph)
   {
      graph = aGraph;
   }

   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      Node node1, node2;
      Point2D point1, point2;

      Graphics2D g2 = (Graphics2D) g;

      for (int i = 0; i < graph.size(); i++)
      {
         graph.getNode(i).resetVisitedNeighbors();
      }

      for (int i = 0; i < graph.size(); i++)
      {
         node1 = graph.getNode(i);


         point1 = new Point2D.Double(x + node1.getX(), y + node1.getY());
         
         Icon box = new Box(node1.getName());
         box.paintIcon(c,g,(int)point1.getX(),(int)point1.getY());
        
             

         point1 = new Point2D.Double(x + node1.getX(), y + node1.getY());
         g2.draw(new Rectangle2D.Double(x + node1.getX(), y + node1.getY(), 5, 5));

         for (int j = 0; j < node1.neighborSize(); j++)
         {
            node2 = node1.getNeighbor(j);
            if (!node2.visited(node1))
            {

               point2 = new Point2D.Double(x + node2.getX(), y + node2.getY());
               g2.draw(new Line2D.Double(point1, point2));
               node1.visit(node2);
            }
         }
      }
   }

   public int getIconWidth()
   {
      return graph.getWidth();
   }

   public int getIconHeight()
   {

      return graph.getHeight();
   }

   private int size = 20; 
   private int width;
   private int height;
   private Graph graph;
}
