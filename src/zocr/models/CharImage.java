package zocr.models;

public class CharImage {
	
	public static final int TOTAL_SUMS = 64;
	public static final int WIDTH_SUMS = 8;
	public static final int HEIGHT_SUMS = 8;
	
	private int[] checksums;

	public CharImage(int[] checksums) {
		
		this.checksums = new int[TOTAL_SUMS];
		
		for(int i = 0; i < TOTAL_SUMS; i++) {
			this.checksums[i] = checksums[i];
		}
		
	}

	public static float getSimilarityMetric(CharImage image1, CharImage image2) {
		
		float similarity = 0;
		
		float sumBuffer = 0;
		
		for(int i = 0; i < TOTAL_SUMS; i++) {
			float difference = Math.abs(image2.getChecksum(i) - image1.getChecksum(i));
			sumBuffer += difference * difference;
		}
		
		System.out.println("Computed similarity metric between images: " + similarity);
		return similarity;
	
	}
	
	public int getChecksum(int i) {
		return checksums[i];
	}
	
	public int[] getChecksums() {
		return checksums;
	}

	public void setChecksums(int[] checksums) {
		this.checksums = checksums;
	}

}