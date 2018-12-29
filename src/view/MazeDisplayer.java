package view;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.BorderPane;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import model.Level;

public class MazeDisplayer extends Canvas{
	
	private Level level;
	private Map<Character,Image> map ;
	private StringProperty pipeWall;
	private StringProperty pipeS;
	private StringProperty pipeG;
	private StringProperty pipeLine;
	private StringProperty pipeCurl;
	
	public MazeDisplayer() {
		pipeS=new SimpleStringProperty();
		pipeG=new SimpleStringProperty();
		pipeLine=new SimpleStringProperty();
		pipeCurl=new SimpleStringProperty();
		pipeWall=new SimpleStringProperty();
		map = new HashMap<>();
	}
	
	public String getPipeS() {return pipeS.get();}
	public String getPipeWall() {return pipeWall.get();}
	public String getPipeG() {return pipeG.get();}
	public String getPipeLine() {return pipeLine.get();}
	public String getPipeCurl() {return pipeCurl.get();}
	
	public void setPipeS(String pipeS) {this.pipeS.set(pipeS);}
	public void setPipeWall(String pipeWall) {this.pipeWall.set(pipeWall);}
	public void setPipeG(String pipeG) {this.pipeG.set(pipeG);}
	public void setPipeLine(String pipeLine) {this.pipeLine.set(pipeLine);}
	public void setPipeCurl(String pipeCurl) {this.pipeCurl.set(pipeCurl);}
	
	public void addTypeOfCell(char c,Image img){ map.put(c, img);}
	
	public void setLevel(Level level) {
		this.level = level;
		redraw();
	}
	
	public void redraw()
	{	
		System.out.println("redraw");
		if(this.level!=null) {
			char[][] maze=level.getLevel();
			double W = this.getWidth();
			double H = this.getHeight();
			double w = W/maze[0].length ;
			double h = H/maze.length;
			GraphicsContext gc = this.getGraphicsContext2D();
			gc.clearRect(0, 0, W, H);		
			
			for(int i=0;i<maze.length;i++)
				for(int j=0;j<maze[0].length;j++) {
					gc.drawImage(map.get(maze[i][j]), j*w, i*h,w,h);
					gc.strokeLine(j*w, i*h,(j+1)*w,i*h);
					gc.strokeLine(j*w, (i+1)*h,(j+1)*w,(i+1)*h);
					gc.strokeLine(j*w, i*h,j*w,(i+1)*h);
					gc.strokeLine((j+1)*w, i*h,(j+1)*w,(i+1)*h);
				}
		}
	}
	
}
