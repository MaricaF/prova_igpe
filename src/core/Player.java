package core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utilities.Chronometer;
import Utilities.MultipleMovementMultiplayer;
import Utilities.MultiplePlayerMovement;
import Utilities.SinglePlayerMovement;
import audio.Sounds;
import core.Pawn;
import gui.Immagine;
import gui.MyPlayPanel;
import interfaces.StaticVariables;
import interfaces.Variables;

public abstract class Player {
	
	protected int id_user;
	protected String colour;
	protected HashMap<Integer,Pawn> pawns;
	protected int num_pawns;
	protected Game game;
    protected Pawn pawnFirstTomove;
    protected Pawn pawnAfterMove;
    protected Pawn pawnMossaPrima;
    protected Pawn pawnToEat;
    protected Player opponent_player;
    protected boolean winner;
    protected boolean move_dama; //mi dice se si deve muovere la dama
    public static int conta_dame_ai;
    protected ArrayList<Cell> valid_mycells_to_eat;
    protected ArrayList<Pawn> pedine_che_devono_mangiare;
    protected Chronometer timer;
    protected static SinglePlayerMovement singlePlayerMovement;
    protected static MultiplePlayerMovement multiplePlayerMovement;
    protected static MultipleMovementMultiplayer multipleMovementMultiplayer;
    protected static Lock singleMovementlock, multipleMovementLock;
    protected ArrayList<Cell> opponent_cells;
    protected ArrayList<Cell> celle_per_pasto_consecutivo;
	private int unpasso, sopraOsotto, destraOsinistra, tot;
    protected static boolean isEnd;
    private String outToServer, inFromServer;
	
	public Player(Game game) 
	{
	    this.game = game;
		this.num_pawns = 12;
		this.pawns = new HashMap<Integer,Pawn>(this.num_pawns);
		this.valid_mycells_to_eat = new ArrayList<Cell>();
		this.pedine_che_devono_mangiare = new ArrayList<Pawn>();
		this.opponent_player = null;
		this.id_user = -1;
		this.colour = this.outToServer = this.inFromServer = "";
		this.pawnFirstTomove = null;
		this.pawnAfterMove = null;
		this.pawnToEat = null;
		this.pawnMossaPrima = null;
		this.winner = false;
		this.move_dama = false;
		this.timer = new Chronometer();
		singleMovementlock = new ReentrantLock(true);
		multipleMovementLock = new ReentrantLock(true);
		conta_dame_ai = 0;
		isEnd = false;
		
		this.sopraOsotto = this.destraOsinistra = this.tot = this.unpasso = 0;
		
		this.celle_per_pasto_consecutivo = new ArrayList<Cell>();
		this.opponent_cells = new ArrayList<Cell>();
		if(this instanceof AIPlayer)
		System.out.println("costruttore AI PLAYER");
		else
			System.out.println("costruttore USER PLAYER");
	}
	
	//verifica se questa pedina si pu� muovere e in caso positivo, si muove
	public void movePawn(Pawn pawnFirstTomove, int iprec, int jprec, int iafter, int jafter) {}
	
	public void printSetPawns()
	{
		for(Pawn p: pawns.values())
			System.out.println("<"+p.getId()+","+p.getI()+","+p.getJ()+","+p.getColour()+"> ");
		System.out.println();
		
	}
	
	/**
	 * Funzione che sposta la pedina e, in caso, mangia una pedina avversaria
	 * @param iprec -> i attuale della pedina da muovere
	 * @param jprec -> j attuale della pedina da muovere
	 * @param iafter -> i della cella su cui vogliamo spostare la pedina
	 * @param jafter -> j della cella su cui vogliamo spostare la pedina
	 * @param pawnToEat -> �, in caso, la pedina da mangiare
	 */
	public void move(int iprec, int jprec, int iafter, int jafter, Pawn pawnToEat)
	{	
		this.changePedinaPremutaProperties(true, iprec, jprec);
		this.pawnAfterMove = this.pawnFirstTomove;
	}
	
