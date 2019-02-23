package Utilities;

import java.util.concurrent.locks.Lock;

import audio.Sounds;
import core.AIPlayer;
import core.Game;
import core.Pawn;
import core.Player;
import core.UserPlayer;
import gui.MyPlayPanel;
import interfaces.StaticVariables;
import interfaces.Variables;

public class SinglePlayerMovement extends Thread{
	
    private Game game;
    private int tot, unpasso, destraOsinistra, sopraOsotto,iprec, iafter,jprec, jafter;
    private Pawn pawnFirstTomove, pawnAfterMove;
    private Lock lock;
    private int id_user;
    private Player player;
	
    public SinglePlayerMovement(Lock lock) {
		// TODO Auto-generated constructor stub
    	this.lock = lock;
	}
    
	public void settoTutto(Game g, int iprec, int jprec,int iafter, int jafter, Player player)
	{
		this.game = g;
		this.player = player;
		this.unpasso = Variables.w_pawn/StaticVariables.pawn_animation;
		this.pawnFirstTomove = this.player.getPawnFirstTomove();
		this.pawnAfterMove = this.player.getPawnAfterMove();
		this.iafter = iafter;
		this.jafter = jafter;
        this.iprec = iprec;
        this.jprec = jprec;
        this.destraOsinistra = this.player.getDestraOsinistra();
        this.sopraOsotto = this.player.getSopraOsotto();
		
	}
	
	@Override
	public void run()
	{
		try {
		this.lock.lock();
		
		if (this.player instanceof AIPlayer)
			System.out.println("passaDaPawnFirstMoveToPawnAfterMove AI");
		else
			System.out.println("passaDaPawnFirstMoveToPawnAfterMove USER");
		
		Thread.sleep(500);
		this.unpasso = Variables.w_pawn/StaticVariables.pawn_animation;
		this.tot = 0;
		System.out.println("single player movement DENTRO");
		while(tot <= unpasso*StaticVariables.pawn_animation+1)
		{
			this.tot += this.unpasso;
			
			if(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()) != null)
			{
            this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId())
		    .setX(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()).getX()+(unpasso*destraOsinistra));
		   
		    this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId())
		    .setY(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()).getY()+(unpasso*sopraOsotto));
            
				
					Thread.sleep(70);
				
			
		    this.game.getPlay_panel().repaint();
			}
		}
				
		if(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()) != null)
		{
		this.game.getPlay_panel().getProporziona().setSizePawn(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()), iafter, jafter);
		this.game.getPlay_panel().repaint();
		}
		Sounds.getSounds().play(StaticVariables.PATH_AUDIO_PAWN_MOVE);

		System.out.println("single player movement FUORI");
		this.game.getPlay_panel().repaint();
		
		((MyPlayPanel)this.game.getPlay_panel()).setTurno_ai(true);
		
		if (!Variables.single_player)
		{
			if(!Variables.canMove && !Variables.mangiata_multipla && !Variables.update && !Variables.interpostaTraMovMultiploEnon)
			{
				this.player.receiveSemplice();
				System.out.println("SinglePlayerMovement receive CAN MOVE");
				Variables.canMove = true;
			}
			else
				System.out.println("SinglePlayerMovement no attesa. canMove"+Variables.canMove+" update: "+Variables.update+
						" giocator1_mangio: "+Variables.giocatore1_mangio+ " interposta: "+Variables.interpostaTraMovMultiploEnon);
//			Variables.canMove = true;
//			this.moveByRightMouseClick(clicked_cells, opponent_cells);
		}
	
	 ((MyPlayPanel)this.game.getPlay_panel()).setTurno_ai(false);
		
		
			if(this.player.verifyEndGame())
			{
				this.game.getPlay_panel().repaint();
				//deve uscire la schermata di win o perso
				return;
			}
		
		
		this.lock.unlock();
		} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
//	@Override
//	public void run()
//	{
//		this.lock.lock();
//		
//		System.out.println("single player movement DENTRO");
//		while((tot <= unpasso*StaticVariables.pawn_animation+1) && Variables.concurrentModRepaint)
//		{
////			System.out.println("getId: "+this.pawnFirstTomove.getId());
////			System.out.println(" RUN tot: "+tot+" x imm: "+ this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()).getX()+
////            		 " y imm: "+ this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()).getY());
////			System.out.println("tottiamo. tot: "+this.tot);
//			tot += unpasso;
//			
//			if(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()) != null)
//			{
//            this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId())
//		    .setX(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()).getX()+(unpasso*destraOsinistra));
//		   
//		    this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId())
//		    .setY(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()).getY()+(unpasso*sopraOsotto));
//            
//		    
////		    this.game.getPlay_panel().repaint();
//		    try {
//				Thread.sleep(30);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    this.game.getPlay_panel().repaint();
//			}
//		}
//		
//		if(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()) != null)
//		{
//		this.game.getPlay_panel().getProporziona().setSizePawn(this.game.getPlay_panel().getCaricatore_immagini().fromIdtoPawnImmagine(this.pawnFirstTomove.getId()), iafter, jafter);
//		this.game.getPlay_panel().repaint();
//		}
//		Sounds.getSounds().play(StaticVariables.PATH_AUDIO_PAWN_MOVE);
//		System.out.println("single player movement FUORI");
//		
//		this.lock.unlock();
//		
//	}
	
}
