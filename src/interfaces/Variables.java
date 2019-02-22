package interfaces;

public class Variables {

	public static boolean endGame = false;
	public static int BUTTONS_HEIGHT = 0;
	public static boolean inizializeButtonsHeight = false;
	public static boolean editor = false;
	public static int w_pawn = 0;
	
	//DEFAULT
	public static int ID_PEDINA1 = 2; 
	public static int ID_PEDINA2 = 3;
	public static int ID_TAVOLO = 6;
	
	//DEFAULT
	public static String PATH_PEDINA1 = "";
	public static String PATH_DAMA1 = "";
	public static String PATH_PEDINA2 = "";
	public static String PATH_DAMA2 = "";
	public static String PATH_TAVOLO = "";
	
	//quando c'è da far vedere solo gli aggiornamenti dell'avversario, in caso di multiplayer, è true
	public static boolean update = false;
	
	public static boolean single_player = true;
	
	//è true quando una pedina deve mangiare
	public static boolean giocatore1_mangio = false;
	public static boolean giocatore2_mangio = false;
	
	//se è il mio turno di muovere, me lo dirà il server la prima volta, nel caso del multiplayer
	//dopo verrà settata a false ogni qual volta muoverò.
	//di default è true per la modalità single player
	public static boolean canMove = true;
	
	public static boolean mangiata_multipla = false;
	
	public static boolean editor_button_enable = true; //è abilitato di default
	
}
