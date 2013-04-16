package cs421.autograder.grader;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

public class AutoGraderTest {

	AutoGrader grader = new AutoGrader();
	
	/*@Test
	public void testSegmentEmpty() {

		Essay essay, expected;
		
		essay = new Essay();		
		
		grader.segmentEssay(essay);
		 
		expected = new Essay();
		
		assertEquals(expected, essay);	
	}*/
	
	/*@Test
	public void testSegmentEssay2() {

		Essay actual, expected;
		
		actual = new Essay();
		
		String rawText = "My name is Angela " + 
				"I From Mexico " +
				"I came the U.S. in 1998 " + 
				"My husband and I We have 2 children " +
				"I not work now because I come to the school to learn English for help my children " +
				"I practice English at home I like second languages is very important for Future and Future for my children ";	
		
		actual.setRawText(rawText);
		
		ArrayList<String> sentences = new ArrayList<String>();
		sentences.add("My name is Angela");
		sentences.add("I From Mexico");
		sentences.add("I came the U.S. in 1998");
		sentences.add("My husband and I We have 2 children");
		sentences.add("I not work now because I come to the school to learn English for help my children");
		sentences.add("I practice English at home I like second languages is very important for Future and Future for my children");
		
		expected = new Essay();		
		expected.setSentences(sentences);
		
		AutoGrader grader = new AutoGrader();
		grader.segmentEssay(actual);
		 
		assertEquals(expected, actual);	
	}*/
	
	@Test
	public void testGetPOS() {
		
		assertEquals(PartOfSpeech.NN, grader.getPOS("NN"));				
	}
	
	@Test
	public void testGetPOSSymbol() {		
		assertEquals(PartOfSpeech.LBRACKET, grader.getPOS("("));				
	}
	
	@Test
	public void testGetStanfordPosTags(){
		
		ArrayList<PosTag> actualTags = grader.getStanfordPosTags("My dog also likes eating sausage, bread.");
		ArrayList<PosTag> expectedTags = new ArrayList<PosTag>();
		expectedTags.add(new PosTag("My", PartOfSpeech.PRP$));
		expectedTags.add(new PosTag("dog", PartOfSpeech.NN));
		expectedTags.add(new PosTag("also", PartOfSpeech.RB));
		expectedTags.add(new PosTag("likes", PartOfSpeech.VBZ));
		expectedTags.add(new PosTag("eating", PartOfSpeech.JJ));
		expectedTags.add(new PosTag("sausage", PartOfSpeech.NN));
		expectedTags.add(new PosTag(",", PartOfSpeech.COMMA));
		expectedTags.add(new PosTag("bread", PartOfSpeech.NN));
		expectedTags.add(new PosTag(".", PartOfSpeech.DOT));
		
		assertEquals(expectedTags, actualTags);
	}
	
	@Test
	public void testGetOpennlpPosTags(){
		ArrayList<PosTag> actualTags = grader.getOpennlpPosTags("My dog also likes eating sausage, bread.".split(" "));
		ArrayList<PosTag> expectedTags = new ArrayList<PosTag>();
		expectedTags.add(new PosTag("My", PartOfSpeech.PRP$));
		expectedTags.add(new PosTag("dog", PartOfSpeech.NN));
		expectedTags.add(new PosTag("also", PartOfSpeech.RB));
		expectedTags.add(new PosTag("likes", PartOfSpeech.VBZ));
		expectedTags.add(new PosTag("eating", PartOfSpeech.VBG));
		expectedTags.add(new PosTag("sausage", PartOfSpeech.NN));
		expectedTags.add(new PosTag("bread", PartOfSpeech.NN));
		
		assertEquals(expectedTags, actualTags);
	}

}
