package gui;

import javax.swing.JFrame;
import interfaces.StaticVariables;

public class MyFrame extends JFrame{
	
	
	public MyFrame()
	{
	 super();
	 
//	 this.setSize(StaticVariables.screen_width,StaticVariables.screen_height);
	 this.setSize(StaticVariables.finestra_width,StaticVariables.finestra_height);
	 this.setLocationRelativeTo(null); //mostra il frame al centro dello schermo
//	 this.game.getProporziona().setSizeBAckgroundAndScacchiera(this.getWidth(),this.getHeight()); //qui proporziono lo sfondo e la scacchiera, prima che venga creato il panel con il rendering
	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

}
