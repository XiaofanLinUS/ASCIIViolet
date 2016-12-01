package com.cs151.asciiviolet;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.horstmann.violet.framework.GraphFrame;

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
   private Reader reader;
   private Font font;
   private GraphFrame frame;

   /**
    * Construct a TextBar with a given reader and graph frame
    * 
    * @param aReader
    *           the given reader
    * @param aFrame
    *           the given graph frame
    */
   public TextBar(Reader aReader, GraphFrame aFrame)
   {
      font = new Font("SansSerif", Font.PLAIN, 18);
      reader = aReader;
      frame = aFrame;
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
      frame.setGraph(reader.read(userInput));
   }
}
