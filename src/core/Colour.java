package core;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("colour")
public class Colour {
	
	@Param(0)
	protected int idPlayer;
	@Param(1)
	protected String colour;
	
	public Colour() {}
	
	public Colour(String c)
	{
		this.colour = c;
	}

	public String getStringColour() {
		return colour;
	}
	
	public void printString() {
		System.out.println(this.colour);
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
	
	public Colour getColour() 
	{
		return this;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}
	

}
