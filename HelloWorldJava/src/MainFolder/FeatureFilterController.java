package MainFolder;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class FeatureFilterController {
	private static FeatureFilterController instance;
	
	private FeatureFilterController(){
		
	}
	
	public static FeatureFilterController initInstance(){
		if(instance == null){
			instance = new FeatureFilterController();
		}
		
		return instance;
	}
	
	//This will be called to filter irrelevant features
	public void filterFeatures(){
		try{
			MessageController.logToConsole("Starting feature filtering...");
			
			//Let's run an Asyn background thread to do the filtering (prevent GUI from freezing)
			SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					//Our filter logic goes here....
					
					
					
					
					
					
					//TODO remove it after testing
					// Simulate doing something useful.
					for (int i = 0; i <= 10; i++) {
						Thread.sleep(1000);

						// The type we pass to publish() is determined
						// by the second template parameter.
						publish(String.valueOf(i));
					}

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
						MessageController.logToConsole("Complete counting!");
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
}
