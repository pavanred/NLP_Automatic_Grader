package cs421.autograder.grader;

import java.util.ArrayList;

import cs421.autograder.IO.FileInput;
import cs421.autograder.evaluation.Score;

public class AutoGrader {	
	
	/**
	 * Read the essay from the input file, segment the essay into sentences
	 * @return object of Essay type
	 * @author pavan
	 */
	public Essay readEssay(String filepath){
		
		FileInput inputfile;
		ArrayList<String> sentences;
		Essay essay;
		
		inputfile = new FileInput();		
		String essayText = inputfile.readInputFile(filepath);
		//TODO read grades from grades.txt
		
		sentences = segmentEssay(essayText);
		
		essay = new Essay();
		essay.setSentences(sentences);
		
		return essay;
	}

	private ArrayList<String> segmentEssay(String essay) {
		// TODO Auto-generated method stub
		return null;
	}

	public Score evaluate(Essay essay) {
		// TODO Auto-generated method stub
		return null;
	}
}
