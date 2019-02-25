package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import interfaces.StaticVariables;

public class Immagine{

	private int id; 
	private String path;
	private Toolkit toolkit;
	private int x;
	private int y;
	private int imageHeight;
	private int imageWidth;
	private BufferedImage bufferedImage;
	//la i della matrice in cui si trova l'immagine
	private int i;
	//la j della matrice in cui si trova l'immagine
	private int j;
	private boolean visible;
	private int numero_immagine; 

	public Immagine(int id, String path, int numero_immagine) {
		this.id = id;
		this.path = path;
		this.numero_immagine = numero_immagine;
		this.i = 0;
		this.j = 0;
		this.x = 0;
	    this.y = 0;
	    
	    if(this.id == StaticVariables.ID_SFONDO1 ||  this.id == StaticVariables.ID_NASTRO)
	    	this.visible = true;
	    else
	        this.visible = false;
		
		this.toolkit = Toolkit.getDefaultToolkit();
	    try {
			this.bufferedImage = ImageIO.read (new File (path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.imageWidth = bufferedImage.getWidth ();
		this.imageHeight = bufferedImage.getHeight ();
		
//		if(this.id == StaticVariables.ID_PAPER_YOUWON || this.id == StaticVariables.ID_PAPER_YOULOST)
//		{
//			this.x = StaticVariables.finestra_width/6;
//			this.y = StaticVariables.finestra_height/10;
//			this.imageWidth = StaticVariables.finestra_width - StaticVariables.finestra_width/3;
//			this.imageHeight = StaticVariables.finestra_width - StaticVariables.finestra_width/3;
//			
//		}
		
//		this.setCoordinateImmagine();
		
	}
	
	int lunghezzaAttualeImmagine() { return this.imageWidth; }
	int altezzaAttualeImmagine() { return this.imageHeight; }
	
	public void setWH(int w, int h)
	{
		this.imageWidth = w;
		this.imageHeight = h;
	}
	
	public BufferedImage getBufferedImage()
	{
		return this.getBufferedImage();
	}
	
	public void setXYWH(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.setWH(w, h);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getI() {
		return i;
	}


	public void setI(int i) {
		this.i = i;
	}


	public int getJ() {
		return j;
	}


	public void setJ(int j) {
		this.j = j;
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
	
	public int getImageHeight() {
		return imageHeight;
	}


	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}


	public int getImageWidth() {
		return imageWidth;
	}


	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}


	public Image getImage() {
		Image image = this.toolkit.getImage(this.path);
		 
		return image;
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero_immagine() {
		return numero_immagine;
	}

	public void setNumero_immagine(int numero_immagine) {
		this.numero_immagine = numero_immagine;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	
    

}
