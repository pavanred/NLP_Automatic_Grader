package cs421.autograder.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.csvreader.CsvWriter;

public class FileOutput {

	/**
	 * Writes essay scores to CSV output file
	 * @param filepath path to the CSV output file
	 * @return scores
	 * @author girish
	 */
	public String readInputFile(String filepath, Score scores) {
        
        boolean alreadyExists = new File(filepath).exists();
        
        try {
        	// use FileWriter constructor that specifies open for appending
        	CsvWriter csvOutput = new CsvWriter(new FileWriter(filepath, true), ',');
        
        	
        	// if the file didn't already exist then we need to write out the header line
        				if (!alreadyExists)
        				{
        					csvOutput.write("1a");
        					csvOutput.write("1b");
        					csvOutput.write("1c");
        					csvOutput.write("1d");
        					csvOutput.write("2a");
        					csvOutput.write("2b");
        					csvOutput.write("3");
        					csvOutput.endRecord();
        				}
        				// else assume that the file already has the correct header line
        				
        				// write out a new records
      
        				csvOutput.write(scores.getWordOrderScore());
              			csvOutput.write(scores.getSubjectVerbAgreementScore());
              			csvOutput.write(scores.getVerbUsageScore());
            			csvOutput.write(scores.getSentenceFormationScore());
              			csvOutput.write(scores.getCoherenceScore());
            			csvOutput.write(scores.getTopicAdherenceScore());
                    	csvOutput.write(scores.getEssayLengthScore());
            			csvOutput.write(scores.getFinalScore());
        				csvOutput.endRecord();
           				csvOutput.close();
        }
        catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
