package model;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import view.Pair;

public interface Model {
	
	public void setLevel(List<String> list);
	public void setLevel(Pair<Double> lastCellClicked,Pair<Double> currentWindowSize);
	public Level getLevel();

	public void solve();//do Something
	public Level getSolution(); //get Something
	
	public void loadLevelFromFile(String path);
	
	//Queries
	public void saveLevel(Level level,User user,int steps,long time);
	public LinkedList<User> getBestOverallPlayers(int num);
	public LinkedList<Level> getToughestLevels();
	public LinkedList<User> getBestStepsPlayers(int numOfBestPlayers,Level level);
	public LinkedList<User> getBestTimePlayers(int numOfBestPlayers,Level level);
	public LinkedList<Level> levelsByUser(User user);
}
