package com.cs151.asciiviolet;

import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class layoutTester
{

   public static void main(String[] args)
   {
      Graph aGraph = new Graph(80);
      Node a = new Node();
      Node b = new Node();
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
      g.connectNeighbor(a);
      aGraph.addNode(a);
      aGraph.addNode(b);
      aGraph.addNode(c);
      aGraph.addNode(d);
      aGraph.addNode(e);
      aGraph.addNode(f);
      aGraph.addNode(g);
      aGraph.addNode(h);
      aGraph.addNode(i);
      JFrame frame = new JFrame();

      Icon icon = new GraphIcon(aGraph);

      final JLabel label = new JLabel(icon);
      frame.setLayout(new FlowLayout());
      frame.add(label);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
}
