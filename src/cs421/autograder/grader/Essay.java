package cs421.autograder.grader;

import java.util.ArrayList;

import cs421.autograder.evaluation.Score;

public class Essay {

	//private String rawText;
	private ArrayList<String> sentences;
	private Score essayScore;
	private ArrayList<ArrayList<PosTag>> posTags;
	private Integer length;
	
	public ArrayList<ArrayList<PosTag>> getPosTags(){
		return this.posTags;
	}
	
	public void setPosTags(ArrayList<ArrayList<PosTag>> _posTags){
		this.posTags = _posTags;
	}
	
	public ArrayList<String> getSentences(){
		return this.sentences;
	}
	
	public void setSentences(ArrayList<String> _sentences){
		this.sentences = _sentences;
	}
	
	public Score getEssayScore(){
		return this.essayScore;
	}
	
	public void setEssayScore(Score _score){
		this.essayScore = _score;
	}
	
	public Integer getLength(){
		return this.length;
	}
	
	public void setLength(Integer _len){
		this.length = _len;
	}
	
	public Essay(){
		this.sentences = new ArrayList<String>();
		this.essayScore = new Score();
		this.posTags = new ArrayList<ArrayList<PosTag>>();
		this.length = 0;
		//this.rawText = "";
	}
	
	/*public String getRawText(){
		return this.rawText;
	}
	
	public void setRawText(String _rawText){
		this.rawText = _rawText;
	}*/
	
	/**
	 * Add a sentence of the essay to the list
	 * @param sentence from the essay
	 * @author pavan
	 */
	public void addSentence(String sentence){
		
		if(this.sentences == null) return;		
		this.sentences.add(sentence);
	}	
	
	public void addPosTag(ArrayList<PosTag> postags){
		
		if(this.posTags == null) return;		
		this.posTags.add(postags);
	}	
	
	@Override
	public boolean equals(Object newEssay){
		
	    if (newEssay == null) return false;
	    if (newEssay == this) return true;	    
	    if (!(newEssay instanceof Essay))return false;
	    
	    Essay instance = (Essay)newEssay;
	    
	    return instance.essayScore.equals(this.essayScore) &&
	    		/*instance.rawText == this.rawText ||
	    		(this.rawText != "" && this.rawText.equals(instance.getRawText())) &&*/
	    		instance.sentences.equals(this.sentences) &&
	    		instance.posTags.equals(this.posTags);	    		
	}
	
}
