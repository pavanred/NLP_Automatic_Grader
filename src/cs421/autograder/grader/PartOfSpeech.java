package cs421.autograder.grader;

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
    COLON("mid sentence punctuation");
	
	private PartOfSpeech(final String description) {
        this.Description = description;
    }

    private final String Description;

    public String getDescription() {
        return this.Description;
    }
    
    public Person getPersonType(PartOfSpeech pos){    	
    	
    	//word = word.toLowerCase();
    	
    	if(pos == PartOfSpeech.VBZ || pos == PartOfSpeech.NN || pos == PartOfSpeech.NNP 
    			|| pos == PartOfSpeech.NNPS || pos == PartOfSpeech.NNS)
    		return Person.THIRD;
    	
    	else if(pos == PartOfSpeech.VBP)
    		return Person.NON_THIRD;
    	//TODO pronouns
    	/*else if(pos == PartOfSpeech.PRP && (word == "i" || word == "me"))
    		
    	else if(pos == PartOfSpeech.PRP$ && (word == "i" || word == "me"))*/
    		
    	else
    		return Person.NA;
    }
    
    public Case getCaseType(PartOfSpeech pos){
    	
    	if(pos == PartOfSpeech.POS || pos == PartOfSpeech.PRP$ || pos == PartOfSpeech.WP$)
    		return Case.POSSESSIVE;
    	else
    		return Case.NON_POSSESSIVE;
    }
    
    public Number getNumberType(PartOfSpeech pos){
    	
    	if(pos == PartOfSpeech.NN || pos == PartOfSpeech.NNP || 
    			pos == PartOfSpeech.VBP || pos == PartOfSpeech.VBZ)
    		return Number.SINGULAR;
    	
    	else if(pos == PartOfSpeech.NNS || pos == PartOfSpeech.NNPS)
    		return Number.PLURAL;
    	
    	else 
    		return Number.NA;    	
    }
    
    public Tense getTenseType(PartOfSpeech pos){
    	
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
    
    public Gender getGenderType(PartOfSpeech pos){
    	
    	//if(pos == PartOfSpeech.)
    	return null;
    }
}
