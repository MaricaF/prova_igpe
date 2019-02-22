package core;

import gui.CaricatoreImmagini;
import gui.Immagine;
import gui.MyButton;
import interfaces.StaticVariables;
import interfaces.Variables;

public class ProporzionaImmagine {
	
	private int moveToRight; //variabile alla quale assegnamo il valore di traslazione verso destra delle immagini
	private int moveToDown;  //variabile alla quale assegnamo il valore di traslazione verso sotto delle immagini
	private int pawnCellWidth; 
	private int widthMatrix;
	private int wframeDividedByWmatrix;
	private int hframeDividedByHmatrix;
	private CaricatoreImmagini caricatore_immagini;
	
	public ProporzionaImmagine(CaricatoreImmagini caricatore_immagini)
	{
		this.caricatore_immagini = caricatore_immagini;
		this.moveToRight = this.moveToDown = this.pawnCellWidth = this.wframeDividedByWmatrix = this.hframeDividedByHmatrix = this.widthMatrix = 0;
	}
	
	public void resizeButton(Immagine button, MyButton bottone)
	{
		int width = StaticVariables.finestra_width;
		
		if (!Variables.inizializeButtonsHeight) {
			Variables.BUTTONS_HEIGHT = (int) (this.pawnCellWidth * (2.5));
			Variables.inizializeButtonsHeight = true;
		}
		
		button.setImageHeight(Variables.BUTTONS_HEIGHT);
		
		int div = button.getImageWidth();
		
		if(bottone.getButton_name() == StaticVariables.PLAY_NAME || bottone.getButton_name() == StaticVariables.RETRY_NAME)
		{
		   button.setImageWidth(this.pawnCellWidth*5);
		   button.setImageHeight(this.pawnCellWidth*2);
		}
		else if(bottone.getButton_name() == StaticVariables.YES_NAME || bottone.getButton_name() == StaticVariables.NO_NAME)
		{
			button.setImageWidth(this.pawnCellWidth*2);
			button.setImageHeight(button.getImageWidth());
			if(bottone.getButton_name() == StaticVariables.YES_NAME)
			   button.setX((width/2)-(this.pawnCellWidth*4));
			else if(bottone.getButton_name() == StaticVariables.NO_NAME)
			   button.setX((width/2)+(this.pawnCellWidth*2));
		}
		else if(bottone.getButton_name() == StaticVariables.MULTIPLAYER_NAME)
		{
			button.setImageWidth(this.pawnCellWidth*11);
			button.setImageHeight(this.pawnCellWidth*2);
		}
		else if(bottone.getButton_name() == StaticVariables.AI_NAME)
		{
			button.setImageWidth(this.pawnCellWidth*2);
			button.setImageHeight(this.pawnCellWidth*2);
		}
		else if(bottone.getButton_name() == StaticVariables.EXIT_NAME || bottone.getButton_name() == StaticVariables.BACK_NAME)
		{
		    button.setImageHeight(this.pawnCellWidth);
			button.setImageWidth(this.pawnCellWidth*4);
			
		}
		else if(bottone.getButton_name() == StaticVariables.SIMPLE_AI_NAME || bottone.getButton_name() == StaticVariables.EDITOR_NAME)
		{
			button.setImageWidth(this.pawnCellWidth*6);
			button.setImageHeight(this.pawnCellWidth*2);
		}
		else if(bottone.getButton_name() == StaticVariables.MEDIUM_AI_NAME)
		{
			button.setImageWidth(this.pawnCellWidth*7);
			button.setImageHeight(this.pawnCellWidth*2);
		}
		else if(bottone.getButton_name() == StaticVariables.HARD_AI_NAME)
		{
			button.setImageWidth(this.pawnCellWidth*4);
			button.setImageHeight(this.pawnCellWidth*2);
		}
		
		if(bottone.getButton_name() != StaticVariables.YES_NAME && bottone.getButton_name() != StaticVariables.NO_NAME)
		button.setX(((width/2)-button.getImageWidth())+(button.getImageWidth()/2));
		
		if(bottone.getButton_name().equals(StaticVariables.PLAY_NAME))
			button.setY(button.getImageHeight()*2);
		else if(bottone.getButton_name().equals(StaticVariables.YES_NAME) || 
				bottone.getButton_name().equals(StaticVariables.NO_NAME) ||
				bottone.getButton_name().equals(StaticVariables.HARD_AI_NAME) || bottone.getButton_name().equals(StaticVariables.MULTIPLAYER_NAME))
			button.setY(button.getImageHeight()*4);
		else if(bottone.getButton_name().equals(StaticVariables.RETRY_NAME) || 
				bottone.getButton_name().equals(StaticVariables.AI_NAME) || 
				bottone.getButton_name().equals(StaticVariables.MEDIUM_AI_NAME))
			button.setY((button.getImageHeight()*2)+this.pawnCellWidth);
		else if(bottone.getButton_name().equals(StaticVariables.EDITOR_NAME) || 
				bottone.getButton_name().equals(StaticVariables.SIMPLE_AI_NAME))
			button.setY(button.getImageHeight());
		else if(bottone.getButton_name().equals(StaticVariables.BACK_NAME) || bottone.getButton_name().equals(StaticVariables.EXIT_NAME))
			 button.setY((int) ((StaticVariables.finestra_height)-((button.getImageHeight()*(1.5)))));
		
		if(Variables.endGame)
		{
			if(bottone.getButton_name().equals(StaticVariables.RETRY_NAME))
				button.setX(StaticVariables.finestra_width/2-button.getImageWidth());
			else if(bottone.getButton_name().equals(StaticVariables.EXIT_NAME))
				button.setX((StaticVariables.finestra_width/2)+(this.pawnCellWidth*2));
			
		       button.setY((int) ((StaticVariables.finestra_height)-((button.getImageHeight()*(1.5)))));
		}
		
		//aggiorno le coordinate del bottone 
		bottone.setX(button.getX());
		bottone.setY(button.getY());
		bottone.setWidth(button.getImageWidth());
		bottone.setHeight(button.getImageHeight());
		
//		System.err.println(" widthMenu: "+width+" heightMenu: "+height+" button. w: "+button.getImageWidth()+" h: "+button.getImageHeight()+" x: "+button.getX()+" y: "+button.getY());
	}
	
