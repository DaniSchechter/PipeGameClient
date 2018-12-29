package model;
import java.io.BufferedReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import settings.SettingsWrapper;
import view.Pair;

public class MyModel extends Observable implements Model{
	
	private Level level;
	private OutputStream outToSolutionServer=null;
	private InputStream inFromSolutionServer=null;
	private OutputStream outToResaultServer=null;
	private InputStream inFromResaultServer=null;
	Map<Character,Character> map=null;
	
	public MyModel()
	{
		map = new HashMap<>();
		map.put('F', '7');
		map.put('7', 'J');
		map.put('J', 'L');
		map.put('L', 'F');
		map.put('-', '|');
		map.put('|', '-');
		this.level=new Level();
		/*Socket resaultServerSocket=null;
		try {
			resaultServerSocket = new Socket(SettingsWrapper.getSetting().getResaultServerIp(),
											 SettingsWrapper.getSetting().getResaultServerPort());
			
		} catch (UnknownHostException e) {System.out.println("error openning socket");} 
		catch (IOException e) {System.out.println("error openning socket");}
		
		try {
			outToResaultServer=resaultServerSocket.getOutputStream();
			inFromResaultServer=resaultServerSocket.getInputStream();
		} catch (IOException e) {
			System.out.println("Couldnt get Resault server's stream");
		}*/
	}
	
	@Override
	public void solve()
	{
		String line=null;
		Socket solutionServerSocket=null;
		char[][] maze=this.level.getLevel();
		//Crating socket for the client
		try {
			solutionServerSocket = new Socket(SettingsWrapper.getSetting().getSolutionServerIp(),
											  SettingsWrapper.getSetting().getSolutionServerPort());
		}catch(IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Connection Error");
			alert.setHeaderText("Could not connect to solution server");
			alert.setContentText("Connection Refused on IP: "+ SettingsWrapper.getSetting().getSolutionServerIp()+
								"Port: " + SettingsWrapper.getSetting().getSolutionServerPort());

			alert.show();
			/*System.out.println("Connection Refused on IP: "+ SettingsWrapper.getSetting().getSolutionServerIp()+
								"Port: " + SettingsWrapper.getSetting().getSolutionServerPort());*/
			return;
		}
		//Get Stream from socket
		try {
			outToSolutionServer=solutionServerSocket.getOutputStream();
			inFromSolutionServer=solutionServerSocket.getInputStream();
		}catch(IOException e) {System.out.println("Stream error");}
		//Send Problem to server
		PrintWriter writer = new PrintWriter(outToSolutionServer);
		for(int i=0; i<this.level.getLevel().length;i++)
		{
			writer.println(maze[i]);
			writer.flush();
		}
		writer.println("done");
		writer.flush();
		//get the solution from the server
		BufferedReader reader = new BufferedReader(new InputStreamReader(inFromSolutionServer));
		try{
			while (!(line = reader.readLine()).equals("done")) {
			int row=line.charAt(0)-'0';
			int col =line.charAt(2)-'0';
			char[][] newMaze=arrayCopyCTOR(maze);
			newMaze[row][col]=  getRotatedChar(newMaze[row][col], line.charAt(4)-'0');
			maze=newMaze;
			Level newLevel = new Level(newMaze);
			this.level=newLevel;
			 setChanged();
			 notifyObservers();
			}
		}catch(IOException e) {System.out.println("Error on reading Solution From Server."+e.getMessage());}
		finally {
			if(writer!=null) {
				writer.close();
			}
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {System.out.println("error closing 'inFromSolutionServer' stream");}
			}
			if(solutionServerSocket!=null) {
				try {
					solutionServerSocket.close();
				} catch (IOException e) {System.out.println("error closing 'solutionServerSocket' stream");}
			}

		}
}
	
	@Override
	public Level getSolution() {
		return null;
	}
	public void setLevel(List<String> list)
	{
		char[][] maze = new char[list.size()][list.get(0).length()];
		for(int i=0;i<list.size();i++)
			maze[i] = list.get(i).toCharArray();
		Level level=new Level(maze);
		this.level = level;
		setChanged();
		notifyObservers();
	}
	@Override
	public void setLevel(Pair<Double> lastCellClicked,Pair<Double> currentWindowSize) {
		if(this.level!=null) {
			char[][]maze=this.level.getLevel();
		
			int coll=(int)(lastCellClicked.getFirst()/(currentWindowSize.getFirst()/maze[0].length));
			int row=(int)(lastCellClicked.getSecond()/(currentWindowSize.getSecond()/maze.length));
			char cell=getRotatedChar(maze[row][coll],1);
			char[][] maze1 = arrayCopyCTOR(maze);
			maze1[row][coll] = cell;
			Level level=new Level(maze1);
			this.level = level;
			setChanged();
			notifyObservers();
		}
	}
	private char getRotatedChar(char c,int numToRotate) {
		if(!map.containsKey(c)) return c;
		char newC=c;
		while(numToRotate>0) {
			newC=map.get(newC);
			numToRotate--;
		}
		return newC;
	}
	private char[][] arrayCopyCTOR(char[][] a2)
	{
		int row = a2.length;
		int col = a2[0].length;
		char[][] a1 = new char[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				a1[i][j] = a2[i][j];
			}
		}
		return a1;
	}
	@Override
	public Level getLevel() {
		return this.level;
	}
	
	@Override
	public void loadLevelFromFile(String path) {
		File f = new File(path);
		List<String> list = new LinkedList();
		BufferedReader inFromFile = null;
		try {
			inFromFile = new BufferedReader(new FileReader(f));
			String line;
		    while ((line = inFromFile.readLine()) != null) {
		    		list.add(line);
		    }
		} catch (IOException e) {System.out.println("could not open this file :( " + e.getMessage());}
		try {
			inFromFile.close();
		} catch (IOException e) {e.printStackTrace();}
		this.setLevel(list);
	}
	
	//Queries
	public void saveLevel(Level level,int steps,long time) {
		PrintWriter writer=null;
		if(outToResaultServer!=null) {
			writer=new PrintWriter(outToResaultServer);
			for(char[] line:level.getLevel())
				writer.write(line);
			writer.write("level done");
			writer.write(steps);
			writer.write((int)time);
			writer.flush();
		}
		else {
			System.out.println("Error saving level");
		}
		
	}
	
	
	//Queries
	@Override
	public void saveLevel(Level level, User user, int steps, long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LinkedList<User> getBestOverallPlayers(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Level> getToughestLevels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<User> getBestStepsPlayers(int numOfBestPlayers, Level level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<User> getBestTimePlayers(int numOfBestPlayers, Level level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Level> levelsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
