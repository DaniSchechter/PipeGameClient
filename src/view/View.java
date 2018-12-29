package view;

import model.Level;

public interface View {
	public Level getLevel();
	public void setLevel(Level level); 
	public Pair<Double> getLastCellClicked();
	public Pair<Double> getCurrentWindowSize();
	public void loadlevel();
}
