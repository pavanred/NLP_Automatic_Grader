package cs421.autograder.grader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.ArrayList;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class AutoGrader {
	
	public AutoGrader(){

	}
	
	/**
	 * Segment the essay text into sentences based on punctuation, capitalization, finite verb hypothesis
	 * @param Essay text encapsulated in an Essay object
	 * @author pavan
	 */
	public void segmentEssay(Essay essay) {
		
		if(essay.getRawText() == "") return;		
	}	
	
	public ArrayList<PosTag> getStanfordPosTags(String text){
	
		ArrayList<PosTag> posTags = new ArrayList<PosTag>();
		String word = "";
		String posText = "";
		int separatorIndex = 0;
		
		try {
			
			//MaxentTagger tagger = new MaxentTagger(System.getProperty("user.dir") + "/Models/bidirectional-distsim-wsj-0-18.tagger");
			MaxentTagger tagger = new MaxentTagger(System.getProperty("user.dir") + "/Models/left3words-wsj-0-18.tagger");
			String tagged = tagger.tagString(text);
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
			modelIn = new FileInputStream(System.getProperty("user.dir") + "/Models/en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			
			POSTaggerME tagger = new POSTaggerME(model);
			tags = tagger.tag(textArray);
			
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
