package core;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

import audio.Sounds;
import gui.FinalPanel;
import gui.Immagine;
import gui.Menu;
import gui.MyPanel;
import gui.MyPlayPanel;
import interfaces.StaticVariables;
import interfaces.Variables;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import network.Client;

public class Game {
	private Dama dama;
	private Player user_player;
	private Player ai_player;
	private InputProgram facts;
	private InputProgram encoding;
	private Set<Pawn> answersetpawns; //qui vengono salvati gli answer sets(pawns)
	protected HashMap<Integer,Pawn> total_pawns; //qui vengono salvate tutte le pedine
	private boolean end_game; //il gioco termina quando o vengono mangiate tutte le pedine del player1 o quelle per player2
	private MyPanel play_panel;
	private String type_of_intelligence;
	private int idImmUser, idImmAi;
	private String pathImmUser, pathImmAi;
	private String secondPlayer_colour;
	
	public Game(MyPanel play_panel)
	{
		this.type_of_intelligence = this.secondPlayer_colour = "";
		this.dama = new Dama();
		this.play_panel = play_panel;
		
		this.end_game = false;
		this.facts = this.dama.createCells(); //assegno a 'facts' di questa classe, i 'facts' di Dama.
		this.dama.printMatrix();
		this.total_pawns = new HashMap<Integer,Pawn>();
		this.idImmUser = this.idImmAi = -1;
		this.pathImmUser = this.pathImmAi = "";
		
		}
	
	public void play()
	{
//		this.assignImagesAtGame();
		this.user_player = new UserPlayer(this);
		
		//se giocheremo contro l'intelligenza, verrà usata come giocatore;
		//altrimenti semplicemente per comodità, cioè per visualizzare le pedine dell'altro 
		//avvversario in rete
		 this.ai_player = new AIPlayer(this, this.type_of_intelligence);
		 this.ai_player.setOpponent_player(this.user_player);
			this.user_player.setOpponent_player(this.ai_player);
		if(Variables.single_player)
		{
			this.initAIandASP();
//			this.ai_player.setOpponent_player(this.user_player);
//			this.user_player.setOpponent_player(this.ai_player);
		}
		else
		{
			this.playersColours();
		    this.singlePlayer_cells_colours_matrix_assignment();
		    
		}
		
		((MyPlayPanel)this.play_panel).setPawnsAreVisible(true);
		System.out.println("user pawns: ");
		user_player.printSetPawns();
//		if(this.single_player)
//		{
		  System.out.println("ai pawns: ");
		  ai_player.printSetPawns();
//		}
		
		//this.answerSets();
	}
	
