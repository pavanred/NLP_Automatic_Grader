package cs421.autograder.grader;

import java.util.ArrayList;

public enum PartOfSpeech {
	
	//Penn TreeBank Part-Of-Speech Tags
	
	CC("Coordinating conjunction"),
    CD("Cardinal number"),
    DT("Determiner"),
    EX("Existential there"),
    FW("Foreign word"),
    IN("Preposition or subordinating conjunction"),
    JJ("Adjective"),
    JJR("Adjective, comparative"),
    JJS("Adjective, superlative"),
    LS("List item marker"),
    MD("Modal"),
    NN("Noun, singular or mass"),
    NNS("Noun, plural"),
    NNP("Proper noun, singular"),
    NNPS("Proper noun, plural"),
    PDT("Predeterminer"),
    POS("Possessive ending"),
    PRP("Personal pronoun"),
    PRP$("Possessive pronoun"),
    RB("Adverb"),
    RBR("Adverb, comparative"),
    RBS("Adverb, superlative"),
    RP("Particle"),
    SYM("Symbol"),
    TO("to"),
    UH("Interjection"),
    VB("Verb, base form"),
    VBD("Verb, past tense"),
    VBG("Verb, gerund or present participle"),
    VBN("Verb, past participle"),
    VBP("Verb, non-3rd person singular present"),
    VBZ("Verb, 3rd person singular present"),
    WDT("Wh-determiner"),
    WP("Wh-pronoun"),
    WP$("Possessive wh-pronoun"),
    WRB("Wh-adverb"),
    DOLLAR("Dollar"),
    POUND("Pound"),
    LQUOTE("Left quote"),
    RQUOTE("Right quote"),
    LBRACKET("Left parenthesis"),
    RBRACKET("Right parenthesis"),
    COMMA("Comma"),
    DOT("sentence final punctuation"),
    COLON("mid sentence punctuation"),
	
	NP("Noun Phrase"),
	VP("Verb Phrase"),
	S("Sentence"),
	SBAR("Sentence fragement"),
	TOP("Root element");
	
	private PartOfSpeech(final String description) {
        this.Description = description;
    }

    private final String Description;

    public String getDescription() {
        return this.Description;
    }
    
    public static Person getPersonType(PartOfSpeech pos, String word){    	
    	
    	if(pos == null || word == null)
    		return Person.NA;    	
    	
    	//first - i, me , my, mine, we, us
    	//second - you, your,  
    	//third he,she,her,him,it,them,they,their
    	
    	word = word.toLowerCase();
    	
    	if(pos == PartOfSpeech.VBZ || pos == PartOfSpeech.NN || pos == PartOfSpeech.NNP 
    			|| pos == PartOfSpeech.NNPS || pos == PartOfSpeech.NNS || word.equals("he") || word.equals("their")
    			|| word.equals("they") || word.equals("she") || word.equals("his") || word.equals("him") 
    			|| word.equals("her") || word.equals("it") || word.equals("them"))
    		return Person.THIRD;    	
    	
    	else if(pos == PartOfSpeech.VBP || word.equals("my") || word.equals("i") || word.equals("i'm") 
    			|| word.equals("me") || word.equals("we") || word.equals("mine") || word.equals("us"))
    		return Person.FIRST;
    	
    	else if (word.equals("you")|| word.equals("your"))
    		return Person.SECOND;
    	
    	else 
    		return Person.NA;
    }
    
    public static Case getCaseType(PartOfSpeech pos){
    	
    	if(pos == PartOfSpeech.POS || pos == PartOfSpeech.PRP$ || pos == PartOfSpeech.WP$)
    		return Case.POSSESSIVE;
    	else
    		return Case.NON_POSSESSIVE;
    }
    
    public static Number getNumberType(PartOfSpeech pos, String word){
    	
    	if(pos == PartOfSpeech.NN || pos == PartOfSpeech.NNP || 
    			pos == PartOfSpeech.VBP || pos == PartOfSpeech.VBZ || getEntityNumber(word) == 1)
    		return Number.SINGULAR;
    	
    	else if(pos == PartOfSpeech.NNS || pos == PartOfSpeech.NNPS || getEntityNumber(word) == 2)
    		return Number.PLURAL;
    	
    	else 
    		return Number.NA;    	
    }
    
    private static int getEntityNumber(String word) {
		
    	int number = 1; //single default
    	
    	ArrayList<EntityGen> entites = EntityGen.getEntityList();
    	
		for(int i=0; i<entites.size(); i++){
			if(entites.get(i).getEntity().equals(word)){
				number = entites.get(i).getNumber();
				break;
			}
		}
		
		return number;
	}

	public static Tense getTenseType(PartOfSpeech pos){
    	
    	if(pos == PartOfSpeech.VBD)
    		return Tense.PAST;
    	else if(pos == PartOfSpeech.VBG)
    		return Tense.PRESENT_PARTICIPLE;
    	else if (pos == PartOfSpeech.VBN)
    		return Tense.PAST_PARTICIPLE;
    	else if(pos == PartOfSpeech.VBP || pos == PartOfSpeech.VBZ)
    		return Tense.PRESENT;
    	else
    		return Tense.NA;    		
    }
    
    public static Gender getGenderType(String word){
    	
    	Gender gen;
    	
    	ArrayList<String> male = new ArrayList<String>();
    	male.add("he");male.add("his");male.add("him");
    	
    	ArrayList<String> female = new ArrayList<String>();
    	female.add("she");female.add("her");
    	
    	if(male.contains(word) || getEntityGender(word) == 1){
    		gen = Gender.MALE;
    	}
    	else if(female.contains(word) || getEntityGender(word) == 0){
    		gen = Gender.FEMALE;
    	}
    	else
    		gen = Gender.NA;
    		
    	return gen;
    }
    
    
    
    private static int getEntityGender(String word) {
		
    	int gender = 2; //true - male, false = female
    	
    	ArrayList<EntityGen> entites = EntityGen.getEntityList();
    	
		for(int i=0; i<entites.size(); i++){
			if(entites.get(i).getEntity().equals(word)){
				gender = entites.get(i).getGender();
				break;
			}
		}
		
		return gender;
	}

	public static ArrayList<String> getNounTypes(){
    	
    	ArrayList<String> nouns = new ArrayList<String>();
		nouns.add(PartOfSpeech.NN.toString());
		nouns.add(PartOfSpeech.NNP.toString());
		nouns.add(PartOfSpeech.NNPS.toString());
		nouns.add(PartOfSpeech.NNS.toString());
		
		return nouns;
    }
    
    public static ArrayList<String> getPronounTypes(){
    	
    	ArrayList<String> pronouns = new ArrayList<String>();
		pronouns.add(PartOfSpeech.PRP.toString());
		pronouns.add(PartOfSpeech.PRP$.toString());
		
		return pronouns;
    }
    
    public static ArrayList<String> getVerbTypes(){
    	
    	ArrayList<String> verbs = new ArrayList<String>();		
		verbs.add(PartOfSpeech.VB.toString());
		verbs.add(PartOfSpeech.VBD.toString());
		verbs.add(PartOfSpeech.VBG.toString());
		verbs.add(PartOfSpeech.VBN.toString());
		verbs.add(PartOfSpeech.VBP.toString());
		verbs.add(PartOfSpeech.VBZ.toString());
		
		return verbs;
    }
}
