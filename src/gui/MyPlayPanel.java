package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import Utilities.Chronometer;
import audio.Sounds;
import core.Cell;
import core.Game;
import core.UserPlayer;
import interfaces.StaticVariables;
import interfaces.Variables;

public class MyPlayPanel extends MyPanel {

	private boolean pawnsAreVisible;
	private int i_cell_before;
	private int j_cell_before;
	private int i_cell_after;
	private int j_cell_after;
	private boolean first_click, first_right_click;
	private boolean enter;
	private boolean turno_ai;
	private Game game;
	private Chronometer chronometer;
	private String intelligence;
	private ArrayList<Cell> celle_per_pasto_consecutivo;
	private ArrayList<Cell> ai_cells;
	private boolean pedina_che_diventera_dama;

	public MyPlayPanel(Menu menu, String panel_name) {
		super(menu, panel_name);
		// this.proporziona.setSizeBAckgroundAndScacchiera(this.menu.getWidth(),this.menu.getHeight());
		// //qui proporziono lo sfondo e la scacchiera, prima che venga creato il panel
		// con il rendering
		this.game = new Game(this);
		this.pawnsAreVisible = false;
		this.turno_ai = false;
		this.initIJplayPanel();
		this.first_click = false;
		this.first_right_click = true;

		this.enter = false;
		this.chronometer = new Chronometer();
		this.intelligence = "";
		this.celle_per_pasto_consecutivo = new ArrayList<Cell>();
		this.ai_cells = new ArrayList<Cell>();

		this.font = new Font("LucidaSans", Font.PLAIN, 50);
		this.createChronometerString();
		
		this.pedina_che_diventera_dama = false;
		
		this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).setVisible(true);
		
		
		if(Variables.editor)
		{
			System.out.println("construct playPanel. editor");
			this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setPath(Variables.PATH_TAVOLO);
		}
		
