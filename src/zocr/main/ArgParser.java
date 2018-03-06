package zocr.main;

public class ArgParser {
	
	public static String[] parseArgs(String[] args) {
		
		String[] processedArgs = new String[4];
		
		//Get training dataset filename
		if(args.length > 2 && args[0] != null) {
			processedArgs[0] = args[0];
		} else {
			processedArgs[0] = "";
		}
		
		//Get testing dataset filename
		if(args.length > 2 && args[1] != null) {
			processedArgs[1] = args[1];
		} else {
			processedArgs[1] = "";
		}
		
		//Check if an integer is present by trying to parse it.
		if(args.length > 3 && args[2] != null) {
			try {
				Integer.parseInt(args[2]);
				processedArgs[2] = args[2];
			} catch(NumberFormatException e) {
				processedArgs[2] = "NO_INT";
			}
		} else {
			processedArgs[2] = "";
		}
		
		//Check whether to save results to log file. Uses regular expressions for case-insensitive checking
		if(args.length >= 4 && args[3] != null) {
			if (args[3].matches("(?i)f|false|no")) {
				processedArgs[3] = "f";
			} else if (args[3].matches("(?i)t|true|yes")) {
				processedArgs[3] = "t";
			} else {
				processedArgs[3] = "f";
			}
		}
		
		return processedArgs;
		
	}

}
