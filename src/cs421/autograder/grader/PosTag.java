package cs421.autograder.grader;

public class PosTag {

	private String word;
	private PartOfSpeech pos;

	public void setString(String _word){
		this.word = _word;
	}

	public void setPartOfSpeech(PartOfSpeech _pos){
		this.pos = _pos;
	}

	public String getString(){
		return this.word;
	}

	public PartOfSpeech getPartOfSpeech(){
		return this.pos;
	}

	public PosTag(String _word, PartOfSpeech _pos){

		//this.word = _word.replaceAll("[^a-zA-Z]","");
		this.word = _word;
		this.pos = _pos;
	}

	@Override
	public boolean equals(Object newObj){

	    if (newObj == null) return false;
	    if (newObj == this) return true;	    
	    if (!(newObj instanceof PosTag))return false;

	    PosTag instance = (PosTag)newObj;

	    return instance.word == this.word ||
	    		(this.word != "" && this.word.equals(instance.getString())) &&
	    		instance.pos == this.pos; 
	}
}
