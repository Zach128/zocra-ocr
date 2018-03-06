package zocr.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.output.TeeOutputStream;

public class LogHandler {
	
	public static final String LOG_LOCATION = "logs/";
	
	private static boolean isInitialised = false;
	private static File logFile = null;
	private static PrintStream ps = null;
	
	public static void initialise(String logSuffix) {
		if(!isInitialised) {
			//Build the file name to save the log as
			String fileName = LOG_LOCATION;
			
			//Add date and time prefix
			fileName += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			//Filter out the csv file extension if present
			String suffix = logSuffix.replaceFirst(".csv", "");
			fileName += "-" + suffix + ".txt";
			
			//Check if the logs directory exists and if not, create it
			File logDirectory = new File(LOG_LOCATION);
			if(!logDirectory.exists()) {
				logDirectory.mkdir();
			}
			
			logFile = new File(fileName);
			
			try {
				
			    FileOutputStream fos = new FileOutputStream(logFile);
			    
			    //Initialise and override System.out with a dual-target stream
			    TeeOutputStream myOut = new TeeOutputStream(System.out, fos);
			    ps = new PrintStream(myOut, true);
			    System.setOut(ps);
			    isInitialised = true;
			    System.out.println("Log handler initialised\r\n");
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
	public static void cleanup() {
		if(isInitialised) {
			ps.close();
			System.out.close();
			isInitialised = false;	
		}
	}

}
