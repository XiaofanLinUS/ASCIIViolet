package com.cs151.asciiviolet;

import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class layoutTester
{

   public static void main(String[] args)
   {
	   /*
      Graph aGraph = new Graph(80);
      Node a = new Node("a");
      Node b = new Node("b");
      Node c = new Node();
      Node d = new Node();
      Node e = new Node();
      Node f = new Node();
      Node g = new Node();
      Node h = new Node();
      Node i = new Node();
      a.connectNeighbor(b);
      a.connectNeighbor(c);
      d.connectNeighbor(b);
      d.connectNeighbor(c);
      e.connectNeighbor(b);
      e.connectNeighbor(f);
      e.connectNeighbor(g);
      g.connectNeighbor(f);
      g.connectNeighbor(h);
      g.connectNeighbor(i);

      aGraph.add(a);
      aGraph.add(b);
      aGraph.add(c);
      aGraph.add(d);
      aGraph.add(e);
      aGraph.add(f);
      aGraph.add(g);
      aGraph.add(h);
      aGraph.add(i);
      aGraph.move();
      */
      
      String text ="[a]-[b]\n[a]-[c]\n[d]-[b]\n[d]-[c]\n[e]-[b]\n[e]-[f]\n[e]-[g]\n[g]-[f]\n[g]-[h]\n[g]-[i]" ;
      classDiagramReader a = new classDiagramReader();

      JFrame frame = new JFrame();

      Icon icon = new GraphIcon(a.read(text));

      //Icon icon = new GraphIcon(aGraph);

      final JLabel label = new JLabel(icon);
      frame.setLayout(new FlowLayout());
      frame.add(label);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
}
