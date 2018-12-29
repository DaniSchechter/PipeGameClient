package settings;

public class SettingsWrapper {

	private static class SettingsHolder{
		public static final Settings settings =new Settings();
	}
	public static Settings getSetting() {
		return SettingsHolder.settings;
	}
	public static class Settings{
		
		private String solutionServerIp;
		private int solutionServerPort;
		private String resaultServerIp;
		private int resaultServerPort;
		private String FileChooserExtention;
		private String initialDirectory;
		

		public Settings() {
			this.solutionServerIp="127.0.0.1";
			this.solutionServerPort=6400;
			this.resaultServerIp="127.0.0.1";
			this.resaultServerPort=6401;
			this.FileChooserExtention = ".txt";
			this.initialDirectory = "./resources/mazelevels";
		}
		
		public String getSolutionServerIp() {return solutionServerIp;}
		
		public void setSolutionServerIp(String solutionServerIp) {
			this.solutionServerIp = solutionServerIp;
		}

		public int getSolutionServerPort() {return solutionServerPort;}

		public void setSolutionServerPort(int solutionServerPort) {
			this.solutionServerPort = solutionServerPort;
		}

		public String getResaultServerIp() {
			return resaultServerIp;
		}

		public void setResaultServerIp(String resaultServerIp) {
			this.resaultServerIp = resaultServerIp;
		}

		public int getResaultServerPort() {
			return resaultServerPort;
		}

		public void setResaultServerPort(int resaultServerPort) {
			this.resaultServerPort = resaultServerPort;
		}
		public String getFileChooserExtention() {
			return FileChooserExtention;
		}

		public void setFileChooserExtention(String fileChooserExtention) {
			FileChooserExtention = fileChooserExtention;
		}

		public String getInitialDirectory() {
			return initialDirectory;
		}

		public void setInitialDirectory(String initialDirectory) {
			this.initialDirectory = initialDirectory;
		}

	}
	
}
