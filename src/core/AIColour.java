package core;

import it.unical.mat.embasp.languages.Id;

@Id("aicolour")
public class AIColour extends Colour{
	
	
	public AIColour(int idPlayer, String colour)
	{
		this.colour = colour;
		this.idPlayer = idPlayer;
	}

}
