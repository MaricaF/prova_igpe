package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import interfaces.StaticVariables;
import interfaces.Variables;

public class FinalPanel extends MyPanel{

	private MyButton retry;
	private MyButton exit;
	private boolean win;
	private boolean exitOrNot;
	private Immagine immagine;
	private int dispose_state;
	
	public FinalPanel(Menu menu, String panel_name, boolean win, int tavolo_w, int tavolo_h, int tavolo_x, int tavolo_y) {
		super(menu, panel_name);
		Variables.endGame = true;
		this.win = win;
		this.tavolo_w = tavolo_w;
		this.tavolo_h = tavolo_h;
		this.tavolo_x = tavolo_x;
		this.tavolo_y = tavolo_y;
		this.init();
		//verrà chiamata l'init quando mi servirà davvero il pannello
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

			for (Immagine i : this.caricatore_immagini.getScacchieraSfondo().values())
			{
				if(i.isVisible())
				  g.drawImage(i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
			}
			
			 if(this.mainButtonsVisible)
			    {
			        for(Immagine i: this.retry.getBottoni())
				         if(i.isVisible())
			               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
			
			        for(Immagine i: this.exit.getBottoni())
				        if(i.isVisible())
				              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
			    }
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.mainButtonsVisible)
		{
		  if((e.getX() >= this.retry.getX() && e.getX() <= (this.retry.getX()+this.retry.getWidth())) && (e.getY() >= this.retry.getY() && e.getY() <= (this.retry.getY()+this.retry.getHeight())))
		  {
			  if(win)
				  this.dispose_state = 1;
			  else
				  this.dispose_state = 2;
			  System.out.println("WOOOOOOOO retry button");
			  //si ricomincia il gioco
			  this.menu.replay(this,this.dispose_state, false);
		  }
		  else if((e.getX() >= this.exit.getX() && e.getX() <= (this.exit.getX()+this.exit.getWidth())) && (e.getY() >= this.exit.getY() && e.getY() <= (this.exit.getY()+this.exit.getHeight())))
		  {
			  if(win)
				  this.dispose_state = 1;
			  else
				  this.dispose_state = 2;
		  
		      System.out.println("WOOOOOOOOO exit button");
			  this.menu.disposeFrame(this.dispose_state, true);
		  }
		 }
	}
	
	public void init()
	{
		this.back_button.getBottoni().get(0).setVisible(false);
		this.dispose_state = 0;
		this.mainButtonsVisible = this.exitOrNot = false;
		System.out.println("final panel w: "+this.menu.getWidth()+", h: "+this.menu.getHeight());
		this.exit =  new MyButton(new Immagine(StaticVariables.ID_EXITBUTTON, StaticVariables.PATH_EXITBUTTON, -1),new Immagine(StaticVariables.ID_PRESSEDEXITBUTTON, StaticVariables.PATH_PRESSEDEXITBUTTON, -1),StaticVariables.EXIT_NAME);
		this.exit.proporzionaB(this.proporziona, this);
		this.retry = new MyButton(new Immagine(StaticVariables.ID_RETRY_BUTTON, StaticVariables.PATH_RETRYBUTTON, -1),new Immagine(StaticVariables.ID_PRESSEDRETRYBUTTON, StaticVariables.PATH_PRESSEDRETRYBUTTON, -1),StaticVariables.RETRY_NAME);
		this.retry.proporzionaB(this.proporziona, this);
		this.retry.getBottoni().get(0).setWH(this.exit.getBottoni().get(0).getImageWidth(),
				this.exit.getBottoni().get(0).getImageHeight());
		this.retry.getBottoni().get(0).setY(this.exit.getBottoni().get(0).getY());

		this.retry.getBottoni().get(1).setWH(this.exit.getBottoni().get(1).getImageWidth(),
				this.exit.getBottoni().get(1).getImageHeight());
		this.retry.getBottoni().get(1).setY(this.exit.getBottoni().get(1).getY());
		
		this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setVisible(true);
		this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setImageHeight(StaticVariables.finestra_height);
	    this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setImageWidth(StaticVariables.finestra_width);
	    if(this.win)
	    	this.immagine = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_PAPER_YOUWON);
		else
			this.immagine = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_PAPER_YOULOST);
	    
	    this.immagine.setXYWH(this.tavolo_x, this.tavolo_y, this.tavolo_w, this.tavolo_h);
	    this.immagine.setVisible(true);
		this.mainButtonsVisible = true;
	}
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	 if(this.mainButtonsVisible)
	 {
		if((e.getX() >= this.retry.getX() && e.getX() <= (this.retry.getX()+this.retry.getWidth())) && (e.getY() >= this.retry.getY() && e.getY() <= (this.retry.getY()+this.retry.getHeight())))
		{
			this.retry.changeImage();
			this.repaint();
		}
		
		else
		{
			this.retry.returnToFirstImage();
			this.repaint();
		}
		
	    if((e.getX() >= this.exit.getX() && e.getX() <= (this.exit.getX()+this.exit.getWidth())) && (e.getY() >= this.exit.getY() && e.getY() <= (this.exit.getY()+this.exit.getHeight())))
		{
			this.exit.changeImage();
			this.repaint();
		}
		 
		 else
			{
				this.exit.returnToFirstImage();
				this.repaint();
			}
	  }
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