	public void resizeEditorButton(Immagine bottone_image, MyButton bottone)
	{
		bottone_image.setImageHeight(Variables.BUTTONS_HEIGHT);
		bottone_image.setImageWidth(Variables.BUTTONS_HEIGHT);
		
		
		switch(bottone.getButton_name())
		{
		   case(StaticVariables.TAVOLO_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/6);
			   bottone_image.setY(StaticVariables.finestra_height/10);
			   break;	
		   }
		   case(StaticVariables.UNIVERSO_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/6);
			   bottone_image.setY(StaticVariables.finestra_height/10+bottone_image.getImageHeight()+(bottone_image.getImageHeight()/2));
			   break;	
		   }
		   case(StaticVariables.TAVOLOVERDE_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/6);
			   bottone_image.setY(StaticVariables.finestra_height/10+(bottone_image.getImageHeight()*2)+bottone_image.getImageHeight());
			   break;	
		   }
		   case(StaticVariables.BIANCASTANDARD_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/2-bottone.getWidth()/2);
			   bottone_image.setY(StaticVariables.finestra_height/10);
			   break;	
		   }
		   case(StaticVariables.NERASTANDARD_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/2+StaticVariables.finestra_width/4);
			   bottone_image.setY(StaticVariables.finestra_height/10);
			   break;	
		   }
		   case(StaticVariables.ROSA_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/2-bottone.getWidth()/2);
			   bottone_image.setY(StaticVariables.finestra_height/10+bottone_image.getImageHeight()+(bottone_image.getImageHeight()/2));
			   break;	
		   }
		   case(StaticVariables.BIANCABIANCA_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/2+StaticVariables.finestra_width/4);
			   bottone_image.setY(StaticVariables.finestra_height/10+bottone_image.getImageHeight()+(bottone_image.getImageHeight()/2));
			   break;	
		   }
		   case(StaticVariables.MARRONE_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/2-bottone.getWidth()/2);
			   bottone_image.setY(StaticVariables.finestra_height/10+(bottone_image.getImageHeight()*2)+bottone_image.getImageHeight());
			   break;	
		   }
		   case(StaticVariables.BIANCASPIRALE_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width/2+StaticVariables.finestra_width/4);
			   bottone_image.setY(StaticVariables.finestra_height/10+(bottone_image.getImageHeight()*2)+bottone_image.getImageHeight());
			   break;	
		   }
		   case(StaticVariables.AI_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width-bottone_image.getImageWidth());
			   bottone_image.setImageHeight(Variables.BUTTONS_HEIGHT/2);
			   bottone_image.setY(StaticVariables.finestra_height/2-bottone_image.getImageHeight());
			   bottone_image.setImageWidth(Variables.BUTTONS_HEIGHT/2);
			   break;	
		   }
		   case(StaticVariables.USER_PAWN_NAME):
		   {
			   bottone_image.setX(StaticVariables.finestra_width-bottone_image.getImageWidth());
			   bottone_image.setY((StaticVariables.finestra_height/5)*3);
			   bottone_image.setImageHeight(Variables.BUTTONS_HEIGHT/2);
			   bottone_image.setImageWidth(Variables.BUTTONS_HEIGHT/2);
			   break;	
		   }
		   case(StaticVariables.SAVE_NAME):
		   {
			   bottone_image.setImageHeight(Variables.BUTTONS_HEIGHT/3);
			   bottone_image.setY(StaticVariables.finestra_height/2-bottone_image.getImageHeight());
			   bottone_image.setImageWidth(Variables.BUTTONS_HEIGHT/2);
			   bottone_image.setX(bottone_image.getImageWidth()/3);
			   break;	
		   }
		   case(StaticVariables.BACK_NAME):
		   {
			   bottone_image.setImageHeight(Variables.BUTTONS_HEIGHT/3);
			   bottone_image.setY((StaticVariables.finestra_height/5)*3);
			   bottone_image.setImageWidth(Variables.BUTTONS_HEIGHT/2);
			   bottone_image.setX(bottone_image.getImageWidth()/3);
			   break;	
		   }
		   
		   
		   default: break;
		}
		
