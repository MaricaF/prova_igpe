package core;

import it.unical.mat.embasp.languages.Id;

@Id("usercolour")
public class UserColour extends Colour{
	
	public UserColour(int idPlayer, String colour)
	{
		this.colour = colour;
		this.idPlayer = idPlayer;
	}

}
