package zocr.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.csv.*;

import zocr.models.CharImage;

public class DatasetHandler {
	
	public static final String DATASET_DIR = "datasets/";
	
	public static ArrayListValuedHashMap<Integer, CharImage> loadDatasetFromCsv(String dataset) throws IOException {

		//MultiMap<Integer, CharImage[]> images = new MultiMap<Integer, CharImage[]>();
		ArrayListValuedHashMap<Integer, CharImage> images = new ArrayListValuedHashMap<Integer, CharImage>();
		
		Path filePath = Paths.get(DATASET_DIR + dataset);
		System.out.println("Loading file " + filePath);
		
		Reader reader = Files.newBufferedReader(filePath);
		CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
			
		for(CSVRecord record : parser.getRecords()) {
			CsvImageResult result = extractImageFromRecord(record);

			images.put(result.imageClass, result.image);
			
			//System.out.println("image constructed belonging to class " + result.imageClass);
			
		}
		
		return images;
		
	}
	
	private static CsvImageResult extractImageFromRecord(CSVRecord record) {
		
		int[] checksums = new int[CharImage.TOTAL_SUMS];
		int imageClass = Integer.parseInt(record.get(checksums.length));
		
		for(int i = 0; i < checksums.length; i++) {
			checksums[i] = Integer.parseInt(record.get(i));
		}
		
		//System.out.println("Record " + record.getRecordNumber() + ": " + imageClass);
		
		CharImage image = new CharImage(checksums);
		
		return new CsvImageResult(image, imageClass);
		
	}
	
	public static class CsvImageResult {
		
		public CharImage image;
		public int imageClass;
		
		public CsvImageResult(CharImage image, int imageClass) {
			this.image = image;
			this.imageClass = imageClass;
		}
		
	}

}