	public void sendMovimentoSemplice(int iprec, int jprec, int iafter, int jafter, boolean exit)
	{
					((UserPlayer)this.game.getUser_player()).getClient().setMessageToSendToServer("");
					if(!exit)
					   this.outToServer += iprec + "," + jprec + " " + iafter + "," + jafter +"\n\n<END>";
					else
						this.outToServer += "end\n\n<END>"; //la mando in caso volessi uscire dal gioco
					
					((UserPlayer)this.game.getUser_player()).getClient().setMessageToSendToServer(this.outToServer);
					((UserPlayer)this.game.getUser_player()).getClient().sendMessageToServer();
					this.outToServer = "";
					
					if(exit)
					{
						((UserPlayer)this.game.getUser_player()).getClient().closeConnection();
						Variables.single_player = true;
						this.game.getPlay_panel().getMenu().setPanelsVisibility(this.game.getPlay_panel().getMenu().getCurrent_panel(), false);
						this.game.getPlay_panel().getMenu().setPanelProperties(this.game.getPlay_panel().getMenu().getMyMenuPanel(), true);
					}
	}
	
	public void sendMangiataMultipla(ArrayList<Cell> clicked_cells, ArrayList<Cell> opponent_cells)
	{
			((UserPlayer)this.game.getUser_player()).getClient().setMessageToSendToServer("");
			String s_clicked_cells = this.CoordToString(clicked_cells);
			String s_opponent_cells = this.CoordToString(opponent_cells);
			this.setOutToServer(s_clicked_cells +  s_opponent_cells + "<END>");
			((UserPlayer)this.game.getUser_player()).getClient().setMessageToSendToServer(this.getOutToServer());
			((UserPlayer)this.game.getUser_player()).getClient().sendMessageToServer();
			this.setOutToServer("");
	}
	
	public void receiveSemplice()
	{
		if (!Variables.interpostaTraMovMultiploEnon) {
			((UserPlayer) this.game.getUser_player()).getClient().miMettoInAttesaDelServer();
			((UserPlayer) this.game.getUser_player())
					.setInFromServer(((UserPlayer) this.game.getUser_player()).getClient().getModifiedSentence()); // stringa
																													// giocatore
			// devo prendere la clientSentence del client ecc
			((UserPlayer) this.game.getUser_player()).algorythmOfTransformationPlayer();
		}
	}
	
	public void receiveMangiataMultipla()
	{
//		((UserPlayer)this.game.getUser_player()).getClient().setModifiedSentence("");
//		   ((UserPlayer)this.game.getUser_player()).getClient().setTemp("");
		Variables.mangiata_multipla = false;
		((UserPlayer) this.game.getUser_player()).getClient().miMettoInAttesaDelServer();
		((UserPlayer) this.game.getUser_player())
				.setInFromServer(((UserPlayer) this.game.getUser_player()).getClient().getModifiedSentence()); // stringa
																												// dall'altro
		// devo prendere la clientSentence del client ecc
		((AIPlayer) this.game.getAi_player()).algorythmOfTransformationPlayer();
		 Variables.canMove = true;
	}
	
