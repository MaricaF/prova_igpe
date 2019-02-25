package audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import interfaces.StaticVariables;

public class Sounds {
	
	private HashMap<String, Clip> clips;
	private HashMap<String, AudioInputStream> sounds;
	private HashMap<String, FloatControl> audios_volume;
	private static Sounds instance = null;
	
	private Sounds() {
		this.init();
		// TODO Auto-generated constructor stub
	}
	
	private void init()
	{
		this.clips = new HashMap<String, Clip>();
		this.sounds = new HashMap<String, AudioInputStream>();
		this.audios_volume = new HashMap<String, FloatControl>();
		this.openFile(StaticVariables.PATH_AUDIO_PROVA);
		this.openFile(StaticVariables.PATH_AUDIO_SOUNDTRACK);
		this.setVolume(StaticVariables.PATH_AUDIO_SOUNDTRACK, -10);
		this.openFile(StaticVariables.PATH_AUDIO_ALERT);
		this.openFile(StaticVariables.PATH_AUDIO_PAWN_MOVE);
		this.setVolume(StaticVariables.PATH_AUDIO_PAWN_MOVE, +5);
		this.openFile(StaticVariables.PATH_AUDIO_WIN);
		this.openFile(StaticVariables.PATH_AUDIO_GAME_OVER);
	}
	
	public static Sounds getSounds()
	{
		if(instance == null)
			instance = new Sounds();
		return instance;
	}
	
	/**
	 * E' un metodo flyweight che crea una nuova clip e la aggiunge alla mappa di clip -> 'clips' e un audioInputStream che aggiunge alla mappa di audioInputStream -> 'sounds'
	 * Dopodiché apre il file
	 * @param file_name -> il nome del file dell'audio che voglio creare
	 */
	private void openFile(String file_name)
	{
		try {
			if(!this.sounds.containsKey(file_name))
			{
		      File file = new File(file_name);
		      AudioInputStream sound = AudioSystem.getAudioInputStream(file);
		      this.sounds.put(file_name, sound);
		      Clip clip = AudioSystem.getClip();
		      this.clips.put(file_name, clip);
			}
			
			 this.clips.get(file_name).open(this.sounds.get(file_name));
		  
		}catch(Exception e)
		{
			System.err.println("Impossibile caricareil file l'audio: "+file_name);
		}
		
	}
	
	public void play(String file_name)
	{
		try {
			if (!this.clips.get(file_name).isOpen())

				this.clips.get(file_name).open(this.sounds.get(file_name));

			this.clips.get(file_name).setFramePosition(0);
			this.clips.get(file_name).start();
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loop(String file_name)
	{
		try {
			if (!this.clips.get(file_name).isOpen())

				this.clips.get(file_name).open(this.sounds.get(file_name));

			this.clips.get(file_name).loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop(String file_name)
	{
		if (this.clips.get(file_name).isOpen()) 
			this.clips.get(file_name).stop();
	}
	
	public void stopAndClose(String file_name)
	{
		if (this.clips.get(file_name).isOpen()) 
		{
			this.clips.get(file_name).stop();
			this.clips.get(file_name).close();
		}
	}
	
	public void close(String file_name)
	{
		if (this.clips.get(file_name).isOpen()) 
		this.clips.get(file_name).close();
	}
	
	/**
	 * E' una funzione che aumenta o abbassa il volume di una clip audio
	 * @param file_name
	 * @param volume
	 */
	public void setVolume(String file_name, int volume)
	{
		if(!this.audios_volume.containsKey(file_name))
		{
		  FloatControl gainControl = (FloatControl) this.clips.get(file_name).getControl(FloatControl.Type.MASTER_GAIN);
	      this.audios_volume.put(file_name, gainControl);
		}
		
		this.audios_volume.get(file_name).setValue(volume+0.0f); 
	}

	public HashMap<String, FloatControl> getAudios_volume() {
		return audios_volume;
	}

	public void setAudios_volume(HashMap<String, FloatControl> audios_volume) {
		this.audios_volume = audios_volume;
	}
	
	
	

}