	/**
	 * Se il giocatore ha scelto le immagini in base all'editor, cambieranno in base ale sue scelte;
	 * altrimenti ci saranno le immagini di default.
	 */
	private void assignImagesAtGame()
	{
		if(Variables.editor)
		{
			this.idImmUser = Variables.ID_PEDINA1;
			this.idImmAi = Variables.ID_PEDINA2;

			this.pathImmUser = Variables.PATH_PEDINA1;
			this.pathImmAi = Variables.PATH_PEDINA2;
			System.out.println("cazzu iu GAME. editor: "+Variables.editor);
		}
		else
		{
			if(Variables.single_player)
			{
			this.idImmUser = StaticVariables.ID_PEDINA_BIANCA;
			this.idImmAi = StaticVariables.ID_PEDINA_NERA;

			this.pathImmUser = StaticVariables.PATH_PEDINA_BIANCA;
			this.pathImmAi = StaticVariables.PATH_PEDINA_NERA;
			}
			else
			{
				if(this.user_player.getStringColour().equals("white"))
				{
					this.idImmUser = StaticVariables.ID_PEDINA_BIANCA;
					this.idImmAi = StaticVariables.ID_PEDINA_NERA;

					this.pathImmUser = StaticVariables.PATH_PEDINA_BIANCA;
					this.pathImmAi = StaticVariables.PATH_PEDINA_NERA;
				}
				else
				{
					this.idImmUser = StaticVariables.ID_PEDINA_NERA;
					this.idImmAi = StaticVariables.ID_PEDINA_BIANCA;

					this.pathImmUser = StaticVariables.PATH_PEDINA_NERA;
					this.pathImmAi = StaticVariables.PATH_PEDINA_BIANCA;
				}
			}
		}
	}
	
	
	private void answerSets()
	{
		Output o =  StaticVariables.handler.startSync();
		AnswerSets answers = (AnswerSets) o;

		for(AnswerSet a:answers.getAnswersets()){
			
			try {
				for(Object obj:a.getAtoms()){
					if(! (obj instanceof Pawn))continue;
					Pawn pawn= (Pawn) obj;
					this.answersetpawns.add(pawn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
		this.printAnswerSets();
	}
	
	private void printAnswerSets()
	{
		for(Pawn p: this.answersetpawns)
		System.out.println("id: "+p.getId()+" i: "+p.getI()+" j: "+p.getJ()+" colour: "+p.getColour());
	}
	
	private void singlePlayer_cells_colours_matrix_assignment()
	{
		this.assignImagesAtGame();
		
		int cont = 1;
		if(this.user_player.getStringColour().equals("black"))
			cont = 24;
			
		Pawn p;
		String tempCol = "";
		String path = "";
		int idImm = 0;
		Immagine immagine = null;
		System.out.println("ciao3");
		int ic = 0, jc = 0;
		
		try {
		
			tempCol = user_player.getStringColour();
			idImm = this.idImmUser;
			path = this.pathImmUser;
		
			for(int i = 5; i < StaticVariables.lenghtMatrix; i++)
			{
				for(int j = 0; j < StaticVariables.lenghtMatrix; j++)
				{
					if(this.dama.getCellColour(i, j) == "black")
					{
						
						p = new Pawn(cont,i,j,tempCol);
						System.out.println("ciao4");
						immagine = new Immagine(idImm,path,cont);
						immagine.setVisible(true);
						
						this.play_panel.getProporziona().setSizePawn(immagine, i, j);
						
						this.play_panel.getCaricatore_immagini().getPaws_images().put(cont, immagine);
				        this.user_player.insertPawn(cont,p);	
				        this.total_pawns.put(cont,p);
				        this.dama.getCellAtPosition(i, j).setPawn(p); //diamo a queste celle della matrice, una pedina
				        this.facts.addObjectInput(p);
				        
				        if(this.user_player.getStringColour().equals("black"))
				        	cont--;
				        else
				           cont++;
					}
				}
			}
		
			 if(this.user_player.getStringColour().equals("black"))
		        	cont = 12;
			 
//			if(this.single_player)
//			{
		tempCol = this.ai_player.getStringColour();
		idImm = this.idImmAi;
		path = this.pathImmAi;
		  
	  for(int i = 0; i < 3; i++)
		{
		  for(int j = 0; j < StaticVariables.lenghtMatrix; j++)
				{
					if(this.dama.getCellColour(i, j) == "black")
					{
						
						p = new Pawn(cont,i,j,tempCol);
						immagine = new Immagine(idImm,path,cont);
						immagine.setVisible(true);
						this.play_panel.getProporziona().setSizePawn(immagine, i, j);
						
						this.play_panel.getCaricatore_immagini().getPaws_images().put(cont, immagine);
				        this.ai_player.insertPawn(cont,p);
				        this.total_pawns.put(cont,p);
				        this.dama.getCellAtPosition(i, j).setPawn(p);
				        this.facts.addObjectInput(p);
				        
				        if(this.user_player.getStringColour().equals("black"))
				        	cont--;
				        else
				        cont++;
					}
				}
			}
//			}
	  
	  System.out.println("QUIIIIIIIIIIII");
	  this.dama.printMatrix();
	    
	  System.out.println("ciao5");
		Immagine i = new Immagine(StaticVariables.ID_CELLA_EVIDENZIATA, StaticVariables.PATH_CELLA_EVIDENZIATA,-1);
		i.setXYWH(0, 0, this.play_panel.getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_NERA).getImageWidth(),
				this.play_panel.getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_NERA).getImageHeight());
		this.play_panel.getCaricatore_immagini().getPaws_images().put(StaticVariables.ID_CELLA_EVIDENZIATA, i);
		
		//riempio l'array dell'animazione delle pedine, momentaneamente con quest'immagine. Ps. ci saranno 5 immagini.
		for(int j = 0; j < 5; j++)
		{
			Immagine imm = i;
			imm.setWH(Variables.w_pawn, Variables.w_pawn);
			imm.setVisible(false);
		   this.play_panel.getCaricatore_immagini().getPawn_animation().add(imm);
		}
		
		i = new Immagine(StaticVariables.ID_CELLA_PEDINA_CHE_MANGIA, StaticVariables.PATH_CELLA_PEDINA_CHE_MANGIA,-1);
		i.setXYWH(0, 0, this.play_panel.getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_NERA).getImageWidth(),
				this.play_panel.getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_NERA).getImageHeight());
		this.play_panel.getCaricatore_immagini().getPaws_images().put(StaticVariables.ID_CELLA_PEDINA_CHE_MANGIA, i);
		
		i = new Immagine(StaticVariables.ID_CELLA_PEDINA_CHE_MANGIA, StaticVariables.PATH_PEDINA_PREMUTA,-1);
		i.setXYWH(0, 0, this.play_panel.getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_NERA).getImageWidth(),
				this.play_panel.getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_NERA).getImageHeight());
		this.play_panel.getCaricatore_immagini().getPaws_images().put(StaticVariables.ID_PEDINA_PREMUTA, i);

		
		
