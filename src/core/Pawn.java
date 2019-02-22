package core;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("pawn")
public class Pawn {
	
	@Param(0)
	private int id;
	@Param(1)
	private int i;
	@Param(2)
	private int j;
	@Param(3)
	private String colour;
	private boolean dama; //indica se questa pedina è una dama o meno
	private boolean avanti_dama; //la dama dell'ai, inizialmente va indietro(sale)
	
	public Pawn(int id,int i, int j, String c)
	{
		this.id = id;
		this.i = i;
		this.j = j;
		this.colour = c;
		this.dama = false;
		this.avanti_dama = false;
	}
	
	public void setIJ(int i, int j)
	{
		this.i = i;
		this.j = j;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isDama() {
		return dama;
	}

	public void setDama(boolean dama) {
		this.dama = dama;
	}

	public boolean isAvanti_dama() {
		return avanti_dama;
	}

	public void setAvanti_dama(boolean avanti_dama) {
		this.avanti_dama = avanti_dama;
	}

	
	
}