		this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO).setVisible(true);
		
		this.tavolo_w = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getImageWidth();
		this.tavolo_h = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getImageHeight();
		this.tavolo_x = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getX();
		this.tavolo_y = this.caricatore_immagini.getScacchieraSfondo().get(StaticVariables.ID_TAVOLO_LEGNO).getY();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		try {
		
		if (this.isVisible()) {

				for (Immagine i : this.caricatore_immagini.getScacchieraSfondo().values()) {
					if (i.isVisible())
						g.drawImage(i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
				}
				
				//scorro l'array dell'animazione, ma si vedrà solo nel momento dell'animazione
				for (Immagine i : this.caricatore_immagini.getPawn_animation()) {
					if (i.isVisible())
						g.drawImage(i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
				}

				if (this.pawnsAreVisible) {
					for (Immagine i : this.caricatore_immagini.getPaws_images().values()) {
						if (i.isVisible())
							g.drawImage(i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
					}
				}
				
				for(Immagine i: this.back_button.getBottoni())
			        if(i.isVisible())
			              g.drawImage( i.getImage(), i.getX(), i.getY(), i.getImageWidth(), i.getImageHeight(), this);
				
			}

		this.createChronometerString();
		g.drawString(font_string.getIterator(), (StaticVariables.finestra_width / 15) * 7,
				StaticVariables.finestra_height / 10);
		}catch(Exception e)
		{
			System.err.println("eccezione");
		}

	}

	public void createChronometerString() {
		this.font_string = new AttributedString(this.chronometer.getCurrentTime());
		// System.out.println("current time: "+this.chronometer.getCurrentTime());
		this.font_string.addAttribute(TextAttribute.FONT, font);
		this.font_string.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, 7);
		this.repaint();
	}

	public void createFinalPanel(boolean winOrNot) {
		
		System.out.println("A");
		
		Sounds.getSounds().stop(StaticVariables.PATH_AUDIO_SOUNDTRACK);
		
		if(winOrNot)
		{
			System.out.println("audio win");
			Sounds.getSounds().loop(StaticVariables.PATH_AUDIO_WIN);
		}
		else
		{
			System.out.println("audio lose");
			Sounds.getSounds().loop(StaticVariables.PATH_AUDIO_GAME_OVER);
		}
		
		this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
		// lo creo solo per eitare il null pointer, ma poi le verrà settata un'altra istanza veritiera
		this.menu.setFinal_panel(new FinalPanel(this.menu, StaticVariables.FINAL_NAME, winOrNot, this.tavolo_w, this.tavolo_h, this.tavolo_x, this.tavolo_y)); 
		this.menu.setPanelProperties(this.menu.getFinal_panel(), true);
		
	}
	

	public boolean isPawnsAreVisible() {
		return pawnsAreVisible;
	}

	public void setPawnsAreVisible(boolean pawnsAreVisible) {
		this.pawnsAreVisible = pawnsAreVisible;
	}

	// DOPO LA MOSSA EFFETTUATA, SETTARE 'first_click' A FALSE!
	@Override
	public void mouseClicked(MouseEvent e) {

		if(Variables.canMove)
		{
		if (e.getButton() == MouseEvent.BUTTON3) {
			System.out.println("caaaaaaaaaaaaaaasaaaaaaaaa");
			int i_prima = this.proporziona.coordI(e.getY());
			int j_prima = this.proporziona.coordJ(e.getX());

			if (this.controllaValiditaCoord(i_prima, j_prima)) {
				((UserPlayer)this.game.getUser_player()).changePedinaPremutaProperties(true, i_prima, j_prima);
				this.celle_per_pasto_consecutivo.add(new Cell(i_prima, j_prima, ""));
				// this.print(this.celle_per_pasto_consecutivo, "nome celle per pasto
				// consecutivo: ");
				// this.print(this.ai_cells, "ai_cells: ");

				if (!this.ai_cells.isEmpty() && !this.weContinue()) {
					Variables.giocatore1_mangio = true;
					//dev'essere mandato solo se siamo in modalità multiplayer e non dobbiamo aggiornare il gioco dopo 
					//i movimenti dell'avversario.
					if (!Variables.single_player && Variables.giocatore1_mangio) {
						((UserPlayer)this.game.getUser_player()).sendMangiataMultipla(this.celle_per_pasto_consecutivo, this.ai_cells);
						((UserPlayer) this.game.getUser_player()).getClient().setModifiedSentence("");
						((UserPlayer) this.game.getUser_player()).getClient().setTemp("");
						Variables.canMove = false;
//						Variables.giocatore1_mangio = false; //la metto qui erché altrimenti si mette in attesa anche nel thread 'singleMovement'
					} else {
						System.out.println("NON ENTRO receive mangiata multipla:(. variables.update: " + Variables.update);
					}
					
					System.out.println("DENTRO");
					if(Variables.single_player)
					this.game.getUser_player().moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.ai_cells);
					else
					{
						this.game.getUser_player().moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.ai_cells);
					}
//						this.game.getUser_player().moveByRightMouseClickUpdateteMovementMultiplayer(this.celle_per_pasto_consecutivo, this.ai_cells);
					
//					if (!Variables.single_player && !Variables.giocatore1_mangio) {
//						((UserPlayer)this.game.getUser_player()).receiveMangiataMultipla();
//						Variables.interpostaTraMovMultiploEnon = false;
////						Variables.mangiata_multipla = false;
//					} else {
//						System.out.println("NON ENTRO receive mangiata multipla:(. variables.update: " + Variables.update);
//					}
					this.pedina_che_diventera_dama = false;
					this.first_right_click = true;
				}
			} else {
				((UserPlayer)this.game.getUser_player()).changePedinaPremutaProperties(false, i_prima, j_prima);
				System.out.println("non è più il first click");
				this.first_right_click = true;
				this.pedina_che_diventera_dama = false;
				this.celle_per_pasto_consecutivo.clear();
				this.ai_cells.clear();
			}

			// System.out.println("celle per pasto consecutivo: ");
			// for(Cell c: this.celle_per_pasto_consecutivo)
			// System.out.print("<"+c.getI()+","+c.getJ()+"> ");

		} else if(e.getButton() == MouseEvent.BUTTON1) {

			this.first_right_click = true;
			this.celle_per_pasto_consecutivo.clear();
			this.ai_cells.clear();

			if (!this.turno_ai) {
				this.onFirstClick(e);
				if (this.enter) // al secondo click
					this.onSecondClick(e);
				else {
					System.out.println("tre. <icellbefore,jcellbefore>: <" + this.i_cell_before + ","
							+ this.j_cell_before + ">. <icellafter,jcellafter>: <" + this.i_cell_after + ","
							+ this.j_cell_after + ">");
					this.enter = false;
				}
				System.out.println("mouse clicked print");
				this.game.getDama().printMatrix();
			}
		}
		}//fine if(canMove)
		if((e.getX() >= this.back_button.getX() && e.getX() <= (this.back_button.getX()+this.back_button.getWidth())) && (e.getY() >= this.back_button.getY() && e.getY() <= (this.back_button.getY()+this.back_button.getHeight())))
		  {
			  System.out.println("back playpanel");
			  this.game.getUser_player().sendMovimentoSemplice(-1, -1, -1, -1, true);
			  this.menu.setPanelsVisibility(this.menu.getCurrent_panel(), false);
			  this.menu.setPanelProperties(this.menu.getMyMenuPanel(), true);
		  }
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if((e.getX() >= this.back_button.getX() && e.getX() <= (this.back_button.getX()+this.back_button.getWidth())) && (e.getY() >= this.back_button.getY() && e.getY() <= (this.back_button.getY()+this.back_button.getHeight())))
		 {
			 this.back_button.changeImage();
			 this.repaint();
		 }
		 else 
			{
				this.back_button.returnToFirstImage();
				this.repaint();
			}

	}

	private void onFirstClick(MouseEvent e) {

		if (!this.enter) {
			this.i_cell_before = this.proporziona.coordI(e.getY());
			this.j_cell_before = this.proporziona.coordJ(e.getX());

			if (this.i_cell_before >= 0 && this.i_cell_before < StaticVariables.RIGHE_COLONNE && this.j_cell_before >= 0
					&& this.j_cell_before < StaticVariables.RIGHE_COLONNE) {
				if (this.game.getDama().isThereaPawn(i_cell_before, j_cell_before)) {
					if (this.game.getDama().getPawnColour(i_cell_before, j_cell_before) == this.game.getUser_player()
							.getStringColour())
						this.enter = true;
					((UserPlayer)this.game.getUser_player()).changePedinaPremutaProperties(true, i_cell_before, j_cell_after);
					// else
					// System.out.println("uno. premuta una pedina dell'avversario");
				}
				// else
				// System.out.println("uno. non c'è nessuna pedina sulla cella");
			}
			// else
			// System.out.println("uno. coordinate sbagliate");

		}
		// else
		// System.out.println("uno. o il cont != 0 oppure enter è true o entrambe.
		// enter: "+this.enter);

		System.out.println("uno");
	}

	private void onSecondClick(MouseEvent e) {
		this.i_cell_after = this.proporziona.coordI(e.getY());
		this.j_cell_after = this.proporziona.coordJ(e.getX());

		if (this.i_cell_after >= 0 && this.i_cell_after < StaticVariables.RIGHE_COLONNE && this.j_cell_after >= 0
				&& this.j_cell_after < StaticVariables.RIGHE_COLONNE) {
			System.out.println("due. il range è corretto. icellbefore: " + i_cell_before + " jcellbefore: "
					+ j_cell_before + " icellafter: " + i_cell_after + " jcellafter: " + j_cell_after);
			this.isOrNotAPawnOnaCell(e);
		} else
			this.enter = false;
		System.out.println("due");
	}

	private void isOrNotAPawnOnaCell(MouseEvent e) {
		// System.out.println("init due. wer");
		if (!this.game.getDama().isThereaPawn(i_cell_after, j_cell_after))

		{
			if ((i_cell_before == i_cell_after && j_cell_before != j_cell_after)
					|| (i_cell_before != i_cell_after && j_cell_before == j_cell_after)
					|| (i_cell_before != i_cell_after && j_cell_before != j_cell_after))
			{
				Variables.interpostaTraMovMultiploEnon = false;
				this.game.getUser_player().movePawn(this.game.getDama().getPawnAtPosition(i_cell_before, j_cell_before),
						i_cell_before, j_cell_before, i_cell_after, j_cell_after);
			}

			this.enter = false;
			this.initIJplayPanel();
		} else if (this.game.getDama().isThereaPawn(i_cell_after, j_cell_after)) {
			// System.out.println(" after due. Ritorno ad uno");
			this.enter = false;
			this.onFirstClick(e);

		}
	}

	/**
	 * Se le coordinate rispettano determinate condizioni, poi si potrà procedere ad
	 * inserire quelle coordinate nell'array 'celle...ecc'
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean controllaValiditaCoord(int i, int j) {
		if (this.areThereSameCells(i, j)) {
			this.first_right_click = true;
			this.celle_per_pasto_consecutivo.clear();
			this.ai_cells.clear();
			// return false;
		}

		// la prima volta va presa la cella con una pedina dello user
		if (this.first_right_click) {
			this.celle_per_pasto_consecutivo.clear();
			this.ai_cells.clear();
			if (this.game.getDama().isThereaPawn(i, j)
					&& this.game.getDama().getPawnColour(i, j) == this.game.getUser_player().getStringColour()) {
				System.out.println("prima volta che clicco una cella su cui c'è una pedina dello user player");
				System.out.print("<" + i + "," + j + "> ");
				this.first_right_click = false;
				return true;
			} else
				System.out.println("Q");
		}
		// dopo la prima volta, la cella dev'essere vuota
		else {
			// se non ci sono pedine e la i e la j cliccate, differisco di 2 con la i e la j
			// della cella precedentemente cliccata, si può ritenere valida questa posizione
			// cliccata
			if (!this.game.getDama().isThereaPawn(i, j)) {
				// Math.abs(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()-i)
				// == 2 &&
				// Math.abs(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getJ()-j)
				// == 2)

				if (Math.abs(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()- i) == 2) {///
					System.out.println("cella cliccata miao: <" + i + "," + j + "> ");
					// sopra
					if (this.comandoSiaPerLeDameCheNon_controllaValiditaCoord(i, j))
						return true;

					// sotto
					if ((i - this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1)
							.getI()) > 0) {
						// se la pedina che voglio muovere è una dama
						if (this.game.getDama().getPawnAtPosition(this.celle_per_pasto_consecutivo.get(0).getI(),
								this.celle_per_pasto_consecutivo.get(0).getJ()).isDama() || this.pedina_che_diventera_dama) 
						{// se la cella cliccata è sotto a destra della cella cliccaa in precendenza
							if (i - 1 >= 0 && j - 1 >= 0
									&& (j - this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()) > 0
									&& this.game.getDama().isThereaPawn(i - 1, j - 1)
									&& this.game.getDama().getPawnColour(i - 1, j - 1) != this.game.getUser_player()
											.getStringColour()) {
								this.ai_cells.add(new Cell(i - 1, j - 1, ""));
								return true;
							} else
								System.out.println("...");
							// se la cella cliccata è sotto a sinistra della cella cliccaa in precendenza
							if (i - 1 >= 0 && j + 1 <= StaticVariables.RIGHE_COLONNE - 1
									&& (j - this.celle_per_pasto_consecutivo
											.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()) < 0
									&& this.game.getDama().isThereaPawn(i - 1, j + 1)
									&& this.game.getDama().getPawnColour(i - 1, j + 1) != this.game.getUser_player()
											.getStringColour()) {
								this.ai_cells.add(new Cell(i - 1, j + 1, ""));
								return true;
							} else
								System.out.println("---");

							if (this.comandoSiaPerLeDameCheNon_controllaValiditaCoord(i, j))
								return true;

						} else
							System.out.println("11");
					} else
						System.out.println("10");
				} else
					System.out.println("3");
			} else
				System.out.println("1");
		}

		System.out.println("fuori");
		return false;

	}

	/**
	 * Verifica se ci sono ancora pedine avversarie da mangiare, oppure se possiamo
	 * procedere col muovere la nostra pedina e mangiare le pedine avversarie.
	 * 
	 * @return
	 */
	private boolean weContinue() {

		System.err.println("ULTIMA CELLA AGGIUNTA: <"
				+ this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 2).getI() + ","
				+ this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 2).getJ() + ">");

		if (this.celle_per_pasto_consecutivo.size() >= 2) {
			// se il verso di gioco è sopra
			if ((this.celle_per_pasto_consecutivo.get(0).getI()-this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI()) > 0) {
				
				this.pedina_che_diventera_dama = true;
				if (this.comandoSiaPerLeDameCheNon_weContinue())
				{
					this.pedina_che_diventera_dama = false;
					return true;
				}else
					System.out.println("SSS");
			}
			// se il verso di gioco è sotto
			else {

				// se l'ultima cella dell'array 'celle...' è una dama...
				if (this.game.getDama().getPawnAtPosition(this.celle_per_pasto_consecutivo.get(0).getI(),this.celle_per_pasto_consecutivo.get(0).getJ()).isDama() || this.pedina_che_diventera_dama) 
				{
					if (this.comandoSoloPerLeDame_weContinue())
						return true;
					System.err.println("5 comandoSoloPerLeDame_weContinue() FALSE");

				} else
					System.err.println("6 NON è UNA DAMA");
			}
		}else
			System.out.println("this.celle_per_pasto_consecutivo.size() < 2 ");
		
		//se l'ultima poszione cliccata è 0, significa che la nostra pedina diventerà dama, quindi richiamo la  this.comandoSoloPerLeDame_weContinue()
		//per verificare se ci sono ancora pedine avversarie da mangiare
		
		//pedina che diventerà dama = true;
		if(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1).getI() == 0)
		{
			if(this.comandoSoloPerLeDame_weContinue())
			{
				this.pedina_che_diventera_dama = true;
				return true;
			}
		}

		return false;
	}

	/**
	 * E' una funzione da utilizzare per gestire i movimentiin alto delle pedine non
	 * dame e delle dame(se volessero mangiare 2 pedine, le quali, la prima si trova
	 * sotto e l'altra sopra, in modo tale da formare una 'mangiata a V'.
	 * 
	 * @param i
	 *            -> i della cella cliccata
	 * @param j
	 *            -> j della cella cliccata
	 * @return
	 */
	private boolean comandoSiaPerLeDameCheNon_controllaValiditaCoord(int i, int j) {
		if ((i - this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()) < 0) {
			// se la cella cliccata è sopra a destra della cella cliccaa in precendenza
			if (i + 1 <= StaticVariables.RIGHE_COLONNE - 1 && j - 1 >= 0
					&& (j - this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()) >= 0
					&& this.game.getDama().isThereaPawn(i + 1, j - 1) 
					&& this.game.getDama().getPawnColour(i + 1,j - 1) != this.game.getUser_player().getStringColour()) {
				this.ai_cells.add(new Cell(i + 1, j - 1, ""));
				return true;
			} else
				System.out.println("C");
			// se la cella cliccata è sopra a sinistra della cella cliccaa in precendenza
			if (i + 1 <= StaticVariables.RIGHE_COLONNE - 1 && j + 1 <= StaticVariables.RIGHE_COLONNE - 1
					&& (j - this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()) < 0
					&& this.game.getDama().isThereaPawn(i + 1, j + 1) && this.game.getDama().getPawnColour(i + 1,
							j + 1) != this.game.getUser_player().getStringColour()) {
				this.ai_cells.add(new Cell(i + 1, j + 1, ""));
				return true;
			} else
				System.out.println("B");
		} else
			System.out.println("A");

		return false;
	}

	private boolean comandoSiaPerLeDameCheNon_weContinue() {
		// se c'è una pedina avversaria, 1 celle sopra a sinistra della pedina dello
		// user player, si può continuare
		if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 1 >= 0
				&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() - 1 >= 0
				&& this.game.getDama().isThereaPawn(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() - 1)
				&& this.game.getDama().getPawnColour(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()
								- 1) != this.game.getUser_player().getStringColour()) {
			// se non c'è una pedina, 2 celle sopra a sinistra della pedina dello user
			// player, si può continuare
			if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 2 >= 0
					&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() - 2 >= 0
					&& !this.game.getDama().isThereaPawn(
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()-2,
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()-2) &&
					!this.isCellAlreadyThere(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()-2,this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()-2, ""))) {
				System.err.println("comandoSiaPerLeDameCheNon_weContinue we continue -1: alto a sinistra");
				return true;
			} else
				System.err.println("comandoSiaPerLeDameCheNon_weContinue we continue FUORI -1: alto a sinistra");
		}
		// se c'è una pedina avversaria, 1 celle sopra a destra della pedina dello user
		// player, si può continuare
		if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 1 >= 0
				&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()
						+ 1 <= StaticVariables.RIGHE_COLONNE - 1
				&& this.game.getDama().isThereaPawn(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() + 1)
				&& this.game.getDama().getPawnColour(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() - 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()
								+ 1) != this.game.getUser_player().getStringColour()) {
			// se non c'è una pedina, 2 celle sopra a destra della pedina dello user player,
			// si può continuare
			if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()-2 >= 0
					&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1
					&& !this.game.getDama().isThereaPawn(
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()-2,
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+2) &&
					!this.isCellAlreadyThere(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()-2,this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+2, ""))) {
				System.err.println("comandoSiaPerLeDameCheNon_weContinue we continue 0: alto a destra");
				return true;
			} else
				System.err.println("comandoSiaPerLeDameCheNon_weContinue we continue FUORI 0: alto a destra");
		}
		return false;

	}

	private boolean comandoSoloPerLeDame_weContinue() {
		
		
		
		// se una cella più giù a sinistra, c'è una pedina avversaria
		if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+1 <= StaticVariables.RIGHE_COLONNE - 1
				&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() - 1 >= 0
				&& this.game.getDama().isThereaPawn(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() + 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() - 1)
				&& this.game.getDama().getPawnColour(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() + 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()
								- 1) != this.game.getUser_player().getStringColour()) {
			// se due celle più giù a sinistra dell'ultima pedina dell'array 'celle...' non ci sono pedine
			if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()
					+ 2 <= StaticVariables.RIGHE_COLONNE - 1
					&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() - 2 >= 0
					&& !this.game.getDama().isThereaPawn(
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+2,
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()-2) &&
					!this.isCellAlreadyThere(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+2,this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()-2, ""))) {
				System.err.println("comandoSoloPerLeDame_weContinue we continue 1: giù a sinistra");
				return true;
			} else
				System.err.println("comandoSoloPerLeDame_weContinue we continue FUORI 1: giù a sinistra");

		}else
			System.out.println("comandoSoloPerLeDame_weContinue we continue FUORI 1.1");
		// se una cella più giù a destra, c'è una pedina avversaria
		//HO TOLTO L'ELSE PER VEDERE UNA COSA IL 23/01 ALL 1:39
		/*else*/ if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+1 <= StaticVariables.RIGHE_COLONNE-1
				&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+1 <= StaticVariables.RIGHE_COLONNE - 1
				&& this.game.getDama().isThereaPawn(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() + 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ() + 1)
				&& this.game.getDama().getPawnColour(
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI() + 1,
						this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+ 1) != this.game.getUser_player().getStringColour()) {

			// se due celle più giù a destra, non ci sono pedine
			if (this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+2 <= StaticVariables.RIGHE_COLONNE-1
					&& this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+2 <= StaticVariables.RIGHE_COLONNE-1
					&& !this.game.getDama().isThereaPawn(
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+2,
							this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+2) &&
					!this.isCellAlreadyThere(new Cell(this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getI()+2,this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size() - 1).getJ()+2, ""))) {
				System.err.println("comandoSoloPerLeDame_weContinue we continue 2: giù a destra");
				return true;
			} else
				System.err.println("comandoSoloPerLeDame_weContinue we continue FUORI 2: giù a destra");
		}else
			System.err.println("comandoSoloPerLeDame_weContinue we continue FUORI 2.2: giù a sinistra");

		System.err.println("prima dell'ultimo if (comandoSoloPerLeDame_weContinue)");
