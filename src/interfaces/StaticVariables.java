package interfaces;

import java.awt.Dimension;
import java.awt.Toolkit;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

public interface StaticVariables {
	
	//questo valore dev'essere uguale alla size dell'array pawn_animation di caricatore_immagini.
	final static int pawn_animation = 5;
	
	final static String encodingResource = "encodings/dama";
	final static Handler handler = new DesktopHandler(new DLVDesktopService("lib/dlv.mingw.exe"));
	final static int idFirstPlayer = 1;
	final static int idSecondPlayer = 2;
	
	final static int lenghtMatrix = 8;
	
	final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int screen_width = (int) screenSize.getWidth();
	final static int screen_height = (int) screenSize.getHeight();
	
	final static int finestra_width = (int) screenSize.getWidth();
	final static int finestra_height = (int) screenSize.getHeight();
//	final static int finestra_width = 800;
//	final static int finestra_height = 600;
	
	final static int RIGHE_COLONNE = 8;
	
	final static int ID_PEDINA_BIANCA = 2;
	final static int ID_PEDINA_NERA = 3;
	final static int ID_SFONDO1 = 4;
	final static int ID_TAVOLO_LEGNO = 5;
	final static int ID_TAVOLO = 6;
	final static int ID_PLAYBUTTON = 7;
	final static int ID_PRESSEDPLAYBUTTON = 8;
	final static int ID_TAVOLO_TRASPARENTE = 9;
	final static int ID_EXITBUTTON = 10;
	final static int ID_PRESSEDEXITBUTTON = 11;
	final static int ID_SFONDO2 = 12;
	final static int ID_PEDINA_DAMANERA = 13;
	final static int ID_PEDINA_DAMABIANCA = 14;
	final static int ID_YESBUTTON = 15;	
	final static int ID_PRESSEDYESBUTTON = 16;
	final static int ID_NOBUTTON = 17;	
	final static int ID_PRESSEDNOBUTTON = 18;
	final static int ID_EXITIMAGE = 19;
	final static int ID_MULTIPLAYERBUTTON = 20;
	final static int ID_PRESSEDMULTIPLAYERBUTTON = 21;
	final static int ID_AIBUTTON = 22;
	final static int ID_PRESSEDAIBUTTON = 23;
	final static int ID_FINAL_YES = 24;
	final static int ID_FINAL_NO = 25;
	final static int ID_FUOCHI = 26;
	final static int ID_SFONDOPIOGGIA = 27;
	final static int ID_PIOGGIA = 28;
	final static int ID_PAPER_YOUWON = 29;
	final static int ID_PAPER_YOULOST = 30;
	final static int ID_SIMPLE_AI = 31;
	final static int ID_MEDIUM_AI = 32;
	final static int ID_HARD_AI = 33;
	final static int ID_CELLA_EVIDENZIATA = 34;
	final static int ID_CELLA_PEDINA_CHE_MANGIA = 35;
	final static int ID_RETRY_BUTTON = 36;
	final static int ID_PRESSEDRETRYBUTTON = 37;
	final static int ID_BACK_BUTTON = 38;
	final static int ID_PRESSEBACKBUTTON = 39;
	final static int ID_PEDINA_PREMUTA = 40;
	final static int ID_PEDINA_ROSA = 41;
	final static int ID_PEDINA_BIANCABIANCA = 42;
	final static int ID_PEDINA_MARRONE_SPIRALE = 43;
	final static int ID_PEDINA_BIANCASPIRALE = 44;
	final static int ID_TAVOLO_UNIVERSO = 45;
	final static int ID_TAVOLO_VERDE = 46;
	final static int ID_EDITOR_BUTTON = 47;
	final static int ID_PEDINA_BIANCA_PRESSED = 48;
	final static int ID_PEDINA_NERA_PRESSED = 49;
	final static int ID_PEDINA_ROSA_PRESSED = 50;
	final static int ID_PEDINA_BIANCABIANCA_PRESSED = 51;
	final static int ID_PEDINA_MARRONE_SPIRALE_PRESSED = 52;
	final static int ID_PEDINA_BIANCASPIRALE_PRESSED = 53;
	final static int ID_TAVOLO_UNIVERSO_PRESSED = 54;
	final static int ID_TAVOLO_VERDE_PRESSED = 55;
	final static int ID_EDITOR_BUTTON_PRESSED = 56;
	final static int ID_TAVOLO_PRESSED = 57;
	final static int ID_SAVE_BUTTON = 58;
	final static int ID_SAVE_BUTTONPRESSED = 59;
	final static int ID_AI_PAWN = 60;
	final static int ID_USER_PAWN = 61;
	final static int ID_NASTRO = 62;
	final static int ID_HAVETOEAT = 63;
	final static int ID_CHOOSEACHESSBOARD = 64;
	final static int ID_CHOOSEYOURPAWN = 65;
	final static int ID_CHOOSEAIPAWN = 66;
	final static int ID_COMEBACK = 67;
	final static int ID_OK = 68;
	
	
	
