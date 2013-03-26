package cs421.autograder.evaluation;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScoreTest {

	private float epsilon = 0.0001f;
	
	@Test
	public void testFinalScoreWithoutRounding() {
		
		Score score  = new Score();
		
		score.setWordOrderScore(2);
		score.setSubjectVerbAgreementScore(2);
		score.setVerbUsageScore(1);
		score.setSentenceFormationScore(2);
		
		score.setCoherenceScore(4);
		score.setTopicAdherenceScore(2);
		
		score.setEssayLengthScore(1);
		
		assertEquals(2.0, score.getFinalScore(),epsilon);
	}
	
	@Test
	public void testFinalScoreWithRounding() {
		
		Score score  = new Score();
		
		score.setWordOrderScore(3);
		score.setSubjectVerbAgreementScore(3);
		score.setVerbUsageScore(3);
		score.setSentenceFormationScore(3);
		
		score.setCoherenceScore(3);
		score.setTopicAdherenceScore(1);
		
		score.setEssayLengthScore(3);
		
		assertEquals(2.5, score.getFinalScore(),epsilon);
	}

	@Test
	public void testFinalScoreEmpty() {
		
		Score score  = new Score();			
		assertEquals(0, score.getFinalScore(),epsilon);
	}
}
