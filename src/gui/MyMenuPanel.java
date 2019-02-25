package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import audio.Sounds;
import interfaces.StaticVariables;

public class MyMenuPanel extends MyPanel{
	
	private MyButton play;
	
	public MyMenuPanel(Menu menu, String panel_name, int frameWidth, int frameHeight)
	{
		super(menu, panel_name);
		this.play = new MyButton(new Immagine(StaticVariables.ID_PLAYBUTTON,StaticVariables.PATH_PLAYBUTTON,-1), new Immagine(StaticVariables.ID_PRESSEDPLAYBUTTON, StaticVariables.PATH_PRESSEDPLAYBUTTON,-1), StaticVariables.PLAY_NAME);
		this.play.proporzionaB(this.proporziona, this);
        this.setProperties(true);
        this.mainButtonsVisible = true;
		
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
		
		        for(Immagine i: this.play.getBottoni())
			         if(i.isVisible())
		               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
		        for(Immagine i: this.back_button.getBottoni())
			        if(i.isVisible())
			              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		  if((e.getX() >= this.play.getX() && e.getX() <= (this.play.getX()+this.play.getWidth())) && (e.getY() >= this.play.getY() && e.getY() <= (this.play.getY()+this.play.getHeight())))
		  {
			  Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			  this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setWH(this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageWidth(), this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageHeight());
			((MyPlayPanel)this.menu.getPlayPanel()).getGame().play();
			this.menu.setPanelProperties(this.menu.getPlayPanel(), true);
		  }else if((e.getX() >= this.back_button.getX() && e.getX() <= (this.back_button.getX()+this.back_button.getWidth())) && (e.getY() >= this.back_button.getY() && e.getY() <= (this.back_button.getY()+this.back_button.getHeight())))
		  {
			  Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			  this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			  this.menu.setPanelProperties(this.menu.getTypesOfIntelligencePanel(), true);
		  }
	}
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if((e.getX() >= this.play.getX() && e.getX() <= (this.play.getX()+this.play.getWidth())) && (e.getY() >= this.play.getY() && e.getY() <= (this.play.getY()+this.play.getHeight())))
		{
			this.play.changeImage();
			this.repaint();
		}
		
		else
		{
			this.play.returnToFirstImage();
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
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
