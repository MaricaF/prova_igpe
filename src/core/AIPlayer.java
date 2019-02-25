package core;

import java.util.Random;

import audio.Sounds;
import gui.MyPlayPanel;
import interfaces.StaticVariables;

public class AIPlayer extends Player{

	private Random random;
	private String type_of_intelligence;
	private boolean isDama;
	private Cell cella_per_pasto_consecutivo_temp;
	
	public AIPlayer(Game game, String ai)
	{
		super(game);
		this.type_of_intelligence = ai;
		this.id_user = 2;
		this.random = new Random();
		this.opponent_player = this.game.getUser_player();
		this.isDama = false;
		
	}
	
	/**
	 * QUesta funzione serve a scegliere quale pedina dovrà muovere il player intelligente, per poi richiamare la funzione movePawn
	 */
	public void chooseAIPawnToMove()
	{
		if(this.mangiataMultipla())
			return;

		this.ai();
		//preferisco mangiare le dame o le pedine più vicine alla riga in cui poi le avversarie potrebbero trasformarsi in dame
		//se posso mangiare una pedina avversaria, un'altra ancora di fila e non posso essere mangiata, muovi
		//se posso mangiare una pedina avversaria, un'altra ancora di fila e vengo mangiata, muovi (constraint che semplifica il gioco)
		//se posso mangiare una pedina e poi non vengo mangiato, muovi
		//se posso mangiare una pedina e poi vengo mangiato, muovi (constraint che semplifica il gioco)
	}
	
	@Override
	protected boolean mangiataMultipla()
	{
		//scorro l hashmap delle mie pedine
		for(Pawn my_pawn: this.pawns.values())
		{
			this.celle_per_pasto_consecutivo.add(new Cell(my_pawn.getI(),my_pawn.getJ(),""));
			if(!my_pawn.isDama() && !this.weContinueDamaENon() && !this.opponent_cells.isEmpty() && !this.celle_per_pasto_consecutivo.isEmpty())
			{
				if(this.isDama)
					this.cella_per_pasto_consecutivo_temp = this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1);
				
				this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
				
				if(this.isDama)
				this.celle_per_pasto_consecutivo.add(this.cella_per_pasto_consecutivo_temp);
				//avvio la funzione di move della pedina corrente, mangiando tutte quelle avversarie selezionate.
				
				if(this.isDama && !this.weContinueOnlyDama())
				{
					
					this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
					this.isDama = false;
					return true;
				}
				else
					return true;
				
			}
			//quì ci entrerà solo se my_pawn è già una dama, oppure se lo è diventata in "this.weContinueDamaENon()"
			else if(my_pawn.isDama() && !this.weContinueOnlyDama() && !this.opponent_cells.isEmpty())
			{
				this.print(this.celle_per_pasto_consecutivo, "celle per sta minchia4");
			    this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
			    
				return true;
			}
			this.celle_per_pasto_consecutivo.clear();
			this.opponent_cells.clear();
		}
		
