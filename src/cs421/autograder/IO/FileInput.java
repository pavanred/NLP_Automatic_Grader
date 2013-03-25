package cs421.autograder.IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileInput {

	/**
	 * Reads the essay text from the input file
	 * @param filepath path to the input essay file
	 * @return essay text
	 * @author pavan
	 */
	public String readInputFile(String filepath) {
		
		BufferedReader reader;	
		StringBuilder essayText = new StringBuilder();
		
		try {
			
			reader = new BufferedReader(new FileReader(filepath));
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				essayText.append(currentLine + " ");					
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {

			System.out.println("[Error] Essay file not found");
			
		} catch (Exception e){
			
			System.out.println("[Error] Error reading the essay");
		}	
		
		return essayText.toString();
	}

}
