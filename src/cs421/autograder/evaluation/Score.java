package cs421.autograder.evaluation;

public class Score {

	//Syntax/Grammar
	private int wordOrderScore; 				//1a
	private int subjectVerbAgreementScore;		//1b
	private int verbUsageScore;					//1c
	private int sentenceFormationScore;			//1d
	
	//Semantics (meaning) / Pragmatics (general quality)
	private int coherenceScore;					//2a
	private int topicAdherenceScore;			//2b
	
	//Length of the essay
	private int essayLengthScore;				//3a
	
	public void setWordOrderScore(int score) {		
		this.wordOrderScore = score;
	}
	
	public int getWordOrderScore() {		
		return this.wordOrderScore;
	}

	public void setSubjectVerbAgreementScore(int score) {
		this.subjectVerbAgreementScore = score;		
	}
	
	public int getSubjectVerbAgreementScore() {		
		return this.subjectVerbAgreementScore;
	}

	public void setVerbUsageScore(int score) {
		this.verbUsageScore = score;		
	}
	
	public int getVerbUsageScore() {		
		return this.verbUsageScore;
	}

	public void setSentenceFormationScore(int score) {
		this.sentenceFormationScore = score;			
	}	
	
	public int getSentenceFormationScore() {		
		return this.sentenceFormationScore;
	}

	public void setCoherenceScore(int score) {
		this.coherenceScore = score;
	}
	
	public int getCoherenceScore() {		
		return this.coherenceScore;
	}

	public void setTopicAdherenceScore(int score) {
		this.topicAdherenceScore = score;		
	}
	
	public int getTopicAdherenceScore() {		
		return this.topicAdherenceScore;
	}

	public void setEssayLengthScore(int score) {
		this.essayLengthScore = score;		
	}
	
	public int getEssayLengthScore() {		
		return this.essayLengthScore;
	}

	public Score(){
		
		this.coherenceScore = 0;
		this.essayLengthScore = 0;
		this.sentenceFormationScore = 0;
		this.subjectVerbAgreementScore = 0;
		this.topicAdherenceScore = 0;
		this.verbUsageScore = 0;
		this.wordOrderScore = 0;
	}
	
	public float getFinalScore() {
		
		float finalScore = 0.0f;
		
		int weightedSyntaxScore = 
				this.getWordOrderScore() + this.getSubjectVerbAgreementScore() 
				+ this.getVerbUsageScore() + (2 * this.getSentenceFormationScore());
		
		int weightedSemanticsScore = 
				this.getCoherenceScore() + (3 * this.getTopicAdherenceScore());		
		
		finalScore = 
				(weightedSyntaxScore + weightedSemanticsScore + this.getEssayLengthScore())/10.0f;
	
		//rounding to the nearest 0.5
		return Math.round(finalScore * 2.0f)/2.0f;
	}		
}