	final static String PATH_SFONDO1 = "Images/sfondo_travi.jpg";
	final static String PATH_EXITIMAGE = "Images/areyousure.png";
	final static String PATH_TAVOLO = "Images/tavolo222.png";
	final static String PATH_TAVOLO_PRESSED = "Images/tavolo222pressed.png";
	final static String PATH_TAVOLO_LEGNO = "Images/legno2.png";
	final static String PATH_TAVOLO_TRASPARENTE = "Images/tavolo3.png";
	final static String PATH_PEDINA_BIANCA = "Images/pedina_bianca22.png";
	final static String PATH_PEDINA_BIANCA_PRESSED = "Images/pedina_biancastandardpressed.png";
	final static String PATH_PEDINA_NERA = "Images/pedina_nera.png";
	final static String PATH_PEDINA_NERA_PRESSED = "Images/pedina_nerapressed.png";
	final static String PATH_PEDINA_DAMANERA = "Images/pedinadamanera2.png";
	final static String PATH_PEDINA_DAMABIANCA = "Images/pedinadamabianca2.png";
	final static String PATH_FUOCHI = "Images/fuochi.png";
	final static String PATH_SFONDOPIOGGIA = "Images/sfondopioggia.jpg";
	final static String PATH_PIOGGIA = "Images/rain.png";
	final static String PATH_PAPER_YOUWON = "Images/youwon.png";
	final static String PATH_PAPER_YOULOST = "Images/youlost.png";
	final static String PATH_CELLA_EVIDENZIATA = "Images/cerchio.png";
	final static String PATH_CELLA_PEDINA_CHE_MANGIA  = "Images/cella_evidenziata.png";
	final static String PATH_PEDINA_PREMUTA  = "Images/pedina_premuta2.png";
	final static String PATH_PEDINA_ROSA = "Images/pedina_rosa.png";
	final static String PATH_PEDINA_ROSA_PRESSED = "Images/pedina_rosapressed.png";
	final static String PATH_PEDINA_ROSA_DAMA = "Images/pedina_rosadama.png";
	final static String PATH_PEDINA_BIANCABIANCA = "Images/pedina_biancadavvero.png";
	final static String PATH_PEDINA_BIANCABIANCA_PRESSED = "Images/pedina_biancadavveropressed.png";
	final static String PATH_PEDINA_BIANCABIANCA_DAMA = "Images/pedina_biancadavverodama.png";
	final static String PATH_PEDINA_MARRONE_SPIRALE = "Images/pedina_marronespirale.png";
	final static String PATH_PEDINA_MARRONE_SPIRALE_PRESSED = "Images/pedinamar.png";
	final static String PATH_PEDINA_MARRONE_SPRIRALE_DAMA = "Images/pedina_marronespirale_d.png";
	final static String PATH_PEDINA_BIANCASPIRALE = "Images/pedina_biancaspirale.png";
	final static String PATH_PEDINA_BIANCASPIRALE_PRESSED = "Images/pedina_biancaspiralepressed.png";
	final static String PATH_PEDINA_BIANCASPIRALE_DAMA = "Images/pedina_biancaspirale_dama.png";
	final static String PATH_TAVOLO_UNIVERSO = "Images/tavolonero.png";
	final static String PATH_TAVOLO_UNIVERSO_PRESSED = "Images/tavoloneropressed.png";
	final static String PATH_TAVOLO_VERDE = "Images/tavolo_verde.png";
	final static String PATH_TAVOLO_VERDE_PRESSED = "Images/tavolo_verdepressed.png";
	final static String PATH_NASTRO = "Images/nastrod.png";
	final static String PATH_HAVETOEAT = "Images/eat.png";
	final static String PATH_CHOOSEACHESSBOARD = "Images/chessboard2.png";
	final static String PATH_CHOOSEYOURPAWN = "Images/chooseyourpawn3.png";
	final static String PATH_CHOOSEAIPAWN = "Images/chooseaipawn2.png";
	final static String PATH_COMEBACK = "Images/come_back.png";
	final static String PATH_SELECT = "Images/select2.png";
	final static String PATH_OK = "Images/ok.png";
	
	
	final static String PATH_PLAYBUTTON = "Images/play_gold.png";
	final static String PATH_PRESSEDPLAYBUTTON = "Images/play_gold.png";
	final static String PATH_EXITBUTTON = "Images/exit_gold.png";
	final static String PATH_PRESSEDEXITBUTTON = "Images/exit_gold.png";
	final static String PATH_YESBUTTON = "Images/yes.png";
	final static String PATH_PRESSEDYESBUTTON = "Images/pressedyes.png";
	final static String PATH_NOBUTTON = "Images/no.png";
	final static String PATH_PRESSEDNOBUTTON = "Images/pressedno.png";
	final static String PATH_MULTIPLAYERBUTTON = "Images/multiplayer_gold.png";
	final static String PATH_PRESSEDMULTIPLAYERBUTTON = "Images/multiplayer_gold.png";
	final static String PATH_AIBUTTON = "Images/ai_gold.png";
	final static String PATH_PRESSEDAI = "Images/ai_gold.png";
	final static String PATH_RETRYBUTTON = "Images/retry.png";
	final static String PATH_PRESSEDRETRYBUTTON = "Images/retry.png";
	final static String PATH_FINAL_YES = "Images/visto.png";
	final static String PATH_FINAL_NO = "Images/x.png";
	final static String PATH_SIMPLE_AI = "Images/simple_ai_gold.png";
	final static String PATH_MEDIUM_AI = "Images/medium_ai_gold.png";
	final static String PATH_HARD_AI = "Images/hard_ai_gold.png";
	final static String PATH_BACKBUTTON = "Images/back.png";
	final static String PATH_SAVE_BUTTON = "Images/save.png";
	final static String PATH_SAVE_BUTTONPRESSED = "Images/save.png";
	final static String PATH_AI_PAWN = "Images/ai_pawn.png";
	final static String PATH_USER_PAWN ="Images/user_pawn.png";
	final static String PATH_EDITOR_BUTTON = "Images/editor22.png";
	
