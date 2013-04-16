package cs421.autograder.grader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.ArrayList;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class AutoGrader {
	
	private MaxentTagger stanfordTagger = null;
	private POSModel opennlpModel = null;
	private POSTaggerME opennlpTagger = null;
	
	public AutoGrader(){

	}
	
	public ArrayList<PosTag> getStanfordPosTags(String text){
	
		ArrayList<PosTag> posTags = new ArrayList<PosTag>();
		String word = "";
		String posText = "";
		int separatorIndex = 0;
		
		try {
			
			if(stanfordTagger == null){
				
				//MaxentTagger stanfordTagger = new MaxentTagger(System.getProperty("user.dir") + "/Models/bidirectional-distsim-wsj-0-18.tagger");
				stanfordTagger = new MaxentTagger(System.getProperty("user.dir") + "/Models/left3words-wsj-0-18.tagger");
			}			
			
			String tagged = stanfordTagger.tagString(text);
			//System.out.println(tagged);
			for(String wordTag : tagged.split(" ")){
				
				separatorIndex = wordTag.indexOf('/');
				word = wordTag.substring(0, separatorIndex);
				posText = wordTag.substring(separatorIndex + 1);
								
				posTags.add(new PosTag(word, getPOS(posText)));
			}	
			
			return posTags;
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Error] Stanford POS tagger model loading failed");
			return new ArrayList<PosTag>();
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("[Error] Part of speech tagging error");
			return new ArrayList<PosTag>();
		}
	}
	
	public ArrayList<PosTag> getOpennlpPosTags(String[] textArray){
		
		InputStream modelIn = null;
		String[] tags;
		ArrayList<PosTag> posTags = new ArrayList<PosTag>();

		try {
			
			if(opennlpModel == null || opennlpTagger == null){
				modelIn = new FileInputStream(System.getProperty("user.dir") + "/Models/en-pos-maxent.bin");
				opennlpModel = new POSModel(modelIn);
				
				opennlpTagger = new POSTaggerME(opennlpModel);
			}
			
			
			tags = opennlpTagger.tag(textArray);
			
			if(textArray.length != tags.length)
				throw new Exception();
			
			for(int i=0; i < tags.length; i++){

				posTags.add(new PosTag(textArray[i], getPOS(tags[i])));
			}
			
			return posTags;
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Error] OpenNLP POS tagger model loading failed");
			return new ArrayList<PosTag>();
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("[Error] Part of speech tagging error");
			return new ArrayList<PosTag>();
		}
		finally {
			if (modelIn != null) {
				
				try {
		    		modelIn.close();
				}
				catch (IOException e) {	}
			}
		}
	}	
	
	/**
	 * grade the essay text based on punctuation, capitalization, finite verb hypothesis
	 * @param Essay object
	 * @author pavan
	 */
	
	public void gradeEssayLength(Essay essay){
		
		int length = 0;
		
		int lines = essay.getSentences().size();
		int verbs = 0;
		int sentencebreaks = 0;
		int localverbs = 0;
		int conjunctions = 0;
		
		ArrayList<PosTag> tags = new ArrayList<PosTag>();
		
		for(int i=0; i < essay.getSentences().size(); i++){
			
			localverbs = 0;
			conjunctions = 0;
			
			if(essay.getSentences().get(i).equals(""))continue;
			
			tags = essay.getPosTags().get(i);
			
			for(PosTag pos : tags){
				
				if(pos.getPartOfSpeech() == PartOfSpeech.VB || pos.getPartOfSpeech() == PartOfSpeech.VBD ||
						pos.getPartOfSpeech() == PartOfSpeech.VBG || pos.getPartOfSpeech() == PartOfSpeech.VBN ||
						pos.getPartOfSpeech() == PartOfSpeech.VBP || pos.getPartOfSpeech() == PartOfSpeech.VBZ){
					
					localverbs = localverbs + 1;
				}
				
				if(pos.getPartOfSpeech() == PartOfSpeech.CC){
					conjunctions = conjunctions + 1;
				}	
				
				if(pos.getPartOfSpeech() == PartOfSpeech.DOT)
					sentencebreaks = sentencebreaks + 1;
			}
			
			if(localverbs > conjunctions)
				verbs = verbs + (localverbs - conjunctions);
			else 
				verbs = verbs + localverbs;							
		}
		
		length = (lines > verbs)?(lines > sentencebreaks)?(lines):(sentencebreaks):(verbs > sentencebreaks)?(verbs):(sentencebreaks);
		
		if(length >= 6)
			essay.getEssayScore().setEssayLengthScore(5);
		else if(length == 5)
			essay.getEssayScore().setEssayLengthScore(4);
		else if(length == 4)
			essay.getEssayScore().setEssayLengthScore(3);
		else if(length == 3)
			essay.getEssayScore().setEssayLengthScore(2);
		else if(length < 3 && length > 0)
			essay.getEssayScore().setEssayLengthScore(1);
		else 
			essay.getEssayScore().setEssayLengthScore(0);
		
		//return essay;
	}
	
	public PartOfSpeech getPOS(String posText){
	    
    	PartOfSpeech returnPos = PartOfSpeech.FW;
    	
    	try{
    	
    		if(posText.equals("."))
				returnPos = PartOfSpeech.DOT;
			else if(posText.equals(","))
				returnPos = PartOfSpeech.COMMA;
			else if(posText.equals(":"))
				returnPos = PartOfSpeech.COLON;
			else if(posText.equals("$"))
				returnPos = PartOfSpeech.DOLLAR;
			else if(posText.equals("#"))
				returnPos = PartOfSpeech.POUND;
			else if(posText.equals("("))
				returnPos = PartOfSpeech.LBRACKET;
			else if(posText.equals(")"))
				returnPos = PartOfSpeech.RBRACKET;
			else if(posText.equals("``"))
				returnPos = PartOfSpeech.LQUOTE;
			else if(posText.equals("''"))
				returnPos = PartOfSpeech.RQUOTE;
			else
			{
				for(PartOfSpeech pos : PartOfSpeech.values()){
	    						
					if(PartOfSpeech.valueOf(posText).equals(pos))
						returnPos = pos;				
				}
			}
			
			return returnPos;
    	}
		catch(IllegalArgumentException e){
			e.printStackTrace();
			returnPos = PartOfSpeech.FW;
			System.out.println("[Error] No such part of speech, using default FW");
			return returnPos;
		}
		catch(NullPointerException e){
			e.printStackTrace();
			returnPos = PartOfSpeech.FW;
			System.out.println("[Error] No part of speech, using default FW");
			return returnPos;
		}
    }
}
