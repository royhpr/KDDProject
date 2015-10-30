package MainFolder;

public class TrainController {
	private static TrainController instance;
	
	private TrainController(){
		
	}

	public static TrainController initInstance(){
		if(instance == null){
			instance = new TrainController();
		}
		
		return instance;
	}
	
	//Train process happen here...
	public void TrainDataset(){
		try{
			//Pass in the real training dataset
			findSuitableK(new String[]{"Hello", "World"});
			
			//Pass in the 'k' and 'query string'
			findKNN(2, "anything");
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
	 
	//Cross validate the train dataset to find suitable K
	private String[] findSuitableK(String[] trainingDataSet){
		try{
			
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
		
		return new String[]{"neighbor1", "neighbor2", "neighbor3"};
	}
	
	//Find K-near neighbours
	private String[] findKNN(int k, String query){
		try{
			
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
		
		return new String[]{"Hello", "World"};
	}
}
