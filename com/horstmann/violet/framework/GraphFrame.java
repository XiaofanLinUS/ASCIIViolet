/*
Violet - A program for editing UML diagrams.

Copyright (C) 2002 Cay S. Horstmann (http://horstmann.com)

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package com.horstmann.violet.framework;

import java.awt.BorderLayout;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.cs151.asciiviolet.Reader;
import com.cs151.asciiviolet.TextBar;

/**
 * A frame for showing a graphical editor
 */
public class GraphFrame extends JInternalFrame
{
   /**
    * Constructs a graph frame with an empty tool bar
    * 
    * @param aGraph
    *           the initial graph
    */
   public GraphFrame(Graph aGraph)
   {
      graph = aGraph;
      toolBar = new ToolBar(graph);
      panel = new GraphPanel(toolBar); // toolbar
      textBar = new TextBar(Reader.get(graph), this);// add textbar

      Container contentPane = getContentPane();
      contentPane.add(textBar, BorderLayout.NORTH); // toolBar
      contentPane.add(new JScrollPane(panel), BorderLayout.CENTER);
      contentPane.add(toolBar, BorderLayout.SOUTH);
      // add listener to confirm frame closing
      addVetoableChangeListener(new VetoableChangeListener()
      {
         public void vetoableChange(PropertyChangeEvent event) throws PropertyVetoException
         {
            String name = event.getPropertyName();
            Object value = event.getNewValue();

            // we only want to check attempts to close a frame
            if (name.equals("closed") && value.equals(Boolean.TRUE) && panel.isModified())
            {
               ResourceBundle editorResources = ResourceBundle
                     .getBundle("com.horstmann.violet.framework.EditorStrings");

               // ask user if it is ok to close
               int result = JOptionPane.showInternalConfirmDialog(GraphFrame.this,
                     editorResources.getString("dialog.close.ok"), null, JOptionPane.YES_NO_OPTION);

               // if the user doesn't agree, veto the close
               if (result != JOptionPane.YES_OPTION)
                  throw new PropertyVetoException("User canceled close", event);
            }
         }
      });

      panel.setGraph(graph);
   }

   /**
    * Gets the graph that is being edited in this frame.
    * 
    * @return the graph
    */
   public Graph getGraph()
   {
      return graph;
   }

   /**
    * Gets the graph panel that is contained in this frame.
    * 
    * @return the graph panel
    */
   public GraphPanel getGraphPanel()
   {
      return panel;
   }

   /**
    * Gets the fileName property.
    * 
    * @return the file name
    */
   public String getFileName()
   {
      return fileName;
   }

   /**
    * Sets the fileName property.
    * 
    * @param newValue
    *           the file name
    */
   public void setFileName(String newValue)
   {
      fileName = newValue;
      setTitle(newValue);
   }

   /**
    * Replace the graph with a new graph
    * 
    * @param aGraph
    *           the new graph
    */
   public void setGraph(Graph aGraph)
   {
      graph = aGraph;
      panel.setGraph(graph);
   }

   // private String userInput;
   private Graph graph;
   private GraphPanel panel;
   private TextBar textBar;
   private ToolBar toolBar;
   private String fileName;

}
