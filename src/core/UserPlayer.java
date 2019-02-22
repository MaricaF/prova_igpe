package core;

import javax.swing.JOptionPane;

import gui.MyPlayPanel;
import interfaces.StaticVariables;
import interfaces.Variables;
import network.Client;

public class UserPlayer extends Player{
	
	private Client client;
	
	public UserPlayer(Game game) 
	{
		super(game);
		this.id_user = 1;
		this.opponent_player = this.game.getAi_player();
	}
	
	@Override
	public void eatOpponentPawn(Pawn p)
	{
		System.err.println("mangiamo la pedina "+p.getId()+" dell'ai player");
		//rimuovo la pedina dalla struttura dati dell'avversario
		this.game.getAi_player().getPawns().remove(p.getId());
		//richiamo la funzione che mi elimina questa stessa pedina dalle altre strutture dati
		this.game.eatPawn(p);
	}
	
	//verifica se questa pedina si può muovere e in caso positivo, si muove
	@Override
		public void movePawn(Pawn pawnFirstTomove, int iprec, int jprec, int iafter, int jafter)
		{
		Variables.giocatore1_mangio = false;
			this.pawnFirstTomove = pawnFirstTomove;
			System.out.println("move pawn");
			
			//se c sono pedine da mangiare ma la posizione csu cui vorrei spostare la mia pedina
			//non è uguale a nessuna delle posizioni valide per mangiare la pedina avversaria, viene lanciato un alert.
			if(this.is_obligatory_eaten())
			{
				if(!this.isMyPositionValid(iafter,jafter))
				{
				 JOptionPane.showMessageDialog(this.game.getPlay_panel(), "devi mangiare obbligatoriamente");
				 return;
				}
			}

				
			
			//manca il caso della dama
			
			if(((iafter == iprec-1 && jafter == jprec-1) || (iafter == iprec-2 && jafter == jprec-2) || (iafter == iprec-1 && jafter == jprec+1) || (iafter == iprec-2 && jafter == jprec+2)) 
					|| (((iafter == iprec+1 && jafter == jprec-1) || (iafter == iprec+2 && jafter == jprec-2) || (iafter == iprec+1 && jafter == jprec+1) || (iafter == iprec+2 && jafter == jprec+2)) && this.pawnFirstTomove.isDama()))
			{
				
//				System.out.println("in move pawn. iprec,jprec: <"+iprec+","+jprec+">. iafter,jafter: <"+iafter+","+jafter+">.");
			
			//queste condizioni valgono se la pedina non è una dama
			
			
			  if(this.pawnFirstTomove.isDama())
				System.err.println("UNA DAMA");
			
			//sedavanti a sinistra o a destra non ha pedine, oppure se le ha e sono del colore opposto e ditre quella pedina non ce ne sono. Se jprec-jafter < 0, signica che vorrei muovere la pedina a destra, altrimenti a sinistra
			  if((!this.game.getDama().isThereaPawn(iafter, jafter) && Math.abs(iprec-iafter) == 1 && Math.abs(jprec-jafter) == 1))
			  {
				System.out.println("davanti non ci sono pedine. Muovo1.");
				this.passaDaPawnFirstMoveToPawnAfterMove(iprec, jprec, iafter, jafter,null);
			  }
			  else if(!this.game.getDama().isThereaPawn(iafter, jafter) && iprec-iafter > 0)
			  {
				  System.out.println("davanti non ci sono pedine. Muovo2.");
				  //ho spostato da sinistra a destra per mangiare la pedina al centro
				  if((jprec-jafter < 0 && this.game.getDama().isThereaPawn(iafter+1, jafter-1) && this.game.getDama().getPawnColour(iafter+1, jafter-1) != this.colour))
				  {
					  System.out.println("sto cazzo1");
					  if(jafter-1 >= 0 && jafter-1 <= StaticVariables.RIGHE_COLONNE-1 && iafter+1 <= StaticVariables.RIGHE_COLONNE-1 && iafter+1 >= 0)
					  { 
						  this.pawnToEat = this.game.getDama().getPawnAtPosition(iafter+1, jafter-1); 
					      System.err.println("Muovo2a. Ho mosso da sinistra a destra per mangiare la pedina al centro, ovvero la "+this.pawnToEat.getId());
					      this.passaDaPawnFirstMoveToPawnAfterMove(iprec, jprec, iafter, jafter,this.pawnToEat);
					  }
				  }
				  //ho spostato da destra a sinistra per mangiare la pedina al centro
				  else if((jprec-jafter > 0 && this.game.getDama().isThereaPawn(iafter+1, jafter+1) && this.game.getDama().getPawnColour(iafter+1, jafter+1) != this.colour))
				  {
					  System.out.println("sto cazzo2");
					  if(jafter+1 >= 0 && jafter+1 <= StaticVariables.RIGHE_COLONNE-1 && iafter+1 <= StaticVariables.RIGHE_COLONNE-1 && iafter+1 >= 0)
					  {
						  this.pawnToEat = this.game.getDama().getPawnAtPosition(iafter+1, jafter+1);
						  System.out.println("Muovo2b. Ho mosso da destra a sinistra per mangiare la pedina al centro, ovvero la "+this.pawnToEat.getId());
						  this.passaDaPawnFirstMoveToPawnAfterMove(iprec, jprec, iafter, jafter,this.pawnToEat);
					  }
					 
				  }
					  
//				  this.move(iprec, jprec, iafter, jafter,this.pawnToEat);
			  }
			  else if(this.pawnFirstTomove.isDama() && !this.game.getDama().isThereaPawn(iafter, jafter) && iprec-iafter < 0  && ((jprec-jafter < 0 && this.game.getDama().isThereaPawn(iafter-1, jafter-1) && this.game.getDama().getPawnColour(iafter-1, jafter-1) != this.colour) || (jprec-jafter > 0 && this.game.getDama().isThereaPawn(iafter-1, jafter+1) && this.game.getDama().getPawnColour(iafter-1, jafter+1) != this.colour) ))
			  {
				System.err.println("davanti non ci sono pedine. Muovo3.");
				
				if(jafter-1 >= 0 && iafter-1 >= 0 && this.game.getDama().isThereaPawn(iafter-1, jafter-1))
					this.pawnToEat = this.game.getDama().getPawnAtPosition(iafter-1, jafter-1);
				else if(jafter+1 <= StaticVariables.RIGHE_COLONNE-1 && iafter-1 >= 0 && this.game.getDama().isThereaPawn(iafter-1, jafter+1))
					this.pawnToEat = this.game.getDama().getPawnAtPosition(iafter-1, jafter+1);
				
				this.passaDaPawnFirstMoveToPawnAfterMove(iprec, jprec, iafter, jafter,this.pawnToEat);
			  }
		  }
			
			System.out.println("QUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
			this.pawnFirstTomove = null;
			this.pawnAfterMove = null;
			
		}
	
	/**
	 *Se ci sono pedine da mangiare, la lenght dell'array valid... sarà > 0, e perciò torno true, altrimenti false
	 * @param pawnFirstTomove
	 * @param iprec
	 * @param jprec
	 * @param iafter
	 * @param jafter
	 * @return
	 */
	private boolean is_obligatory_eaten()
	{
		for(Pawn my_pawn: this.pawns.values())
		{
			if(my_pawn.getI()-2 >= 0 && my_pawn.getJ()-2 >= 0 && this.game.getDama().isThereaPawn(my_pawn.getI()-1, my_pawn.getJ()-1) && !this.game.getDama().isThereaPawn(my_pawn.getI()-2, my_pawn.getJ()-2) && this.game.getDama().getPawnColour(my_pawn.getI()-1, my_pawn.getJ()-1) != this.colour)
			{
				this.valid_mycells_to_eat.add(new Cell(my_pawn.getI()-2,my_pawn.getJ()-2,"null"));
				this.pedine_che_devono_mangiare.add(my_pawn);
			}
			if(my_pawn.getI()-2 >= 0 && my_pawn.getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 && this.game.getDama().isThereaPawn(my_pawn.getI()-1, my_pawn.getJ()+1) && !this.game.getDama().isThereaPawn(my_pawn.getI()-2, my_pawn.getJ()+2) && this.game.getDama().getPawnColour(my_pawn.getI()-1, my_pawn.getJ()+1) != this.colour)
			{
				this.valid_mycells_to_eat.add(new Cell(my_pawn.getI()-2,my_pawn.getJ()+2,"null"));
				this.pedine_che_devono_mangiare.add(my_pawn);
			}
		}
		
		if(this.valid_mycells_to_eat.isEmpty())
			return false;
		
		System.out.println("print cells: ");
		for(Cell c: this.valid_mycells_to_eat)
			System.out.print("<"+c.getI()+","+c.getJ()+"> ");
		
		return true;
			
	}
	/**
	 * Se le coordinate rispettano determinate condizioni, poi si potrà procedere ad inserire quelle coordinate nell'array 'celle...ecc'
	 * @param i
	 * @param j
	 * @return
	 */
	@Override
	public boolean controllaValiditaCoord(int i, int j)
	{
		if(this.areThereSameCells(i, j))
		{
			((MyPlayPanel)this.game.getPlay_panel()).setFirst_click(true);
			 this.celle_per_pasto_consecutivo.clear();
			 this.opponent_cells.clear();
		}
			
			//la prima volta va presa la cella con una pedina dello user
		if(((MyPlayPanel)this.game.getPlay_panel()).isFirst_click())
		{
			 this.celle_per_pasto_consecutivo.clear();
			 this.opponent_cells.clear();
		  if(this.game.getDama().isThereaPawn(i, j) && this.game.getDama().getPawnColour(i, j) == this.game.getUser_player().getStringColour())
		  {
			  System.out.println("prima volta che clicco una cella su cui c'è una pedina dello user player");
			  System.out.print("<"+i+","+j+"> ");
			  ((MyPlayPanel)this.game.getPlay_panel()).setFirst_click(false);
		 	return true;
		  }else
			  System.out.println("Q");
		}
		//dopo la prima volta, la cella dev'essere vuota
		else
		{
			//se non ci sono pedine e la i e la j cliccate, differisco di 2 con la i e la j della cella precedentemente cliccata, si può ritenere valida questa posizione cliccata
		 if(!this.game.getDama().isThereaPawn(i, j))
		 {
//			   Math.abs(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-i) == 2 &&
//				 Math.abs(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-j) == 2)
			
				if(Math.abs(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-i) == 2)
				{///
					System.out.println("cella cliccata miao: <"+i+","+j+"> ");
					//sopra
					if(this.comandoSiaPerLeDameCheNon_controllaValiditaCoord(i, j))
						return true;
					
                 //sotto
					if((i-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()) > 0)
					{
						//se la pedina che voglio muovere è una dama
		       if(this.game.getDama().getPawnAtPosition(this.celle_per_pasto_consecutivo.get(0).getI(), this.celle_per_pasto_consecutivo.get(0).getJ()).isDama())
			   {//
		    	 //se la cella cliccata è sotto a destra della cella cliccaa in precendenza
				if(i-1 >= 0 && j-1 >= 0 && (j-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()) > 0 &&
						this.game.getDama().isThereaPawn(i-1, j-1) &&
						this.game.getDama().getPawnColour(i-1, j-1) != this.game.getUser_player().getStringColour())
				{
					this.opponent_cells.add(new Cell(i-1, j-1,""));
					return true;
				}else
					System.out.println("...");
				//se la cella cliccata è sotto a sinistra della cella cliccaa in precendenza
				if(i-1 >= 0 && j+1 <= StaticVariables.RIGHE_COLONNE-1 &&
						(j-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()) < 0 &&
						this.game.getDama().isThereaPawn(i-1, j+1) &&
						this.game.getDama().getPawnColour(i-1, j+1) != this.game.getUser_player().getStringColour())
			    {
					this.opponent_cells.add(new Cell(i-1, j+1,""));
					return true;
			    }else
					System.out.println("---");
				
				if(this.comandoSiaPerLeDameCheNon_controllaValiditaCoord(i, j))
					return true;
				
			  }else
					System.out.println("11");
				}else
						System.out.println("10");
			}else
					System.out.println("3");
		 }else
			 System.out.println("1");
		}
		
		System.out.println("fuori");
		return false;
	}
	
	/**
	 * Verifica se ci sono ancora pedine avversarie da mangiare, oppure se possiamo procedere col muovere la nostra pedina e mangiare le pedine avversarie.
	 * @return
	 */
	@Override
	public boolean weContinue()
	{
		//se il verso di gioco è sopra
		if((this.celle_per_pasto_consecutivo.get(0).getI() -this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()) > 0)
		{
		 if(this.comandoSiaPerLeDameCheNon_weContinue())
		 	   return true;
		}
		//se il verso di gioco è sotto
		else
		{
		
		    //se l'ultima cella dell'array 'celle...' è una dama...
       if(this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(0).getI(),this.celle_per_pasto_consecutivo.get(0).getJ())
		   && this.game.getDama().getPawnAtPosition(this.celle_per_pasto_consecutivo.get(0).getI(),this.celle_per_pasto_consecutivo.get(0).getJ()).isDama())
		{
	      if(this.comandoSoloPerLeDame_weContinue())
	    	  return true;
	      System.err.println("5 comandoSoloPerLeDame_weContinue() FALSE");
		
		}else
			 System.err.println("6 NON è UNA DAMA");
		}
		
		return false;
	}
	
