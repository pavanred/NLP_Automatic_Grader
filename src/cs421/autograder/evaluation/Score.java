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
	
	@Override
	public boolean equals(Object newObject){
		
	    if (newObject == null) return false;
	    if (newObject == this) return true;	    
	    if (!(newObject instanceof Score))return false;
	    
	    Score instance = (Score)newObject;
	    
	    return instance.coherenceScore == this.coherenceScore &&
	    		instance.essayLengthScore == this.essayLengthScore &&
	    		instance.sentenceFormationScore == this.essayLengthScore &&
	    		instance.subjectVerbAgreementScore == this.subjectVerbAgreementScore &&
	    		instance.topicAdherenceScore == this.topicAdherenceScore &&
	    		instance.verbUsageScore == this.verbUsageScore &&
	    		instance.wordOrderScore == this.wordOrderScore;
	}
	
	/**
	 * compute the final weighted score based on the syntactic, semantic and essay length 
	 * parameters and their respective weights	 
	 * @return the graded score awarded for the evaluated essay
	 * @author pavan
	 */
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
	
	public int computeScore1b(int error, int total){
		
		float score = (error/(float)total);		
		
		if(score >= 0.85)
			return 1;
		else if(score < 0.85 && score >= 0.6)
			return 2;
		else if(score < 0.6 && score >= 0.4)
			return 3; 
		else if(score < 0.4 && score >= 0.2)
			return 4; 
		else 
			return 5;
	}
	
	public int computeScore1a(int error, int total){
		
		float score = (error/(float)total);		
		
		if(score >= 0.95)
			return 1;
		else if(score < 0.95 && score >= 0.75)
			return 2;
		else if(score < 0.75 && score >= 0.55)
			return 3; 
		else if(score < 0.55 && score >= 0.35)
			return 4; 
		else 
			return 5;
	}
	
	public int computeScore1c(int error, int total){
		
		float score = (error/(float)total);		
		
		if(score >= 0.90)
			return 1;
		else if(score < 0.90 && score >= 0.7)
			return 2;
		else if(score < 0.7 && score >= 0.5)
			return 3; 
		else if(score < 0.5 && score >= 0.3)
			return 4; 
		else 
			return 5;
	}
	
	public int computeScore2b(int error, int total){
		
		float score = (error/(float)total);	
		//System.out.println(score);

		if(score >= 0.65)
			return 0;
		else if(score < 0.65 && score >= 0.55)
			return 1; 
		else if(score < 0.55 && score >= 0.45)
			return 2; 
		else if(score < 0.45 && score >= 0.35)
			return 3; 
		else if(score < 0.35 && score >= 0.25)
			return 4; 
		else 
			return 5;		
	}

	public int computeScore1d(int error, int total){
		
		float score = (error/(float)total);		
		//System.out.println(score);
		if(score >= 0.65)
			return 0;
		else if(score < 0.65 && score >= 0.55)
			return 1;
		else if(score < 0.55 && score >= 0.45)
			return 2; 
		else if(score < 0.45 && score >= 0.25)
			return 3; 
		else if(score < 0.25 && score >= 0.15)
			return 4; 
		else 
			return 5;
	}

	public int computeScore2a(float error, int size) {
		
		float score = (error/(float)size);	
		
		//System.out.println(score);
		
		if(score >= 0.30)
			return 1;
		else if(score < 0.30 && score >= 0.25)
			return 2; 
		else if(score < 0.25 && score >= 0.15)
			return 3; 
		else if(score < 0.15 && score >= 0.10)
			return 4; 
		else 
			return 5;
	}
}
