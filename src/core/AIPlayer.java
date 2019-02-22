package core;

import java.util.ArrayList;
import java.util.Random;

import gui.MyPlayPanel;
import interfaces.StaticVariables;
import interfaces.Variables;

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
//		this.timer.reset(); 
//		System.err.println("-.-.-.-.-sleeep-.-.-.-.-.-");
//	    while(this.timer.getTempoTrascorso() <= 1000.0)
//	    { 
//		 
//		}
//	    System.err.println("-.-.-.-.-end sleeep-.-.-.-.-.-");
		
		
//		int cont = 1;
//		while(cont <= 100000)
//			cont++;
		System.out.println("SONO QUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
		if(this.mangiataMultipla())
			return;

//		if(!this.isEnd)
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
			System.out.println("AI i,j <"+my_pawn.getI()+","+my_pawn.getJ()+">");
			if(!my_pawn.isDama() && !this.weContinueDamaENon() && !this.opponent_cells.isEmpty() && !this.celle_per_pasto_consecutivo.isEmpty())
			{
//				this.print(celle_per_pasto_consecutivo, "AI CELLE PER PASTO CONSECUTIVE");
//				this.print(this.opponent_cells, "AI OPPONENT CELLS");
				if(this.isDama)
					this.cella_per_pasto_consecutivo_temp = this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1);
				
				this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
				
				if(this.isDama)
				this.celle_per_pasto_consecutivo.add(this.cella_per_pasto_consecutivo_temp);
				//avvio la funzione di move della pedina corrente, mangiando tutte quelle avversarie selezionate.
//				if(!this.celle_per_pasto_consecutivo.isEmpty())
//				   this.print(this.celle_per_pasto_consecutivo, "celle per sta minchia1");
				
				if(this.isDama && !this.weContinueOnlyDama())
				{
					System.out.println("ERR isDama");
					
//					this.print(this.celle_per_pasto_consecutivo, "celle per sta minchia3");
					this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
					this.isDama = false;
					return true;
				}
				else
				{
//					this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
					return true;
				}
				
				
//				if(!this.isDama)
				
				//rivedere, perché la pedina potrebbe anche diventare dama e continuare sotto, ma se ritorno true, è sbagliato in quel caso
				
			}
			//quì ci entrerà solo se my_pawn è già una dama, oppure se lo è diventata in "this.weContinueDamaENon()"
			else if(my_pawn.isDama() && !this.weContinueOnlyDama() && !this.opponent_cells.isEmpty())
			{
				this.print(this.celle_per_pasto_consecutivo, "celle per sta minchia4");
//				this.celle_per_pasto_consecutivo.add(new Cell(my_pawn.getI(),my_pawn.getJ(),"")); 
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
//		this.togliDoppioni(this.celle_per_pasto_consecutivo, this.opponent_cells);
		
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
				
//			    this.print(celle_per_pasto_consecutivo, "AI CELLE PER PASTO CONSECUTIVE");
//				this.print(this.opponent_cells, "AI OPPONENT CELLS");
			    this.weContinueDamaENon(); //se si può andare avanti, si va avanti
			}else
				System.out.println("weContinueDamaENon 3");
			
		  }else
				System.out.println("weContinueDamaENon 2");
		}else
			System.out.println("weContinueDamaENon 1");
			
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
					 
//				     this.print(celle_per_pasto_consecutivo, "AI CELLE PER PASTO CONSECUTIVE");
//					this.print(this.opponent_cells, "AI OPPONENT CELLS");
				    this.weContinueDamaENon(); //se si può andare avanti, si va avanti
				  }else
						System.out.println("weContinueDamaENon 6");
		        }else
					System.out.println("weContinueDamaENon 5");
			  }else
					System.out.println("weContinueDamaENon 4");
		  
		  //bisogna fare un ulteriore controllo per verificare che non ci siano pedine in alto a destra e/0 a sinistra per 
		  //se la pedina in questione è una dama
		  
//		  if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI(), 
//				  this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()))
//              {
//		  
//		        if(this.isDama || this.game.getDama().getPawnAtPosition(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI(), 
//				      this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()).isDama())
//		         {
//			       if(!this.weContinueOnlyDama())
//				       return false;
//		         }
//              }
		  
		  
			return false;
	}
	
	protected boolean weContinueOnlyDama()
	{
//		if(!this.celle_per_pasto_consecutivo.isEmpty() && !this.opponent_cells.isEmpty())
//		{
//		this.togliDoppioni(this.celle_per_pasto_consecutivo);
//		this.togliDoppioni(this.opponent_cells);
//		}
		
//		this.print(this.celle_per_pasto_consecutivo, "celle per sta minchia2");
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
//					    this.print(celle_per_pasto_consecutivo, "AI DAMA CELLE PER PASTO CONSECUTIVE");
//						this.print(this.opponent_cells, "AI DAMA OPPONENT CELLS");
					    this.weContinueOnlyDama(); //se si può andare avanti, si va avanti
					}else
						System.out.println("ERR dama sinistra 3");
			  }else
					System.out.println("ERR dama sinistra 2");
		}
		else
			System.out.println("ERR dama sinistra 1");
		
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1 >= 0 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1)
		{
			//se c'è una pedina avversaria una cella più in alto a destra
			  if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) &&
					  this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) != this.colour)
			  {
				  System.out.println("sopra l'if dell'else 'dama destra 3. coord cella da mangiare <"+(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1)+","+
						  (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1)+">");
				  if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2 >= 0 &&
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
							!this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2))
					{
						this.opponent_cells.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1,""));
						this.celle_per_pasto_consecutivo.add(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2,""));
