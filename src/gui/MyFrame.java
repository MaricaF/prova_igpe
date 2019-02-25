package gui;

import javax.swing.JFrame;
import interfaces.StaticVariables;

public class MyFrame extends JFrame{
	
	
	public MyFrame()
	{
	 super();
	 this.setSize(StaticVariables.finestra_width,StaticVariables.finestra_height);
	 this.setLocationRelativeTo(null); //mostra il frame al centro dello schermo
	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
