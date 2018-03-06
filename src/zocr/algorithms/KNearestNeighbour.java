package zocr.algorithms;

import java.util.Arrays;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import zocr.models.CharImage;
import zocr.models.ImageDistPair;
import zocr.utils.Utils;

public class KNearestNeighbour {
	
	public static int K = 1;
	
	public static ArrayListValuedHashMap<Integer, CharImage> trainImages;
	
	public static ImageDistPair[] getKNearestNeighbours(ArrayListValuedHashMap<Integer, CharImage> trainImages, CharImage unknown) {
		ImageDistPair[] distances;
		
		//Compute distances from x to x-i
		distances = Utils.computeDistancesAgainstAllImages(trainImages, unknown);
		
		//Sort results by distance in ascending order
		Arrays.parallelSort(distances, ImageDistPair.SHORTEST_DIST);
		
		return distances;
	}
	
	//Get most relevant class based on Boyer-Moore's majority vote algorithm
	
	public static int getClassFromResults(ImageDistPair[] results) {
		int count = 0;
        int imageCandidate = 0;
        
        //Get the most frequently occurring candidate
        for (int i = 0; i < K; i++) {
            //Initial value
        	if (count == 0) {
                imageCandidate = results[i].getImageClass(); 
                // set the count as 1
                count = 1;
                continue;
            } else if (imageCandidate == results[i].getImageClass())
                count++;
            else {
                count--;
            }
        }

        if (count == 0) {
            
        	//Take the closest candidate if no result is found
            return results[0].getImageClass();

        } else {
        	
        	return imageCandidate;
        	
        }
	}
	
	public static int classifyImage(CharImage image, ArrayListValuedHashMap<Integer, CharImage> trainImages) {
		ImageDistPair[] results;
		if(trainImages != null) {
			results = getKNearestNeighbours(trainImages, image);
		} else {
			results = getKNearestNeighbours(KNearestNeighbour.trainImages, image);
		}
		int result = getClassFromResults(results);
		return result;
	}
}
