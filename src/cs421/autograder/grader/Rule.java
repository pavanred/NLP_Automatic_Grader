package cs421.autograder.grader;

import java.util.ArrayList;

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
	
	public static ArrayList<Rule> getSyntaxRules(){
		
		ArrayList<Rule> rules = new ArrayList<Rule>();
		rules.add(new Rule(PartOfSpeech.PRP$,PartOfSpeech.JJ));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.JJ));
		rules.add(new Rule(PartOfSpeech.VBP,PartOfSpeech.NN));
		rules.add(new Rule(PartOfSpeech.NNS,PartOfSpeech.PRP$));
		rules.add(new Rule(PartOfSpeech.NN,PartOfSpeech.PRP$));
		rules.add(new Rule(PartOfSpeech.VBD,PartOfSpeech.DT));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.PRP));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.RB));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.VB));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.VBZ));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.VBG));
		rules.add(new Rule(PartOfSpeech.VBP,PartOfSpeech.VBP));
		rules.add(new Rule(PartOfSpeech.PRP$,PartOfSpeech.VBG));
		rules.add(new Rule(PartOfSpeech.VBD,PartOfSpeech.VBG));
		rules.add(new Rule(PartOfSpeech.VBG,PartOfSpeech.VBZ));
		rules.add(new Rule(PartOfSpeech.PRP,PartOfSpeech.IN));
		rules.add(new Rule(PartOfSpeech.NN,PartOfSpeech.NNP));
		rules.add(new Rule(PartOfSpeech.NNP,PartOfSpeech.NNP));
		rules.add(new Rule(PartOfSpeech.NNP,PartOfSpeech.PRP));
		rules.add(new Rule(PartOfSpeech.VBZ,PartOfSpeech.NN));
		rules.add(new Rule(PartOfSpeech.NN,PartOfSpeech.PRP));
		rules.add(new Rule(PartOfSpeech.NNS,PartOfSpeech.PRP));	
		
		return rules;
		
	}
}
