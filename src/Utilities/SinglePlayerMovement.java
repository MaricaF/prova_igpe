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
		this.game.getPlay_panel().repaint();
		
		((MyPlayPanel)this.game.getPlay_panel()).setTurno_ai(true);
		
		if (!Variables.single_player)
		{
			if(!Variables.canMove && !Variables.mangiata_multipla && !Variables.update && !Variables.interpostaTraMovMultiploEnon)
			{
				this.player.receiveSemplice();
				Variables.canMove = true;
			}
		}
	
	 ((MyPlayPanel)this.game.getPlay_panel()).setTurno_ai(false);
		
			if(this.player.verifyEndGame())
			{
				this.game.getPlay_panel().repaint();
				return;
			}
		this.lock.unlock();
		} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
}
