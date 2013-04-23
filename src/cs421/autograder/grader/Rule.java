package cs421.autograder.grader;

public class Rule {

	private PartOfSpeech pos1;
	private PartOfSpeech pos2;
	
	public void setPos1(PartOfSpeech _pos){
		this.pos1 = _pos;
	}

	public PartOfSpeech getPos1(){
		return this.pos1;
	}
	
	public void setPos2(PartOfSpeech _pos){
		this.pos2 = _pos;
	}

	public PartOfSpeech getPos2(){
		return this.pos2;
	}
	
	public Rule(PartOfSpeech pos1, PartOfSpeech pos2){
		
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
}
