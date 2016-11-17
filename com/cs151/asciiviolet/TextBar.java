package com.cs151.asciiviolet;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.horstmann.violet.framework.Graph;
import com.horstmann.violet.framework.GraphPanel;

/**
 * A TextBar that receives string
 * 
 * @version 11/11/2016
 *
 */
public class TextBar extends JPanel
{
   private String userInput;
   private JTextArea textfield;
   private JButton button;
   private Graph graph;
   private GraphPanel panel;
   private ClassDiagramReader reader;

   /**
    * Construct a TextBar
    */
   public TextBar(Graph aGraph, GraphPanel aPanel)
   {
      reader = new ClassDiagramReader(aGraph);
      panel = aPanel;
      setLayout(new BorderLayout());
      graph = aGraph;
      userInput = "";
      textfield = new JTextArea(5, 100); // 5 row
      JScrollPane scrollableTextfield = new JScrollPane(textfield);
      scrollableTextfield.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      button = new JButton("draw");

      button.addActionListener((e) -> {
         executeCommand();
      });

      add(scrollableTextfield, BorderLayout.SOUTH);
      add(button, BorderLayout.NORTH);
   }

   private void executeCommand()
   {
      userInput = textfield.getText();
      reader.read(userInput);
      panel.setModified(true);
      panel.repaint();

   }

   public String getUserInput()
   {
      return userInput;
   }
}