//		this.cella_per_pasto_consecutivo_temp = this.celle_per_pasto_consecutivo.get(this.celle_per_pasto_consecutivo.size()-1);
//		this.game.getUser_player().moveByRightMouseClick(this.celle_per_pasto_consecutivo, this.ai_cells);
//	    this.celle_per_pasto_consecutivo.add(this.cella_per_pasto_consecutivo_temp);
		if (this.comandoSiaPerLeDameCheNon_weContinue())
			return true;
		return false;
	}
	
	/**
	 * Restituisce true se la cella passatagli è già presente
	 * @param cell
	 * @return
	 */
	private boolean isCellAlreadyThere(Cell cell)
	{
		for(Cell c: this.celle_per_pasto_consecutivo)
			if(cell.getI() == c.getI() && cell.getJ() == c.getJ())
				return true;
		return false;
	}

	/**
	 * Se nell'array 'celle...' ci sono celle uguali, svuotare gli array 'celle...',
	 * 'ai pawns' e mettere a true la booleana first right move
	 * 
	 * @return
	 */
	private boolean areThereSameCells(int i, int j) {
		for (Cell c : this.celle_per_pasto_consecutivo)
			if (c.getI() == i && c.getJ() == j)
				return true;
		return false;
	}

	public void print(ArrayList<Cell> a, String nome_array) {
		System.out.println(nome_array);
		for (Cell c : a)
			System.out.print("<" + c.getI() + "," + c.getJ() + "> ");
		System.out.println();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isFirst_click() {
		return first_click;
	}

	public void setFirst_click(boolean first_click) {
		this.first_click = first_click;
	}

	public void initIJplayPanel() {
		this.i_cell_before = this.j_cell_before = this.i_cell_after = this.j_cell_after = -1;
	}

	public boolean isEnter() {
		return enter;
	}

	public void setEnter(boolean enter) {
		this.enter = enter;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isTurno_ai() {
		return turno_ai;
	}

	public void setTurno_ai(boolean turno_ai) {
		this.turno_ai = turno_ai;
	}

	public String getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(String intelligence) {
		this.intelligence = intelligence;
	}

}
