package cs421.autograder.grader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class AutoGrader {
	
	private MaxentTagger stanfordTagger = null;
	private POSModel opennlpModel = null;
	private POSTaggerME opennlpTagger = null;	
	private ParserModel pmodel = null; 
	private InputStream parsermodelIn = null;
	private InputStream smodel = null;
		
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
	
	public Parse getParseTree(String sentence){
		
		try {	
		
			if(pmodel == null || parsermodelIn == null){
								
				parsermodelIn = new FileInputStream(System.getProperty("user.dir") + "/Models/en-parser-chunking.bin");			
				pmodel = new ParserModel(parsermodelIn);				
			}
			
			Parser parser = ParserFactory.create(pmodel);
						
			Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);	
			
			//System.out.println(sentence);
			//topParses[0].show();
			
			return topParses[0];
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (parsermodelIn != null) {
				try {
					parsermodelIn.close();
				}
				catch (IOException e) {
				}
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
		essay.setLength(length);
		
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

	public void segmentEssay(Essay essay){
		
		try {
			
			//if(smodel == null){
				
				smodel = new FileInputStream(System.getProperty("user.dir") + "/Models/en-sent.bin");	
			//}
			
			SentenceModel model = new SentenceModel(smodel);
		  
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
		  
			ArrayList<String> newsent = new ArrayList<String>();
		  
			for(int i=0; i<essay.getSentences().size(); i++){
		  
				newsent.addAll(Arrays.asList(sentenceDetector.sentDetect(essay.getSentences().get(i))));		  
			}
		  
		  essay.setDetectedSentences(newsent);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (smodel != null) {
				try {
					//smodel = null;
					smodel.close();
				}
				catch (IOException e) {
				}
			}
		}
	}
	
	public void gradeSyntax(Essay essay) {
		
		PosTag subject = new PosTag(null,null);
		PosTag mainVerb = new PosTag(null,null);
		PosTag object = new PosTag(null,null);
		Parse node;
		Parse tag;		
		Parse top;
		int berrorcount = 0;
		int cerrorcount = 0;
		int aerrorcount = 0;
		int derrorcount = 0;
		int dtotalcount = 0;
		
		ArrayList<Parse> allS = new ArrayList<Parse>();
		ArrayList<Parse> allVerbs = new ArrayList<Parse>();
		ArrayList<Parse> parse = essay.getParsedSentences();	
		
		int sCount = parse.size();
		
		ArrayList<String> nouns = PartOfSpeech.getNounTypes();		
		ArrayList<String> pronouns = PartOfSpeech.getPronounTypes();		
		ArrayList<String> verbs = PartOfSpeech.getVerbTypes();			
				
		for(int i=0; i<parse.size();i++){	
			
			subject = new PosTag(null,null);
			mainVerb = new PosTag(null,null);
			object = new PosTag(null,null);
			//parse.get(i).show();
			
			allS = getAllBFS(parse.get(i),PartOfSpeech.S.toString());
			
			if(allS.size() > 1){
				
				for(int j=0; j< allS.size(); j++){
					
					dtotalcount = dtotalcount + 1; //subject,verb,object
					subject = new PosTag(null,null);
					mainVerb = new PosTag(null,null);
					object = new PosTag(null,null);
					//parse.get(i).show();
					
					node = BFS(allS.get(j),PartOfSpeech.NP.toString());	
								
					if(node != null){
						
						tag = BFS(node,nouns);
						
						if(tag == null)
							tag = BFS(node, pronouns);
						
						if(tag != null)
							subject = new PosTag(tag.toString(), getPOS(tag.getType()));				
					}
						
					node = BFS(allS.get(j),PartOfSpeech.VP.toString());
					
					if(node != null){
						
						tag = BFS(node,verbs);
						
						if(tag != null)
							mainVerb = new PosTag(tag.toString(), getPOS(tag.getType()));	
						
						tag = BFS(node,nouns);
						
						if(tag != null)
							object = new PosTag(tag.toString(), getPOS(tag.getType()));
					}	
					
					if(mainVerb.getPartOfSpeech() == PartOfSpeech.VB);				
					else if(PartOfSpeech.getPersonType(subject.getPartOfSpeech(),subject.getString())
							!= PartOfSpeech.getPersonType(mainVerb.getPartOfSpeech(), mainVerb.getString())){
						//System.out.println(mainVerb.getString());
						//System.out.println(subject.getString());
						berrorcount = berrorcount + 1;
						sCount = sCount + 1;
					}
					
					//1d

					if(subject.getString() == null){						
						derrorcount = derrorcount + 1;
					}
					if(mainVerb.getString() == null){						
						derrorcount = derrorcount + 1;
					}
					if(object.getString() == null){						
						derrorcount = derrorcount + 1;
					}	
				}
			}
			else{
				
				dtotalcount = dtotalcount + 1; //subject,verb,object
				
				node = BFS(parse.get(i),PartOfSpeech.NP.toString());	
				
				if(node != null){
					
					tag = BFS(node,nouns);
					
					if(tag == null)
						tag = BFS(node, pronouns);
					
					if(tag != null)
						subject = new PosTag(tag.toString(), getPOS(tag.getType()));				
				}
					
				node = BFS(parse.get(i),PartOfSpeech.VP.toString());
				
				if(node != null){
					
					tag = BFS(node,verbs);
					
					if(tag != null)
						mainVerb = new PosTag(tag.toString(), getPOS(tag.getType()));	
					
					tag = BFS(node,nouns);
					
					if(tag != null)
						object = new PosTag(tag.toString(), getPOS(tag.getType()));
				}
				
				//evaluation 1b
				if(mainVerb.getPartOfSpeech() == PartOfSpeech.VB);				
				else if(PartOfSpeech.getPersonType(subject.getPartOfSpeech(),subject.getString())
						!= PartOfSpeech.getPersonType(mainVerb.getPartOfSpeech(), mainVerb.getString())){
					//System.out.println(mainVerb.getString());
					//System.out.println(subject.getString());
					berrorcount = berrorcount + 1;
				}
				/*else if(PartOfSpeech.getNumberType(subject.getPartOfSpeech(),subject.getString())
						!= PartOfSpeech.getNumberType(mainVerb.getPartOfSpeech(), mainVerb.getString())){*/
				
				//1d
				if(subject.getString() == null){					
					derrorcount = derrorcount + 1;
				}
				if(mainVerb.getString() == null){					
					derrorcount = derrorcount + 1;
				}
				if(object.getString() == null){					
					derrorcount = derrorcount + 1;
				}				
			}
						
			//evaluation 1c
			allVerbs = getAllBFS(parse.get(i), verbs);
			int[] tensecounts = new int[4];
						
			if(allVerbs.size() > 0){
				
				for(int c=0;c<allVerbs.size();c++){
					
					if(PartOfSpeech.getTenseType(getPOS(allVerbs.get(c).getType())) == Tense.PAST)
						tensecounts[0] = tensecounts[0] + 1;
					else if(PartOfSpeech.getTenseType(getPOS(allVerbs.get(c).getType())) == Tense.PRESENT_PARTICIPLE)
						tensecounts[1] = tensecounts[1] + 1;
					else if(PartOfSpeech.getTenseType(getPOS(allVerbs.get(c).getType())) == Tense.PRESENT)
						tensecounts[2] = tensecounts[2] + 1;
					else if(PartOfSpeech.getTenseType(getPOS(allVerbs.get(c).getType())) == Tense.PAST_PARTICIPLE)
						tensecounts[3] = tensecounts[3] + 1;
					
					int max = 0; 
					
					for(int s=0; s<tensecounts.length;s++){
						if(tensecounts[s] >= max)
							max = tensecounts[s];
					}
					
					cerrorcount =  allVerbs.size() - max;
				}
				
			}
			else{
				cerrorcount = cerrorcount + 1;
			}
			
			//1d
			top =  BFS(parse.get(i), PartOfSpeech.TOP.toString());
			if(top != null){
				for(Parse child : top.getChildren()){
					dtotalcount = dtotalcount + 1;
					if(!child.getType().equals(PartOfSpeech.S.toString())){
						//System.out.println("e " + child.toString() + "-" + child.getType());
						derrorcount = derrorcount + 1;
					}
				}
			}
			
			if(top.toString().contains("because") && BFS(parse.get(i), PartOfSpeech.SBAR.toString()) != null)
				derrorcount = derrorcount + 1;			
		}
		
		
		//evaluation 1a
		ArrayList<Rule> rules = Rule.getSyntaxRules();
			
		for(int p=0; p<essay.getPosTags().size();p++){
			
			if(essay.getPosTags().get(p).get(0).getPartOfSpeech() == PartOfSpeech.VB ||
					essay.getPosTags().get(p).get(0).getPartOfSpeech() == PartOfSpeech.VBD ||
					essay.getPosTags().get(p).get(0).getPartOfSpeech() == PartOfSpeech.VBG ||
					essay.getPosTags().get(p).get(0).getPartOfSpeech() == PartOfSpeech.VBN ||
					essay.getPosTags().get(p).get(0).getPartOfSpeech() == PartOfSpeech.VBP ||
					essay.getPosTags().get(p).get(0).getPartOfSpeech() == PartOfSpeech.VBZ)
				aerrorcount = aerrorcount + 1;
			
			for(int q=0; q<essay.getPosTags().get(p).size()-1;q++){
				
				for(int a=0; a<rules.size(); a++){
					if(essay.getPosTags().get(p).get(q).getPartOfSpeech() == rules.get(a).getPos1() &&
							essay.getPosTags().get(p).get(q+1).getPartOfSpeech() == rules.get(a).getPos2())
						aerrorcount = aerrorcount + 1;

				}
			}
		}
		
		//System.out.println(derrorcount / (float)dtotalcount);		
		essay.getEssayScore().setSubjectVerbAgreementScore(essay.getEssayScore().computeScore1b(berrorcount,sCount));
		essay.getEssayScore().setVerbUsageScore(essay.getEssayScore().computeScore1c(cerrorcount,allVerbs.size()));
		essay.getEssayScore().setWordOrderScore(essay.getEssayScore().computeScore1a(aerrorcount,essay.getLength()));
		essay.getEssayScore().setSentenceFormationScore(essay.getEssayScore().computeScore1d(derrorcount, dtotalcount));
		
	}
			
	public Parse BFS(Parse graph,String searchText){
		
		Queue<Parse> queue = new LinkedList<Parse>();
				
		queue.add(graph);
		
		while(!queue.isEmpty()){
			
			Parse node = (Parse)queue.remove();
						
			if(node.getType().equals(searchText)){
				return node;
			}
			
			for(int i=0; i<node.getChildCount();i++ ){	
				
				queue.add(node.getChildren()[i]);
			}			
		}
		
		return null;
	}
	
	public ArrayList<Parse> getAllBFS(Parse graph,String searchText){
		
		Queue<Parse> queue = new LinkedList<Parse>();
		ArrayList<Parse> ret = new ArrayList<Parse>();
				
		queue.add(graph);
		
		while(!queue.isEmpty()){
			
			Parse node = (Parse)queue.remove();
						
			if(node.getType().equals(searchText)){
				ret.add(node);
			}
			
			for(int i=0; i<node.getChildCount();i++ ){	
				
				queue.add(node.getChildren()[i]);
			}			
		}
		
		return ret;
	}
	
	public ArrayList<Parse> getAllBFS(Parse graph,ArrayList<String> searchText){
		
		Queue<Parse> queue = new LinkedList<Parse>();
		ArrayList<Parse> ret = new ArrayList<Parse>();
				
		queue.add(graph);
		
		while(!queue.isEmpty()){
			
			Parse node = (Parse)queue.remove();
						
			if(searchText.contains(node.getType())){
				ret.add(node);
			}
			
			for(int i=0; i<node.getChildCount();i++ ){	
				
				queue.add(node.getChildren()[i]);
			}			
		}
		
		return ret;
	}
	
	public Parse BFS(Parse graph,ArrayList<String> searchText){
		
		Queue<Parse> queue = new LinkedList<Parse>();
				
		queue.add(graph);
		
		while(!queue.isEmpty()){
			
			Parse node = (Parse)queue.remove();
						
			if(searchText.contains(node.getType())){
				return node;
			}
			
			for(int i=0; i<node.getChildCount();i++ ){	
				
				queue.add(node.getChildren()[i]);
			}			
		}
		
		return null;
	}

	public void gradeTopicCoherence(Essay essay) {
		int b2ErrorCount=0;
		
		
		ArrayList<String> commonNounList= new ArrayList<String>();
		
		ArrayList<String> commonNounTypes= new ArrayList<String>();
		commonNounTypes.add(PartOfSpeech.NN.toString());
		commonNounTypes.add(PartOfSpeech.NNS.toString());

		ArrayList<Parse> parse = essay.getParsedSentences();	

		ArrayList<Parse> allCommonNouns = new ArrayList<Parse>();

		for(int i=0; i<parse.size();i++){
			
			allCommonNouns=getAllBFS(parse.get(i),commonNounTypes);
			for(int j=0;j<allCommonNouns.size();j++){
				commonNounList.add(allCommonNouns.get(j).toString());
			}
		}
		
		ArrayList<String> storeWordList = new ArrayList<String>();
		try {
			String wordListDirPath = System.getProperty("user.dir") + "/Autobiography list/autobiography.txt";
			FileInputStream fstream = new FileInputStream(wordListDirPath);
			// Get the words from file
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				storeWordList.add(strLine);
			}
			in.close();
		}
		catch (Exception e) {
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		} 
		 
		//Search for common nouns in file
		for (String key :commonNounList) {
		    if (!storeWordList.contains(key)) {
		        b2ErrorCount++;
		    }
		}

		//System.out.println(b2ErrorCount / (float)commonNounList.size());
		essay.getEssayScore().setTopicAdherenceScore(essay.getEssayScore().computeScore2b(b2ErrorCount,commonNounList.size()));
		
	}
	
	public void gradeTextCoherence(Essay essay){

		//2a		
		ArrayList<Parse> parse = essay.getParsedSentences();
						
		//get all pronouns in the sentence
		ArrayList<String> pronounTypes = PartOfSpeech.getPronounTypes();	
		ArrayList<Parse> allPronouns;
		ArrayList<Parse> allProperNouns;
		
		float errorcount_2a = 0;
		int bonus_2a = 0;
		int size = 0;
		
		ConcurrentLinkedQueue<EntityGen> entities = new ConcurrentLinkedQueue<EntityGen>();
		
		ArrayList<String> properNounTypes= new ArrayList<String>();
		properNounTypes.add(PartOfSpeech.NNP.toString());
		properNounTypes.add(PartOfSpeech.NNPS.toString());
			
		for(int i=0; i<parse.size();i++){
			
			allPronouns = getAllBFS(parse.get(i),pronounTypes);
			allProperNouns = getAllBFS(parse.get(i), properNounTypes);
				
			size = size + allPronouns.size();
			
			for(int j=0;j<allPronouns.size();j++){
				
				PosTag pronoun = new PosTag(allPronouns.get(j).toString(), getPOS(allPronouns.get(j).getType()));
				
				if(PartOfSpeech.getPersonType(pronoun.getPartOfSpeech(), pronoun.getString()) == Person.THIRD){
					//bonus
					bonus_2a = bonus_2a + 1;
				}
				else if(PartOfSpeech.getPersonType(pronoun.getPartOfSpeech(), pronoun.getString()) == Person.SECOND){
					//penalize and remove from list
					errorcount_2a = errorcount_2a + 1;
					allPronouns.remove(j);
				}				
				else {
					//remove from list
					allPronouns.remove(j);
				}
			}
			
			//queue of entities and their generation - 1,2,3....
			//remove from queue if generation > 2
			//if antecedent, remove entity from queue, re-add to queue with generation current
			//if pronoun without antecedent, penalise
			//ambiguous antecedent, less errornous
			
			for(EntityGen eg : entities){
				eg.setGeneration(eg.getGeneration() + 1);
								
				if(eg.getGeneration() > 2){					
					entities.remove(eg);					
				}				
			}				
			
			for(Parse pn : allProperNouns){
				
				for(EntityGen eg : entities){
				
					if(eg.getEntity().equals(pn.toString())){
						//System.out.println("removing - " + eg.getEntity());
						entities.remove(eg);						
					}		
				}
				
				entities.add(new EntityGen(pn.toString(), 0));		
			}
			
			for(int j=0;j<allPronouns.size();j++){

				Stack<EntityGen> antecedents = new Stack<EntityGen>();
				antecedents = hasAntecedent(allPronouns.get(j),entities);
				
				if(antecedents.isEmpty()){
					errorcount_2a = errorcount_2a + 1;
				}
				else if(antecedents.size() > 1){
					errorcount_2a = errorcount_2a + (1/(float)antecedents.size());
				}
				
				EntityGen candidateAntecedent = null;
				
				if(antecedents.size() > 0){
					candidateAntecedent = antecedents.firstElement();
					
					entities.remove(candidateAntecedent);

					EntityGen neweg = new EntityGen(candidateAntecedent.getEntity(),0); //current
					entities.add(neweg);
				}					
			}		
			
			/*for(EntityGen eg : entities){
				System.out.print(eg.getEntity() + "-" + eg.getGeneration() + " <- ");
			}
			
			System.out.print("\n");*/
		}
		
		//float total = bonus_2a/(float)parse.size() - errorcount_2a/(float)parse.size();
		//System.out.println(size);
		//System.out.println(bonus_2a);
		//System.out.println(errorcount_2a);
		essay.getEssayScore().setCoherenceScore(essay.getEssayScore().computeScore2a(errorcount_2a, size + bonus_2a));

	}
	
	private Stack<EntityGen> hasAntecedent(Parse parse, ConcurrentLinkedQueue<EntityGen> entities) {
		//TODO - gender of entities ????
		
		Stack<EntityGen> en = new Stack<EntityGen>();
		
		for(EntityGen eg : entities){
			en.add(eg);			
		}
		
		return en;
	}
}
