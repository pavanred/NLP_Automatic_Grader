package cs421.autograder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cs421.autograder.IO.FileInput;
import cs421.autograder.IO.FileOutput;
import cs421.autograder.grader.AutoGrader;
import cs421.autograder.grader.Essay;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		FileInput inputfile;
		FileOutput outputfile;
		Essay essay;
		ArrayList<String> essayText;
		AutoGrader grader;
		File[] fileList;
		
		String trainingSetPath = System.getProperty("user.dir") + "/TrainingSet/";
		String gradesDirPath = System.getProperty("user.dir") + "/output/grades.txt";
		
		File trainingSetDir = new File(trainingSetPath);		
		
		//get list of essay files
		fileList = trainingSetDir.listFiles();
		
		if(fileList.length < 0)
			throw new FileNotFoundException("No essay files found");
		
		inputfile = new FileInput();
		outputfile = new FileOutput(gradesDirPath);
		grader = new AutoGrader();
		essayText = new ArrayList<String>();
		
		//loop through every essay in the training set and learn the evaluation scheme
		for(File file : fileList){
			
			essay = new Essay();
			
			essayText = inputfile.readInputFile(file.getAbsolutePath());
			//essay.setRawText(essayText);
			essay.setSentences(essayText);

			for(int i=0; i < essay.getSentences().size(); i++){
			
				String line = essay.getSentences().get(i);
				
				if(!line.equals("")){
					//essay.addPosTag(grader.getOpennlpPosTags(line.split(" ")));
					essay.addPosTag(grader.getStanfordPosTags(line));
					//essay.addParsedSentence(grader.getParseTree(line));
				}
			}
			
			grader.gradeEssayLength(essay);  //TODO capitalization
			
			grader.segmentEssay(essay);
			
			for(int i=0; i < essay.getDetectedSentences().size(); i++){
				
				String text = essay.getDetectedSentences().get(i);
				
				if(!text.equals("")){
					
					essay.addParsedSentence(grader.getParseTree(text));
				}
			}
			
			grader.gradeSyntax(essay);
			
			grader.gradeTopicCoherence(essay);
			
			grader.gradeTextCoherence(essay);
							
			outputfile.writeOutput(file.getName().replace(".txt", "") , essay.getEssayScore());			
		}
		
		outputfile.Finished();
	}
}
