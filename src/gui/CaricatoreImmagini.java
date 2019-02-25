package gui;

import java.util.ArrayList;
import java.util.HashMap;
import interfaces.StaticVariables;

public class CaricatoreImmagini {
	
	private HashMap<Integer,Immagine> scacchieraSfondo; //conterrà solo le immagini di sfondo e colore della scacchiera
	private HashMap<Integer,Immagine> paws_images; //Integer: è il numero della pedina
	private ArrayList<Immagine> pawn_animation;

	public CaricatoreImmagini()
	{
		this.scacchieraSfondo = new HashMap<Integer,Immagine>();
		//ORA, PER COMODITà, conterrà questi sfondo e scacchiera, ma dopo, varieranno in base alla difficoltà del livello o se il livello verrà creato tramite editor
		this.scacchieraSfondo.put(StaticVariables.ID_SFONDO1, new Immagine(StaticVariables.ID_SFONDO1,this.pathImmagini(StaticVariables.ID_SFONDO1),-1));
		this.scacchieraSfondo.put(StaticVariables.ID_TAVOLO, new Immagine(StaticVariables.ID_TAVOLO,this.pathImmagini(StaticVariables.ID_TAVOLO),-1));
		this.scacchieraSfondo.put(StaticVariables.ID_TAVOLO_LEGNO, new Immagine(StaticVariables.ID_TAVOLO_LEGNO,this.pathImmagini(StaticVariables.ID_TAVOLO_LEGNO),-1));
		this.scacchieraSfondo.put(StaticVariables.ID_NASTRO, new Immagine(StaticVariables.ID_NASTRO, StaticVariables.PATH_NASTRO, -1));
		
		this.scacchieraSfondo.put(StaticVariables.ID_PAPER_YOUWON, new Immagine(StaticVariables.ID_PAPER_YOUWON,StaticVariables.PATH_PAPER_YOUWON,-1));
		this.scacchieraSfondo.put(StaticVariables.ID_PAPER_YOULOST, new Immagine(StaticVariables.ID_PAPER_YOULOST,StaticVariables.PATH_PAPER_YOULOST,-1));
		this.paws_images = new HashMap<Integer,Immagine>();
		this.pawn_animation = new ArrayList<Immagine>();
	}
	
