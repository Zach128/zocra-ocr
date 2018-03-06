package zocr.utils;

import java.util.Map.Entry;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import zocr.models.CharImage;
import zocr.models.ImageDistPair;

public class Utils {
	
	public static ImageDistPair[] computeDistancesAgainstAllImages(ArrayListValuedHashMap<Integer, CharImage> imageSet, CharImage target) {
		
		ImageDistPair[] results = new ImageDistPair[imageSet.size()];
		
		int index = 0;
		double distBuffer = 0;
		for(Entry<Integer, CharImage> entry : imageSet.entries()) {
			distBuffer = euclDistance(entry.getValue().getChecksums(), target.getChecksums());
			results[index] = new ImageDistPair(entry.getValue(), target, distBuffer, entry.getKey());
			index++;
		}
		
		return results;
		
	}
	
	public static double euclDistance(int[] coords1, int[] coords2) {
		
		double dist = 0;
		double diffBuffer = 0;
		for(int i = 0; i < coords1.length; i++) {
			diffBuffer = (double) coords1[i] - coords2[i];
			dist += Math.pow(diffBuffer, 2);
		}
		
		//System.out.println("value before root: " + dist);
		dist = Math.sqrt(dist);
		
		return dist;
	}

}