	public void passaDaPawnFirstMoveToPawnAfterMove(int iprec, int jprec, int iafter, int jafter, Pawn pawnToEat)
	{
		//se devo solo muovere, mando al server queste posizioni, altrimenti no, perch� significa che
		// devo mangiare pedina, ma devo mandargli tutte quelle posizioni in un'altra funzione
		if (!Variables.single_player && Variables.canMove && !Variables.giocatore1_mangio && !Variables.update) {
			this.sendMovimentoSemplice(iprec, jprec, iafter, jafter, false);
			((UserPlayer) this.game.getUser_player()).getClient().setModifiedSentence("");
			((UserPlayer) this.game.getUser_player()).getClient().setTemp("");
			Variables.canMove = false;
		}
		
		if(this instanceof AIPlayer)
			System.out.println("passaDaPawnFirstMoveToPawnAfterMove AI PLAYER");
			else
				System.out.println("passaDaPawnFirstMoveToPawnAfterMove USER PLAYER");
		
             this.move(iprec, jprec, iafter, jafter, pawnToEat);
		
		//Qui, nel caso in cui l'array che contiene le posizioni in cui si dovrebbero muovere le medine per mangiare, fosse pieno, l'immagine della cella
				// selezionata non si deve pi� vedere.
				if(!this.valid_mycells_to_eat.isEmpty())
				{
					this.changeCellaEvidenziataProperties(false);
					this.valid_mycells_to_eat.clear();
					this.pedine_che_devono_mangiare.clear();
					this.game.getPlay_panel().repaint();
				}
				this.changePedinaPremutaProperties(false, iprec, jprec);
		
		this.pawnAfterMove.setIJ(iafter, jafter);
		this.game.getDama().getCellAtPosition(iprec, jprec).setSvuotaCella();
		this.game.getDama().getCellAtPosition(iafter, jafter).setPawn(this.pawnAfterMove);
		this.game.getTotal_pawns().get(this.pawnFirstTomove.getId()).setIJ(iafter, jafter);
		this.pawns.get(this.pawnFirstTomove.getId()).setIJ(iafter, jafter);
		
		//verifico se la pedina appena mossa pu� essere promossa a dama
		if(this.id_user == 1)
		{
			System.out.println("--------------------move dello user player---------------------");
			if(this.pawnAfterMove.getI() == 0)
				this.graduatePawnToDama();
		}
		else if(this.id_user == 2)
		{
			System.out.println("--------------------move dell'ai player--------------------------");
			if(this.pawnAfterMove.getI() == StaticVariables.RIGHE_COLONNE-1)
				this.graduatePawnToDama();
		}
		
	
		this.pawnAnimation(iprec,jprec,iafter,jafter);
		
		((MyPlayPanel)this.game.getPlay_panel()).setEnter(false);
		((MyPlayPanel)this.game.getPlay_panel()).initIJplayPanel();
		
		if(this.pawnToEat != null)
		{
			System.out.println("prima di mangiare");
			this.game.getDama().printMatrix();
			if(this instanceof UserPlayer)
			{
				if(this.pawnToEat.isDama())
				conta_dame_ai--;
			}
			this.eatOpponentPawn(this.pawnToEat);
			System.out.println("dopo aver aver mangiato");
			this.game.getDama().printMatrix();
			
			if(this.verifyEndGame())
			{
				this.game.getPlay_panel().repaint();
				return;
			}
		    this.pawnToEat = null;
}
		
		if(this instanceof UserPlayer && !Variables.mangiata_multipla && !Variables.update)
		{
			  this.siPassaDalloUserAllAi();
		}
	}
	
	 /**
     * Scompone la string ricevuta dal server in coordinate di interi per poter vedere a video gli aggiornamenti dell'altro avversario.
     */
    public void algorythmOfTransformationPlayer()
    {
    	Variables.update = true;
    	//my_cells, opponent_cell(le mie praticamente) e <END>. 
		String [] array_parole = ((UserPlayer) this.game.getUser_player()).getClient().getModifiedSentence().split("\n");
		//la prima righe di my_cells dell'avversario
	    ArrayList<Cell> clicked_cells =  new ArrayList<Cell>();
	    
	    if(array_parole[0].contains("end"))
	    {
	    	((UserPlayer)this.game.getUser_player()).getClient().closeConnection();
	    	Variables.single_player = true;
	    	this.game.getPlay_panel().getMenu().setPanelsVisibility(this.game.getPlay_panel().getMenu().getCurrent_panel(), false);
	    	this.game.getPlay_panel().getMenu().setPanelProperties(this.game.getPlay_panel().getMenu().getMyMenuPanel(), true);
	    }
	    else
	    {
		   this.stringToCoord(clicked_cells, array_parole[0]);
	   //la seconda riga di opponent_cells dell'avversario
	   String stringa_opponent_cells = "";
	   //creo un array list di opponents_cells e richiamo la clickToMove...
	   if(array_parole.length > 1)
	   {
		   ArrayList<Cell> opponent_cells = new ArrayList<Cell>();
	       this.stringToCoord(opponent_cells, array_parole[1]);
	       ((AIPlayer)this.game.getAi_player()).moveByRightMouseClickUpdateteMovementMultiplayer(clicked_cells, opponent_cells);
	   }
	   //muovo semplicemente, senza mangiare
	   else
	   {
		   ((AIPlayer)this.game.getAi_player()).
		   setPawnFirstTomove(this.game.getDama().getPawnAtPosition(clicked_cells.get(0).getI(), clicked_cells.get(0).getJ()));
		   
		   ((AIPlayer)this.game.getAi_player()).passaDaPawnFirstMoveToPawnAfterMoveAI(clicked_cells.get(0).getI(), clicked_cells.get(0).getJ(), 
				   clicked_cells.get(1).getI(), clicked_cells.get(1).getJ(), null);
	   }
	   Variables.update = false;
	    }
	   
    }
    