	//gli passo l indice dell array sopra perché ho bisogno del path di quell'immagine
	protected String pathImmagini(int i)
	{
		System.out.println("i caricatore immagini: "+i);
		String p = "";
		switch(i)
		{
		case(StaticVariables.ID_SFONDO1): p = StaticVariables.PATH_SFONDO1; break;
		case(StaticVariables.ID_SFONDO2): p = StaticVariables.PATH_SFONDO2; break;
		case(StaticVariables.ID_EXITIMAGE): p = StaticVariables.PATH_EXITIMAGE; break;
		case(StaticVariables.ID_TAVOLO): p = StaticVariables.PATH_TAVOLO; break;
		case(StaticVariables.ID_TAVOLO_LEGNO): p = StaticVariables.PATH_TAVOLO_LEGNO; break;
		case(StaticVariables.ID_TAVOLO_TRASPARENTE): p = StaticVariables.PATH_TAVOLO_TRASPARENTE; break;
		case(StaticVariables.ID_PEDINA_BIANCA): p = StaticVariables.PATH_PEDINA_BIANCA; break;
		case(StaticVariables.ID_PEDINA_NERA): p = StaticVariables.PATH_PEDINA_NERA; break;
		case(StaticVariables.ID_PEDINA_DAMANERA): p = StaticVariables.PATH_PEDINA_DAMANERA; break;
		case(StaticVariables.ID_PEDINA_DAMABIANCA): p = StaticVariables.PATH_PEDINA_DAMABIANCA; break;
		case(StaticVariables.ID_PLAYBUTTON): p = StaticVariables.PATH_PLAYBUTTON; break;
		case(StaticVariables.ID_PRESSEDPLAYBUTTON): p = StaticVariables.PATH_PRESSEDPLAYBUTTON; break;
		case(StaticVariables.ID_EXITBUTTON): p = StaticVariables.PATH_EXITBUTTON; break;
		case(StaticVariables.ID_PRESSEDEXITBUTTON): p = StaticVariables.PATH_PRESSEDEXITBUTTON; break;
		case(StaticVariables.ID_YESBUTTON): p = StaticVariables.PATH_YESBUTTON; break;
		case(StaticVariables.ID_PRESSEDYESBUTTON): p = StaticVariables.PATH_PRESSEDYESBUTTON; break;
		case(StaticVariables.ID_NOBUTTON): p = StaticVariables.PATH_NOBUTTON; break;
		case(StaticVariables.ID_PRESSEDNOBUTTON): p = StaticVariables.PATH_PRESSEDNOBUTTON; break;
		case(StaticVariables.ID_MULTIPLAYERBUTTON): p = StaticVariables.PATH_MULTIPLAYERBUTTON; break;
		case(StaticVariables.ID_PRESSEDMULTIPLAYERBUTTON): p = StaticVariables.PATH_PRESSEDMULTIPLAYERBUTTON; break;
		case(StaticVariables.ID_AIBUTTON): p = StaticVariables.PATH_AIBUTTON; break;
		case(StaticVariables.ID_PRESSEDAIBUTTON): p = StaticVariables.PATH_PRESSEDAI; break;
		case(StaticVariables.ID_RETRY_BUTTON): p = StaticVariables.PATH_CELLA_PEDINA_CHE_MANGIA; break;
		case(StaticVariables.ID_PRESSEDRETRYBUTTON): p = StaticVariables.PATH_PRESSEDRETRYBUTTON; break;
		case(StaticVariables.ID_FINAL_YES): p = StaticVariables.PATH_FINAL_YES; break;
		case(StaticVariables.ID_FINAL_NO): p = StaticVariables.PATH_FINAL_NO; break;
		case(StaticVariables.ID_FUOCHI): p = StaticVariables.PATH_FUOCHI; break;
		case(StaticVariables.ID_SFONDOPIOGGIA): p = StaticVariables.PATH_SFONDOPIOGGIA; break;
		case(StaticVariables.ID_PIOGGIA): p = StaticVariables.PATH_PIOGGIA; break;
		case(StaticVariables.ID_PAPER_YOUWON): p = StaticVariables.PATH_PAPER_YOUWON; break;
		case(StaticVariables.ID_PAPER_YOULOST): p = StaticVariables.PATH_PAPER_YOULOST; break;
		case(StaticVariables.ID_SIMPLE_AI): p = StaticVariables.PATH_SIMPLE_AI; break;
		case(StaticVariables.ID_MEDIUM_AI): p = StaticVariables.PATH_MEDIUM_AI; break;
		case(StaticVariables.ID_HARD_AI): p = StaticVariables.PATH_HARD_AI; break;
		case(StaticVariables.ID_CELLA_EVIDENZIATA): p = StaticVariables.PATH_CELLA_EVIDENZIATA; break;
		case(StaticVariables.ID_CELLA_PEDINA_CHE_MANGIA): p = StaticVariables.PATH_CELLA_PEDINA_CHE_MANGIA; break;
		case(StaticVariables.ID_BACK_BUTTON): p = StaticVariables.PATH_BACKBUTTON; break;
		case(StaticVariables.ID_PEDINA_PREMUTA): p = StaticVariables.PATH_PEDINA_PREMUTA; break;
		case(StaticVariables.ID_PEDINA_ROSA): p = StaticVariables.PATH_PEDINA_ROSA; break;
		case(StaticVariables.ID_PEDINA_BIANCABIANCA): p = StaticVariables.PATH_PEDINA_BIANCABIANCA; break;
		case(StaticVariables.ID_PEDINA_MARRONE_SPIRALE): p = StaticVariables.PATH_PEDINA_MARRONE_SPIRALE; break;
		case(StaticVariables.ID_PEDINA_BIANCASPIRALE): p = StaticVariables.PATH_PEDINA_BIANCASPIRALE; break;
		case(StaticVariables.ID_TAVOLO_UNIVERSO): p = StaticVariables.PATH_TAVOLO_UNIVERSO; break;
		case(StaticVariables.ID_TAVOLO_VERDE): p = StaticVariables.PATH_TAVOLO_VERDE; break;
		case(StaticVariables.ID_EDITOR_BUTTON): p = StaticVariables.PATH_EDITOR_BUTTON; break;
		case(StaticVariables.ID_NASTRO): p = StaticVariables.PATH_NASTRO; break;
		
		default: break;
		}
		
		return p;
	}
	
	
	public void setImagesVisibility( boolean b, Immagine...immagini)
	{
		for(Immagine i: immagini)
		{
			if(i.getId() != StaticVariables.ID_SFONDO1)
			   i.setVisible(b);
		}
	}
	
	public void printImmaginePawns()
	{
		for(Immagine i: this.paws_images.values())
		   System.out.println("pawn immagine <numero_immagine,i,j,x,y>. <"+i.getNumero_immagine()+","+i.getI()+","+i.getJ()+","+i.getX()+","+i.getY()+">");
	}
	
	public Immagine fromIdtoPawnImmagine(int id)
	{
		return this.paws_images.get(id);
	}
	
	public void printPawns()
	{
		for(Integer p: this.paws_images.keySet())
			System.out.println("id: "+p);
	}

	public HashMap<Integer, Immagine> getScacchieraSfondo() {
		return scacchieraSfondo;
	}

	public void setScacchieraSfondo(HashMap<Integer, Immagine> scacchieraSfondo) {
		this.scacchieraSfondo = scacchieraSfondo;
	}

	public HashMap<Integer, Immagine> getPaws_images() {
		return paws_images;
	}

	public void setPaws_images(HashMap<Integer, Immagine> paws_images) {
		this.paws_images = paws_images;
	}

	public ArrayList<Immagine> getPawn_animation() {
		return pawn_animation;
	}

	public void setPawn_animation(ArrayList<Immagine> pawn_animation) {
		this.pawn_animation = pawn_animation;
	}

	
	
	
}


