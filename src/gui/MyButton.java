package gui;

import java.util.ArrayList;
import javax.swing.JPanel;
import core.ProporzionaImmagine;

public class MyButton {

	private Immagine notImagePressed;
	private Immagine imagePressed;
	private ArrayList<Immagine> bottoni;
	private String button_name;
	private boolean clicked;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public MyButton(Immagine notImagePressed, Immagine imagePressed, String name)
	{
		this.notImagePressed = notImagePressed;
		this.notImagePressed.setVisible(true);
		this.imagePressed = imagePressed;
		this.button_name = name;
		this.clicked = false;
		this.x = this.notImagePressed.getX();
		this.y = this.notImagePressed.getY();
		this.width = this.notImagePressed.getImageWidth();
		this.height = this.notImagePressed.getImageHeight();
		
		this.bottoni = new ArrayList<Immagine>();
		this.bottoni.add(this.notImagePressed);
		this.bottoni.add(this.imagePressed);
		
		
		
	}
	
//	Questa funzione viene invocata ogni qual volta abbiamo premuto il bottone e quindi, vogliamo far vedere l'immagine cliccata
	public void changeImage()
	{
		this.notImagePressed.setVisible(false);
		this.imagePressed.setVisible(true);
	}
	
	public void returnToFirstImage()
	{
		this.notImagePressed.setVisible(true);
		this.imagePressed.setVisible(false);
		
	}
	
	public void proporzionaB(ProporzionaImmagine p, JPanel panel)
	{
		for(Immagine i: this.bottoni)
			p.resizeButton(i, this);
		this.setPressedButtonsCoord(p);
			
	}
	
	public void proporzionaEditorButton(ProporzionaImmagine p) {
		// TODO Auto-generated method stub
		for (Immagine i : this.bottoni)
			p.resizeEditorButton(i, this);
		this.setPressedButtonsCoord(p);

	}
	
	private void setPressedButtonsCoord(ProporzionaImmagine p) {
		int pawnWidthDiv = p.getPawnCellWidth() / 5;
		this.imagePressed.setImageWidth(this.notImagePressed.getImageWidth() + pawnWidthDiv);
		this.imagePressed.setImageHeight(this.notImagePressed.getImageHeight() + pawnWidthDiv);
		this.imagePressed.setX(this.notImagePressed.getX() - pawnWidthDiv);
		this.imagePressed.setY(this.notImagePressed.getY() - pawnWidthDiv);
	}
	
	
	
	public void setPressedImageAndNotPressedImage(Immagine pressed, Immagine notPressed)
	{
//		pressed.setXYWH(this.imagePressed.getX(), this.imagePressed.getY(), this.imagePressed.getImageWidth(), this.imagePressed.getImageHeight());
//		notPressed.setXYWH(this.notImagePressed.getX(), this.notImagePressed.getY(), this.notImagePressed.getImageWidth(), this.notImagePressed.getImageHeight());
		this.imagePressed.setBufferedImage(pressed.getBufferedImage());
		this.notImagePressed.setBufferedImage(notPressed.getBufferedImage());
	}

	public ArrayList<Immagine> getBottoni() {
		return bottoni;
	}

	public void setBottoni(ArrayList<Immagine> bottoni) {
		this.bottoni = bottoni;
	}

	public String getButton_name() {
		return button_name;
	}

	public void setButton_name(String button_name) {
		this.button_name = button_name;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
}