      this.play_panel.getCaricatore_immagini().printImmaginePawns();
      
      //ci entra il secondo giocatore all'inizio della partita
      if(this.user_player.getStringColour().equals("black"))
      {
    	  ((MyPlayPanel)this.play_panel).setTurno_ai(true);
			((UserPlayer)this.user_player).setInFromServer(((UserPlayer)this.user_player).getClient().getModifiedSentence()); //stringa ricevuta dall'altro giocatore
			((UserPlayer)this.user_player).algorythmOfTransformationPlayer();
			((MyPlayPanel)this.play_panel).setTurno_ai(false);
      }
		
		} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	}
	
	/**
	 * Restituisce la coordinata opposta
	 * @param x
	 * @return
	 */
	private int opponentCoord(int x)
	{
		return (StaticVariables.RIGHE_COLONNE-1)-x;
	}
	
	private void initAIandASP()
	{
		this.answersetpawns = new HashSet<Pawn>();
		this.encoding = new ASPInputProgram();
		this.encoding.addFilesPath(StaticVariables.encodingResource);
		
		try {
			this.facts.addObjectInput(new Colour("black"));
			this.facts.addObjectInput(new Colour("white"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StaticVariables.handler.addProgram(facts);
		StaticVariables.handler.addProgram(encoding);
//	    this.ai_player = new AIPlayer(this, this.type_of_intelligence);
	    this.playersColours();
	    this.singlePlayer_cells_colours_matrix_assignment();
	}
	
	
	private void playersColours() {
		
	    //poi questo verrà settato in Client. Il server gli manderà il colore
//		user_player.setColour("white");

		// se è il multiplayer
		if (!Variables.single_player) {
			((UserPlayer)this.user_player).setClient(new Client("192.168.1.27", 80, this));
			System.out.println("ciao");
			if (this.user_player.getStringColour().equals("white"))
				ai_player.setColour("black");
			else
				ai_player.setColour("white");
			System.out.println("ciao2");
		}

		else {
			user_player.setColour("white");
			ai_player.setColour("black");
//			user_player.setColour("black");
//			ai_player.setColour("white");

			try {
				this.facts
						.addObjectInput(new AIColour(StaticVariables.idFirstPlayer, this.ai_player.getStringColour()));
				this.facts.addObjectInput(
						new UserColour(StaticVariables.idSecondPlayer, this.user_player.getStringColour()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void eatPawn(Pawn p)
	{
		//elimina la pedina dalla cella in cui si trova
		this.dama.getCellAtPosition(p.getI(), p.getJ()).setSvuotaCella();
		//elimino la pedina dalla struttura dati total_pawns
		this.total_pawns.remove(p.getId());
		//elimino la cella dalla struttura dati di caricatore immagini
		this.play_panel.getCaricatore_immagini().getPaws_images().remove(p.getId());
//		this.dama.printMatrix();
	}


	public boolean isEnd_game() {
		return end_game;
	}
	
	public void setEND_GAME(boolean end_game, boolean winOrNot)
	{
		try {
		if (!this.end_game) {
			Player.isEnd = true;
			this.setEnd_game(end_game);
			
				Thread.sleep(2000);
			
			((MyPlayPanel) this.play_panel).createFinalPanel(winOrNot);
		}} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

	private void setEnd_game(boolean end_game) {
		this.end_game = end_game;
	}

	public Dama getDama() {
		return dama;
	}

	public void setDama(Dama dama) {
		this.dama = dama;
	}

	public HashMap<Integer, Pawn> getTotal_pawns() {
		return total_pawns;
	}

	public void setTotal_pawns(HashMap<Integer, Pawn> total_pawns) {
		this.total_pawns = total_pawns;
	}

	public Player getUser_player() {
		return user_player;
	}

	public void setUser_player(Player user_player) {
		this.user_player = user_player;
	}

	public Player getAi_player() {
		return ai_player;
	}

	public void setAi_player(Player ai_player) {
		this.ai_player = ai_player;
	}

	public MyPanel getPlay_panel() {
		return play_panel;
	}

	public void setPlay_panel(MyPanel play_panel) {
		this.play_panel = play_panel;
	}

	public String getType_of_intelligence() {
		return type_of_intelligence;
	}

	public void setType_of_intelligence(String type_of_intelligence) {
		this.type_of_intelligence = type_of_intelligence;
	}
	
	

}
