package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import core.Game;
import core.UserPlayer;
import gui.MyPlayPanel;
import interfaces.Variables;

public class Client {
	
	private BufferedReader inFromUser;
    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private String server_address;
    private int port;
    private String modifiedSentence;
    private String x;
    private int cont_file;
    private FileWriter da_mandare;
    private String messageToSendToServer;
    private int size;
    private String[] array;
    private final int l;
    private File file;
    private FileWriter file_from_server;
    private String temp;
    private Game game;
    
    public Client(String user_address, int port, Game game)
    {
    	this.server_address = user_address;
    	this.port = port;
    	this.modifiedSentence = "";
    	this.x  = this.messageToSendToServer = "";
    	this.cont_file = 1;
    	this.size = 0;
    	this.l = 1000;
    	this.temp = "";
    	this.game = game;
    	this.connectionToServer(user_address, port);
    	this.initConversation();
    	
    }
    
    private void connectionToServer(String server_address, int port)
    {
			try {
				this.clientSocket = new Socket(server_address, port);
				this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
		        this.inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	
    }
    
    
    /**
     * In questo metodo, il server dirà a questo client se deve mettersi in attesa oppure se dev'essere lui
     * il primo ad iniziare la conversazione(a muovere).
     * Se riceverà "start", dovrà iniziare questo client; se riceverà "no", dovrà attendere.
     * Questo metodo verrà chiamato solo prima di stabilire chi inizierà a giocare.
     */
    public void initConversation()
    {
    	    //la prima volta devo ricevere o "start" o "no"
//			this.miMettoInAttesaDelServer();
    	//leggo la prima frase del file iniviatomi dal server
		try {
			this.modifiedSentence = this.inFromServer.readLine();
			System.out.println("Client "+this.clientSocket.getLocalAddress()+". Ho ricevuto: "+this.modifiedSentence+" dal Server.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			if(this.modifiedSentence.equals("start"))
			{
				this.game.getUser_player().setColour("white");
				Variables.canMove = true;
//				this.sendMessageToServerVersioneDaInput();
//				this.sendMessageToServer();
			}
			else
			{
				Variables.canMove = false;
				this.game.getUser_player().setColour("black");
				this.miMettoInAttesaDelServer();
//				this.sendMessageToServerVersioneDaInput();
//				this.sendMessageToServer();
			}
			
    }
    
    
//    /**
//     * Questa funzione verrà chiamata ogni qual volta dovrò comunicare al server,
//     * i cambiamenti del gioco in seguito alla mia mossa di gioco
//     */
    public void sendMessageToServer()
    {
    	try {
    		this.temp = "";
    		this.modifiedSentence = "";
    		//riga 0: posizioni delle pedine su cui si deve muovere la mia pedina
    		//riga 1: posizioni delle 
//    		 this.messageToSendToServer += "1,5 4,6"+"\n"+"3,5 4,7";
//    		 this.messageToSendToServer += "\n<END>";
    		  System.out.println("Mandi una richiesta al server.");
    		 
    		  //questa funzione crea il file new.txt che mi servirà dopo 
    		 this.da_mandare = this.createFile(this.messageToSendToServer);
    		 
    		 //prendo il file creato
    		 this.file = new File("new.txt");
    		 this.array = new String[l];
    		 
    		 //predo l'array di stringhe dal file new.txt
             this.array = this.tornaStringa(this.file);
             
             //mando ogni riga del file al server
             for(int i = 0; i < size; i++)
             {
            	 System.out.println("array[i]: "+this.array[i]);
            	 if(array[i] != null)
			      this.outToServer.writeBytes(this.array[i]);
             }
	    	
//		    while(!messageToSendToServer.equals("end"))
//		    {
		    	
//               this.miMettoInAttesaDelServer();
				
//				this.file_from_server = this.createFile(this.modifiedSentence);
//				
//				this.modifiedSentence = "";
//				this.temp = "";
//		    }
				 
		         
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    /**
     * E' il metodo che chiude la connessione con il server.
     * Viene richiamata quando il gioco termina, oppure quando si torna al menù per uscire dal gioco.
     */
	public void closeConnection() {
		try {
			this.inFromServer.close();
			this.outToServer.close();
			this.clientSocket.close();
			this.inFromServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

    /**
     * Ritorna il fileWriter da me creato dopo aver ricevuto la risposta del server
     * @param s -> stringa che sarà contenuta nel file
     * @return FileWriter
     */
    public FileWriter createFile(String s)
	{
		FileWriter tmp = null;
	 try {
		tmp = new FileWriter("new.txt");
		this.cont_file++;
		tmp.write(s); 
		tmp.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 return tmp;
	 
	}
    
    //Ritorna true quando bisogna chiudere la connessione, cioè quando si è ricevuto 'end'.
    public boolean miMettoInAttesaDelServer()
    {
    	try {
    		this.modifiedSentence = "";
    		this.temp = "";
    	System.out.println("sto in attesa che il server mi risponda...");
    	//leggo la prima frase del file iniviatomi dal server
			this.modifiedSentence = this.inFromServer.readLine();
			
			if(this.modifiedSentence.contains("end"))
				return true;
		
		//ciclo finché nel file non ho trovato <END>
		while(!this.modifiedSentence.equals("<END>") && !this.modifiedSentence.equals("avanti"))
		{
			//System.out.println("modifiedSentence: "+modifiedSentence);
			 this.temp += this.modifiedSentence+"\n";
			 this.modifiedSentence = this.inFromServer.readLine();
		}
		
		this.modifiedSentence = this.temp;
		
		System.out.println("FROM SERVER: " + this.modifiedSentence);
		
		this.file_from_server = this.createFile(this.modifiedSentence);
		
//		this.modifiedSentence = "";
//		this.temp = "";
		
		
		if(!Variables.mangiata_multipla)
		{
		Variables.canMove = true;
		System.out.println("Client CAN MOVE");
		}
		else
		{
			System.out.println("Client NO CAN MOVE");
			Variables.canMove = false;
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    public String[] tornaStringa(File f)
	{
		String[] array_stringhe = new String[l];
		Scanner scanner = null;
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//tmp = f.getName() + "\n";
		int i = 0;
	while(scanner.hasNext())
	{
		for(; i < array_stringhe.length;)
		{
			array_stringhe[i] = scanner.nextLine()+"\n";
			this.size++;
			//System.out.println("ecco"+array_stringhe[i]);
			break;
	     //tmp += scanner.nextLine();
	      // tmp += "\n";
		}
		i++;
	}
	
	return array_stringhe;
	}

	public String getModifiedSentence() {
		return modifiedSentence;
	}

	public void setModifiedSentence(String modifiedSentence) {
		this.modifiedSentence = modifiedSentence;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getMessageToSendToServer() {
		return messageToSendToServer;
	}

	public void setMessageToSendToServer(String messageToSendToServer) {
		this.messageToSendToServer = messageToSendToServer;
	}

	public BufferedReader getInFromUser() {
		return inFromUser;
	}

	public void setInFromUser(BufferedReader inFromUser) {
		this.inFromUser = inFromUser;
	}

	public DataOutputStream getOutToServer() {
		return outToServer;
	}

	public void setOutToServer(DataOutputStream outToServer) {
		this.outToServer = outToServer;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
    
    
    
}
