package zocr.models;

import java.util.Comparator;

public class ImageDistPair implements Comparable<ImageDistPair> {

	private double distance = 0.0;
	private CharImage[] images;
	private int imageClass;
	
	public ImageDistPair(CharImage sourceImage, CharImage targetImage, double distance, int imageClass) {
		
		this.distance = distance;
		this.images = new CharImage[] {sourceImage, targetImage};
		this.imageClass = imageClass;
		
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public CharImage[] getImages() {
		return images;
	}

	public void setImages(CharImage[] images) {
		this.images = images;
	}

	public int getImageClass() {
		return imageClass;
	}

	public void setImageClass(int imageClass) {
		this.imageClass = imageClass;
	}
	
	//Use to sort by distance in ascending order
	public static final Comparator<ImageDistPair> SHORTEST_DIST = new Comparator<ImageDistPair>() {
        public int compare(ImageDistPair result, ImageDistPair result1) {
        	return Double.compare(result.distance, result1.distance);
        }
    };

	@Override
	public int compareTo(ImageDistPair arg0) {
		return (int) (this.distance - arg0.distance);
	}
	
}
