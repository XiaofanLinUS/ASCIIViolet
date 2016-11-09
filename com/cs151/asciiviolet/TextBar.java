package com.cs151.asciiviolet;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextBar extends JPanel {
	private String userInput;
	private JTextArea textfield;
	private JButton button;
	
	public TextBar(){
		
		setLayout(new BorderLayout());
		userInput = "";
		textfield = new JTextArea();
		textfield.setPreferredSize(new Dimension(200,200));
		button = new JButton("draw");
		
		button.addActionListener( (e) -> {getInput();});
		
		add(textfield,BorderLayout.SOUTH);
		add(button, BorderLayout.NORTH);
	}
	private void getInput(){
		userInput = textfield.getText();
	}
}

	