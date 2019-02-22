package Utilities;

import java.util.ArrayList;
import java.util.List;

import core.Cell;
import core.Game;
import core.Pawn;
import interfaces.StaticVariables;

public class FindPreferredPath extends Backtracking{

	private List<Cell> coordinate;
	private int steps_number;
	private Game game;
	private Pawn pawn; //questa è la pawn su cui applico la backtracking
	private Cell current_cell;
	private String direzione;
	
	public FindPreferredPath(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.coordinate = new ArrayList<Cell>();
		this.steps_number = 0;
		this.pawn = null;
		this.current_cell = new Cell(0,0,"x");
		this.direzione = "sinistra"; //si parte visitando il nodo sinistro
	}
	
	public void init(Pawn pawn)
	{
		this.pawn = pawn;
		this.steps_number = 3;
		//il valore minimo è la i della pawn che gli passo e il max val è l'ultima riga della matrice
		this.setMinMaxVal(pawn.getI()+1, StaticVariables.RIGHE_COLONNE-1);
	}
	
	@Override
	boolean canAdd(int x) {
		// TODO Auto-generated method stub
		
//		this.pawn.setIJ(this.current_cell.getI(), this.current_cell.getJ()); 
		
		//se davanti a sinistra non ci sono pedine, posso aggiungere
		if(this.coordinate.size() < this.steps_number)
		{
		  if(this.pawn.getJ()-1 > 0 && !this.game.getDama().isThereaPawn(x, this.pawn.getJ()-1) && this.direzione == "sinistra")
		  {
			  System.out.println("step 1: <"+x+","+(this.pawn.getJ()-1)+">");
			  this.current_cell.setIJDirezione(x, this.pawn.getJ()-1,"sinistra");
			  return true;
		  }
		   if(this.pawn.getJ()+1 < StaticVariables.RIGHE_COLONNE && !this.game.getDama().isThereaPawn(x, this.pawn.getJ()+1) && this.direzione == "destra")
		  {
			  System.out.println("step 2: "+x+","+(this.pawn.getJ()+1)+">");
			  this.current_cell.setIJDirezione(x, this.pawn.getJ()+1,"destra");
			  return true;
		  }
		}
		return false;
	}

	@Override
	boolean isComplete() {
		// TODO Auto-generated method stub
		//se la size è == steps_number
		if(this.coordinate.size() == this.steps_number)
		{
			//se davanti a sinistra c'è una pedina avversaria
			if(this.game.getDama().isThereaPawn(this.pawn.getI()+1, this.pawn.getJ()-1) && this.game.getDama().getCellColour(this.pawn.getI()+1, this.pawn.getJ()-1) != this.pawn.getColour()) 
			{
				System.out.println("is complete 1");
				this.steps_number--;
				return false;
			}
			//se davanti a destra c'è una pedina avversaria
			else if(this.pawn.getI()+1 < StaticVariables.RIGHE_COLONNE && this.game.getDama().isThereaPawn(this.pawn.getI()+1, this.pawn.getJ()+1) && this.game.getDama().getCellColour(this.pawn.getI()+1, this.pawn.getJ()+1) != this.pawn.getColour()) 
			{
				System.out.println("is complete 2");
				this.steps_number--;
				return false;
			}
			//ritorna true se questi due casi non si verificano
				return true;
		}
		
			return false;
	}

	@Override
	void add(int x) {
		// TODO Auto-generated method stub
		System.out.println("add: <"+this.current_cell.getI()+","+this.current_cell.getJ()+">");
		this.coordinate.add(this.current_cell);
	}

	/**
	 * Se ho appena visitato un nodo di destra da eliminare, lo elimino e setto che ora la direzione da visitare, è la sinistra.
	 */
	@Override
	void remove() {
		// TODO Auto-generated method stub
		if(this.current_cell.getDirezione() == "destra")
			this.direzione = "sinistra";
		else
			this.direzione = "destra";
		
		System.out.println("remove");
		this.coordinate.remove(this.coordinate.size()-1);
		
	}
	
	public void print()
	{
		for(Cell c: this.coordinate)
			System.out.print(" <"+c.getI()+","+c.getJ()+"> ");
	}
	
	public void svuota()
	{
		this.coordinate.clear();
	}

}
