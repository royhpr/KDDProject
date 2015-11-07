package MainFolder;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class FeatureFilterController 
{
	private static FeatureFilterController instance;
	
	private int lowestCount = 434;
	
	private FeatureFilterController(){
		
	}
	
	public static FeatureFilterController initInstance(){
		if(instance == null){
			instance = new FeatureFilterController();
		}
		
		return instance;
	}
	
	//This will be called to filter irrelevant features
	public void filterFeatures(int ratio)
	{
		try{
			MessageController.logToConsole("Starting feature filtering...");
			
			//Let's run an Asyn background thread to do the filtering (prevent GUI from freezing)
			SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					//Our filter logic goes here....
					
					divideDataSetbasedOnPercentage(ratio, "msd_genre_dataset.txt", "TrainSet", "TestSet", ",", true);
					//processDataFile("msd_genre_dataset.txt", "processedData.csv", ",", true);
					// Here we can return some object of whatever type
					// we specified for the first template parameter.
					// (in this case we're auto-boxing 'true').
					return true;
				}

				//Can safely update the GUI from this method.
				protected void done() {
					boolean status = false;
					try {
						//Retrieve the return value of doInBackground.
						status = get();
						
						//Update necessary GUI upon completion
						MessageController.logToConsole("Test/Train Set generated!");
					} catch (InterruptedException ex) {
						// This is thrown if the thread's interrupted.
						
					} catch (ExecutionException ex) {
						// This is thrown if we throw an exception
						// from doInBackground.
						
					}
				}

				@Override
				// Can safely update the GUI from this method.
				protected void process(List<String> chunks) {
					// Here we receive the values that we publish().
					// They may come grouped in chunks.
					String mostRecentMessage = chunks.get(chunks.size()-1);
					
					//Update GUI component in the process, if necessary
					MessageController.logToConsole(mostRecentMessage);
				}
			};
			worker.execute();
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
	
	public void divideDataSetbasedOnPercentage(int division, String dataSetFileName, String trainSetFileName, 
			String testSetFileName, String delimeter, boolean withTitle)
	{
		//%genre	track_id	artist_name	title	loudness	tempo	time_signature	key	mode	duration	avg_timbre1	avg_timbre2	avg_timbre3	avg_timbre4	avg_timbre5	avg_timbre6	avg_timbre7	avg_timbre8	avg_timbre9	avg_timbre10	avg_timbre11	avg_timbre12	var_timbre1	var_timbre2	var_timbre3	var_timbre4	var_timbre5	var_timbre6	var_timbre7	var_timbre8	var_timbre9	var_timbre10	var_timbre11	var_timbre12

		BufferedReader br = null;		
		try 
		{
			String fileExtension = "";
			if (delimeter.equals(",")) fileExtension = ".csv";
			else fileExtension = ".txt";
			
			File trainSetfile = new File(trainSetFileName + (10 - division) + "0" + fileExtension);
			File testSetfile = new File(testSetFileName + division + "0"  + fileExtension);
			if (!trainSetfile.exists())	trainSetfile.createNewFile();
			if (!testSetfile.exists())	testSetfile.createNewFile();				
			FileWriter trainSetfilefw = new FileWriter(trainSetfile.getAbsoluteFile());
			FileWriter testSetfilefw = new FileWriter(testSetfile.getAbsoluteFile());
			BufferedWriter trainSetfilebw = new BufferedWriter(trainSetfilefw);
			BufferedWriter testSetfilebw = new BufferedWriter(testSetfilefw);				
			br = new BufferedReader(new FileReader(dataSetFileName));

			String[] firstLineArray = br.readLine().split("\t");
			String tempStr = firstLineArray[0];
			for (int i = 4; i < firstLineArray.length; i ++)
			{
				tempStr += delimeter + firstLineArray[i];
			}
			if (withTitle)
			{
				trainSetfilebw.write(tempStr + "\r");
				testSetfilebw.write(tempStr + "\r");
			}
			String sCurrentLine, newLineWithClass = "", trainSetClass = "", testSetClass = "";
			int trainClassCount = 0, testClassCount = 0; 
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				String[] featurevalue = new String[sCurrentLine.split("\t").length];
				featurevalue = sCurrentLine.split("\t");
				Random rand = new Random();
				
				//if (rand.nextInt(20) >= division) //Train Set
					if (rand.nextInt(20) >= 1) //Train Set
				{
					if (!featurevalue[0].equals(trainSetClass)) 
					{
						trainClassCount++;
						trainSetClass = featurevalue[0];
					}
					
					//newLineWithClass = Integer.toString(trainClassCount);
					newLineWithClass = featurevalue[0];;
					for (int i = 4; i < featurevalue.length; i++) 
						newLineWithClass += "," + featurevalue[i];
					newLineWithClass += "\r";
					
					trainSetfilebw.write(newLineWithClass);
				}
				else //Test Set
				{
					if (!featurevalue[0].equals(testSetClass)) 
					{
						testClassCount++;
						testSetClass = featurevalue[0];
					}
					
					//newLineWithClass = Integer.toString(testClassCount);
					newLineWithClass = featurevalue[0];
					for (int i = 4; i < featurevalue.length; i++) 
						newLineWithClass += "," + featurevalue[i];
					newLineWithClass += "\r";
					
					testSetfilebw.write(newLineWithClass);
				}
			}
			
			testSetfilebw.close();
			trainSetfilebw.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void processDataFile(String dataSetFileName, String newFileName, String delimeter, boolean withTitle)
	{
		//%genre	track_id	artist_name	title	loudness	tempo	time_signature	key	mode	duration	avg_timbre1	avg_timbre2	avg_timbre3	avg_timbre4	avg_timbre5	avg_timbre6	avg_timbre7	avg_timbre8	avg_timbre9	avg_timbre10	avg_timbre11	avg_timbre12	var_timbre1	var_timbre2	var_timbre3	var_timbre4	var_timbre5	var_timbre6	var_timbre7	var_timbre8	var_timbre9	var_timbre10	var_timbre11	var_timbre12

				BufferedReader br = null;		
				try 
				{
					File datasetfile = new File(newFileName);
					if (!datasetfile.exists())	datasetfile.createNewFile();				
					
					FileWriter datasetfilefw = new FileWriter(datasetfile.getAbsoluteFile());
				
					BufferedWriter datasetfilebw = new BufferedWriter(datasetfilefw);				
					br = new BufferedReader(new FileReader(dataSetFileName));
					

					
					String sCurrentLine, newLineWithClass = "", trainSetClass = "";
					int trainClassCount = 0; 
					
					while ((sCurrentLine = br.readLine()) != null) 
					{
						String[] featurevalue = new String[sCurrentLine.split("\t").length];
						featurevalue = sCurrentLine.split("\t");
						
						if (!featurevalue[0].equals(trainSetClass)) 
						{
							trainClassCount++;
							trainSetClass = featurevalue[0];
						}
						
						newLineWithClass = Integer.toString(trainClassCount);
						for (int i = 4; i < featurevalue.length; i++) 
							newLineWithClass += delimeter + featurevalue[i];
						newLineWithClass += "\r";
						 
						datasetfilebw.write(newLineWithClass);
					}
					
					datasetfilebw.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
	}
}