//					    this.print(celle_per_pasto_consecutivo, "AI DAMA CELLE PER PASTO CONSECUTIVE");
//						this.print(this.opponent_cells, "AI DAMA OPPONENT CELLS");
						 this.weContinueOnlyDama(); //se si può andare avanti, si va avanti
					}else
						System.out.println("ERR dama destra 3");
			  }else
					System.out.println("ERR dama destra 2");
		}else
			System.out.println("ERR dama destra 1");
		
		
		//modificare da quà..
//		this.cella_per_pasto_consecutivo_temp = this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1);
//	    this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
//	    this.celle_per_pasto_consecutivo.add(this.cella_per_pasto_consecutivo_temp);
	    
		
//		if(this.weContinueDamaENon())
//		{
//			System.out.println("ERR dama sotto sotto x");
//			this.cella_per_pasto_consecutivo_temp = this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1);
//		    this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
//		    this.celle_per_pasto_consecutivo.add(this.cella_per_pasto_consecutivo_temp);
//			this.weContinueOnlyDama();
//		}
		
		if(!this.weContinueDamaENon())
		{
			System.out.println("ERR niente da fare");
			return false;
		}
//		else
//		{
//			System.out.println("ERR dama sotto sotto x");
//			this.cella_per_pasto_consecutivo_temp = this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1);
//		    this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
//		    this.celle_per_pasto_consecutivo.add(this.cella_per_pasto_consecutivo_temp);
//		    this.moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.opponent_cells);
//			
//		    if()
//		    this.weContinueOnlyDama();
//		}
		//a quà. Rivedere!
		
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
				System.out.println("l'ai ha perso 1");
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
	    	  System.out.println("ERR more advanced intelligence move è false");
	    	  if(!this.simpleIntelligenceMove())
	    	  {
	    		  System.out.println("l'ai ha perso 2");
	    		  this.game.setEND_GAME(true,true);
	    	  }
	    	
	      }
	      else
	    	  System.out.println("ERR more advanced intelligence move è true");
		  return;
		}
		
		//se è stata scelta la superior intelligence
		else if(this.type_of_intelligence.equals(StaticVariables.HARD_AI_NAME))
		{
		  if(!this.superiorIntelligenceMove())
		  {
			  if(!this.mediumMove())
		      {
		    	  System.out.println("ERR more advanced intelligence move è false");
		    	  if(!this.simpleIntelligenceMove())
		    	  {
		    		  System.out.println("l'ai ha perso 3");
		    		  this.game.setEND_GAME(true,true);
		    	  }
		      }
		      else
		      	  System.out.println("ERR more advanced intelligence move è true");
		  }
		  else
			  System.out.println("ERR superior intelligence move è true");
		}
			
	}
	
	private int getRandom()
	{
		return (random.nextInt(12)+13);
	}
	
	private boolean mediumMove()
	{
		System.out.println("MEDIUM INTELLIGENCE");
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
				System.out.println("ERR MORE ADVANCED INTELLIGENCE random: "+ x);
					
			if(this.pawns.get(x).getI()-1 >= 0 && this.pawns.get(x).getI()-2 >= 0 && this.pawns.get(x).getI()-1 <= StaticVariables.RIGHE_COLONNE-1 && this.pawns.get(x).isDama())
			{
				if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()-1) )
				{
					//se due celle in avanti(o dietro.. è un esempio), ci sono pedine non avversarie, muovi, altrimenti esco da questa funzuione e richiamo la simple Intelligence
					if(this.pawns.get(x).getJ()-2 >= 0 && ((this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()-2) 
						&& this.game.getDama().getPawnColour(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()-2) == this.colour )
							|| !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-2, this.pawns.get(x).getJ()-2)))
                     {
					  this.utilita(x, -1, -1, "MIAO3");
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
					  this.utilita(x, -1, +1, "MIAO4");
					  return true;
					 }
				}
			}
			
			System.out.println("AI PLAYER. my_pawn: "+this.pawns.get(x).getId()+" cont dame: "+conta_dame_ai);
			//verifico le coordinate di my_pawn siano ammissibili
			if(this.pawns.get(x).getI()+1 >= 0 && this.pawns.get(x).getI()+2 <= StaticVariables.RIGHE_COLONNE-1 && this.pawns.get(x).getI()+1 <= StaticVariables.RIGHE_COLONNE-1 )
			{
				System.out.println("ela ela. move dama: "+this.move_dama+" cont dame: "+conta_dame_ai);
				//si deve andare avanti o se la pedina è una dama che deve andare avanti, oppure se la pedina non è una dama.
			   if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()-1))
			    {
				 //se due celle in avanti(o dietro.. è un esempio), ci sono pedine non avversarie, muovi, altrimenti esco da questa funzuione e richiamo la simple Intelligence
				   if(this.pawns.get(x).getJ()-2 >= 0 && ((this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()-2)
						   && this.game.getDama().getPawnColour(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()-2) == this.colour)
						   || !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+2, this.pawns.get(x).getJ()-2)))
				   {
				    this.utilita(x, +1, -1, "MIAO1"); 
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
				    this.utilita(x, +1, +1, "MIAO2");
				    return true;
				   }
		    	}
			}
			cont++;
			if(cont >= 20)
				return false;
			x = this.getRandom();
		}//fine while
		 
			System.out.println("ai pedina "+this.pawns.get(x).getId()+" i,j: <"+this.pawns.get(x).getI()+","+this.pawns.get(x).getJ()+">");
			return false; 
		}
	private boolean simpleIntelligenceMove()
	{
		//se non c'è nessuna pedina avversaria da mangiare, muovo la prima che davanti non ha pedine
				
		System.out.println("SIMPLE INTELLIGENCE");
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
						System.out.println("ERR SIMPLE INTELLIGENCE random: "+x);
							
					if(this.pawns.get(x).getI()-1 >= 0 && this.pawns.get(x).getI()-1 <= StaticVariables.RIGHE_COLONNE-1 && this.pawns.get(x).isDama() && this.pawns.get(x).getI()-2 >= 0)
					{
						System.out.println("ola ola. "+" cont dame: "+conta_dame_ai);
						//nella seconda riga dell'if, effettuo un'ottimizzazione dell'algoritmo per renderlo più intelligente
						if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()-1))
						{
							this.utilita(x, -1, -1, "MIAO3");
							return true;
						}
						 if(this.pawns.get(x).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()-1, this.pawns.get(x).getJ()+1))
						{
							this.utilita(x, -1, +1, "MIAO4");
							return true;
						}
					}
					
					System.out.println("AI PLAYER. my_pawn: "+this.pawns.get(x).getId()+" cont dame: "+conta_dame_ai);
					//verifico le coordinate di my_pawn siano ammissibili
					if(this.pawns.get(x).getI()+1 >= 0 && this.pawns.get(x).getI()+1 <= StaticVariables.RIGHE_COLONNE-1)
					{
						System.out.println("ela ela. move dama: "+this.move_dama+" cont dame: "+conta_dame_ai);
						//si deve andare avanti o se la pedina è una dama che deve andare avanti, oppure se la pedina non è una dama.
					   if(this.pawns.get(x).getJ()-1 >= 0 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()-1))
					    {
						   this.utilita(x, +1, -1, "MIAO1"); 
						   return true;
					    }
					   if(this.pawns.get(x).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 && !this.game.getDama().isThereaPawn(this.pawns.get(x).getI()+1, this.pawns.get(x).getJ()+1))
					    {
						    this.utilita(x, +1, +1, "MIAO2");
						    return true;
				    	}
					}
					cont++;
					if(cont >= 20)
						return false;
					
					x = this.getRandom();
				}//fine while
				
					System.out.println("ai pedina "+this.pawns.get(x).getId()+" i,j: <"+this.pawns.get(x).getI()+","+this.pawns.get(x).getJ()+">");
				return false;	 
	}
	
	
	
	private boolean superiorIntelligenceMove()
	{
		System.out.println("SUPERIOR INTELLIGENCE");
		int x = this.getNum_pawns();
		if(x >= 10)
			return true;
		return false;
	}
	
	private void utilita(int x, int varI, int varJ, String commento)
	{
		System.out.println(commento);
	    this.pawnFirstTomove = this.pawns.get(x);
	    this.passaDaPawnFirstMoveToPawnAfterMove(this.pawns.get(x).getI(), this.pawns.get(x).getJ(), this.pawns.get(x).getI()+varI, this.pawns.get(x).getJ()+varJ, null);
//	    this.move(this.pawns.get(x).getI(), this.pawns.get(x).getJ(), this.pawns.get(x).getI()+varI, this.pawns.get(x).getJ()+varJ, null);
	}
	
	
	@Override
	public void eatOpponentPawn(Pawn p)
	{
		System.out.println("ERR mangiamo la pedina "+p.getId()+" dello user player");
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
//		System.out.println("move");
//		this.pawnAfterMove = this.pawnFirstTomove;
		
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
//		    this.game.eatPawn(this.pawnToEat);
			System.out.println("prima di mangiare");
			this.game.getDama().printMatrix();
			this.eatOpponentPawn(this.pawnToEat);
			System.out.println("dopo aver aver mangiato");
			this.game.getDama().printMatrix();
			
			
			
			if(this.verifyEndGame())
			{
				this.game.getPlay_panel().repaint();
//				this.game.getMenu().dispose();
				//deve uscire la schermata di win o perso
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
