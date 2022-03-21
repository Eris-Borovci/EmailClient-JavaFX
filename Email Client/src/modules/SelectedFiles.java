package modules;

public class SelectedFiles {
	private String fileName, absoultePath;

	public SelectedFiles(String fileName, String absoultePath) {
		super();
		this.fileName = fileName;
		this.absoultePath = absoultePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAbsoultePath() {
		return absoultePath;
	}

	public void setAbsoultePath(String absoultePath) {
		this.absoultePath = absoultePath;
	}
}
