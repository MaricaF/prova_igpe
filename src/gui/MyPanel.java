package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import core.ProporzionaImmagine;
import interfaces.StaticVariables;

public abstract class MyPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	protected String panel_name;
	protected ProporzionaImmagine proporziona;
	protected CaricatoreImmagini caricatore_immagini;
	protected Menu menu;
	protected boolean mainButtonsVisible; //è la booleana che indica se i bottoni Play ed Exit sono visibili e premibili.
	protected int tavolo_w, tavolo_h, tavolo_x, tavolo_y;
	protected MyButton back_button, ok_button;
	
	public MyPanel(Menu menu, String panel_name)
	{
		this.menu = menu;
	    this.panel_name = panel_name;
	    this.caricatore_immagini = new CaricatoreImmagini();
	    this.proporziona = new ProporzionaImmagine(this.caricatore_immagini); 
	    this.proporziona.setSizeBAckgroundAndScacchiera(StaticVariables.finestra_width,StaticVariables.finestra_height);
        this.proporziona.resizeOtherPopups();
       
        this.back_button = new MyButton(new Immagine(StaticVariables.ID_BACK_BUTTON, StaticVariables.PATH_BACKBUTTON,-1), 
        		new Immagine(StaticVariables.ID_PRESSEBACKBUTTON, StaticVariables.PATH_BACKBUTTON,-1), 
        		StaticVariables.BACK_NAME);
        this.back_button.proporzionaB(this.proporziona, this);
        
        this.ok_button = new MyButton(new Immagine(StaticVariables.ID_OK, StaticVariables.PATH_OK, -1), 
        		new Immagine(StaticVariables.ID_OK, StaticVariables.PATH_OK, -1),StaticVariables.OK_NAME);
        this.ok_button.proporzionaB(this.proporziona, this);
        this.ok_button.getBottoni().get(0).setVisible(false);
        
		this.setPreferredSize(new Dimension(StaticVariables.finestra_width,StaticVariables.finestra_height));
		this.setBackground(Color.WHITE);
        this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setProperties(true);
		this.mainButtonsVisible = false;
		this.tavolo_h = this.tavolo_w = this.tavolo_x = this.tavolo_y = 0;
		
	}
	
	public void setProperties(boolean b)
	{
		this.setVisible(b);
		this.setFocusable(b);
		this.setRequestFocusEnabled(b);
	}
	
	/**
	 * Cambia l'immagine di questo pannello per mostrare l'immagine del "do you really want to exit?".
	 * Rende 'invisibili' le immagine 'play' ed 'exit' e rende la loro posizione non rilevabile, tramite la booleana del loro MyButton
	 * Oppure fa tutto il contrario
	 */
	protected void exitOrNot(String image_path, boolean mainButtonVisible)
	{
		this.mainButtonsVisible = mainButtonVisible;
		this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setPath(image_path);
		this.repaint();
	}
	
	protected void printScacchieraSfondo()
	{
		for(Immagine i: this.caricatore_immagini.getScacchieraSfondo().values())
			System.out.println("path: "+i.getPath()+"is visible: "+i.isVisible());
		System.out.println();
	}
	
	public void actionPerformed(ActionEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String getPanel_name() {
		return panel_name;
	}

	public void setPanel_name(String panel_name) {
		this.panel_name = panel_name;
	}

	public ProporzionaImmagine getProporziona() {
		return proporziona;
	}

	public void setProporziona(ProporzionaImmagine proporziona) {
		this.proporziona = proporziona;
	}

	public CaricatoreImmagini getCaricatore_immagini() {
		return caricatore_immagini;
	}

	public void setCaricatore_immagini(CaricatoreImmagini caricatore_immagini) {
		this.caricatore_immagini = caricatore_immagini;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	
	

}
