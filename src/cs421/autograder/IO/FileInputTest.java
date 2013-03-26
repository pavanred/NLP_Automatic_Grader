package cs421.autograder.IO;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileInputTest {

	@Test
	public void testReadEssay1() {
		
		String filepath = System.getProperty("user.dir") + "/TrainingSet/1.txt";
		FileInput file;
		String rawText;
		
		rawText = "My name is Cici Maria " + 
				"I am from Mexico. " + 
				"My sister live in the U.S. " +
				"My fathers lives in Mexico " +
				"My live in Chicago come husband and children " + 
				"My baby Cherry the 3 years my husband his name is Ben the 27 years " +
				"I like Chicago for work ";	
		
		file = new FileInput();
		 
		assertEquals(rawText, file.readInputFile(filepath)) ;
	}
	
	@Test
	public void testReadEssay2() {
		
		String filepath = System.getProperty("user.dir") + "/TrainingSet/2.txt";
		FileInput file;
		String rawText;
		
		rawText = "My name is Angela " + 
				"I From Mexico " +
				"I came the U.S. in 1998 " + 
				"My husband and I We have 2 children " +
				"I not work now because I come to the school to learn English for help my children " +
				"I practice English at home I like second languages is very important for Future and Future for my children ";	
		
		file = new FileInput();
		 
		assertEquals(rawText, file.readInputFile(filepath)) ;	
	}
}
