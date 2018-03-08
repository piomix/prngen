package prngenerate;

public class PrnGenerateResult {
	public static final int OK = 0;
	public static final int COULDNOTREADFILE = -1;
	public static final int ERRORPROCESSINGFILE = -2;
	public static final int OTHERERROR = -3;
	
	public int status = OK;
	
	public String lastFileName = "";
	public String errMsg = "";
	public String exceptionMsg = "";
	
	public boolean ok() {
		return (status == OK);
	}
}
