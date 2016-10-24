import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class GraphIcon implements Icon
{

   public GraphIcon(Graph aGraph)
   {
      graph = aGraph;
      width = (int) (aGraph.maxX() - aGraph.minX()) + 100;
      height = (int) (aGraph.maxY() - aGraph.minY()) + 100;
   }

   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      double[] coordinate1, coordinate2;

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
         coordinate1 = relocate(node1);
         point1 = new Point2D.Double(x + coordinate1[0], y + coordinate1[1]);
         g2.draw(new Rectangle2D.Double(x + coordinate1[0], y + coordinate1[1], 5, 5));
         for (int j = 0; j < node1.neighborSize(); j++)
         {
            node2 = node1.getNeighbor(j);
            coordinate2 = relocate(node2);
            if (!node2.visited(node1))
            {

               point2 = new Point2D.Double(x + coordinate2[0], y + coordinate2[1]);
               g2.draw(new Line2D.Double(point1, point2));
               node1.visit(node2);
            }
         }
      }
   }

   public double[] relocate(Node node)
   {
      double[] coordinate = new double[2];
      /*
       * double x = width / 2.0 + node.getX(); double y = node.getY() - width /
       * 2.0; y = Math.abs(y); coordinate[0] = x; coordinate[1] = y;
       */
      coordinate[0] = node.getX() - graph.minX() + 50;
      coordinate[1] = node.getY() - graph.minY() + 50;
      return coordinate;
   }

   public int getIconWidth()
   {
      return width;
   }

   public int getIconHeight()
   {

      return height;
   }

   private int width;
   private int height;
   private Graph graph;
}