    /**
     * Estrapola le coordinate dalla stringa e le inserisce nell'array.
     */
    private void stringToCoord(ArrayList<Cell> array, String s)
    {	
    	String [] array_parole = s.split(" ");
    	char [] array_coord_string = new char[2];
    	
    	for(String i: array_parole)
    	{
    		array_coord_string = i.toCharArray();
    		array.add(new Cell(Character.getNumericValue(array_coord_string[0]), Character.getNumericValue(array_coord_string[2]),this.game.getAi_player().getStringColour()));
    	}
    }
    
    /**
     * Estrapola tutte le coordinate intere di 'array' per metterle in una stringa che poi restituisco.
     * @param array
     * @param s
     */
    public String CoordToString(ArrayList<Cell> array)
    {	
    	String final_s = "";
    	for(Cell i: array)
    	{
    		final_s += i.getI() + "," + i.getJ() + " ";
    	}
    	
    	String final_stringa = "";
    	
    	for(int i = 0; i < final_s.length()-1; i++)
    		final_stringa += final_s.charAt(i);
    	
    	return final_stringa + "\n";
    }
    
	
	protected void pawnAnimation(int iprec, int jprec, int iafter, int jafter) {
		// TODO Auto-generated method stub
		this.unpasso = Variables.w_pawn/StaticVariables.pawn_animation;
		//se va sopra
		if(iafter-iprec > 0)
			this.sopraOsotto = +1;
		//se va sotto
		else
			this.sopraOsotto = -1;
		
		//destra
		if(jafter-jprec > 0)
			this.destraOsinistra = +1;
		//sinistra
		else
			this.destraOsinistra = -1;
		
		singlePlayerMovement = new SinglePlayerMovement(singleMovementlock);
		singlePlayerMovement.settoTutto(this.game,iprec,jprec,iafter,jafter,this);
		singlePlayerMovement.start();
		
	}
	

	public void siPassaDalloUserAllAi()
	{
		if(!isEnd)
		{
		if(this.id_user == 1)
		{
			
			((MyPlayPanel)this.game.getPlay_panel()).setTurno_ai(true);
			
				if (Variables.single_player)
					((AIPlayer) this.game.getAi_player()).chooseAIPawnToMove();
			 if(this.verifyEndGame())
				{
					this.game.getPlay_panel().repaint();
					return;
				}
			 ((MyPlayPanel)this.game.getPlay_panel()).setTurno_ai(false);
		}
		}
	}
	
	/**
	 * Verifico se dopo la mossa del player a cui spettava muovere, l'avversario � rimasto senza pedine. Se cos� fosse, sarebbe game over.
	 * @return
	 */
	public boolean verifyEndGame()
	{
		if(this.opponent_player.getPawns().isEmpty()) //se l'avversario non ha pi� pedine, il gioco termina
		{
			this.winner = true;
			this.opponent_player.setWinner(false);
			
			isEnd = true;
			if(this instanceof AIPlayer) //se l'avversario dell'ai � rimasto senza pedine (ovvero lo user(noi)), allora significa che abbiamo perso e la seconda booleana � false.
				this.game.setEND_GAME(true, false);
			else
				this.game.setEND_GAME(true, true);
			return true;
		}
		
		if(!this.canIMove())
		{
			this.winner = false;
            this.opponent_player.setWinner(true);
			this.game.setEND_GAME(true, false);
			return true;
		}
		return false;
	}
	
	/**
	 * La pedina si sposter�, ogni volta che verr� richiamata questa funzione, di 
	 * @param immagine
	 */
	public void pawn_animation(Immagine immagine, int iprec, int jprec, int iafter, int jafter)
	{
		//se l'animazione� verso sopra e a destra o a sinistra
		if(iprec-iafter > 0)
		{
			//se � a destra
			if(jprec-jafter > 0)
				immagine.setX(immagine.getX()+immagine.getImageWidth()/5);
			else
				immagine.setX(immagine.getX()-immagine.getImageWidth()/5);
			immagine.setY(immagine.getY()-immagine.getImageHeight()/5);
				
		}
		else if(iprec-iafter < 0)
		{
			//se � a destra+1
			if(jprec-jafter > 0)
				immagine.setX(immagine.getX()+immagine.getImageWidth()/5);
			else
				immagine.setX(immagine.getX()-immagine.getImageWidth()/5);
			immagine.setY(immagine.getY()+immagine.getImageHeight()/5);
		}
		
		this.game.getPlay_panel().repaint();
		
	}
	