		return false;
	}	
	
	/**
	 * E'una funzione ricorsiva dove si esce quando non si può più continuare
	 */
	protected boolean weContinueDamaENon()
	{
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1 >= 0)
		{
			//se c'è una pedina avversaria una cella più in basso a sinistra
		  if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) &&
				  this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) != this.colour)
		  {
			  //se non ci sono pedine due celle in basso a sinitra, quella cella è calpestabile
			if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
					this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2 >= 0 &&
					!this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2))
			{
				this.opponent_cells.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1,""));
				this.celle_per_pasto_consecutivo.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2,""));
			    
				 //controllo se sull'ultima cella aggiunta, c è una dama
				 this.isADame(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI());
				
			    this.weContinueDamaENon(); //se si può andare avanti, si va avanti
			}
			
		  }
		}
			
		//se c'è una pedina avversaria una cella più in basso a destra
		  /*else*/ if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
					this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1)
			  {
			  if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) &&
				  this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) != this.colour)
		        {
				 //se non ci sono pedine due celle in basso a destra, quella cella è calpestabile
				 if( this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1
						 && !this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2))
				  {
				  	 this.opponent_cells.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1,""));
					 this.celle_per_pasto_consecutivo.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2,""));
				      
					 //controllo se sull'ultima cella aggiunta, c è una dama
					 this.isADame(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI());
					 
				    this.weContinueDamaENon(); //se si può andare avanti, si va avanti
				  }
		        }
			  }
		  
		  
			return false;
	}
	
	protected boolean weContinueOnlyDama()
	{
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1 >= 0 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1 >= 0)
		{
			//se c'è una pedina avversaria una cella più in alto a sinistra
			  if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) &&
					  this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) != this.colour)
			  {
				  if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2 >= 0 &&
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2 >= 0 &&
							!this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2))
					{
						this.opponent_cells.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1,""));
						this.celle_per_pasto_consecutivo.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2,""));
					    this.weContinueOnlyDama(); //se si può andare avanti, si va avanti
					}
			  }
		}
		
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1 >= 0 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1)
		{
			//se c'è una pedina avversaria una cella più in alto a destra
			  if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) &&
					  this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) != this.colour)
			  {
				  if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2 >= 0 &&
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
							!this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2))
					{
						this.opponent_cells.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1,""));
						this.celle_per_pasto_consecutivo.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2,""));
						 this.weContinueOnlyDama(); //se si può andare avanti, si va avanti
					}
			  }
		}
		
		if(!this.weContinueDamaENon())
		{
			return false;
		}
		return false;
	}
	
	private void isADame(int i)
	{
		if(i == StaticVariables.RIGHE_COLONNE-1)
			this.isDama = true;
	}
	
	/**
	 * Decide, in base al tipo di intelligenza scelta dall'utente, l'intelligenza da dare alle pedine
	 */
	private void ai()
	{
		//se è stata scelta la simple intelligence
		if(this.type_of_intelligence.equals(StaticVariables.SIMPLE_AI_NAME))
		{
			if(!this.simpleIntelligenceMove())
			{
				//il primo valore è per l'end game e il secondo è per creare il final panel con il winner dell'avversario, quindi win avversario = true
				this.game.setEND_GAME(true,true);
			}
			return;
		}
		
		//se è stata scelta la more advanced intelligence
		else if(this.type_of_intelligence.equals(StaticVariables.MEDIUM_AI_NAME))
		{
		  if(!this.mediumMove())
	      {
	    	  if(!this.simpleIntelligenceMove())
	    	  {
	    		  this.game.setEND_GAME(true,true);
	    	  }
	    	
	      }
		  return;
		}
		
		//se è stata scelta la superior intelligence
		else if(this.type_of_intelligence.equals(StaticVariables.HARD_AI_NAME))
		{
		  if(!this.superiorIntelligenceMove())
		  {
			  if(!this.mediumMove())
		      {
		    	  if(!this.simpleIntelligenceMove())
		    	  {
		    		  this.game.setEND_GAME(true,true);
		    	  }
		      }
		  }
		}
			
	}
	
	private int getRandom()
	{
		return (random.nextInt(12)+13);
	}
	
	private boolean mediumMove()
	{
			int x = this.getRandom();
			boolean dentro = true;
			int cont = 0;
			while(dentro)
			{
				if(!this.pawns.containsKey(x))
				{
					x = this.getRandom();
					continue;
				}
			
			//verifico le coordinate di my_pawn siano ammissibili
			if(this.pawns.get(x).getI()+1 >= 0 && this.pawns.get(x).getI()+2 <= StaticVariables.RIGHE_COLONNE-1 && this.pawns.get(x).getI()+1 <= StaticVariables.RIGHE_COLONNE-1 )
			{
				//si deve andare avanti o se la pedina è una dama che deve andare avanti, oppure se la pedina non è una dama.
			   if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()-1))
			    {
				 //se due celle in avanti(o dietro.. è un esempio), ci sono pedine non avversarie, muovi, altrimenti esco da questa funzuione e richiamo la simple Intelligence
				   if(this.pawns.get(x).getJ()-2 >= 0 && ((this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()-2)
						   && this.game.getDama().getPawnColour(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()-2) == this.colour)
						   || !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()-2)))
				   {
				    this.utilita(x, +1, -1); 
				    return true;
				   }
			    }
			   if(this.pawns.get(x).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()+1))
			    {
				 //se due celle in avanti(o dietro.. è un esempio), ci sono pedine non avversarie, muovi, altrimenti esco da questa funzuione e richiamo la simple Intelligence
				   if(this.pawns.get(x).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1
						   && ((this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()+2)
						   && this.game.getDama().getPawnColour(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()+2) == this.colour)
						   || !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()+2)))
				   {
				    this.utilita(x, +1, +1);
				    return true;
				   }
		    	}
			}
			
			
			if(this.pawns.get(x).getI()-1 >= 0 && this.pawns.get(x).getI()-2 >= 0 && this.pawns.get(x).getI()-1 <= StaticVariables.RIGHE_COLONNE-1 && this.pawns.get(x).isDama())
			{
				if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()-1) )
				{
					//se due celle in avanti(o dietro.. è un esempio), ci sono pedine non avversarie, muovi, altrimenti esco da questa funzuione e richiamo la simple Intelligence
					if(this.pawns.get(x).getJ()-2 >= 0 && ((this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()-2) 
						&& this.game.getDama().getPawnColour(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()-2) == this.colour )
							|| !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()-2)))
                     {
					  this.utilita(x, -1, -1);
					  return true;
                     }
				}
				 if(this.pawns.get(x).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()+1))
				{
					//se due celle in avanti(o dietro.. è un esempio), ci sono pedine non avversarie, muovi, altrimenti esco da questa funzuione e richiamo la simple Intelligence
					 if(this.pawns.get(x).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 && ((this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()+2) 
							 && this.game.getDama().getPawnColour(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()+2) == this.colour)
							 || !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()+2)))
					 {
					  this.utilita(x, -1, +1);
					  return true;
					 }
				}
			}
			
			cont++;
			if(cont >= 20)
				return false;
			x = this.getRandom();
		}//fine while
		 
			return false; 
		}
	private boolean simpleIntelligenceMove()
	{
		//se non c'è nessuna pedina avversaria da mangiare, muovo la prima che davanti non ha pedine
				    int x = this.getRandom();
				    int cont = 0;
					boolean dentro = true;
					
					while(dentro)
					{
						if(!this.pawns.containsKey(x))
						{
							x = this.getRandom();
							continue;
						}
					
					
					//verifico le coordinate di my_pawn siano ammissibili
					if(this.pawns.get(x).getI()+1 >= 0 && this.pawns.get(x).getI()+1 <= StaticVariables.RIGHE_COLONNE-1)
					{
						//si deve andare avanti o se la pedina è una dama che deve andare avanti, oppure se la pedina non è una dama.
					   if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()-1))
					    {
						   this.utilita(x, +1, -1); 
						   return true;
					    }
					   if(this.pawns.get(x).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()+1))
					    {
						    this.utilita(x, +1, +1);
						    return true;
				    	}
					}
					
							
					if(this.pawns.get(x).getI()-1 >= 0 && this.pawns.get(x).getI()-1 <= StaticVariables.RIGHE_COLONNE-1 && this.pawns.get(x).isDama() && this.pawns.get(x).getI()-2 >= 0)
					{
						//nella seconda riga dell'if, effettuo un'ottimizzazione dell'algoritmo per renderlo più intelligente
						if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()-1))
						{
							this.utilita(x, -1, -1);
							return true;
						}
						 if(this.pawns.get(x).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()+1))
						{
							this.utilita(x, -1, +1);
							return true;
						}
					}
					cont++;
					if(cont >= 20)
						return false;
					
					x = this.getRandom();
				}//fine while
				return false;	 
	}
	
	
	
	private boolean superiorIntelligenceMove()
	{
		int x = this.getNum_pawns();
		if(x >= 10)
			return true;
		return false;
	}
	
	private void utilita(int x, int varI, int varJ)
	{
	    this.pawnFirstTomove = this.pawns.get(x);
	    this.passaDaPawnFirstMoveToPawnAfterMove(this.pawns.get(x).getI(), this.pawns.get(x).getJ(), this.pawns.get(x).getI()+varI, this.pawns.get(x).getJ()+varJ, null);
	}
	
	
	@Override
	public void eatOpponentPawn(Pawn p)
	{
		Sounds.getSounds().play(StaticVariables.PATH_AUDIO_EAT);
		//rimuovo la pedina dalla struttura dati dell'avversario
		this.game.getUser_player().getPawns().remove(p.getId());
		//richiamo la funzione che mi elimina questa stessa pedina dalle altre strutture dati
		this.game.eatPawn(p);
	}
	
	public void passaDaPawnFirstMoveToPawnAfterMoveAI(int iprec, int jprec, int iafter, int jafter, Pawn pawnToEat)
	{
		
             this.move(iprec, jprec, iafter, jafter, pawnToEat);
		
		//Qui, nel caso in cui l'array che contiene le posizioni in cui si dovrebbero muovere le medine per mangiare, fosse pieno, l'immagine della cella
				// selezionata non si deve più vedere.
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
		
		//verifico se la pedina appena mossa può essere promossa a dama
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
		
	}

	public String getType_of_intelligence() {
		return type_of_intelligence;
	}

	public void setType_of_intelligence(String type_of_intelligence) {
		this.type_of_intelligence = type_of_intelligence;
	}
	
	
	

}