	final static String PATH_AUDIO_ALERT = "audios/alert.wav";
	final static String PATH_AUDIO_PAWN_MOVE = "audios/button12.wav";
	final static String PATH_AUDIO_WIN = "audios/fireworks.wav";
	final static String PATH_AUDIO_GAME_OVER = "audios/rain.wav";
	final static String PATH_AUDIO_EAT = "audios/mangio.wav";
	final static String PATH_AUDIO_MENU_CLICK = "audios/button6.wav";
	
	final static String PLAY_NAME = "Play";
	final static String FINAL_NAME = "Final";
	final static String EXIT_NAME = "Exit";
	final static String MENU_NAME = "Menu";
	final static String YES_NAME = "Yes";
	final static String NO_NAME = "No";
	final static String TYPE_OF_OPPONENT_PANELNAME = "Type_of_opponent_panel";
	final static String MULTIPLAYER_NAME = "Multiplayer";
	final static String AI_NAME = "Ai";
	final static String USER_PAWN_NAME = "User pawn";
	final static String SIMPLE_AI_NAME = "Simple";
	final static String MEDIUM_AI_NAME = "Medium";
	final static String HARD_AI_NAME = "Hard";
	final static String AI_PANEL_NAME = "Ai panel";
	final static String RETRY_NAME = "Retry";
	final static String BACK_NAME = "Back";
	final static String ROSA_NAME = "Pedina rosa";
	final static String BIANCABIANCA_NAME = "Pedina biancabianca";
	final static String MARRONE_NAME = "Pedina marrone";
	final static String BIANCASPIRALE_NAME = "Pedina biancaspirale";
	final static String TAVOLOVERDE_NAME = "Tavolo verde";
	final static String UNIVERSO_NAME = "Tavolo universo";
	final static String TAVOLO_NAME = "Tavolo standerd";
	final static String BIANCASTANDARD_NAME = "Pedina bianca standard";
	final static String NERASTANDARD_NAME = "Pedina nera standard";
    final static String EDITOR_NAME = "Editor";
    final static String SAVE_NAME = "Save";
    final static String OK_NAME = "OK";
}


