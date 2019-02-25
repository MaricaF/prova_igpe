package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import audio.Sounds;
import interfaces.StaticVariables;
import interfaces.Variables;

public class MyTypeOfOpponentPanel extends MyPanel{
	
	private MyButton multiplayer;
	private MyButton ai;
	private MyButton editor;
	private boolean game_is_started; //indica se il gioco è partito o meno
	private MyButton exit;
	private MyButton yes;
	private MyButton no;
	private int frameWidth, frameHeight;
	
	public MyTypeOfOpponentPanel(Menu menu, String panel_name, int frameWidth, int frameHeight)
	{
		super(menu,panel_name);
		this.game_is_started = false;
		this.multiplayer = new MyButton(new Immagine(StaticVariables.ID_MULTIPLAYERBUTTON,StaticVariables.PATH_MULTIPLAYERBUTTON,-1), new Immagine(StaticVariables.ID_PRESSEDMULTIPLAYERBUTTON, StaticVariables.PATH_PRESSEDMULTIPLAYERBUTTON,-1), StaticVariables.MULTIPLAYER_NAME);
		this.multiplayer.proporzionaB(this.proporziona, this);
		
		this.ai = new MyButton(new Immagine(StaticVariables.ID_AIBUTTON,StaticVariables.PATH_AIBUTTON,-1), new Immagine(StaticVariables.ID_PRESSEDAIBUTTON, StaticVariables.PATH_PRESSEDAI,-1), StaticVariables.AI_NAME);
		this.ai.proporzionaB(this.proporziona, this);
		
		this.exit = new MyButton(new Immagine(StaticVariables.ID_EXITBUTTON,StaticVariables.PATH_EXITBUTTON,-1), new Immagine(StaticVariables.ID_PRESSEDEXITBUTTON,StaticVariables.PATH_PRESSEDEXITBUTTON,-1), StaticVariables.EXIT_NAME);
        this.exit.proporzionaB(this.proporziona, this);
        
        this.yes = new MyButton(new Immagine(StaticVariables.ID_YESBUTTON,StaticVariables.PATH_YESBUTTON,-1), new Immagine(StaticVariables.ID_PRESSEDYESBUTTON,StaticVariables.PATH_PRESSEDYESBUTTON,-1), StaticVariables.YES_NAME);
        this.yes.proporzionaB(this.proporziona, this);
        
        this.no = new MyButton(new Immagine(StaticVariables.ID_NOBUTTON,StaticVariables.PATH_NOBUTTON,-1), new Immagine(StaticVariables.ID_PRESSEDNOBUTTON,StaticVariables.PATH_PRESSEDNOBUTTON,-1), StaticVariables.NO_NAME);
        this.no.proporzionaB(this.proporziona, this);
        
        this.editor = new MyButton(new Immagine(StaticVariables.ID_EDITOR_BUTTON,StaticVariables.PATH_EDITOR_BUTTON,-1), new Immagine(StaticVariables.ID_EDITOR_BUTTON,StaticVariables.PATH_EDITOR_BUTTON,-1), StaticVariables.EDITOR_NAME);
        this.editor.proporzionaB(this.proporziona, this);
        
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
		
		this.setProperties(true);
		this.mainButtonsVisible = true;
		this.back_button.getBottoni().get(0).setVisible(false);
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
		
		    if(this.mainButtonsVisible)
		    {
		        for(Immagine i: this.multiplayer.getBottoni())
			         if(i.isVisible())
		               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
		
		        for(Immagine i: this.ai.getBottoni())
			        if(i.isVisible())
			              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
		        
		        for(Immagine i: this.exit.getBottoni())
			        if(i.isVisible())
			              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
		        
		        for(Immagine i: this.editor.getBottoni())
			        if(i.isVisible())
			              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
		    }
		    else
		    {
		    	for(Immagine i: this.yes.getBottoni())
		            if(i.isVisible())
	                  g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
	
	           for(Immagine i: this.no.getBottoni())
		           if(i.isVisible())
		              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
		    }
		   
		 }
}
		
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(this.mainButtonsVisible)
		{
		
		if((e.getX() >= this.multiplayer.getX() && e.getX() <= (this.multiplayer.getX()+this.multiplayer.getWidth())) && (e.getY() >= this.multiplayer.getY() && e.getY() <= (this.multiplayer.getY()+this.multiplayer.getHeight())) && !this.game_is_started)
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setWH(this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageWidth(), this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageHeight());
			this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			Variables.single_player = false;
			this.menu.setPanelProperties(this.menu.getMyMenuPanel(), true);
			
		  }
		  else if((e.getX() >= this.ai.getX() && e.getX() <= (this.ai.getX()+this.ai.getWidth())) && (e.getY() >= this.ai.getY() && e.getY() <= (this.ai.getY()+this.ai.getHeight())) && !this.game_is_started)
		  {
			  Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			    this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setWH(this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageWidth(), this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).getImageHeight());
				this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
				Variables.single_player = true;
				this.menu.setPanelProperties(this.menu.getTypesOfIntelligencePanel(), true);
				((MyPlayPanel)this.menu.getPlayPanel()).getGame().getDama().printMatrix();
		  }
		  else if((e.getX() >= this.exit.getX() && e.getX() <= (this.exit.getX()+this.exit.getWidth())) && (e.getY() >= this.exit.getY() && e.getY() <= (this.exit.getY()+this.exit.getHeight())))
		  {
			  Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			  this.exitOrNot(StaticVariables.PATH_EXITIMAGE, false);
		  }
		  else if((e.getX() >= this.editor.getX() && e.getX() <= (this.editor.getX()+this.editor.getWidth())) && (e.getY() >= this.editor.getY() && e.getY() <= (this.editor.getY()+this.editor.getHeight())))
		  {
			  Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			  this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			  this.menu.setPanelProperties(this.menu.getEditorPanel(), true);
		  }
		}
		else //se play ed exit non sono più visibili e premibili
		{
			if((e.getX() >= this.yes.getX() && e.getX() <= (this.yes.getX()+this.yes.getWidth())) && (e.getY() >= this.yes.getY() && e.getY() <= (this.yes.getY()+this.yes.getHeight())))
			{
				 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.menu.disposeFrame(0, true); //chiude il frame.
			}
			else if((e.getX() >= this.no.getX() && e.getX() <= (this.no.getX()+this.no.getWidth())) && (e.getY() >= this.no.getY() && e.getY() <= (this.no.getY()+this.yes.getHeight())))
			{
				 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
				this.exitOrNot(StaticVariables.PATH_SFONDO1, true);
			}
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(this.mainButtonsVisible)
		{
	   if((e.getX() >= this.multiplayer.getX() && e.getX() <= (this.multiplayer.getX()+this.multiplayer.getWidth())) && (e.getY() >= this.multiplayer.getY() && e.getY() <= (this.multiplayer.getY()+this.multiplayer.getHeight())) && !this.game_is_started)
		{
			this.multiplayer.changeImage();
			this.repaint();
		}
		
		else 
		{
			this.multiplayer.returnToFirstImage();
			this.repaint();
		}
		
	   if((e.getX() >= this.ai.getX() && e.getX() <= (this.ai.getX()+this.ai.getWidth())) && (e.getY() >= this.ai.getY() && e.getY() <= (this.ai.getY()+this.ai.getHeight())) && !this.game_is_started)
		{
			this.ai.changeImage();
			this.repaint();
		}
		 
		 else
			{
				this.ai.returnToFirstImage();
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
	   
	   if((e.getX() >= this.editor.getX() && e.getX() <= (this.editor.getX()+this.editor.getWidth())) && (e.getY() >= this.editor.getY() && e.getY() <= (this.editor.getY()+this.editor.getHeight())))
		{
			this.editor.changeImage();
			this.repaint();
		}
		 
		 else
			{
				this.editor.returnToFirstImage();
				this.repaint();
			}
		}//fine if mainButton
		else
		{
			if((e.getX() >= this.yes.getX() && e.getX() <= (this.yes.getX()+this.yes.getWidth())) && (e.getY() >= this.yes.getY() && e.getY() <= (this.yes.getY()+this.yes.getHeight())))
			{
				this.yes.changeImage();
				this.repaint();
			}
			
			else
			{
				this.yes.returnToFirstImage();
				this.repaint();
			}
			
		    if((e.getX() >= this.no.getX() && e.getX() <= (this.no.getX()+this.no.getWidth())) && (e.getY() >= this.no.getY() && e.getY() <= (this.no.getY()+this.no.getHeight())))
			{
				this.no.changeImage();
				this.repaint();
			}
			 
			 else
				{
					this.no.returnToFirstImage();
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

	public MyButton getMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(MyButton multiplayer) {
		this.multiplayer = multiplayer;
	}

	public MyButton getAi() {
		return ai;
	}

	public void setAi(MyButton ai) {
		this.ai = ai;
	}

	public MyButton getExit() {
		return exit;
	}

	public void setExit(MyButton exit) {
		this.exit = exit;
	}

	public MyButton getYes() {
		return yes;
	}

	public void setYes(MyButton yes) {
		this.yes = yes;
	}

	public MyButton getNo() {
		return no;
	}

	public void setNo(MyButton no) {
		this.no = no;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
	
	

	

}
