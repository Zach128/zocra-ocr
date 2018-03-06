package zocr.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import zocr.algorithms.KNearestNeighbour;
import zocr.io.DatasetHandler;
import zocr.io.LogHandler;
import zocr.models.CharImage;

public class MainClass {
	
	public static void main(String[] args) {

		//Get the command-line arguments
		String[] processedArgs = ArgParser.parseArgs(args);
		
		if(processedArgs[3].equalsIgnoreCase("t")) {
			LogHandler.initialise(processedArgs[1]);
		}
		
		ArrayListValuedHashMap<Integer, CharImage> images;
		ArrayListValuedHashMap<Integer, CharImage> testImages;
		
		//Attempt to load datasets
		try {
			images = DatasetHandler.loadDatasetFromCsv(processedArgs[0]);
			testImages = DatasetHandler.loadDatasetFromCsv(processedArgs[1]);
		} catch(IOException e) {
			System.out.println("ERROR: Failed to read datasets. Please check files are present and that the arguments are correct.");
			e.printStackTrace(System.out);
			return;
		}
		//Set K for the KNN algorithm
		int kValue = 0;
		try {
			kValue = Integer.parseInt(processedArgs[2]);
		} catch(NumberFormatException e) {
			System.out.println("WARN: Failed to read integer for K. Defaulting to value of 3.");
			System.out.println(e.getMessage());
			kValue = 3;
		} finally {
			KNearestNeighbour.K = kValue;
		}
		//Assign the training dataset to the KNN algorithm
		KNearestNeighbour.trainImages = images;
		
		List<int[]> results = new ArrayList<int[]>(images.size());
		
		int correctResults = 0;
		
		for(int digit = 0; digit < 10; digit++) {
			for(int i = 0; i < testImages.get(digit).size(); i++) {
				int result = KNearestNeighbour.classifyImage(testImages.get(digit).get(i), null);
				//Store result as candidate digit and correct answer
				results.add(new int[] {result, digit});
				//Record correct results for calculating accuracy
				if(result == digit) {
					correctResults++;
				}
			}
		}
		
		float accuracy = (correctResults * 100.0f) / results.size();
		System.out.println("Correct results: " + correctResults + "\nIncorrect results: " + (testImages.size() - correctResults) + "\nAccuracy: " + accuracy + "%");
		
	}

}