	/**
	 * E' una funzione da utilizzare per gestire i movimentiin alto delle pedine non dame e delle dame(se volessero mangiare 2 pedine, le quali, la prima si trova sotto
	 * e l'altra sopra, in modo tale da formare una 'mangiata a V'.
	 * @param i -> i della cella cliccata
	 * @param j -> j della cella cliccata
	 * @return
	 */
	@Override
	protected boolean comandoSiaPerLeDameCheNon_controllaValiditaCoord(int i, int j)
	{
		if((i-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()) < 0)
		{
		//se la cella cliccata è sopra a destra della cella cliccaa in precendenza
	     if(i+1 <= StaticVariables.RIGHE_COLONNE-1 && j-1 >= 0 && 
	    		 (j-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()) >= 0 &&
	    		 this.game.getDama().isThereaPawn(i+1, j-1) && this.game.getDama().getPawnColour(i+1, j-1) != this.game.getUser_player().getStringColour())
	     {
		     this.opponent_cells.add(new Cell(i+1, j-1,""));
		     return true;
	     }else
				System.out.println("C");
	   //se la cella cliccata è sopra a sinistra della cella cliccaa in precendenza
	    if(i+1 <= StaticVariables.RIGHE_COLONNE-1 && j+1 <= StaticVariables.RIGHE_COLONNE-1 &&
	    		(j-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()) < 0 && this.game.getDama().isThereaPawn(i+1, j+1) && this.game.getDama().getPawnColour(i+1, j+1) != this.game.getUser_player().getStringColour())
	     {
		    this.opponent_cells.add(new Cell(i+1, j+1,""));
		    return true;
	     }else
			System.out.println("B");
	   }else
			System.out.println("A");
		
		return false;
	}
	
