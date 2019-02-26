package editor;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JOptionPane;

import audio.Sounds;
import gui.Immagine;
import gui.Menu;
import gui.MyButton;
import gui.MyPanel;
import gui.MyPlayPanel;
import interfaces.StaticVariables;
import interfaces.Variables;

public class MyEditorPanel extends MyPanel{

	private HashMap<Integer, MyButton> editor_buttons;
	private MyButton save;
	private String path_image, path_dama;
	private MyButton user_pawn, ai_pawn;
	private boolean click_user_pawn, click_ai_pawn, click_table;
	private int id_table_pressed, id_ai_pressed, id_user_pressed;
	
	public MyEditorPanel(Menu menu, String panel_name) {
		super(menu, panel_name);
		this.editor_buttons = new HashMap<Integer, MyButton>();
		
		this.save = new MyButton(new Immagine(StaticVariables.ID_SAVE_BUTTON,StaticVariables.PATH_SAVE_BUTTON,-1), new Immagine(StaticVariables.ID_SAVE_BUTTONPRESSED, StaticVariables.PATH_SAVE_BUTTONPRESSED,-1), StaticVariables.SAVE_NAME);
		this.save.proporzionaEditorButton(this.proporziona);
		
		this.add(StaticVariables.ID_PEDINA_BIANCA,StaticVariables.PATH_PEDINA_BIANCA,StaticVariables.ID_PEDINA_BIANCA_PRESSED,StaticVariables.BIANCASTANDARD_NAME,StaticVariables.PATH_PEDINA_BIANCA_PRESSED);
		this.add(StaticVariables.ID_PEDINA_NERA,StaticVariables.PATH_PEDINA_NERA,StaticVariables.ID_PEDINA_NERA_PRESSED,StaticVariables.NERASTANDARD_NAME, StaticVariables.PATH_PEDINA_NERA_PRESSED);
		this.add(StaticVariables.ID_PEDINA_ROSA,StaticVariables.PATH_PEDINA_ROSA,StaticVariables.ID_PEDINA_ROSA_PRESSED,StaticVariables.ROSA_NAME, StaticVariables.PATH_PEDINA_ROSA_PRESSED);
		this.add(StaticVariables.ID_PEDINA_BIANCABIANCA,StaticVariables.PATH_PEDINA_BIANCABIANCA,StaticVariables.ID_PEDINA_BIANCABIANCA_PRESSED,StaticVariables.BIANCABIANCA_NAME, StaticVariables.PATH_PEDINA_BIANCABIANCA_PRESSED);
		this.add(StaticVariables.ID_PEDINA_MARRONE_SPIRALE,StaticVariables.PATH_PEDINA_MARRONE_SPIRALE,StaticVariables.ID_PEDINA_MARRONE_SPIRALE_PRESSED,StaticVariables.MARRONE_NAME, StaticVariables.PATH_PEDINA_MARRONE_SPIRALE_PRESSED);
		this.add(StaticVariables.ID_PEDINA_BIANCASPIRALE,StaticVariables.PATH_PEDINA_BIANCASPIRALE,StaticVariables.ID_PEDINA_BIANCASPIRALE_PRESSED,StaticVariables.BIANCASPIRALE_NAME, StaticVariables.PATH_PEDINA_BIANCASPIRALE_PRESSED);
		this.add(StaticVariables.ID_TAVOLO,StaticVariables.PATH_TAVOLO,StaticVariables.ID_TAVOLO_PRESSED,StaticVariables.TAVOLO_NAME, StaticVariables.PATH_TAVOLO_PRESSED);
		this.add(StaticVariables.ID_TAVOLO_UNIVERSO,StaticVariables.PATH_TAVOLO_UNIVERSO,StaticVariables.ID_TAVOLO_UNIVERSO_PRESSED,StaticVariables.UNIVERSO_NAME, StaticVariables.PATH_TAVOLO_UNIVERSO_PRESSED);
		this.add(StaticVariables.ID_TAVOLO_VERDE,StaticVariables.PATH_TAVOLO_VERDE,StaticVariables.ID_TAVOLO_VERDE_PRESSED,StaticVariables.TAVOLOVERDE_NAME, StaticVariables.PATH_TAVOLO_VERDE_PRESSED);
		
		this.back_button.proporzionaEditorButton(this.proporziona);
		this.path_image = this.path_dama = "";
		this.user_pawn = new MyButton(new Immagine(StaticVariables.ID_USER_PAWN, StaticVariables.PATH_USER_PAWN,-1), 
				new Immagine(StaticVariables.ID_USER_PAWN, StaticVariables.PATH_USER_PAWN,-1), StaticVariables.USER_PAWN_NAME);
		this.ai_pawn = new MyButton(new Immagine(StaticVariables.ID_AI_PAWN, StaticVariables.PATH_AI_PAWN,-1), 
				new Immagine(StaticVariables.ID_AI_PAWN, StaticVariables.PATH_AI_PAWN,-1), StaticVariables.AI_NAME);
		this.user_pawn.proporzionaEditorButton(this.proporziona);
		this.ai_pawn.proporzionaEditorButton(this.proporziona);
		
		this.click_ai_pawn = this.click_user_pawn = this.click_table = false;
		this.id_table_pressed = this.id_ai_pressed = this.id_user_pressed = -1;
		
		this.setProperties(true);
		this.mainButtonsVisible = true;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		
		
		if(this.isVisible())
		{
			if(!Variables.show_popup)
		{
			for (Immagine i : this.caricatore_immagini.getScacchieraSfondo().values()) {
				if (i.isVisible())
					g.drawImage(i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
			}
			
			for(MyButton b: this.editor_buttons.values())
			{
				for(Immagine i: b.getBottoni())
				{
		         if(i.isVisible())
	               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
				}
				
				 for(Immagine i: this.back_button.getBottoni())
			         if(i.isVisible())
		               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
				
				 for(Immagine i: this.save.getBottoni())
			         if(i.isVisible())
		               g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this); 
				 
				 for(Immagine i: this.ai_pawn.getBottoni())
				        if(i.isVisible())
				              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
				 
				 for(Immagine i: this.user_pawn.getBottoni())
				        if(i.isVisible())
				              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
			}
		}
			
			else
			{
						g.drawImage(this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).getImage(), 
								this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).getX(),
								this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).getY(), 
								this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).getImageWidth(), 
								this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).getImageHeight(), this);
				
				 for(Immagine i: this.ok_button.getBottoni())
				        if(i.isVisible())
				              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
			}
		}
	}
	
	private void add(int id, String path, int id2, String name, String pathImagePressed)
	{
		Immagine im1 = new Immagine(id, path,-1);
		Immagine im2 = new Immagine(id2, pathImagePressed,-1);
        MyButton button = new MyButton(im1, im2, name);
        button.proporzionaEditorButton(this.proporziona);
		this.editor_buttons.put(id, button);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
		if(Variables.show_popup)
		{
			if((e.getX() >= this.ok_button.getX() && e.getX() <= (this.ok_button.getX()+this.ok_button.getWidth())) && (e.getY() >= this.ok_button.getY() && e.getY() <= (this.ok_button.getY()+this.ok_button.getHeight())))
			{
				 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
				Variables.show_popup = false;
				this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setVisible(false);
			}
		}
		else {
		
		if((e.getX() >= this.save.getX() && e.getX() <= (this.save.getX()+this.save.getWidth())) && (e.getY() >= this.save.getY() && e.getY() <= (this.save.getY()+this.save.getHeight())))
		{
			if(this.controlloSave())
			{
				 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
				this.click_ai_pawn = this.click_user_pawn = this.click_table = false;
				this.id_table_pressed = this.id_ai_pressed = this.id_user_pressed = -1;
				Variables.editor = true;
				//cambio path qui, perché il costruttore di myPanel è già stato creato
				((MyPlayPanel)this.menu.getPlayPanel()).getCaricatore_immagini().getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setPath(Variables.PATH_TAVOLO);
				this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
				Variables.single_player = true;
				this.menu.setPanelProperties(this.menu.getTypesOfIntelligencePanel(), true);
			}
//			this.save.changeImage();
//		    this.repaint();
		}
		else if((e.getX() >= this.back_button.getX() && e.getX() <= (this.back_button.getX()+this.back_button.getWidth())) && (e.getY() >= this.back_button.getY() && e.getY() <= (this.back_button.getY()+this.back_button.getHeight())))
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			this.click_ai_pawn = this.click_user_pawn = this.click_table = false;
			this.id_table_pressed = this.id_ai_pressed = this.id_user_pressed = -1;
			  this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			  this.menu.setPanelProperties(this.menu.getMyTypeOfOpponentPanel(), true);
			  
		  }
		else if((e.getX() >= this.user_pawn.getX() && e.getX() <= (this.user_pawn.getX()+this.user_pawn.getWidth())) && (e.getY() >= this.user_pawn.getY() && e.getY() <= (this.user_pawn.getY()+this.user_pawn.getHeight())))
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			this.click_user_pawn = true;
			this.click_ai_pawn = false;
			this.click_table = false;
		  }
		else if((e.getX() >= this.ai_pawn.getX() && e.getX() <= (this.ai_pawn.getX()+this.ai_pawn.getWidth())) && (e.getY() >= this.ai_pawn.getY() && e.getY() <= (this.ai_pawn.getY()+this.ai_pawn.getHeight())))
		  {
			 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			this.click_ai_pawn = true;
			this.click_user_pawn = false;
			this.click_table = false;
		  }
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO).getX()+this.editor_buttons.get(StaticVariables.ID_TAVOLO).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO).getY()+this.editor_buttons.get(StaticVariables.ID_TAVOLO).getHeight())))
		  {
	    	 Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_TAVOLO).changeImage();
			   this.click_table = true;
			   this.click_user_pawn = false;
				this.click_ai_pawn = false;
			   this.path_image = StaticVariables.PATH_TAVOLO;
			   this.setPathImage(StaticVariables.ID_TAVOLO, this.path_image, this.path_dama);
			   this.repaint();
		  }
			
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getX()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getY()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).changeImage();
			   this.click_table = true;
			   this.click_user_pawn = false;
				this.click_ai_pawn = false;
			   this.path_image = StaticVariables.PATH_TAVOLO_UNIVERSO;
			   this.setPathImage(StaticVariables.ID_TAVOLO_UNIVERSO, this.path_image, this.path_dama);
				this.repaint();
			}
	    else  if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getX()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getY()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).changeImage();
			   this.click_table = true;
			   this.click_user_pawn = false;
				this.click_ai_pawn = false;
			   this.path_image = StaticVariables.PATH_TAVOLO_VERDE;
			   this.setPathImage(StaticVariables.ID_TAVOLO_VERDE, this.path_image, this.path_dama);
				this.repaint();
			}
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).changeImage();
			   this.path_image = StaticVariables.PATH_PEDINA_BIANCA;
			   this.path_dama = StaticVariables.PATH_PEDINA_DAMABIANCA;
			   this.setPathImage(StaticVariables.ID_PEDINA_BIANCA, this.path_image, this.path_dama);
				this.repaint();
			}
			
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).changeImage();
			   this.path_image = StaticVariables.PATH_PEDINA_NERA;
			   this.path_dama = StaticVariables.PATH_PEDINA_DAMANERA;
			   this.setPathImage(StaticVariables.ID_PEDINA_NERA, this.path_image, this.path_dama);
				this.repaint();
			}
		   
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).changeImage();
			   this.path_image = StaticVariables.PATH_PEDINA_BIANCABIANCA;
			   this.path_dama = StaticVariables.PATH_PEDINA_BIANCABIANCA_DAMA;
			   this.setPathImage(StaticVariables.ID_PEDINA_BIANCABIANCA, this.path_image, this.path_dama);
				this.repaint();
			}
		   
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getHeight())))
			{ 
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).changeImage();
			   this.path_image = StaticVariables.PATH_PEDINA_BIANCASPIRALE;
			   this.path_dama = StaticVariables.PATH_PEDINA_BIANCASPIRALE_DAMA;
			   this.setPathImage(StaticVariables.ID_PEDINA_BIANCASPIRALE, this.path_image, this.path_dama);
				this.repaint();
			}
		   
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).changeImage();
			   this.path_image = StaticVariables.PATH_PEDINA_MARRONE_SPIRALE;
			   this.path_dama = StaticVariables.PATH_PEDINA_MARRONE_SPRIRALE_DAMA;
			   this.setPathImage(StaticVariables.ID_PEDINA_MARRONE_SPIRALE, this.path_image, this.path_dama);
				this.repaint();
			}
		   
	    else if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getHeight())))
			{
	    	   Sounds.getSounds().play(StaticVariables.PATH_AUDIO_MENU_CLICK);
			   this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).changeImage();
			   this.path_image = StaticVariables.PATH_PEDINA_ROSA;
			   this.path_dama = StaticVariables.PATH_PEDINA_ROSA_DAMA;
			   this.setPathImage(StaticVariables.ID_PEDINA_ROSA, this.path_image, this.path_dama);
				this.repaint();
			}
		}
	}
	
   @Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	   
	   if(Variables.show_popup)
	   {
		   if((e.getX() >= this.ok_button.getX() && e.getX() <= (this.ok_button.getX()+this.ok_button.getWidth())) && (e.getY() >= this.ok_button.getY() && e.getY() <= (this.ok_button.getY()+this.ok_button.getHeight())))
			{
			   this.ok_button.changeImage();
				this.repaint();
			}
			
			else 
			{
				this.ok_button.returnToFirstImage();
				this.repaint();
			}
	   }
	   else
	   {
	   
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO).getX()+this.editor_buttons.get(StaticVariables.ID_TAVOLO).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO).getY()+this.editor_buttons.get(StaticVariables.ID_TAVOLO).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_TAVOLO).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_table_pressed != StaticVariables.ID_TAVOLO)
				this.editor_buttons.get(StaticVariables.ID_TAVOLO).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getX()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getY()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_table_pressed != StaticVariables.ID_TAVOLO_UNIVERSO)
				this.editor_buttons.get(StaticVariables.ID_TAVOLO_UNIVERSO).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getX()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getY()+this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_table_pressed != StaticVariables.ID_TAVOLO_VERDE)
				this.editor_buttons.get(StaticVariables.ID_TAVOLO_VERDE).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_ai_pressed != StaticVariables.ID_PEDINA_BIANCA
					&& this.id_user_pressed != StaticVariables.ID_PEDINA_BIANCA)
				this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCA).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_ai_pressed != StaticVariables.ID_PEDINA_NERA
					&& this.id_user_pressed != StaticVariables.ID_PEDINA_NERA)
				this.editor_buttons.get(StaticVariables.ID_PEDINA_NERA).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_ai_pressed != StaticVariables.ID_PEDINA_BIANCABIANCA
					&& this.id_user_pressed != StaticVariables.ID_PEDINA_BIANCABIANCA)
				this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCABIANCA).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_ai_pressed != StaticVariables.ID_PEDINA_BIANCASPIRALE
					&& this.id_user_pressed != StaticVariables.ID_PEDINA_BIANCASPIRALE)
				this.editor_buttons.get(StaticVariables.ID_PEDINA_BIANCASPIRALE).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_ai_pressed != StaticVariables.ID_PEDINA_MARRONE_SPIRALE
					&& this.id_user_pressed != StaticVariables.ID_PEDINA_MARRONE_SPIRALE)
				this.editor_buttons.get(StaticVariables.ID_PEDINA_MARRONE_SPIRALE).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getX() && e.getX() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getX()+this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getWidth())) && (e.getY() >= this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getY() && e.getY() <= (this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getY()+this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).getHeight())))
		{
		   this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).changeImage();
			this.repaint();
		}
		
		else 
		{
			if (this.id_ai_pressed != StaticVariables.ID_PEDINA_ROSA
					&& this.id_user_pressed != StaticVariables.ID_PEDINA_ROSA)
				this.editor_buttons.get(StaticVariables.ID_PEDINA_ROSA).returnToFirstImage();
			this.repaint();
		}
	   
	   if((e.getX() >= this.save.getX() && e.getX() <= (this.save.getX()+this.save.getWidth())) && (e.getY() >= this.save.getY() && e.getY() <= (this.save.getY()+this.save.getHeight())))
		{
		   this.save.changeImage();
			this.repaint();
		}
		
		else 
		{
			this.save.returnToFirstImage();
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
	  
	   if((e.getX() >= this.user_pawn.getX() && e.getX() <= (this.user_pawn.getX()+this.user_pawn.getWidth())) && (e.getY() >= this.user_pawn.getY() && e.getY() <= (this.user_pawn.getY()+this.user_pawn.getHeight())))
		 {
			 this.user_pawn.changeImage();
			 this.repaint();
		 }
	   else 
		{
				this.user_pawn.returnToFirstImage();
				this.repaint();
	    }
	   
	   if((e.getX() >= this.ai_pawn.getX() && e.getX() <= (this.ai_pawn.getX()+this.ai_pawn.getWidth())) && (e.getY() >= this.ai_pawn.getY() && e.getY() <= (this.ai_pawn.getY()+this.ai_pawn.getHeight())))
		 {
			 this.ai_pawn.changeImage();
			 this.repaint();
		 }
	   else 
		{
				this.ai_pawn.returnToFirstImage();
				this.repaint();
	    }
	   
	   }
	}
   
   /**
    * Controlla se sono stati selezionati: sfondo, pedinaUser e pedinaAi.
    * Se è coì, si va al pannello "choseTypeOfAi', altrimenti si dovrà prima scegliere ciò che non si è scelto.
    * @return
    */
   private boolean controlloSave()
   {
	   if(!Variables.PATH_TAVOLO.isEmpty() && !Variables.PATH_PEDINA1.isEmpty() && !Variables.PATH_PEDINA2.isEmpty())
	   {
		   //qui poi verranno settati 
			return true;
	   }
	   else if(Variables.PATH_TAVOLO.isEmpty())
	   {
		   System.out.println("miao1");
		   Variables.show_popup = true;
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setPath(StaticVariables.PATH_CHOOSEACHESSBOARD);
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setVisible(true);
		   this.repaint();
//		   JOptionPane.showMessageDialog(this, "devi scegliere una scacchiera.");
	   }
	   else if(Variables.PATH_PEDINA1.isEmpty())
	   {
		   System.out.println("miao2");
		   Variables.show_popup = true;
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setPath(StaticVariables.PATH_CHOOSEYOURPAWN);
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setVisible(true);
		   this.repaint();
//		   JOptionPane.showMessageDialog(this, "devi scegliere la tua pedina.");
	   }
	   else if(Variables.PATH_PEDINA2.isEmpty())
	   {
		   System.out.println("miao3");
		   Variables.show_popup = true;
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setPath(StaticVariables.PATH_CHOOSEAIPAWN);
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setVisible(true);
		   
		   this.repaint();
//		   JOptionPane.showMessageDialog(this, "devi mangiare la pedina dell'ai.");
	   }
	   
	   return false;
	   
		   
   }
   
   /**
    * Se è già stata scelta la pedina dell'utente, significa che se ho chiamato questa funzione dopo aver scelto la pedina dell'ai. 
    * E viceversa.
    * @param id_image
    */
   private void setPathImage(int id_image, String path_image, String path_dama)
   {
	   
	   if(this.click_ai_pawn)
	   {
		   Variables.ID_PEDINA2 = id_image;
		   Variables.PATH_PEDINA2 = path_image;
		   Variables.PATH_DAMA2 = path_dama;
		   this.id_ai_pressed = id_image;
		   this.editor_buttons.get(id_image).changeImage();
	   }
	   else if(this.click_user_pawn)
	   {
		   Variables.ID_PEDINA1 = id_image;
		   Variables.PATH_PEDINA1 = path_image;
		   Variables.PATH_DAMA1 = path_dama;
		   this.id_user_pressed = id_image;
		   this.editor_buttons.get(id_image).changeImage();
	   }
	   else if(this.click_table)
	   {
		   Variables.ID_TAVOLO = id_image;
		   Variables.PATH_TAVOLO = path_image;
		   this.id_table_pressed = id_image;
		   this.editor_buttons.get(id_image).changeImage();
	   }
	   else
	   {
		   Variables.show_popup = true;
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setPath(StaticVariables.PATH_SELECT);
		   this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_CHOOSEAIPAWN).setVisible(true);
//		   JOptionPane.showMessageDialog(this, "seleziona a destra il tipo di pedina da scegliere, se la pedina dell'ai opppure la tua.");
	   }
	  this.repaint();
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
