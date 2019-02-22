package core;

import interfaces.StaticVariables;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class Dama {
	
	private Cell [][] matrix;
	private Pawn pawn;

	public Dama()
	{
		this.matrix = new Cell [StaticVariables.lenghtMatrix][StaticVariables.lenghtMatrix];
		this.pawn = null;
		//this.createCells();
		//this.printMatrix();
	}
	
	    
    
     public InputProgram createCells() //dlv parte da 1 a lenght, non da 0
    {
    	String c;
    	Cell cell;
    	InputProgram facts= new ASPInputProgram();
    	for(int i = 0; i < StaticVariables.lenghtMatrix; i++)
    	{
    		for(int j = 0; j < StaticVariables.lenghtMatrix; j++)
    		{
    			//se la i è 0 o è pari e la j è 0 o è pari, c = nero
    			if((i == 0 || i%2 == 0) && (j == 0 || j%2 == 0))
    				c = "black";
    			else if((i == 0 || i%2 == 0) && (j%2 != 0))
    				c = "white";
    			else if(i%2 != 0 && j%2 == 0 ) //se la i è dispari e la j pari, c = bianco
    				c = "white";
    			else
    				c = "black";
    				
    			cell = new Cell(i,j,c);
    			this.matrix[i][j] = cell;
    			try {
					facts.addObjectInput(cell); //aggiungo i fatti Cell 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	return facts;
    }
     
     //gli passo la Pedina che voglio spostare, per aggiornare la matrice e la pedina stessa alle posizioni i e j 
     public void setIjPawn(Pawn pawn, int i, int j)
     {
    	 //alla posizione della pedina
    	 this.getCellAtPosition(pawn.getI(), pawn.getJ()).setPawn(null);
    	 pawn.setIJ(i, j);
    	 this.getCellAtPosition(i, j).setPawn(pawn);
     }
     
     //questo metodo deve settare a null la cella da muovere(iprec,jprec) e crearne un'altra nella posizione iafter jafter
     public void createAPawn(int iprec, int jprec, int iafter, int jafter, Pawn pawnAfterMove)
     {
    	 this.matrix[iprec][jprec].setSvuotaCella();
    	 this.matrix[iafter][jafter].setPawn(pawnAfterMove);
     }
    
    public void printMatrixColours()
    {
    	for(int i = 0; i < StaticVariables.lenghtMatrix; i++)
    	{
    		System.out.println();
    		for(int j = 0; j < StaticVariables.lenghtMatrix; j++)
    			System.out.print("<"+i+","+j+","+matrix[i][j].getColour()+"> ");
    	}
    	System.out.println();
    }
    
    public boolean isThereaPawn(int i, int j)
    {
    	if(this.getCellAtPosition(i, j).getPawn() == null)
    		return false;
//    	System.err.println("isThereaPawn. i: "+i+" j: "+j);
    	return true;
    }
    
    //restituisce la cella della posizione i,j
    public Cell getCellAtPosition(int i, int j)
    {
    	return matrix[i][j];
    }
    
    public Pawn getPawnAtPosition(int i, int j)
    {
    	return this.matrix[i][j].getPawn();
    }
    
    
/**
 * E' la funzione che, dati in input due parametri (i e j), ritorna il colore della cella a loro corrispondente
 * @param i -> è l'ascissa della cella
 * @param j -> è l'ordinata della cella
 * @return -> ritorna il colore della cella
 */
    public String getCellColour(int i, int j)
    {
    	return this.matrix[i][j].getColour();
    }
    
    public String getPawnColour(int i, int j)
    {
    	return this.matrix[i][j].getPawn().getColour();
    }
    
    public void printMatrix()
    {
    	for(int i = 0; i < this.matrix.length; i++)
    	{
    		System.out.println();
    		for(int j = 0; j < this.matrix.length; j++)
    		{
    			if(this.matrix[i][j].getPawn() != null)
    				System.out.print(this.matrix[i][j].getPawn().getId()+" ");
    			else
    				System.out.print("0 ");
    		}
    	}
    }
    
}