	@Override
	protected boolean comandoSiaPerLeDameCheNon_weContinue()
	{
		 //se c'è una pedina avversaria, 1 celle sopra a sinistra della pedina dello user player, si può continuare
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1 >= 0 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1 >= 0 &&
				this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) &&
				this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) != this.game.getUser_player().getStringColour())
		{
		 //se non c'è una pedina, 2 celle sopra a sinistra della pedina dello user player, si può continuare
	       if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2 >= 0 &&
	    		   this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2 >= 0 &&
	    		   !this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2))
	       {
	    	   System.err.println("we continue -1: alto a sinistra");
	    	   return true;
	       }
	       else
	    	   System.err.println("we continue FUORI -1: alto a sinistra");
		}
		 //se c'è una pedina avversaria, 1 celle sopra a destra della pedina dello user player, si può continuare
		 if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1 >= 0 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
				this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) &&
				this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) != this.game.getUser_player().getStringColour())
		{
			 //se non c'è una pedina, 2 celle sopra a destra della pedina dello user player, si può continuare
	       if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2 >= 0 &&
	    		   this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
	    		   !this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2))
	       {
	    	   System.err.println("we continue 0: alto a destra");
	    	   return true;
	       }
	       else
	    	   System.err.println("we continue FUORI 0: alto a destra");
		}
	       return false;
		
	}
	
	@Override
	protected boolean comandoSoloPerLeDame_weContinue()
	{
		 //se una cella più giù a sinistra, c'è una pedina avversaria
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1 >= 0 &&
				this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) &&
				this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-1) != this.game.getUser_player().getStringColour())
		{
	       //se due celle più giù a sinistra dell'ultima pedina dell'array 'celle...' non ci sono pedine 
			  if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
					  this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2 >= 0 &&
					  !this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-2))
			  {
				  System.err.println("we continue 1: giù a sinistra");
				  return true;
			  }
			  else
				  System.err.println("we continue FUORI 1: giù a sinistra");
			  
		}
		 //se una cella più giù a destra, c'è una pedina avversaria
		else if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
				this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1 <= StaticVariables.RIGHE_COLONNE-1 &&
				this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) &&
				this.game.getDama().getPawnColour(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+1, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+1) != this.game.getUser_player().getStringColour())
		{
			  
			  //se due celle più giù a destra, non ci sono pedine
			  if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
					  this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1 &&
					  !this.game.getDama().isThereaPawn(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()+2, this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()+2))
			  {
				  System.err.println("we continue 2: giù a destra");
				  return true;
			  }
			  else
				  System.err.println("we continue FUORI 2: giù a destra");
		}
			  
//			  if(this.comandoSiaPerLeDameCheNon_weContinue())
//				  return true;
			  return false;
	}
	
	/**
	 * Se nell'array 'celle...' ci sono celle uguali, svuotare gli array 'celle...', 'ai pawns' e mettere a true la booleana first right move
	 * @return
	 */
	@Override
	protected boolean areThereSameCells(int i, int j)
	{
		for(Cell c: this.celle_per_pasto_consecutivo)
			if(c.getI() == i && c.getJ() == j)
				return true;
		return false;
	}
	
	/**
	 * E' una funzione che sarà vera se la posizione che ho cliccato è uguale ad una delle posizioni in cui dovrebbe essere per mangiare una pedina; 
	 * altrimenti sarà falsa
	 * @return
	 */
	private boolean isMyPositionValid(int iafter, int jafter)
	{
		for(Cell c: this.valid_mycells_to_eat)
		{
			if(c.getI() == iafter && c.getJ() == jafter)
				return true;
		}
		
		this.changeCellaEvidenziataProperties(true);
		return false;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	

}
