package Utilities;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import core.AIPlayer;
import core.Cell;
import core.Game;
import core.Player;

//Serve solo per le modifiche di mangiate multiple dell'avversario
public class MultipleMovementMultiplayer extends Thread{
	
	private Game game;
	private ArrayList<Cell> clicked_cells, opponent_cells;
	private Lock lock;
	private Player player;
	
	public void settaTuttoMultiple(Game game, Lock l, ArrayList<Cell> clicked_cells, ArrayList<Cell> opponent_cells, Player player)
	{
		this.clicked_cells = clicked_cells;
		this.opponent_cells = opponent_cells;
		this.game = game;
		this.lock = l;
		this.player = player;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		this.lock.lock();
//		Variables.giocatore1_mangio = true;
		this.player.togliDoppioni(clicked_cells, opponent_cells);
		
		//dev'essere mandato solo se siamo in modalità multiplayer e non dobbiamo aggiornare il gioco dopo 
		//i movimenti dell'avversario.
		
		System.out.println("DENTRO");
		while(clicked_cells.size() > 1)
		{
			System.out.println("cia");
//			this.hasArrayAnElement(clicked_cells, ai_cells);
			//la iprec e la jprec saranno SEMPRE le coordinate della cella alla posizione 0 dell'array 'clicked cells'
			//la iafter e la jafter saranno SEMPRE le coordinate della cella alla posizione 1 dell'array 'clicked cells'
			//la pawn to eat avrà SEMPRE le coordinate della Cell alla posizione 0 dell'array 'ai_cells' 
			if(!opponent_cells.isEmpty())
				this.player.setPawnToEat(this.game.getDama().getPawnAtPosition(opponent_cells.get(0).getI(), opponent_cells.get(0).getJ()));
			
			if(!clicked_cells.isEmpty())
			//la pawn first to move = alla pawn con coordinate della Cell in posizione 0 dell'array 'clicked cells'
				this.player.setPawnFirstTomove(this.game.getDama().getPawnAtPosition(clicked_cells.get(0).getI(), clicked_cells.get(0).getJ()));
			//la pawn aftermove = pawn after to move
			this.player.setPawnAfterMove(this.player.getPawnFirstTomove());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				((AIPlayer)this.game.getAi_player()).passaDaPawnFirstMoveToPawnAfterMoveAI(clicked_cells.get(0).getI(), clicked_cells.get(0).getJ(), 
						clicked_cells.get(1).getI(), clicked_cells.get(1).getJ(), this.player.getPawnToEat());
			
			//elimino l'elemento alla posizione 0 dell'array 'clicked cells'
			//elimino l'elemento alla posizione 0 dell'array 'ai cells'
//			this.player.print(clicked_cells, opponent_cells);
			
			clicked_cells.remove(0);
			if(!opponent_cells.isEmpty())
			opponent_cells.remove(0);
			
			
		}
		
		clicked_cells.clear();
		opponent_cells.clear();
		
		this.lock.unlock();
		
	}

}
