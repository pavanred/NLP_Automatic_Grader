package cs421.autograder;

import java.io.File;
import java.io.FileNotFoundException;

import cs421.autograder.IO.FileInput;
import cs421.autograder.grader.AutoGrader;
import cs421.autograder.grader.Essay;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		FileInput inputfile;
		Essay essay;
		String essayText;
		AutoGrader grader;
		File[] fileList;
		
		String trainingSetPath = System.getProperty("user.dir") + "/TrainingSet/";
		
		File trainingSetDir = new File(trainingSetPath);		
		
		//get list of essay files
		fileList = trainingSetDir.listFiles();
		
		if(fileList.length < 0)
			throw new FileNotFoundException("No essay files found");
		
		inputfile = new FileInput();	
		grader = new AutoGrader();
		
		//loop through every essay in the training set and learn the evaluation scheme
		for(File file : fileList){
			
			essay = new Essay();
			
			essayText = inputfile.readInputFile(file.getAbsolutePath());
			essay.setRawText(essayText);
			
			grader.segmentEssay(essay);
		}
	}
}
