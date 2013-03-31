package cs421.autograder.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.csvreader.CsvWriter;

import cs421.autograder.evaluation.Score;

public class FileOutput {

	/**
	 * Writes essay scores to CSV output file
	 * @param filepath path to the CSV output file
	 * @return scores
	 * @author girish
	 */
	public void writeOutputFile(String filepath, Score scores) {
        
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
      
        				csvOutput.write(Integer.toString(scores.getWordOrderScore()));
              			csvOutput.write(Integer.toString(scores.getSubjectVerbAgreementScore()));
              			csvOutput.write(Integer.toString(scores.getVerbUsageScore()));
            			csvOutput.write(Integer.toString(scores.getSentenceFormationScore()));
              			csvOutput.write(Integer.toString(scores.getCoherenceScore()));
            			csvOutput.write(Integer.toString(scores.getTopicAdherenceScore()));
                    	csvOutput.write(Integer.toString(scores.getEssayLengthScore()));
            			csvOutput.write(Float.toString(scores.getFinalScore()));
        				csvOutput.endRecord();
           				csvOutput.close();
        }
        catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
