package gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import audio.Sounds;
import interfaces.StaticVariables;
import interfaces.Variables;

public class Menu extends MyFrame{
	
	private MyPanel playPanel;
	private MyPanel myMenuPanel;
	private MyPanel final_panel;
	private MyPanel myTypeOfOpponentPanel;
	private MyPanel typesOfIntelligencePanel;
	private MyPanel editorPanel;
	private List<MyPanel> all_panels;
	private MyPanel current_panel;
	
	
	//E' il frame del gioco
	public Menu() 
	{
		super();
		this.setTitle("Menu");
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		java.awt.GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice cc = ge.getDefaultScreenDevice();
//		cc.setFullScreenWindow(this);
		this.all_panels = new ArrayList<MyPanel>();
		this.createPanels();
		this.pack();
		this.setVisible(true);
		
	}
	
	public void setPanelsVisibility(MyPanel panel, boolean v)
	{

		System.err.println("v: "+v);
		if(v)
		{
			
			this.all_panels.add(panel);
			this.add(panel);
			panel.setProperties(v);
		}
		else
		{
			panel.setProperties(v);
			this.remove(panel);
			this.all_panels.remove(panel);
		}
		
		this.pack();
	}
	
	/**
	 *  E' un metodo al quale passo il pannello da rendere o meno visibile e setta p come pannello corrente del frame.
	 * @param p: il pannello che dev'essere reso visibile o meno.
	 * @param isVisible: la booleana che determina la visibilità di p.
	 */
	public void setPanelProperties(MyPanel p, boolean isVisible)
	{
		this.setPanelsVisibility(p, isVisible);
		this.setCurrent_panel(p);
	}
	
	private void createPanels()
	{
		this.myTypeOfOpponentPanel = new MyTypeOfOpponentPanel(this,StaticVariables.TYPE_OF_OPPONENT_PANELNAME, this.getWidth(), this.getHeight());
		this.getContentPane().add(this.myTypeOfOpponentPanel);
		this.current_panel = this.myTypeOfOpponentPanel; //salgo il pannello corrente
		this.typesOfIntelligencePanel = new TypesOfIntelligencePanel(this, StaticVariables.AI_PANEL_NAME, this.getWidth(), this.getHeight());
		this.playPanel = new MyPlayPanel(this,StaticVariables.PLAY_NAME);
		this.myMenuPanel = new MyMenuPanel(this,StaticVariables.MENU_NAME, this.getWidth(), this.getHeight());
		this.editorPanel = new MyEditorPanel(this, StaticVariables.EDITOR_NAME);
		
		this.myAdd(this.myMenuPanel,this.typesOfIntelligencePanel,this.playPanel,this.myTypeOfOpponentPanel,this.editorPanel);
	}
	
	public void replay(MyPanel current, int state, boolean exit)
	{
		System.out.println("replay");
		Variables.endGame = false;
		Variables.editor = false;
		this.stopAudios(state, exit);
		this.setPanelProperties(current, false);
		this.setPanelProperties(this.myTypeOfOpponentPanel, true);
		MyPlayPanel new_game = new MyPlayPanel(this,StaticVariables.PLAY_NAME);
		this.playPanel = new_game;
	}
	
	public void disposeFrame(int state, boolean exit)
	{
		System.out.println("disposeFrame");
		this.stopAudios(state, exit);
		System.exit(0);
	}
	
	/**
	 * Se è !exit (cioè replay), deve solo stoppare gli audio; altrimenti deve stopparli e chiuderli.
	 * @param state
	 * @param exit
	 */
	private void stopAudios(int state, boolean exit)
	{
		//se si deve uscire
		if(exit)
		{
			switch(state)
			{
			case(1): Sounds.getSounds().stopAndClose(StaticVariables.PATH_AUDIO_WIN); break;
			case(2): Sounds.getSounds().stopAndClose(StaticVariables.PATH_AUDIO_GAME_OVER); break;
			default: break;
			}
		}
		//se è replay
		else
		{
		   switch(state)
		   {
		    case(1): Sounds.getSounds().stop(StaticVariables.PATH_AUDIO_WIN); break;
		    case(2): Sounds.getSounds().stop(StaticVariables.PATH_AUDIO_GAME_OVER); break;
		    default: break;
		   }
		}
	}
	
	private void myAdd(MyPanel...myPanel) 
	{
		for(MyPanel m : myPanel)
			this.all_panels.add(m);
	}
	
	public List<MyPanel> getAll_panels() {
		return all_panels;
	}

	public void setAll_panels(List<MyPanel> all_panels) {
		this.all_panels = all_panels;
	}

	public MyPanel getCurrent_panel() {
		return current_panel;
	}

	public void setCurrent_panel(MyPanel current_panel) {
		this.current_panel = current_panel;
	}

	public MyPanel getPlayPanel() {
		return playPanel;
	}

	public void setPlayPanel(MyPanel playPanel) {
		this.playPanel = playPanel;
	}

	public MyPanel getMyMenuPanel() {
		return myMenuPanel;
	}

	public void setMyMenuPanel(MyPanel myMenuPanel) {
		this.myMenuPanel = myMenuPanel;
	}

	public MyPanel getMyTypeOfOpponentPanel() {
		return myTypeOfOpponentPanel;
	}

	public void setMyTypeOfOpponentPanel(MyPanel myTypeOfOpponentPanel) {
		this.myTypeOfOpponentPanel = myTypeOfOpponentPanel;
	}

	public MyPanel getFinal_panel() {
		return final_panel;
	}

	public void setFinal_panel(MyPanel final_panel) {
		this.final_panel = final_panel;
	}

	public MyPanel getTypesOfIntelligencePanel() {
		return typesOfIntelligencePanel;
	}

	public void setTypesOfIntelligencePanel(MyPanel typesOfIntelligencePanel) {
		this.typesOfIntelligencePanel = typesOfIntelligencePanel;
	}

	public MyPanel getEditorPanel() {
		return editorPanel;
	}

	public void setEditorPanel(MyPanel editorPanel) {
		this.editorPanel = editorPanel;
	}
	
	
	
	
}
