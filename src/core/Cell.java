package core;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class Cell {
	
	@Param(0)
	private int i;
	@Param(1)
	private int j;
	@Param(2)
	private String colour;
	private Pawn pawn;
	private String direzione;
	
	public Cell(int i, int j, String c)
	{
		this.i = i;
		this.j = j;
		this.colour = c;
		this.pawn = null;
		this.direzione = "";
	}
	
	public void setIJ(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	public void setSvuotaCella()
    {
    	this.pawn = null;
    }


	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public void setIJDirezione(int i, int j, String dir)
	{
		this.setIJ(i, j);
		this.direzione = dir;
	}
	

}
