package controller;
import java.nio.file.Path;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import settings.SettingsWrapper;
import view.View;

public class Controller implements Observer{
	private Model model;
	private View view;
	
	public void setModel(Model m) {
		this.model=m;
	}
	
	public void setView(View v) {
		this.view=v;
	}
	@Override
	public void update(Observable observable, Object o) {
		if(observable==view) { //request to solve level from the solutionServer
			if(o!=null && ((String)o).equals("solve")) {
				model.solve();
				
			}
			else if (o!=null && ((String)o).contains(SettingsWrapper.getSetting().getFileChooserExtention())) {
				model.loadLevelFromFile((String)o);
			}
			else {this.model.setLevel(this.view.getLastCellClicked(), this.view.getCurrentWindowSize());}

		}
		else {
			view.setLevel(model.getLevel());
		}
	}
	
	
}
