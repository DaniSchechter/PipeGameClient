package model;

public class Level {
	private char[][] level = {{'s','|','|','J'},
							  {' ',' ',' ','-'},
							  {'g','|','|','L'}};
							  
	
	public Level(char[][] level) {
		super();
		this.level = level;
	}
	
	public Level() {
		
	}

	public char[][] getLevel() {
		return level;
	}

	public void setLevel(char[][] level) {
		int rows=level.length;
		int cols=level[0].length;
		this.level=new char[rows][cols];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				this.level[i][j]=level[i][j];
			}
		}
		
	}
	@Override
	public boolean equals(Object other) {
		if(other==null)return false;
		if(other==this)return true;
		if(!(other instanceof Level)) return false;
		Level otherLevel= (Level)other;
		
		int numRows=level.length;
		int numCols=level[0].length;
		for(int i=0;i<numRows;i++)
			for(int j=0;j<numCols;j++)
				if(level[i][j]!=otherLevel.getLevel()[i][j]) return false;
		return true;
	}
}
