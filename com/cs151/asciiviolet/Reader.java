package com.cs151.asciiviolet;

import com.horstmann.violet.ClassDiagramGraph;
import com.horstmann.violet.framework.Graph;

/**
 * A reader that reads a command and do some modification on a graph
 * 
 * @author linxiaofan
 *
 */
public interface Reader
{

   /**
    * Read the input and execute it
    * 
    * @param input
    *           the given input
    */
   void read(String command);

   /**
    * Return the corresponding reader with a given graph
    * 
    * @param graph
    *           the given graph
    * @return a reader that deals with the graph
    */
   static Reader get(Graph graph)
   {
      if (graph.getClass().equals(ClassDiagramGraph.class))
      {
         return new ClassDiagramReader(graph);
      }
      return new ClassDiagramReader(graph);
   }

}
