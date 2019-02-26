package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import audio.Sounds;
import interfaces.StaticVariables;

public class TypesOfIntelligencePanel extends MyPanel{

	private MyButton simple;
	private MyButton medium;
	
	public TypesOfIntelligencePanel(Menu menu, String panel_name, int frameWidth, int frameHeight) {
		super(menu, panel_name);
		// TODO Auto-generated constructor stub
		this.simple = new MyButton(new Immagine(StaticVariables.ID_SIMPLE_AI, StaticVariables.PATH_SIMPLE_AI, -1), new Immagine(StaticVariables.ID_SIMPLE_AI, StaticVariables.PATH_SIMPLE_AI, -1), StaticVariables.SIMPLE_AI_NAME);
		this.simple.proporzionaB(this.proporziona, this);
		
		this.medium = new MyButton(new Immagine(StaticVariables.ID_MEDIUM_AI, StaticVariables.PATH_MEDIUM_AI, -1), new Immagine(StaticVariables.ID_MEDIUM_AI, StaticVariables.PATH_MEDIUM_AI, -1), StaticVariables.MEDIUM_AI_NAME);
		this.medium.proporzionaB(this.proporziona, this);
		
		this.setProperties(true);
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		
		if(this.isVisible())
		{
			
			for(Immagine i: this.caricatore_immagini.getScacchieraSfondo().values())
		         if(i.isVisible())
	               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
			
//		    g.drawImage( this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImage(),
//		    		     this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getX(),
//		    		     this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getY(), 
//		    		     this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageWidth(), 
//		    		     this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageHeight(), 
//					     this);
		
		        for(Immagine i: this.simple.getBottoni())
			         if(i.isVisible())
		               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
		        
		        for(Immagine i: this.medium.getBottoni())
			         if(i.isVisible())
		               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
		        
		        for(Immagine i: this.back_button.getBottoni())
			        if(i.isVisible())
			              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if((e.getX() >= this.simple.getX() && e.getX() <= (this.simple.getX()+this.simple.getWidth())) && (e.getY() >= this.simple.getY() && e.getY() <= (this.simple.getY()+this.simple.getHeight())))
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setWH(this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageWidth(), this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageHeight());
			this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			((MyPlayPanel)this.menu.getPlayPanel()).getGame().setType_of_intelligence(this.simple.getButton_name());
			this.menu.setPanelProperties(this.menu.getMyMenuPanel(), true);
		  }
		else if((e.getX() >= this.medium.getX() && e.getX() <= (this.medium.getX()+this.medium.getWidth())) && (e.getY() >= this.medium.getY() && e.getY() <= (this.medium.getY()+this.medium.getHeight())))
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setWH(this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageWidth(), this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageHeight());
			this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			((MyPlayPanel)this.menu.getPlayPanel()).getGame().setType_of_intelligence(this.medium.getButton_name());
			this.menu.setPanelProperties(this.menu.getMyMenuPanel(), true);
		  }
		else if((e.getX() >= this.back_button.getX() && e.getX() <= (this.back_button.getX()+this.back_button.getWidth())) && (e.getY() >= this.back_button.getY() && e.getY() <= (this.back_button.getY()+this.back_button.getHeight())))
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			  this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			  this.menu.setPanelProperties(this.menu.getMyTypeOfOpponentPanel(), true);
		  }
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		 if((e.getX() >= this.simple.getX() && e.getX() <= (this.simple.getX()+this.simple.getWidth())) && (e.getY() >= this.simple.getY() && e.getY() <= (this.simple.getY()+this.simple.getHeight())))
			{
				this.simple.changeImage();
				this.repaint();
			}
			
			else 
			{
				this.simple.returnToFirstImage();
				this.repaint();
			}
		 
		 if((e.getX() >= this.medium.getX() && e.getX() <= (this.medium.getX()+this.medium.getWidth())) && (e.getY() >= this.medium.getY() && e.getY() <= (this.medium.getY()+this.medium.getHeight())))
			{
				this.medium.changeImage();
				this.repaint();
			}
			
			else 
			{
				this.medium.returnToFirstImage();
				this.repaint();
			}
		 
		 if((e.getX() >= this.back_button.getX() && e.getX() <= (this.back_button.getX()+this.back_button.getWidth())) && (e.getY() >= this.back_button.getY() && e.getY() <= (this.back_button.getY()+this.back_button.getHeight())))
		 {
			 this.back_button.changeImage();
			 this.repaint();
		 }
		 else 
			{
				this.back_button.returnToFirstImage();
				this.repaint();
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