	//Ritorna true se il set di pawns contiene il pawn che gli passo; altrimenti false.
	public boolean hasThisPawn(Pawn p)
	{
		if(this.pawns.containsValue(p))
			return true;
		return false;
	}
	
	public void eatOpponentPawn(Pawn p)	{}
	
	public void graduatePawnToDama()
	{
		if (this instanceof AIPlayer) {
			conta_dame_ai++;
		}
		
		this.pawns.get(this.pawnAfterMove.getId()).setDama(true);
		this.game.getTotal_pawns().get(this.pawnAfterMove.getId()).setDama(true);
		if(this.pawnAfterMove.getColour() == "white")
		{
			if(!Variables.editor)
			   this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(this.pawnAfterMove.getId()).setPath(StaticVariables.PATH_PEDINA_DAMABIANCA);
			else 
				 this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(this.pawnAfterMove.getId()).setPath(Variables.PATH_DAMA1);
		}
		else
		{
			if(!Variables.editor)
				   this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(this.pawnAfterMove.getId()).setPath(StaticVariables.PATH_PEDINA_DAMANERA);
				else 
					 this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(this.pawnAfterMove.getId()).setPath(Variables.PATH_DAMA2);
		}
	}

	public int getId() {
		return id_user;
	}
	
	/**
	 * E' una funzione che mostra all'utente dove muovere la sua pedina per poter mangiare
	 * @param visibility
	 */
	public void changeCellaEvidenziataProperties(boolean visibility)
	{
		//se voglio evidenziare tutte le celle su cui posso spostarmi per mangiare, scorro i due for 'valid_cells_to eat' e 'pedine_che_devono_mangiare'
		
		((MyPlayPanel)this.game.getPlay_panel()).getProporziona().setSizePawn(this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_CELLA_EVIDENZIATA),this.valid_mycells_to_eat.get(0).getI(), this.valid_mycells_to_eat.get(0).getJ());
		this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_CELLA_EVIDENZIATA).setVisible(visibility);
		
		((MyPlayPanel)this.game.getPlay_panel()).getProporziona().setSizePawn(this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_CELLA_PEDINA_CHE_MANGIA),this.pedine_che_devono_mangiare.get(0).getI(), this.pedine_che_devono_mangiare.get(0).getJ());
		this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_CELLA_PEDINA_CHE_MANGIA).setVisible(visibility);
		this.game.getPlay_panel().repaint();
		if(visibility)
		Sounds.getSounds().play(StaticVariables.PATH_AUDIO_ALERT);
	}
	
	/**
	 * E' la funziona che far� vedere o meno una pedina una volta cliccata
	 * @param visibility
	 * @param i
	 * @param j
	 */
	public void changePedinaPremutaProperties(boolean visibility, int i, int j)
	{
		((MyPlayPanel)this.game.getPlay_panel()).
		getProporziona().
		setSizePawn(this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().
				get(StaticVariables.ID_PEDINA_PREMUTA), i, j);
		this.game.getPlay_panel().getCaricatore_immagini().getPaws_images().get(StaticVariables.ID_PEDINA_PREMUTA).setVisible(visibility);
		((MyPlayPanel)this.game.getPlay_panel()).repaint();
	}
	
	/**
	 * E' una funzione che verifica se sia game over o no, nel caso in cui allo user player � rimasta un'unica pedina,
	 *  ma che non ha pi� mosse a disposizione( cio� che � rimasto vloccato dalle pedine avversarie.
	 * Se attorno alla pedina c'� anche solo una cella libera, ritorno true perch� posso muovere.
	 * @param iprec -> i della pedina prima di 
	 * @param jprec
	 * @return
	 */
	protected boolean canIMove()
	{
		if(((UserPlayer)this.game.getUser_player()).getPawns().size() == 1)
		{
			Pawn pawn = null;
			for(Pawn p: this.pawns.values())
			{
				 pawn = p;
				 break;
			}
			if(pawn.isDama())
		    {
		    	if(pawn.getI()+1 <= StaticVariables.RIGHE_COLONNE-1 && pawn.getJ()-1 >= 0 &&
		    			(!this.game.getDama().isThereaPawn(pawn.getI()+1, pawn.getJ()-1) || (this.game.getDama().isThereaPawn(pawn.getI()+1, pawn.getJ()-1) && pawn.getI()+2 <= StaticVariables.RIGHE_COLONNE-1 && pawn.getJ()-2 >= 0 && !this.game.getDama().isThereaPawn(pawn.getI()+2, pawn.getJ()-2))) )
		    		return true;
		    	else if(pawn.getI()+1 <= StaticVariables.RIGHE_COLONNE-1 && pawn.getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
		    			(!this.game.getDama().isThereaPawn(pawn.getI()+1, pawn.getJ()+1) || (this.game.getDama().isThereaPawn(pawn.getI()+1, pawn.getJ()+1) && pawn.getI()+2 <= StaticVariables.RIGHE_COLONNE-1 && pawn.getJ()+2 >= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(pawn.getI()+2, pawn.getJ()+2))) )
		    		return true;
		    	else
		    		return false;
		    }
				//se davanti alla pedina dello user player, non ci sono pedine, oppure se ci sono, ma dietro di loro non c'� nessuna pedina
			    if(pawn.getI()-1 >= 0 && pawn.getJ()-1 >= 0 && 
			    		(!this.game.getDama().isThereaPawn(pawn.getI()-1, pawn.getJ()-1) || (this.game.getDama().isThereaPawn(pawn.getI()-1, pawn.getJ()-1) && pawn.getI()-2 >= 0 && pawn.getJ()-2 >= 0 && !this.game.getDama().isThereaPawn(pawn.getI()-2, pawn.getJ()-2))))
			    	return true;
			    else if(pawn.getI()-1 >= 0 && pawn.getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
			    		(!this.game.getDama().isThereaPawn(pawn.getI()-1, pawn.getJ()+1) || (this.game.getDama().isThereaPawn(pawn.getI()-1, pawn.getJ()+1) && pawn.getI()-2 >= 0 && pawn.getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(pawn.getI()-2, pawn.getJ()+2))) )
			    	return true;
			    else
			    return false;
		}
		else
		  return true;
	}
	
	protected boolean mangiataMultipla()
	{
		return false;
	}
	
	/**
	 * E' la funzione che elimina tutti gli elementi di 'celle_pronte_per_il_pasto' da dopo l'indice della prima cella che ha un doppione, perch� se quella cella ha un doppione,
	 * tutte le celle dopo di esse, sono sbagliate.
	 * Poi, ogni due celle di 'celle consecutive per il pasto' ne deve corrispondere una di 'oppponet_cells'; in base a questo, 
	 * elimino tutte le celle in pi� di 'opponent_cells'.
	 * @param a
	 * @param b
	 */
	public void togliDoppioni(ArrayList<Cell> celle_per_pasto_consecutivo, ArrayList<Cell> opponent_cells)
	{
		if(this instanceof AIPlayer && !celle_per_pasto_consecutivo.isEmpty() && !opponent_cells.isEmpty())
		{
		int index = 0;
		for(int i = 0; i < celle_per_pasto_consecutivo.size(); i++)
		{
			for(int j = 0; j < celle_per_pasto_consecutivo.size(); j++)
			{
				if(i < j)
				{
					if(celle_per_pasto_consecutivo.get(i).getI() == celle_per_pasto_consecutivo.get(j).getI() &&
							celle_per_pasto_consecutivo.get(i).getJ() == celle_per_pasto_consecutivo.get(j).getJ())
					{
						index = i;
						break;
					}
				}
			}
			if(index != 0)
				break;
		}
		
		   if(index != 0)
		   {
		
		      this.removeElementsFromAnIndex1(celle_per_pasto_consecutivo, index);
//		      this.print(celle_per_pasto_consecutivo, "celle_per_pasto_consecutivo dopo");
		
		    if(opponent_cells.size() >= celle_per_pasto_consecutivo.size())
		    {
//			  this.print(opponent_cells, "opponent_cells prima");
			  index = celle_per_pasto_consecutivo.size()-1;
		  	  this.removeElementsFromAnIndex2(opponent_cells, index);
//		  	  this.print(opponent_cells, "opponent_cells dopo");
		    }
		   }
		}
		
	}
	
	private void removeElementsFromAnIndex1(ArrayList<Cell> array, int index)
	{
		for(int i = array.size()-1; i > index+1; i--)
			   array.remove(i);
	}
	
	private void removeElementsFromAnIndex2(ArrayList<Cell> array, int index)
	{
		for(int i = array.size()-1; i > index-1; i--)
			   array.remove(i);
	}
	
	public void moveByRightMouseClick(ArrayList<Cell> clicked_cells, ArrayList<Cell> opponent_cells)
	{
		multiplePlayerMovement = new MultiplePlayerMovement();
		multiplePlayerMovement.settaTuttoMultiple(this.game, multipleMovementLock, clicked_cells, opponent_cells, this);
		multiplePlayerMovement.start();
	}
	
	public void moveByRightMouseClickUpdateteMovementMultiplayer(ArrayList<Cell> clicked_cells, ArrayList<Cell> opponent_cells)
	{
		multipleMovementMultiplayer = new MultipleMovementMultiplayer();
		multipleMovementMultiplayer.settaTuttoMultiple(this.game, multipleMovementLock, clicked_cells, opponent_cells, this);
		multipleMovementMultiplayer.start();
	}
	

	/**
	 * E' la funzione che viene richiamata quando si pu� procedere a mangiare effettivamente le pedine avversarie selezionate prima
	 * @param clicked_cells
	 * @param ai_cells
	 */
	public void moveByRightMouseClickSenzaThread(ArrayList<Cell> clicked_cells, ArrayList<Cell> opponent_cells)
	{
		this.togliDoppioni(clicked_cells, opponent_cells);
		this.sendMangiataMultipla(clicked_cells, opponent_cells);
		
		//cicla finch� la sua size non � 1
		while(clicked_cells.size() > 1)
		{
			//la iprec e la jprec saranno SEMPRE le coordinate della cella alla posizione 0 dell'array 'clicked cells'
			//la iafter e la jafter saranno SEMPRE le coordinate della cella alla posizione 1 dell'array 'clicked cells'
			//la pawn to eat avr� SEMPRE le coordinate della Cell alla posizione 0 dell'array 'ai_cells' 
			if(!opponent_cells.isEmpty())
				this.setPawnToEat(this.game.getDama().getPawnAtPosition(opponent_cells.get(0).getI(), opponent_cells.get(0).getJ()));
			
			if(!clicked_cells.isEmpty())
			//la pawn first to move = alla pawn con coordinate della Cell in posizione 0 dell'array 'clicked cells'
				this.setPawnFirstTomove(this.game.getDama().getPawnAtPosition(clicked_cells.get(0).getI(), clicked_cells.get(0).getJ()));
			//la pawn aftermove = pawn after to move
			this.setPawnAfterMove(this.getPawnFirstTomove());
			
				((AIPlayer)this.game.getAi_player()).passaDaPawnFirstMoveToPawnAfterMoveAI(clicked_cells.get(0).getI(), clicked_cells.get(0).getJ(), 
						clicked_cells.get(1).getI(), clicked_cells.get(1).getJ(), this.getPawnToEat());
			
			//elimino l'elemento alla posizione 0 dell'array 'clicked cells'
			//elimino l'elemento alla posizione 0 dell'array 'ai cells'
			
			clicked_cells.remove(0);
			if(!opponent_cells.isEmpty())
			opponent_cells.remove(0);
		}
		clicked_cells.clear();
		opponent_cells.clear();
		this.receiveMangiataMultipla();
	}
	
	protected boolean controllaValiditaCoord(int i, int j)
	{
		return true;
	}
	
	protected boolean weContinue()
	{
		return true;
	}
	
	protected boolean comandoSiaPerLeDameCheNon_controllaValiditaCoord(int i, int j)
	{
		return true;
	}
	
	protected void print(ArrayList<Cell> a, String nome_array)
	{
		if(a.size() > 0)
		{
		System.out.println(nome_array);
		for(Cell c: a)
			System.out.print("<"+c.getI()+","+c.getJ()+"> ");
		System.out.println();
		}
	}
	
	public void setId(int id) {
		this.id_user = id;
	}

	public String getStringColour()
	{
		return this.colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
	
	public HashMap<Integer, Pawn> getPawns() {
		return pawns;
	}

	public void setPawns(HashMap<Integer, Pawn> pawns) {
		this.pawns = pawns;
	}

	public int getNum_pawns() {
		return num_pawns;
	}

	public void setNum_pawns(int num_pawns) {
		this.num_pawns = num_pawns;
	}

	public void insertPawn(Integer pair,Pawn p)
	{
		this.pawns.put(pair,p);
	}

	public Pawn getPawnFirstTomove() {
		return pawnFirstTomove;
	}

	public void setPawnFirstTomove(Pawn pawnFirstTomove) {
		this.pawnFirstTomove = pawnFirstTomove;
	}

	public Pawn getPawnAfterMove() {
		return pawnAfterMove;
	}

	public void setPawnAfterMove(Pawn pawnAfterMove) {
		this.pawnAfterMove = pawnAfterMove;
	}

	public Pawn getPawnToEat() {
		return pawnToEat;
	}

	public void setPawnToEat(Pawn pawnToEat) {
		this.pawnToEat = pawnToEat;
	}

	public Player getOpponent_player() {
		return opponent_player;
	}

	public void setOpponent_player(Player opponent_player) {
		this.opponent_player = opponent_player;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	protected boolean comandoSiaPerLeDameCheNon_weContinue() {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean comandoSoloPerLeDame_weContinue() {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean areThereSameCells(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Cell> getOpponent_cells() {
		return opponent_cells;
	}

	public void setOpponent_cells(ArrayList<Cell> opponent_cells) {
		this.opponent_cells = opponent_cells;
	}

	public ArrayList<Cell> getCelle_per_pasto_consecutivo() {
		return celle_per_pasto_consecutivo;
	}

	public void setCelle_per_pasto_consecutivo(ArrayList<Cell> celle_per_pasto_consecutivo) {
		this.celle_per_pasto_consecutivo = celle_per_pasto_consecutivo;
	}

	public static boolean isEnd() {
		return isEnd;
	}

	public static void setEnd(boolean isEnd) {
		Player.isEnd = isEnd;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public static int getConta_dame_ai() {
		return conta_dame_ai;
	}

	public static void setConta_dame_ai(int conta_dame_ai) {
		Player.conta_dame_ai = conta_dame_ai;
	}

	public ArrayList<Cell> getValid_mycells_to_eat() {
		return valid_mycells_to_eat;
	}

	public void setValid_mycells_to_eat(ArrayList<Cell> valid_mycells_to_eat) {
		this.valid_mycells_to_eat = valid_mycells_to_eat;
	}

	public static SinglePlayerMovement getSinglePlayerMovement() {
		return singlePlayerMovement;
	}

	public static void setSinglePlayerMovement(SinglePlayerMovement singlePlayerMovement) {
		Player.singlePlayerMovement = singlePlayerMovement;
	}

	public static MultiplePlayerMovement getMultiplePlayerMovement() {
		return multiplePlayerMovement;
	}

	public static void setMultiplePlayerMovement(MultiplePlayerMovement multiplePlayerMovement) {
		Player.multiplePlayerMovement = multiplePlayerMovement;
	}

	public static Lock getSingleMovementlock() {
		return singleMovementlock;
	}

	public static void setSingleMovementlock(Lock singleMovementlock) {
		Player.singleMovementlock = singleMovementlock;
	}

	public static Lock getMultipleMovementLock() {
		return multipleMovementLock;
	}

	public static void setMultipleMovementLock(Lock multipleMovementLock) {
		Player.multipleMovementLock = multipleMovementLock;
	}

	public int getUnpasso() {
		return unpasso;
	}

	public void setUnpasso(int unpasso) {
		this.unpasso = unpasso;
	}

	public int getSopraOsotto() {
		return sopraOsotto;
	}

	public void setSopraOsotto(int sopraOsotto) {
		this.sopraOsotto = sopraOsotto;
	}

	public int getDestraOsinistra() {
		return destraOsinistra;
	}

	public void setDestraOsinistra(int destraOsinistra) {
		this.destraOsinistra = destraOsinistra;
	}

	public int getTot() {
		return tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Pawn getPawnMossaPrima() {
		return pawnMossaPrima;
	}

	public void setPawnMossaPrima(Pawn pawnMossaPrima) {
		this.pawnMossaPrima = pawnMossaPrima;
	}

	public boolean isMove_dama() {
		return move_dama;
	}

	public void setMove_dama(boolean move_dama) {
		this.move_dama = move_dama;
	}

	public ArrayList<Pawn> getPedine_che_devono_mangiare() {
		return pedine_che_devono_mangiare;
	}

	public void setPedine_che_devono_mangiare(ArrayList<Pawn> pedine_che_devono_mangiare) {
		this.pedine_che_devono_mangiare = pedine_che_devono_mangiare;
	}

	public String getOutToServer() {
		return outToServer;
	}

	public void setOutToServer(String outToServer) {
		this.outToServer = outToServer;
	}

	public String getInFromServer() {
		return inFromServer;
	}

	public void setInFromServer(String inFromServer) {
		this.inFromServer = inFromServer;
	}

	
	

}