//			System.err.println("button name: "+bottone.getButton_name()+" x: "+bottone_image.getX()+" y: "+bottone_image.getY());
		
		// aggiorno le coordinate del bottone
		bottone.setX(bottone_image.getX());
		bottone.setY(bottone_image.getY());
		bottone.setWidth(bottone_image.getImageWidth());
		bottone.setHeight(bottone_image.getImageHeight());
	}
	
	public void setSizeBAckgroundAndScacchiera(int width, int height)
	{
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setImageHeight(height);
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_SFONDO1).setImageWidth(width);
       	int wtavolo = width/(StaticVariables.RIGHE_COLONNE*3);
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setImageWidth(wtavolo*StaticVariables.RIGHE_COLONNE);
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setImageHeight(wtavolo*StaticVariables.RIGHE_COLONNE);
       	
       	this.pawnCellWidth = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).getImageWidth()/StaticVariables.RIGHE_COLONNE;
        this.widthMatrix = this.pawnCellWidth*StaticVariables.RIGHE_COLONNE;
       	
       	this.wframeDividedByWmatrix = width/this.widthMatrix;
       	this.moveToRight = (this.widthMatrix*(this.wframeDividedByWmatrix/2));
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setX(this.moveToRight);
       	
       	this.hframeDividedByHmatrix = height-this.widthMatrix;
       	this.moveToDown = (this.hframeDividedByHmatrix/2)/*-this.pawnCellWidth*/;
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setY(this.moveToDown);
       	 
       	   
       	//settiamo anche le coordinate del tavolo sotto la scacchiera:
       	//il tavolo sottostante deve avere la widtt e laheight della scacchiera, solo che gli vanno aggiunti 2 pawnCellWidth, così sarà più grande
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).setImageWidth(this.widthMatrix+(this.pawnCellWidth*2));
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).setImageHeight(this.widthMatrix+(this.pawnCellWidth*2));
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).setX(this.moveToRight-this.pawnCellWidth);
       	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).setY(this.moveToDown-this.pawnCellWidth);
       	
//       	System.err.println("w frame: "+width+" pawncellwidth: "+this.pawnCellWidth+ " wframe/pawncellwidth: "+this.moveToRight);
       	
       	
	}
	
	public void resizeOtherPopups()
	{
       	
     	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_PAPER_YOULOST).setXYWH(
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getX(),
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getY(), 
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getImageWidth(), 
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getImageHeight());
     	
     	this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_PAPER_YOUWON).setXYWH(
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getX(),
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getY(), 
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getImageWidth(), 
     	  this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getImageHeight());
     	
	}
	
	public void setSizePawn(Immagine pawn, int i, int j)
	{
//		System.out.println("prima della resize. numero imm: "+pawn.getNumero_immagine()+" x: "+pawn.getX()+" y: "+pawn.getY());
		pawn.setImageWidth(this.pawnCellWidth);
		Variables.w_pawn = this.pawnCellWidth;
		pawn.setImageHeight(pawn.getImageWidth()); //dato che sono quadrati
		pawn.setX((pawn.getImageWidth()*j)+this.moveToRight);
		pawn.setY((pawn.getImageWidth()*i)+this.moveToDown);
//		System.err.println("dopo della resize. numero imm: "+pawn.getNumero_immagine()+" x: "+pawn.getX()+" y: "+pawn.getY());
//		System.out.println();
	}
	
	//in base alla x del mouse, mi restituisce la j della matrice
	public int coordJ(int xClick)
	{
		return -(-xClick + this.moveToRight)/this.pawnCellWidth;
	}
	
	public int coordI(int yClick)
	{
		return -(-yClick + this.moveToDown)/this.pawnCellWidth;
	}

	public int getPawnCellWidth() {
		return pawnCellWidth;
	}
	
	
	

}
