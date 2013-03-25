package cs421.autograder;

import cs421.autograder.evaluation.Score;
import cs421.autograder.grader.AutoGrader;
import cs421.autograder.grader.Essay;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Essay essay;
		Score score;
		String filepath = System.getProperty("user.dir") + "/TrainingSet/1.txt";
		
		AutoGrader grader = new AutoGrader();
		
		essay = grader.readEssay(filepath);
		score = grader.evaluate(essay); 
		
		float finalScore = score.getFinalScore();
		
		//TODO output
		System.out.println(finalScore);
	}

}
