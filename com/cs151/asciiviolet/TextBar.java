package com.cs151.asciiviolet;

import java.awt.BorderLayout;
import java.awt.Font;

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
   private Reader reader;
   private Font font;

   /**
    * Construct a TextBar with a given graph and graph panel
    * 
    * @param aGraph
    *           the given graph
    * @param aPanel
    *           the iven graph panel
    */
   public TextBar(Graph aGraph, GraphPanel aPanel)
   {
      font = new Font("SansSerif", Font.PLAIN, 18);
      reader = Reader.get(aGraph);
      panel = aPanel;
      setLayout(new BorderLayout());
      userInput = "";
      textfield = new JTextArea(10, 100); // 15 row
      textfield.setFont(font);
      JScrollPane scrollableTextfield = new JScrollPane(textfield);
      scrollableTextfield.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollableTextfield.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

   private String getUserInput()
   {
      return userInput;
   }
}
